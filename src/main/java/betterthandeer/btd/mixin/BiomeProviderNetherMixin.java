package betterthandeer.btd.mixin;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.provider.BiomeProviderNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeProviderNether.class)
public abstract class BiomeProviderNetherMixin {

	@Inject(method = "lookupBiome", at = @At("HEAD"), cancellable = true)
	public void lookupBiome(double temperature, double humidity, double altitude, double variety, CallbackInfoReturnable<Biome> cir) {
		if (temperature <= 0.2) {
			cir.setReturnValue(Biomes.NETHER_CRAG);
		} else if (temperature < 0.65) {
			cir.setReturnValue(humidity < 0.1 ? Biomes.NETHER_CRYSTAL_PLAINS : Biomes.NETHER_CRYSTAL_FOREST);
		} else if (temperature <= (double) 0.75F) {
			cir.setReturnValue(Biomes.NETHER_SHELF);
		} else if (humidity > 0.85) {
			cir.setReturnValue(Biomes.NETHER_CRAG);
		} else {
			cir.setReturnValue(temperature >= 0.95 && humidity < 0.1 ? Biomes.NETHER_VOLCANIC_ISLANDS : Biomes.NETHER_CRAG);
		}
		cir.cancel();
	}


}
