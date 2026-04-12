package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class AcidArmorMeltMixin extends Entity {

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

		if (this instanceof IArmorWearing && this.isInAcid()) {
			((IArmorWearing<?>) this).damageArmor(2);
		}
	}
}
