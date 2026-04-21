package betterthandeer.btd.world.chunk.generator;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.noise.FractalNoise2D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import net.minecraft.core.world.noise.Noise2D;
import net.minecraft.core.world.noise.WorleyNoise;
import net.minecraft.core.world.noise.operator.Normalize2D;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.type.WorldType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class RubyglassFloeLargeFeature extends LargeFeature {
	private @Nullable WorleyNoise chunkNoise = null;
	private @Nullable Noise2D gapNoise = null;
	private @Nullable Noise2D scaleNoise = null;
	private @Nullable World lastWorld = null;

	public RubyglassFloeLargeFeature() {
	}

	protected void doGeneration(@NonNull World world, @NonNull Random random, int chunkX, int chunkZ, int baseChunkX, int baseChunkZ, @NonNull ChunkGeneratorResult result) {
		if (this.chunkNoise == null || this.gapNoise == null || this.scaleNoise == null || this.lastWorld != world) {
			this.chunkNoise = new WorleyNoise(new Random(world.getRandomSeed()));
			this.gapNoise = new FractalNoise2D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4));
			this.scaleNoise = new Normalize2D<>(new FractalNoise2D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 2)));
			this.lastWorld = world;
		}

		WorldType worldType = world.getWorldType();
		int oceanY = worldType.getMinY(world) + worldType.getOceanY();
		TilePos tilePos = new TilePos();
		WorleyNoise.Result worleyResult = new WorleyNoise.Result();

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				tilePos.set(16 * baseChunkX + x, oceanY, 16 * baseChunkZ + z);
				Biome biome = world.getBlockBiome(tilePos);
				if (biome == Biomes.NETHER_CRYSTAL_FOREST || biome == Biomes.NETHER_CRYSTAL_PLAINS) {

					double sampleX = (double) baseChunkX + (double) x / 16.0;
					double sampleZ = (double) baseChunkZ + (double) z / 16.0;
					this.chunkNoise.getValue(sampleX, sampleZ, worleyResult);

					double lineScale = 15.0;
					double lineWidth = this.gapNoise.getValue((double) (baseChunkX * 16 + x) / lineScale, (double) (baseChunkZ * 16 + z) / lineScale) / 2.0 + 0.5;
					double edgeThreshold = 0.8 + lineWidth * 0.05;

					if (MathHelper.unsignedMod(worleyResult.index, 15.0F) != 0.0 && worleyResult.distance <= edgeThreshold) {

						double thicknessVariation = this.scaleNoise.getValue((double) x / 10.0, (double) z / 10.0);
						int depth = (int) (thicknessVariation * 6.0);
						for (int y = oceanY - (depth + 2); y < oceanY; ++y) {
							this.trySetBlock(result, x, y, z, Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id());
						}

						for (int y = oceanY; y < oceanY + depth; ++y) {
							this.trySetBlock(result, x, y, z, Blocks.COBBLE_NETHERRACK.id());
						}
					}
				}
			}
		}
	}

	private void trySetBlock(@NonNull ChunkGeneratorResult result, int x, int y, int z, int id) {
		int current = result.getBlock(x, y, z);
		if (current == BTDBlocks.ICE_RUBYGLASS.id() || current == Blocks.FLUID_WATER_STILL.id() || current == Blocks.AIR.id()) {
			result.setBlock(x, y, z, id);
		}
	}

	@Override
	public int getRadiusChunk() {
		return 0;
	}
}
