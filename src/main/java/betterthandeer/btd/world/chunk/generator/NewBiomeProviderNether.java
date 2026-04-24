package betterthandeer.btd.world.chunk.generator;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.noise.FractalNoise2D;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.SimplexNoise;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Set;

public final class NewBiomeProviderNether extends BiomeProvider {
	public static final BiomeRangeMap brm = new BiomeRangeMap();
	private static Biome[] biomes;
	private static final double BIOME_STRETCH_FACTOR = 2.0;

	private static final double TEMP_SCALE = 0.025 / BIOME_STRETCH_FACTOR;
	private static final double DOWNFALL_SCALE = 0.05 / BIOME_STRETCH_FACTOR;
	private static final double NOISE_SCALE = 0.25F / BIOME_STRETCH_FACTOR;

	private final FractalNoise2D<?> temperatureNoise;
	private final FractalNoise2D<?> downfallNoise;
	private final FractalNoise2D<?> fuzzinessNoise;

	static {
		init();
	}

	public NewBiomeProviderNether(@NonNull World world) {
		super(world);
		long seed = world.getRandomSeed();
		this.temperatureNoise = new FractalNoise3D<>(SimplexNoise.genOctaves(seed * 9871L, 4));
		this.downfallNoise = new FractalNoise3D<>(SimplexNoise.genOctaves(seed * 39811L, 4));
		this.fuzzinessNoise = new FractalNoise2D<>(SimplexNoise.genOctaves(seed * 543321L, 4));
	}

	public Biome[] getBiomes(Biome[] biomes, double[] temperatures, double[] humidities, double[] varieties, int x, int y, int z, int xSize, int ySize, int zSize) {
		if (biomes == null || biomes.length < xSize * ySize * zSize) {
			biomes = new Biome[xSize * ySize * zSize];
		}

		if (temperatures == null || temperatures.length < xSize * zSize) {
			temperatures = new double[xSize * zSize];
		}

		this.temperatureNoise.setLacunarity(0.25F).getRegion(temperatures, x, z, xSize, xSize, TEMP_SCALE, TEMP_SCALE);

		if (humidities == null || humidities.length < xSize * zSize) {
			humidities = new double[xSize * zSize];
		}

		this.downfallNoise.setLacunarity(0.3333333333333333).getRegion(humidities, x, z, xSize, xSize, DOWNFALL_SCALE, DOWNFALL_SCALE);

		if (varieties == null || varieties.length < xSize * zSize) {
			varieties = new double[xSize * zSize];
		}

		Arrays.fill(varieties, 0.0F);

		double[] noises = this.fuzzinessNoise.setLacunarity(0.5882352941176471).getRegion(null, x, z, xSize, xSize, NOISE_SCALE, NOISE_SCALE);

		for (int xx = 0; xx < xSize; ++xx) {
			for (int zz = 0; zz < zSize; ++zz) {
				double a = noises[xx * zSize + zz] * 1.1 + 0.5;
				double b = 0.01;
				double c = 1.0 - b;
				double temperature = (temperatures[xx * zSize + zz] * 0.15 + 0.7) * c + a * b;
				b = 0.002;
				c = 1.0 - b;
				double downfall = (humidities[xx * zSize + zz] * 0.15 + 0.5) * c + a * b;
				temperature = 1.0 - (1.0 - temperature) * (1.0 - temperature);

				temperature = Math.max(0.0, Math.min(1.0, temperature));
				downfall = Math.max(0.0, Math.min(1.0, downfall));

				temperatures[xx * zSize + zz] = temperature;
				humidities[xx * zSize + zz] = downfall;
				double variety = varieties[xx * zSize + zz];

				for (int yy = 0; yy < ySize; ++yy) {
					double actualY = (double) y + yy;
					double altitude = actualY / 128.0;
					altitude = Math.max(0.0, Math.min(1.0, altitude));

					biomes[yy * xSize * zSize + zz * xSize + xx] = this.lookupBiome(temperature, downfall, altitude, variety);
				}
			}
		}
		return biomes;
	}

