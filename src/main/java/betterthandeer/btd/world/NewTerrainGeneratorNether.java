package betterthandeer.btd.world;

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
import org.jspecify.annotations.Nullable;

public class NewTerrainGeneratorNether extends TerrainGeneratorNether {
	private final DensityGenerator densityGenerator;
	private @Nullable Biome lastBiome = null;
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
		label31:
		{
			if (Integer.MIN_VALUE == x && Integer.MIN_VALUE == z) {
				break label31;
			}

			this.lastBiome = chunk.getBlockBiome(x, 0, z);
		}

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
				int fluidId = this.fluidLookup.getOrDefault(this.lastBiome, 0);

				if (fluidId == Blocks.FLUID_WATER_STILL.id() && y == oceanY - 1) {
					return BTDBlocks.ICE_RUBYGLASS.id();
				}

				return fluidId;
			}

			return 0;
		}
	}

	@Override
	public @NonNull DensityGenerator getDensityGenerator() {
		return this.densityGenerator;
	}
}
