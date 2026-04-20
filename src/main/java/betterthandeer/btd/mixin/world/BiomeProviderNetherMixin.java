package betterthandeer.btd.mixin.world;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProviderNether;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeProviderNether.class)
public abstract class BiomeProviderNetherMixin {

	@Unique
	private static final BiomeRangeMap brm = new BiomeRangeMap();

	@Shadow
	@Final
	@Mutable
	public static Biome[] BIOMES_ORDER;

	@Unique
	private static final double BIOME_STRETCH_FACTOR = 2.0;

	@ModifyArg(method = "getBiomes", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/noise/FractalNoise2D;getRegion([DDDIIDD)[D", ordinal = 0), index = 5)
	private double modifyTemperatureScaleX(double original) {
		return original / BIOME_STRETCH_FACTOR;
	}

	@ModifyArg(method = "getBiomes", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/noise/FractalNoise2D;getRegion([DDDIIDD)[D", ordinal = 0), index = 6)
	private double modifyTemperatureScaleZ(double original) {
		return original / BIOME_STRETCH_FACTOR;
	}

	@ModifyArg(method = "getTemperatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/noise/FractalNoise2D;getRegion([DDDIIDD)[D"), index = 5)
	private double modifyGetTemperaturesScaleX(double original) {
		return original / BIOME_STRETCH_FACTOR;
	}

	@ModifyArg(method = "getTemperatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/noise/FractalNoise2D;getRegion([DDDIIDD)[D"), index = 6)
	private double modifyGetTemperaturesScaleZ(double original) {
		return original / BIOME_STRETCH_FACTOR;
	}

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void initBRM(CallbackInfo ci) {
		brm.clear();

		brm.addRange(Biomes.NETHER_VOLCANIC_ISLANDS, new BiomeRange(
			0.90,
			1.0,
			0.0,
			0.1,
			0.0, 1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRAG, new BiomeRange(
			0.75,
			1.0,
			0.0,
			0.85,
			0.0, 1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_SULFUR_POOLS, new BiomeRange(
			0.75,
			1.0,
			0.85,
			1.0,
			0.0, 1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_SHELF, new BiomeRange(
			0.65,
			0.75,
			0.0,
			1.0,
			0.0,
			1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRYSTAL_FOREST, new BiomeRange(
			0.35,
			0.65,
			0.1,
			1.0,
			0.0, 1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRYSTAL_PLAINS, new BiomeRange(
			0.35,
			0.65,
			0.0,
			0.1,
			0.0, 1.0, 0.0, 1.0));
		brm.addRange(Biomes.NETHER_OLD_WORLD, new BiomeRange(
			0.0,
			0.35,
			0.0,
			1.0,
			0.0, 1.0, 0.0, 1.0));


		brm.lock();

		BIOMES_ORDER = brm.allBiomes().toArray(new Biome[0]);
	}

	@Inject(method = "lookupBiome", at = @At("HEAD"), cancellable = true)
	public void lookupBiome(double temperature, double humidity, double altitude, double variety, CallbackInfoReturnable<Biome> cir) {
		cir.setReturnValue(brm.lookupBiome(temperature, humidity, variety, altitude));
	}
}
