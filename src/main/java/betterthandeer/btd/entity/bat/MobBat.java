package betterthandeer.btd.entity.bat;

import betterthandeer.btd.BTDItems;
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
import net.minecraft.core.world.season.Seasons;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.Nullable;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class MobBat extends MobFlying implements Enemy {
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private int flapTimer;

	@Nullable
	private Entity target;

	public MobBat(@Nullable World world) {
		super(world);
		this.setTextureIdentifier(MOD_ID, "bat");
		this.setSize(1.0F, 1.0F);
		this.scoreValue = 200;
		this.moveSpeed = 0.25F;
		this.flapTimer = this.random.nextInt(4);
		this.mobDrops.add(new WeightedRandomLootObject(BTDItems.EYE_BAT.getDefaultStack(), 0, 2));
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	public int getMaxHealth() {
		return 16;
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
			this.remove();
		}

		this.flapTimer++;
		if (this.flapTimer >= 4 && this.isAlive()) {
			world.playSoundAtEntity(null, this, "btd:mob.batflap", 0.25F, (random.nextFloat() / 2) + 1.5F);
			this.flapTimer = 0;
		}
		this.onGround = false;
	}

	@Override
	protected void updateAI() {
		if (!world.getDifficulty().canHostileMobsSpawn()) {
			this.remove();
			return;
		}

		if (target == null || !target.isAlive() || !(target instanceof Player)) {
			Player player = world.getClosestPlayerToEntity(this, 24.0);
			if (player != null && player.getGamemode().hasHostileMobs()) {
				target = player;
			}
		}

		if (target != null) {
			attackMovement();
		} else {
			idleFlight();
		}
	}

	private void idleFlight() {
		double dx = waypointX - x;
		double dy = waypointY - y;
		double dz = waypointZ - z;
		double dist = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);

		if (dist < 1.0 || dist > 20.0) {
			waypointX = x + (random.nextFloat() * 2 - 1);
			waypointY = y + (random.nextFloat() * 2 - 1);
			waypointZ = z + (random.nextFloat() * 2 - 1);
		}

		if (courseChangeCooldown-- <= 0) {
			courseChangeCooldown = random.nextInt(5) + 2;

			if (this.isCourseTraversable(waypointX, waypointY, waypointZ, dist)) {
				xd += dx / dist * 0.08;
				yd += dy / dist * 0.08;
				zd += dz / dist * 0.08;
			} else {
				waypointX = x;
				waypointY = y;
				waypointZ = z;
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

		if (this.attackTime <= 0 && dist < 2.5F) {
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
		return "btd:mob.bat";
	}

	@Override
	protected String getHurtSound() {
		return "btd:mob.bathurt";
	}

	@Override
	protected String getDeathSound() {
		return "btd:mob.batdeath";
	}

}
