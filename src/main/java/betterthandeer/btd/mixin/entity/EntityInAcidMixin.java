package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityInAcidMixin {

	@Shadow
	public abstract boolean hurt(Entity attacker, int baseDamage, DamageType type);

	@Shadow
	@NonNull
	public World world;

	@Shadow
	@Final
	@NonNull
	public AABBd bb;

	@Inject(method = "baseTick", at = @At("TAIL"))
	public void baseTick(CallbackInfo ci) {
		if (this.isInAcid()) {
			this.acidHurt();
		}
	}

	@Unique
	public void acidHurt() {
		this.hurt(null, 1, DamageType.GENERIC);
	}

	@Unique
	public boolean isInAcid() {
		return this.world.isMaterialInBB(this.bb, BTDBlocks.ACID);
	}


}
