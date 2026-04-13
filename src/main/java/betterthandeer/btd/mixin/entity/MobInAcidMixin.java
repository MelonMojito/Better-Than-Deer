package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobInAcidMixin extends Entity {

	@Shadow
	protected boolean isJumping;

	@Shadow
	protected abstract void jump();

	protected MobInAcidMixin(World world) {
		super(world);
	}

	@Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
	private void onGetDeathMessage(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
		if (this.world.isMaterialInBB(this.bb, BTDBlocks.ACID)) {
			String name = Entity.getNameFromEntity(this, true);
			cir.setReturnValue(String.format("%s%s melted in acid.", name, TextFormatting.RED));
		}
	}


	@Unique
	public boolean isInAcid() {
		return this.world.isMaterialInBB(this.bb, BTDBlocks.ACID);
	}

	@Inject(method = "updateAI", at = @At("TAIL"))
	private void updateAI(CallbackInfo ci) {
		boolean inAcid = this.isInAcid();
		if (inAcid) {
			this.isJumping = this.random.nextFloat() < 0.8F;
		}
	}

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void onLivingUpdate(CallbackInfo ci) {
		boolean inAcid = this.isInAcid();
		if (this.isJumping) {
			if (inAcid) {
				this.yd += 0.04;
			} else if (this.onGround) {
				this.jump();
			}
		}
	}

	@Redirect(method = "moveEntityWithHeading", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isInLava()Z"))
	private boolean redirectIsInLava(Mob mob) {
		return mob.isInLava() ||  isInAcid();
	}
}
