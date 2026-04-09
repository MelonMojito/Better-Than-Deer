package betterthandeer.btd.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.PostProcessingManager;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostProcessingManager.class)
public abstract class OldWorldColorMixin {

	@Shadow
	@Final
	private @NonNull Minecraft mc;

	@Shadow
	@Final
	private PostProcessingManager.@NonNull PostProcessingConfig current;

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/PostProcessingManager;smoothLerp(FF)F", ordinal = 0))
	private void applyOldWorldGrayCorrection(CallbackInfo ci) {
		if (mc.currentWorld == null || !mc.currentWorld.getWorldType().hasTag(net.minecraft.core.world.type.tag.WorldTypeTags.NETHER))
			return;
		if (mc.currentWorld.getBlockBiome(new TilePos(mc.thePlayer)) != Biomes.NETHER_OLD_WORLD) return;
		if (GameSettings.COLOR_CORRECTION.value == 0.0F) return;

		PostProcessingManager.PostProcessingConfig c = this.current;
		c.saturation = -0.75f;
		c.rMod = 0.98f;
		c.gMod = 0.94f;
		c.bMod = 0.90f;
		c.contrast = -0.10f;
		c.brightness = -0.05f;
		c.exposure = -0.03f;
	}

}
