package betterthandeer.btd.entity.rock;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class ProjectileRock extends Projectile {
	public ProjectileRock(World world) {
		super(world);
	}

	public ProjectileRock(World world, Mob owner) {
		super(world, owner);
	}

	public ProjectileRock(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void initProjectile() {
		super.initProjectile();
		this.damage = 1;
	}

	@Override
	public void onHit(@NonNull HitResult hitResult) {
		if (hitResult instanceof HitResult.Entity hitEntity) {
			hitEntity.entity.hurt(this.owner, this.damage, DamageType.FIRE);
		}

		if (!this.world.isClientSide) {
			EntityItem item = new EntityItem(this.world, this.x, this.y, this.z, new ItemStack(BTDItems.AMMO_ROCK, 1));
			this.world.entityJoinedWorld(item);
		}

		this.remove();
	}
}
