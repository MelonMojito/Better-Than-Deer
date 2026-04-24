package betterthandeer.btd.world.chunk.generator;

import betterthandeer.btd.block.BTDBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.nether.DensityGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.TerrainGeneratorNether;
import net.minecraft.core.world.type.WorldType;
import org.jspecify.annotations.NonNull;

public class NewTerrainGeneratorNether extends TerrainGeneratorNether {
	private final DensityGenerator densityGenerator;
	private final Object2IntMap<Biome> fluidLookup = new Object2IntArrayMap<>();
	private final int minY;
	private final int maxY;

	public NewTerrainGeneratorNether(@NonNull World world) {
		super(world);
		this.densityGenerator = new DensityGeneratorNether(world);
		this.fluidLookup.put(Biomes.NETHER_VOLCANIC_ISLANDS, Blocks.FLUID_LAVA_STILL.id());

		this.fluidLookup.put(Biomes.NETHER_SULFUR_POOLS, Blocks.FLUID_LAVA_STILL.id());

		this.fluidLookup.put(Biomes.NETHER_CRAG, Blocks.FLUID_LAVA_STILL.id());
		this.fluidLookup.put(Biomes.NETHER_SHELF, Blocks.COBBLE_NETHERRACK.id());

		this.fluidLookup.put(Biomes.NETHER_CRYSTAL_FOREST, Blocks.FLUID_WATER_STILL.id());
		this.fluidLookup.put(Biomes.NETHER_CRYSTAL_PLAINS, Blocks.FLUID_WATER_STILL.id());

		this.fluidLookup.put(Biomes.NETHER_OLD_WORLD, Blocks.OBSIDIAN.id());

		this.minY = world.getWorldType().getMinY(world);
		this.maxY = world.getWorldType().getMaxY(world);
	}

	@Override
	protected int getBlockAt(@NonNull Chunk chunk, int x, int y, int z, double density) {
		WorldType type = this.world.getWorldType();
		int quarterHeight = this.maxY / 4;
		if (y < quarterHeight) {
			return Blocks.BEDROCK.id();
		} else if (y >= this.maxY - this.rand.nextInt(10)) {
			return Blocks.BEDROCK.id();
		} else if (y <= quarterHeight + this.rand.nextInt(10)) {
			return Blocks.BEDROCK.id();
		} else if (density > 0.0) {
			return type.getFillerBlockId();
		} else {
			int oceanY = type.getOceanY();

			if (y < oceanY) {
				Biome oceanBiome = chunk.getBlockBiome(x, oceanY, z);
				int fluidId = this.fluidLookup.getOrDefault(oceanBiome, 0);

				if (fluidId == Blocks.FLUID_WATER_STILL.id() && y == oceanY - 1) {
					return BTDBlocks.ICE_RUBYGLASS.id();
				}

				if (fluidId == Blocks.OBSIDIAN.id() && y == oceanY - 1) {
					return BTDBlocks.FLUID_TAR_STILL.id();
				}

				return fluidId;
			}

			return Blocks.AIR.id();
		}
	}

	@Override
	public @NonNull DensityGenerator getDensityGenerator() {
		return this.densityGenerator;
	}
}
