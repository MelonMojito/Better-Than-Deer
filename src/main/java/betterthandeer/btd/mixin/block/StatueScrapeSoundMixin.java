package betterthandeer.btd.mixin.block;

import net.minecraft.core.block.BlockLogicStatue;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;

@Mixin(value = BlockLogicStatue.class, remap = false)
public class StatueScrapeSoundMixin {

	@ModifyArgs(method = "onInteracted", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V"))
	private void boostPitchShift(@NonNull Args args) {
		args.set(5, "btd:scrape");
		float customPitch = 0.2F + (new Random()).nextFloat() * 0.6F;
		args.set(7, customPitch);
	}
}
