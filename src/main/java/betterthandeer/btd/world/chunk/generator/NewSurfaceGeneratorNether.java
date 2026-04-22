package betterthandeer.btd.world.chunk.generator;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.BiomeTags;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.nether.SurfaceGeneratorNether;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class NewSurfaceGeneratorNether extends SurfaceGeneratorNether {
	private final @NonNull World world;

	private final @NonNull FractalNoise3D<ImprovedPerlinNoise> beachNoise;
	private final @NonNull FractalNoise3D<ImprovedPerlinNoise> soilNoise;
	private final @NonNull FractalNoise3D<ImprovedPerlinNoise> mainNoise;

	private final boolean generateStoneVariants = true;

	public NewSurfaceGeneratorNether(@NonNull World world) {
		super(world);
		this.world = world;
		this.beachNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 40));
		this.soilNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44));
		this.mainNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 8, 32));
	}

	@Override
	public void generateSurface(@NonNull Chunk chunk, @NonNull ChunkGeneratorResult result) {
		int oceanY = this.world.getWorldType().getOceanY();
		int minY = this.world.getWorldType().getMinY(world);
		int maxY = this.world.getWorldType().getMaxY(world);
		int chunkX = chunk.pos.x;
		int chunkZ = chunk.pos.z;

		int oceanBlock = this.world.getWorldType().getOceanBlockIds()[0];
		int worldFillBlock = this.world.getWorldType().getFillerBlockId();

		Random rand = new Random((long) chunkX * 0x4F9939F508L + (long) chunkZ * 0x1EF1565BD5L);

		double beachScale = 0.03125D;
		double[] sandBeachNoise = this.beachNoise.getRegion(null, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, beachScale, beachScale, 1.0D);
		double[] gravelBeachNoise = this.beachNoise.getRegion(null, chunkX * 16, 109.0134D, chunkZ * 16, 16, 1, 16, beachScale, 1.0D, beachScale);
		double[] soilThicknessNoise = this.soilNoise.getRegion(null, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, beachScale * 2D, beachScale * 2D, beachScale * 2D);

		double[] stoneLayerNoiseBasalt = null;
		double[] stoneLayerNoiseNetherrack = null;
		double[] stoneLayerNoiseGloomstone = null;
		if (this.generateStoneVariants) {
			stoneLayerNoiseBasalt = this.soilNoise.getRegion(null, chunkX * Chunk.CHUNK_SIZE_X, chunkZ * Chunk.CHUNK_SIZE_Z, 0.0D, Chunk.CHUNK_SIZE_X, Chunk.CHUNK_SIZE_Z, 1, beachScale * 4D, beachScale * 4D, beachScale * 4D);
			stoneLayerNoiseNetherrack = this.mainNoise.getRegion(null, chunkX * Chunk.CHUNK_SIZE_X, chunkZ * Chunk.CHUNK_SIZE_Z, 0.0D, Chunk.CHUNK_SIZE_X, Chunk.CHUNK_SIZE_Z, 1, beachScale * 4D, beachScale * 4D, beachScale * 4D);
			stoneLayerNoiseGloomstone = this.beachNoise.getRegion(null, chunkX * Chunk.CHUNK_SIZE_X, chunkZ * Chunk.CHUNK_SIZE_Z, 0.0D, Chunk.CHUNK_SIZE_X, Chunk.CHUNK_SIZE_Z, 1, beachScale * 4D, beachScale * 4D, beachScale * 4D);
		}
		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				boolean generateSoulSandBeach = sandBeachNoise[z + x * 16] + rand.nextDouble() * 0.2D > 0.0D;
				boolean generateGravelBeach = gravelBeachNoise[z + x * 16] + rand.nextDouble() * 0.2D > 0.0D;
				int soilThickness = (int) (soilThicknessNoise[z + x * 16] / 3D + 3D + rand.nextDouble() * 0.25D);

				int currentLayerDepth = -1;
				short topBlock = -1;
				short fillerBlock = -1;

				Biome lastBiome = null;

				for (int y = maxY; y >= minY; y--) {
					Biome biome = chunk.getBlockBiome(x, y, z);
					if (biome == null)
						biome = this.world.getBiomeProvider().getBiome(chunkX * Chunk.CHUNK_SIZE_X + x, y >> 3, chunkZ * Chunk.CHUNK_SIZE_Z + z);

					int block = result.getBlock(x, y, z);

					if ((biome != lastBiome || topBlock == -1 || fillerBlock == -1) && block == 0) {
						topBlock = (short) biome.getSurfaceProperties().getTopBlock().id();
						fillerBlock = (short) biome.getSurfaceProperties().getFillerBlock().id();
					}
					lastBiome = biome;

					// reset the currently generating surface thickness to -1 if encountered air
					if (block == 0) {
						currentLayerDepth = -1;
						continue;
					}

					// will skip a generation loop if it encounters something other than Netherrack (or basalt for the sulfur pools)
					if (block != worldFillBlock) {
						continue;
					}

					// if thickness == -1, find what block to place on layer level
					if (currentLayerDepth == -1) {
						// if soil thickness is below 0, generate a stone basin where there is no top block layer
						if (soilThickness <= 0) {
							topBlock = 0;
							if (biome == Biomes.NETHER_VOLCANIC_ISLANDS || biome == Biomes.NETHER_SULFUR_POOLS) {
								fillerBlock = (short) Blocks.BRIMSAND.id();
							} else if (biome == Biomes.NETHER_OLD_WORLD || biome == Biomes.NETHER_OLD_WORLD_DESERT) {
								fillerBlock = (short) Blocks.COBBLE_GLOOMSTONE.id();
							} else {
								fillerBlock = (short) Blocks.NETHERRACK.id();
							}
						} else {
							boolean biomeGeneratesSulfur = biome.hasTag(BiomeTags.HAS_SULFUR_POOLS);

							if (y >= minY + oceanY - 4 && y <= minY + oceanY + 1) {
								// Generate coastlines
								topBlock = (short) biome.getSurfaceProperties().getTopBlock().id();
								fillerBlock = (short) biome.getSurfaceProperties().getFillerBlock().id();
								if (generateGravelBeach) {
									topBlock = (short) Blocks.SOULSAND.id();
									fillerBlock = (short) Blocks.SOULSAND.id();
								}
								if (generateSoulSandBeach) {
									topBlock = (short) Blocks.SOULSAND.id();
									fillerBlock = (short) Blocks.SOULSAND.id();
								}
							} else if (y <= oceanY && biomeGeneratesSulfur) {
								topBlock = (short) Blocks.BRIMSAND.id();
								fillerBlock = (short) Blocks.BRIMSAND.id();
							}
						}

						// failsafe so that if a basin is generated under the ocean, the ocean is not replaced with air
						if (y < minY + oceanY && topBlock == 0) {
							topBlock = (short) oceanBlock;
						}
						// if a new surface layer has been chosen to be generated, set the current layer depth to the generated soil thickness level and begin generating downwards
						currentLayerDepth = soilThickness + 2;

						// set block at index to designated surface block above ocean level
						if (y >= minY + oceanY - 1) {
							result.setBlock(x, y, z, topBlock);
						} else {
							// if the block is below ocean level, set it to the chosen filler block
							result.setBlock(x, y, z, fillerBlock);
						}
						continue;
					}

					if (currentLayerDepth > 0) {
						result.setBlock(x, y, z, fillerBlock);
						currentLayerDepth--;
					}

					if (currentLayerDepth == 0 && biome == Biomes.NETHER_OLD_WORLD && fillerBlock == Blocks.BLOCK_ASH.id()) {
						currentLayerDepth = rand.nextInt(8) + 2;
						fillerBlock = (short) Blocks.SLATE.id();
					}


					if (this.generateStoneVariants && currentLayerDepth <= 0) {
						int stoneBlockId = worldFillBlock;

						if (biome == Biomes.NETHER_VOLCANIC_ISLANDS || biome == Biomes.NETHER_SULFUR_POOLS) {
							stoneBlockId = Blocks.BRIMSAND.id();
						} else if (biome == Biomes.NETHER_CRAG || biome == Biomes.NETHER_SHELF) {
							stoneBlockId = (rand.nextInt(2) == 0) ? Blocks.NETHERRACK.id() : Blocks.COBBLE_NETHERRACK.id();
						} else if (biome == Biomes.NETHER_CRYSTAL_PLAINS || biome == Biomes.NETHER_CRYSTAL_FOREST) {
							stoneBlockId = (rand.nextInt(8) == 0) ? Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id() : Blocks.COBBLE_NETHERRACK.id();
						} else if (biome == Biomes.NETHER_OLD_WORLD || biome == Biomes.NETHER_OLD_WORLD_DESERT) {
							stoneBlockId = Blocks.COBBLE_GLOOMSTONE.id();
						}

						result.setBlock(x, y, z, stoneBlockId);
					}
				}
			}
		}
	}
}
