package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class AcidArmorMeltMixin extends Entity {

	@Shadow
	public int hurtTime;

	@Unique
	private int acidSoundCooldown = 0;

	protected AcidArmorMeltMixin(World world) {
		super(world);
	}

	@Unique
	private boolean isInAcid() {
		return this.world.isMaterialInBB(this.bb, BTDBlocks.ACID);
	}

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void acidArmorDamage(CallbackInfo ci) {
		if (this.world.isClientSide) return;
		if (this.noPhysics) return;

		if (this instanceof IArmorWearing && this.isInAcid()) {
			((IArmorWearing<?>) this).damageArmor(2);
			if (acidSoundCooldown > 0) {
				acidSoundCooldown--;
			}

			if (this.hurtTime == 0 && acidSoundCooldown == 0) {
				world.playSoundAtEntity(null, this, "random.fizz", 0.1f, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
				acidSoundCooldown = 25;
			}
		} else {
			acidSoundCooldown = 0;
		}
	}
}
