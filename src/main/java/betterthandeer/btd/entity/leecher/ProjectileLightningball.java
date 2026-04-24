package betterthandeer.btd.entity.leecher;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

import java.util.List;

public class ProjectileLightningball extends Projectile {
	private static final float HOMING_POWER = 0.15F;
	private static final float TOP_SPEED = 0.5F;
	private Mob target;

	public ProjectileLightningball(World world) {
		super(world);
		this.initProjectile();
	}

	public ProjectileLightningball(World world, Mob owner) {
		super(world, owner);
		this.initProjectile();
	}

	public ProjectileLightningball(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.initProjectile();
	}

	@Override
	public void initProjectile() {
		this.damage = 4;
		this.defaultGravity = 0.0F;
		this.defaultProjectileSpeed = 1.0F;
		this.setSize(1.0F, 1.0F);
	}

	@Override
	public void tick() {
		++this.ticksInAir;
		if (ticksInAir > 100) {
			this.remove();
			world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, (random.nextFloat() * 1.4F + 1.8F));
			return;
		}

		findTarget();
		applyHoming();

		super.tick();

	}

	private void findTarget() {
		if (this.target == null || !this.target.isAlive()) {
			AABBdc searchBox = new AABBd(this.x - 16.0, this.y - 16.0, this.z - 16.0, this.x + 16.0, this.y + 16.0, this.z + 16.0);
			List<Mob> entities = this.world.getEntitiesWithinAABB(Mob.class, searchBox);
			Player closestPlayer = null;
			for (Mob entity : entities) {
				if (entity instanceof Player player && entity.isAlive() && this.distanceTo(entity) < 32.0f) {
					closestPlayer = player;
				}

			}
			this.target = closestPlayer;
		}
	}

	private void applyHoming() {
		if (this.target != null && this.target.isAlive()) {
			double dx = this.target.x - this.x;
			double dy = this.target.y + this.target.getHeadHeight() - this.y;
			double dz = this.target.z - this.z;
			double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (dist > 0) {
				this.xd += (dx / dist * TOP_SPEED - this.xd) * HOMING_POWER;
				this.yd += (dy / dist * TOP_SPEED - this.yd) * HOMING_POWER;
				this.zd += (dz / dist * TOP_SPEED - this.zd) * HOMING_POWER;

				double speed = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
				if (speed > TOP_SPEED) {
					this.xd = this.xd / speed * TOP_SPEED;
					this.yd = this.yd / speed * TOP_SPEED;
					this.zd = this.zd / speed * TOP_SPEED;
				}
			}
		}
	}

	@Override
	public void onHit(@NonNull HitResult result) {
		if (this.tickCount > 5 && !this.world.isClientSide && result instanceof HitResult.Entity hitEntity) {
			if (hitEntity.entity instanceof MobLeecher) {
			} else {
				hitEntity.entity.hurt(this.owner, this.damage, DamageType.GENERIC);
				this.remove();
			}
		}
	}

	@Override
	public void afterTick() {
		this.x += this.xd;
		this.y += this.yd;
		this.z += this.zd;
		this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

		if (this.isInWater()) this.waterTick();
		this.setPos(this.x, this.y, this.z);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public float getPickRadius() {
		return 1.0F;
	}

	@Override
	public boolean hurt(Entity entity, int damage, DamageType type) {
		return false;
	}

}