	public double[] getTemperatures(double[] temperatures, int x, int z, int xSize, int zSize) {
		if (temperatures == null || temperatures.length < xSize * zSize) {
			temperatures = new double[xSize * zSize];
		}

		this.temperatureNoise.setLacunarity(0.25F).getRegion(temperatures, x, z, xSize, xSize, TEMP_SCALE, TEMP_SCALE);
		double[] noises = this.fuzzinessNoise.setLacunarity(0.5882352941176471).getRegion(null, x, z, xSize, zSize, NOISE_SCALE, NOISE_SCALE);

		int i = 0;
		for (int xx = 0; xx < xSize; ++xx) {
			for (int zz = 0; zz < zSize; ++zz) {
				double a = noises[i] * 1.1 + 0.5;
				double b = 0.01;
				double c = 0.99;
				double temperature = (temperatures[i] * 0.15 + 0.7) * c + a * b;
				temperature = 1.0 - (1.0 - temperature) * (1.0 - temperature);
				temperatures[i++] = Math.max(0.0, Math.min(1.0, temperature));
			}
		}
		return temperatures;
	}

	public double[] getHumidities(double[] humidities, int x, int z, int xSize, int zSize) {
		if (humidities == null || humidities.length < xSize * zSize) {
			humidities = new double[xSize * zSize];
		}
		return this.downfallNoise.setLacunarity(0.5F).getRegion(humidities, x, z, xSize, zSize, DOWNFALL_SCALE, DOWNFALL_SCALE);
	}

	public double[] getVarieties(double[] varieties, int x, int z, int xSize, int zSize) {
		if (varieties == null || varieties.length < xSize * zSize) {
			varieties = new double[xSize * zSize];
		}

		Arrays.fill(varieties, 0.0F);
		return varieties;
	}

	public double[] getBiomenesses(double[] biomenesses, int x, int y, int z, int xSize, int ySize, int zSize) {
		if (biomenesses == null || biomenesses.length < xSize * ySize * zSize) {
			biomenesses = new double[xSize * ySize * zSize];
		}

		Arrays.fill(biomenesses, 1.0F);
		return biomenesses;
	}

	public Biome lookupBiome(double temperature, double humidity, double altitude, double variety) {
		return brm.lookupBiome(temperature, humidity, altitude, variety);
	}

	public static void init() {
		brm.clear();

		brm.addRange(Biomes.NETHER_VOLCANIC_ISLANDS, new BiomeRange(
			0.95, 1.0,
			0.0, 1.0,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRAG, new BiomeRange(
			0.75, 0.95,
			0.0, 0.85,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_SULFUR_POOLS, new BiomeRange(
			0.75, 0.95,
			0.85, 1.0,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_SHELF, new BiomeRange(
			0.65, 0.75,
			0.0, 1.0,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRYSTAL_FOREST, new BiomeRange(
			0.35, 0.65,
			0.1, 1.0,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_CRYSTAL_PLAINS, new BiomeRange(
			0.35, 0.65,
			0.0, 0.1,
			0.5, 1.0,
			0.0, 1.0));
		brm.addRange(Biomes.NETHER_OLD_WORLD, new BiomeRange(
			0.0, 0.35,
			0.0, 1.0,
			0.50, 1.0,
			0.0, 1.0));

		brm.addRange(Biomes.NETHER_OLD_WORLD_DESERT, new BiomeRange(
			0.0, 1.0,
			0.0, 1.0,
			0.0, 0.5,
			0.0, 1.0));

		brm.lock();

		Set<Biome> allBiomes = brm.allBiomes();
		biomes = allBiomes.toArray(new Biome[0]);
	}

	public static Biome[] allBiomes() {
		return biomes;
	}
}
