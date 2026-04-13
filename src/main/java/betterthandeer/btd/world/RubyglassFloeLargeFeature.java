package betterthandeer.btd.world;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubyglassFloeLargeFeature extends LargeFeature {
	private @Nullable WorleyNoise chunkNoise = null;
	private @Nullable Noise2D gapNoise = null;
	private @Nullable Noise2D scaleNoise = null;
	private @Nullable World lastWorld = null;

	public RubyglassFloeLargeFeature() {
	}

	protected void doGeneration(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, int baseChunkX, int baseChunkZ, @NotNull ChunkGeneratorResult result) {
		if (this.chunkNoise == null || this.gapNoise == null || this.scaleNoise == null || this.lastWorld != world) {
			this.chunkNoise = new WorleyNoise(new Random(world.getRandomSeed()));
			this.gapNoise = new FractalNoise2D(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4));
			this.scaleNoise = new Normalize2D(new FractalNoise2D(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 2)));
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
					this.chunkNoise.getValue((double) baseChunkX + (double) x / (double) 16.0F, (double) baseChunkZ + (double) z / (double) 16.0F, worleyResult);
					double lineScale = 15.0F;
					double lineWidth = this.gapNoise.getValue((double) (baseChunkX * 16 + x) / lineScale, (double) (baseChunkZ * 16 + z) / lineScale) / (double) 2.0F + (double) 0.5F;
					if (MathHelper.unsignedMod(worleyResult.index, 15.0F) != (double) 0.0F && !(worleyResult.distance > 0.9 + lineWidth * 0.05)) {
						for (int y = oceanY - 3; y < oceanY; ++y) {
							this.trySetBlock(result, x, y, z, Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id());
						}

						for (int y = oceanY; y < oceanY + 1; ++y) {
							this.trySetBlock(result, x, y, z, Blocks.COBBLE_NETHERRACK.id());
						}
					}
				}
			}
		}

	}

	private void trySetBlock(@NotNull ChunkGeneratorResult result, int x, int y, int z, int id) {
		int current = result.getBlock(x, y, z);
		if (current == BTDBlocks.ICE_RUBYGLASS.id() || current == 0) {
			result.setBlock(x, y, z, id);
		}

	}

	@Override
	public int getRadiusChunk() {
		return 0;
	}
}
