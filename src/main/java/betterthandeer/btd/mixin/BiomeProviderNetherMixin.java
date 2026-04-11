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

//	@Inject(method = "lookupBiome", at = @At("HEAD"), cancellable = true)
//	public void lookupBiome(double temperature, double humidity, double altitude, double variety, CallbackInfoReturnable<Biome> cir) {
//		if (temperature >= 0.95) {
//			cir.setReturnValue(Biomes.NETHER_VOLCANIC_ISLANDS);
//		}
//		else if (temperature >= 0.75) {
//			cir.setReturnValue(Biomes.NETHER_CRAG);
//		}
//		else if (temperature >= 0.70) {
//			cir.setReturnValue(Biomes.NETHER_SHELF);
//		}
//		else if (temperature >= 0.45) {
//			cir.setReturnValue(Biomes.NETHER_SULFUR_POOLS);
//		}
//		else if (temperature >= 0.40) {
//			cir.setReturnValue(Biomes.NETHER_SHELF);
//		}
//		else if (temperature >= 0.15) {
//			cir.setReturnValue(humidity < 0.2 ? Biomes.NETHER_CRYSTAL_PLAINS : Biomes.NETHER_CRYSTAL_FOREST);
//		}
//		else {
//			cir.setReturnValue(Biomes.NETHER_OLD_WORLD);
//		}
//
//		cir.cancel();
//	}
}
