package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.SurfaceProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biomes.class)
public abstract class UpdateNetherBiomesMixin {

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void patchNetherSurfaceBlocks(CallbackInfo ci) {
		Biomes.NETHER_VOLCANIC_ISLANDS.withSurfaceProperties(new SurfaceProperties.Builder()
			.withTopBlock(BTDBlocks.BRIMSTONE)
			.withFillerBlock(BTDBlocks.BRIMSTONE)
			.build());

	}
}
