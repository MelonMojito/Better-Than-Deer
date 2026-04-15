package betterthandeer.btd.entity;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class ProjectileArrowFlaming extends ProjectileArrow {
	public ProjectileArrowFlaming(World world) {
		super(world, 4);
		this.stack = new ItemStack(BTDItems.AMMO_ARROW_FLAMING);
		this.entityBrightness = 1.0F;
	}

	public ProjectileArrowFlaming(World world, double x, double y, double z) {
		super(world, x, y, z, 4);
		this.stack = new ItemStack(BTDItems.AMMO_ARROW_FLAMING);
		this.entityBrightness = 1.0F;
	}

	public ProjectileArrowFlaming(World world, Mob owner, boolean doesArrowBelongToPlayer) {
		super(world, owner, doesArrowBelongToPlayer, 4);
		this.stack = new ItemStack(BTDItems.AMMO_ARROW_FLAMING);
		this.entityBrightness = 1.0F;
	}

	@Override
	protected void initProjectile() {
		super.initProjectile();
		this.damage = 6;
		this.entityBrightness = 1.0F;
	}

	@Override
	public float getBrightness(float partialTick) {
		return 10.0F;
	}

	@Override
	public float calcBrightness(float partialTick) {
		return 1.0F;
	}

	@Override
	public byte calcLightIndex(float partialTick) {
		return LightIndexHelper.setBlockLight(super.calcLightIndex(partialTick), 15);
	}

	@Override
	public void tick() {
		world.spawnParticle("flame", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("flame", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("smoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("smoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		super.tick();
	}

	@Override
	public void onHit(@NonNull HitResult hitResult) {
		if (hitResult instanceof HitResult.Entity hitEntity) {
			if (hitEntity.entity instanceof MobZombiePig || hitEntity.entity instanceof MobGargoyle) {
				hitEntity.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			} else if (hitEntity.entity.hurt(this.owner, this.damage, DamageType.FIRE)) {
				if (hitEntity.entity instanceof MobCreeper entityCreeper) {
					entityCreeper.setTarget(entityCreeper);
				}
				hitEntity.entity.fireHurt();

				if (!this.world.isClientSide) {
					this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				}
			}
		}
		this.remove();

		super.onHit(hitResult);

	}

	@Override
	protected void inGroundAction() {
		if (!this.world.isClientSide) {
			if (this.tilePos != null) {
				int fireX = tilePos.x;
				int fireY = tilePos.y + 1;
				int fireZ = tilePos.z;

				if (world.isAirBlock(fireX, fireY, fireZ)) {
					world.setBlockWithNotify(fireX, fireY, fireZ, Blocks.FIRE.id());
				}
			}
		}

		world.spawnParticle("explode", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("explode", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("ventsmoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.spawnParticle("ventsmoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0, false);
		world.playSoundAtEntity(null, this, "random.fizz", 0.1f, (random.nextFloat() * 1.4F + 0.8F));

		super.inGroundAction();
	}
}
