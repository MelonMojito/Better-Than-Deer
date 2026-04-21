package betterthandeer.btd.mixin;

import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.entity.projectile.ProjectileCannonball;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProjectileCannonball.class)
public abstract class ProjectileCannonballMixin extends Projectile {
	protected ProjectileCannonballMixin(@NonNull World world) {
		super(world);
	}

	@Override
	public void onHit(@NotNull HitResult hitResult) {
		this.world.createExplosion(this.owner, this.x, this.y + (double) (this.bbHeight / 2.0F), this.z, 1.5F, false, true);
		this.remove();
	}

}
