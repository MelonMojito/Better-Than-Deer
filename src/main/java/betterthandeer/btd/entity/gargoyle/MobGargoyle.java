package betterthandeer.btd.entity.gargoyle;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.Nullable;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class MobGargoyle extends MobFlying implements Enemy {
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private int flapTimer;
	public boolean isHanging = false;

	@Nullable
	private Entity target;

	public MobGargoyle(@Nullable World world) {
		super(world);
		this.setTextureIdentifier(MOD_ID, "gargoyle");
		this.setSize(1.0F, 1.5F);
		this.scoreValue = 200;
		this.moveSpeed = 0.25F;
		this.flapTimer = this.random.nextInt(4);
		this.fireImmune = true;
		this.mobDrops.add(new WeightedRandomLootObject(BTDItems.EYE_GARGOYLE.getDefaultStack(), 0, 2));
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public int getMaxHealth() {
		return 20;
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
	public void tick() {
		if (!isHanging) {
			this.flapTimer++;
			if (this.flapTimer >= 4 && this.isAlive()) {
				world.playSoundAtEntity(null, this, "btd:mob.gargoyleflap", 0.15F, (random.nextFloat() / 2) + 1.5F);
				this.flapTimer = 0;
			}
		} else {
			this.xd = this.yd = this.zd = 0;
		}

		super.tick();
		if (!isHanging && random.nextInt(12) == 0) {
			this.yd += 0.018;
			this.xd += (2 * random.nextDouble() - 1) / 16.0;
			this.zd += (2 * random.nextDouble() - 1) / 16.0;
		}

		if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
			this.remove();
		}


		this.onGround = false;
	}


	@Override
	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (type == DamageType.FIRE) {
			return false;
		}

		if (super.hurt(attacker, i, type)) {
			if (this.passenger != attacker && this.vehicle != attacker && attacker != this) {
				if (this.isHanging) {
					this.isHanging = false;
				}
				this.target = attacker;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void updateAI() {
		Player player;
		if (isHanging) {
			player = world.getClosestPlayerToEntity(this, 16.0);
		} else {
			player = world.getClosestPlayerToEntity(this, 32.0);
		}

		if (isHanging) {
			if (player != null && player.getGamemode().hasHostileMobs() && this.canEntityBeSeen(player) && !player.isSneaking()) {
				isHanging = false;
				target = player;
			}

			TilePos posAbove = new TilePos(MathHelper.floor(x), MathHelper.floor(y + 1.6), MathHelper.floor(z));
			if (world.isAirBlock(posAbove)) {
				isHanging = false;
			}

			return;
		}

		if (target != null && (!target.isAlive() || this.distanceTo(target) > 32.0)) {
			target = null;
		}


		if ((target == null || !target.isAlive() || !(target instanceof Player)) && player != null && player.getGamemode().hasHostileMobs() && this.canEntityBeSeen(player)) {
			target = player;
		}


		if (target != null) {
			attackMovement();
		} else {
			if (random.nextInt(5) == 0) {
				TilePos posAbove = new TilePos(MathHelper.floor(x), MathHelper.floor(y + 1.6), MathHelper.floor(z));
				if (!world.isAirBlock(posAbove)) {
					isHanging = true;
				}
			}
			idleFlight();
		}
	}

	private void idleFlight() {
		double dx = waypointX - x;
		double dy = waypointY - y;
		double dz = waypointZ - z;
		double dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

		if (dist < 1.5 || dist > 25.0) {
			double upwardBias = random.nextFloat() * 2.2;
			double range = 8.0 + random.nextDouble() * 12.0;

			waypointX = x + (random.nextDouble() * 2.0 - 1.0) * range;
			waypointY = y + upwardBias;
			waypointZ = z + (random.nextDouble() * 2.0 - 1.0) * range;
		}

		if (courseChangeCooldown-- <= 0) {
			courseChangeCooldown = random.nextInt(6) + 3;

			if (this.isCourseTraversable(waypointX, waypointY, waypointZ, dist)) {
				double speed = 0.085;

				xd += dx / dist * speed;
				yd += dy / dist * speed;
				zd += dz / dist * speed;
			} else {
				waypointX = x + (random.nextDouble() * 4.0 - 2.0);
				waypointY = y + 2.0 + random.nextFloat() * 3.0;
				waypointZ = z + (random.nextDouble() * 4.0 - 2.0);
			}
		}

		faceVelocity();
	}

	private boolean isCourseTraversable(double targetX, double targetY, double targetZ, double distance) {
		double stepX = (this.waypointX - this.x) / distance;
		double stepY = (this.waypointY - this.y) / distance;
		double stepZ = (this.waypointZ - this.z) / distance;
		AABBd aabb = new AABBd(this.bb);

		for (int i = 1; (double) i < distance; ++i) {
			aabb.translate(stepX, stepY, stepZ);
			if (!this.world.areBlocksLoaded(aabb) || !this.world.getCubes(this, aabb).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private void attackMovement() {
		Vector3dc look = target.getViewVector(1.0F);
		double offset = 1.8;
		double targetX = target.x + look.x() * offset;
		double targetY = target.y + target.getHeadHeight() * 0.6;
		double targetZ = target.z + look.z() * offset;

		double dx = targetX - x;
		double dy = targetY - y;
		double dz = targetZ - z;
		double dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

		if (courseChangeCooldown-- <= 0) {
			courseChangeCooldown = random.nextInt(3) + 1;
			xd += dx / dist * 0.18;
			yd += dy / dist * 0.18;
			zd += dz / dist * 0.18;
		}

		lookAt(target, 40.0F, 40.0F);

		if (this.attackTime <= 0 && dist < 2.0F) {
			this.attackTime = 25;
			target.hurt(this, 2, DamageType.COMBAT);

		}
	}


	private void faceVelocity() {
		this.yRot = this.yBodyRot = -((float) Math.atan2(this.xd, this.zd)) * 180.0F / (float) Math.PI;
	}

	@Override
	protected boolean makeStepSound() {
		return false;
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
	public String getLivingSound() {
		return "btd:mob.gargoyle";
	}

	@Override
	protected String getHurtSound() {
		return "btd:mob.gargoylehurt";
	}

	@Override
	protected String getDeathSound() {
		return "btd:mob.gargoyledeath";
	}

}
