package betterthandeer.btd.mixin.block;

import net.minecraft.core.block.BlockLogicStatue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = BlockLogicStatue.class, remap = false)
public class StatueScrapeSoundMixin {

	@ModifyArg(method = "onInteracted", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V"))
	private String swapScrapeSound(String originalSound) {
		if ("random.scrape".equals(originalSound)) {
			return "btd:scrape";
		}
		return originalSound;
	}
}
