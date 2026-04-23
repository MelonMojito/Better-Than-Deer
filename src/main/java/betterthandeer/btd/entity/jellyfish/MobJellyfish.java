package betterthandeer.btd.entity.jellyfish;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class MobJellyfish extends MobMonster {
	public float xBodyRot = 0.0F;
	public float xBodyRotO = 0.0F;
	public float zBodyRot = 0.0F;
	public float zBodyRotO = 0.0F;
	public float tentacleMovement = 0.0F;
	public float oldTentacleMovement = 0.0F;
	public float tentacleAngle = 0.0F;
	public float oldTentacleAngle = 0.0F;
	private float _speed = 0.0F;
	private float tentacleSpeed;
	private float rotateSpeed = 0.0F;
	private float tx = 0.0F;
	private float ty = 0.0F;
	private float tz = 0.0F;

	public MobJellyfish(World world) {
		super(world);
		this.setTextureIdentifier("btd", "jellyfish");
		this.setSize(1.0F, 2.0F);
		this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
		this.mobDrops.add(new WeightedRandomLootObject(Items.RUBYGLASS_CRYSTAL.getDefaultStack(), 1, 3));
		this.fireImmune = true;
	}

	@Override
	public boolean canSpawnHere() {
		TilePos tilePos = new TilePos(this);
		if (!this.world.isAirBlock(tilePos)) {
			return false;
		} else {
			Block<?> below = this.world.getBlockType(tilePos.down(new TilePos()));
			if (!BlockTags.NETHER_MOBS_SPAWN.appliesTo(below)) {
				return false;
			} else {
				int blockLight = this.world.getSavedLightValue(LightLayer.Block, tilePos);
				if (blockLight > 7) {
					return false;
				} else if (!this.world.areBlocksLoaded(new TilePos(this.bb.minX - (double) 1.0F, this.bb.minY - (double) 1.0F, this.bb.minZ - (double) 1.0F), new TilePos(this.bb.maxX + (double) 1.0F, this.bb.maxY + (double) 1.0F, this.bb.maxZ + (double) 1.0F))) {
					return false;
				} else {
					return this.world.getDifficulty().canHostileMobsSpawn() && this.world.checkIfAABBIsClear(this.bb) && this.world.getCubes(this, this.bb).isEmpty() && !this.world.getIsAnyLiquid(this.bb);
				}
			}
		}
	}

	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (type == DamageType.FIRE) {
			return false;
		}
		return super.hurt(attacker, i, type);
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	public void tick() {
		super.tick();
		this.remainingFireTicks = 0;
	}

	@Override
	protected void attackEntity(@NonNull Entity entity, float distance) {
//		if (distance < 10.0F) {
//			double d = entity.x - this.x;
//			double d1 = entity.z - this.z;
//			if (this.attackTime == 0) {
//				if (!this.world.isClientSide) {
//					ProjectileLightningball elementLightning = new ProjectileLightningball(this.world, this);
//					elementLightning.setHeading(world.rand.nextDouble(), this.getLookingTilt() + 5.0F, world.rand.nextDouble(), 0.5F, 0.0F);
//					this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() + this.random.nextFloat()) * 1.2F + 1.0F);
//					elementLightning.moveTo(this.x, this.y - 1, this.z, 0.0F, 0.0F);
//					this.world.entityJoinedWorld(elementLightning);
//				}
//				this.attackTime = 80;
//			}
//			this.yRot = (float) (Math.atan2(d1, d) * (double) 180.0F / Math.PI) - 90.0F;
//			this.hasAttacked = true;
//		}
	}

	@Override
	protected void causeFallDamage(float distance) {
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (!this.world.isClientSide) {
			double pillarHeight = 8.0;

			AABBd pillarHitbox = new AABBd(this.x - 1, this.y - pillarHeight, this.z - 1, this.x + 1, this.y, this.z + 1);
			List<Mob> targets = this.world.getEntitiesWithinAABB(Mob.class, pillarHitbox);

			for (Mob entity : targets) {
				if (!(entity instanceof MobJellyfish) && entity.isAlive() && entity.hurt(this, 2, DamageType.COMBAT)) {
					entity.hurtTime = entity.maxHurtTime = 1;
				}
			}
		}

		Direction direction = Direction.getDirectionById(0);
		TilePos checkPos = new TilePos((int) x, (int) y, (int) z).add(direction);
		if (world.isAirBlock(checkPos.x, checkPos.y, checkPos.z)) {
			world.spawnParticle("jellyfishLightning", this.x, this.y - 1, this.z, 0.0F, 0.0F, 0.0F, 0, 1600.0F, false);
		}

		world.spawnParticle("reddust", this.x, this.y + 1.0, this.z, 0.0F, -15.0F, 0.0F, 15, false);

		this.xBodyRotO = this.xBodyRot;
		this.zBodyRotO = this.zBodyRot;
		this.oldTentacleMovement = this.tentacleMovement;
		this.oldTentacleAngle = this.tentacleAngle;
		this.tentacleMovement += this.tentacleSpeed;

		if (this.tentacleMovement > ((float) Math.PI * 2F)) {
			this.tentacleMovement = 0.0F;
			this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
		}

		if (this.tentacleMovement < (float) Math.PI) {
			float f = this.tentacleMovement / (float) Math.PI;
			this.tentacleAngle = MathHelper.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
			this._speed = 1.0F;
			this.rotateSpeed = 1.0F;
		}

		if (!this.isMultiplayerEntity) {
			float smoothFactor = 0.08F;

			this.xd += (this.tx * this._speed - this.xd) * smoothFactor;
			this.yd += (this.ty * this._speed - this.yd) * smoothFactor;
			this.zd += (this.tz * this._speed - this.zd) * smoothFactor;
		}

		this.zBodyRot += (float) Math.PI * this.rotateSpeed * 1.5F;

	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		this.move(this.xd, this.yd, this.zd);
	}

	@Override
	protected void updateAI() {
		Entity target = findPlayerToAttack();

		if (target != null && this.canEntityBeSeen(target)) {
			this.target = target;

			double hoverHeight = 5.0;
			double dX = target.x - this.x;
			double dY = (target.y + hoverHeight) - this.y;
			double dZ = target.z - this.z;
			float dist = MathHelper.sqrt(dX * dX + dY * dY + dZ * dZ);

			if (dist > 1.0F) {
				this.tx = (float) (dX / dist) * 0.2F;
				this.ty = (float) (dY / dist) * 0.2F;
				this.tz = (float) (dZ / dist) * 0.2F;
			} else {
				this.tx *= 0.5F;
				this.ty *= 0.5F;
				this.tz *= 0.5F;
			}

			this.attackEntity(target, dist);
		} else {
			if (this.random.nextInt(50) == 0 || this.tx == 0.0F) {
				float f = this.random.nextFloat() * (float) Math.PI * 2.0F;
				this.tx = MathHelper.cos(f) * 0.1F;
				this.ty = -0.1f + this.random.nextFloat() * 0.2f;
				this.tz = MathHelper.sin(f) * 0.1F;
			}
		}

		super.updateAI();
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean hurtByNetherWater() {
		return false;
	}

	@Override
	public float calcBrightness(float partialTick) {
		return 1.0F;
	}

	@Override
	public byte calcLightIndex(float partialTick) {
		return LightIndexHelper.lightIndex2i(15, 15);
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}

}
