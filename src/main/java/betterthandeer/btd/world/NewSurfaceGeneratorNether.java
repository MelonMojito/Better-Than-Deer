package betterthandeer.btd.world;

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
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class NewSurfaceGeneratorNether extends SurfaceGeneratorNether {
	private final @NotNull World world;
	private final @NotNull FractalNoise3D<ImprovedPerlinNoise> beachNoise;
	private final @NotNull FractalNoise3D<ImprovedPerlinNoise> soilNoise;
	private final @NotNull FractalNoise3D<ImprovedPerlinNoise> mainNoise;
	private final boolean generateStoneVariants = true;

	public NewSurfaceGeneratorNether(@NotNull World world) {
		super(world);
		this.world = world;
		this.beachNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 40));
		this.soilNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44));
		this.mainNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 8, 32));
	}

	@Override
	public void generateSurface(@NotNull Chunk chunk, @NotNull ChunkGeneratorResult result) {
		int oceanY = this.world.getWorldType().getOceanY();
		int minY = this.world.getWorldType().getMinY(this.world);
		int maxY = this.world.getWorldType().getMaxY(this.world);
		int chunkX = chunk.pos.x;
		int chunkZ = chunk.pos.z;
		int oceanBlock = this.world.getWorldType().getOceanBlockIds()[0];
		int worldFillBlock = this.world.getWorldType().getFillerBlockId();
		Random rand = new Random((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
		double beachScale = 0.03125F;
		double[] sandBeachNoise = this.beachNoise.getRegion(null, chunkX * 16.0, chunkZ * 16.0, 0.0F, 16, 16, 1, beachScale, beachScale, 1.0F);
		double[] gravelBeachNoise = this.beachNoise.getRegion(null, chunkX * 16.0, 109.0134, chunkZ * 16.0, 16, 1, 16, beachScale, 1.0F, beachScale);
		double[] soilThicknessNoise = this.soilNoise.getRegion(null, chunkX * 16.0, chunkZ * 16.0, 0.0F, 16, 16, 1, beachScale * (double) 2.0F, beachScale * (double) 2.0F, beachScale * (double) 2.0F);
		double[] stoneLayerNoiseBasalt;
		double[] stoneLayerNoiseNetherrack;
		double[] stoneLayerNoiseGloomstone;
		stoneLayerNoiseBasalt = this.soilNoise.getRegion(null, chunkX * 16.0, chunkZ * 16.0, 0.0F, 16, 16, 1, beachScale * (double) 4.0F, beachScale * (double) 4.0F, beachScale * (double) 4.0F);
		stoneLayerNoiseNetherrack = this.mainNoise.getRegion(null, chunkX * 16.0, chunkZ * 16.0, 0.0F, 16, 16, 1, beachScale * (double) 4.0F, beachScale * (double) 4.0F, beachScale * (double) 4.0F);
		stoneLayerNoiseGloomstone = this.beachNoise.getRegion(null, chunkX * 16.0, chunkZ * 16.0, 0.0F, 16, 16, 1, beachScale * (double) 4.0F, beachScale * (double) 4.0F, beachScale * (double) 4.0F);

		for (int z = 0; z < 16; ++z) {
			for (int x = 0; x < 16; ++x) {
				boolean generateSoulSandBeach = sandBeachNoise[z + x * 16] + rand.nextDouble() * 0.2 > (double) 0.0F;
				boolean generateGravelBeach = gravelBeachNoise[z + x * 16] + rand.nextDouble() * 0.2 > (double) 0.0F;
				int soilThickness = (int) (soilThicknessNoise[z + x * 16] / (double) 3.0F + (double) 3.0F + rand.nextInt() * (double) 0.25F);
				boolean generateBasaltLayer;
				boolean generateNetherrackLayer;
				boolean generateGloomstoneLayer;
				int basaltThicknessLevel;
				int netherrackThicknessLevel;
				int gloomstoneThicknessLevel;
				generateBasaltLayer = stoneLayerNoiseBasalt[z + x * 16] + rand.nextDouble() * 0.2 > (double) 0.0F;
				generateNetherrackLayer = stoneLayerNoiseNetherrack[z + x * 16] + rand.nextDouble() * 0.2 > (double) 2.0F;
				generateGloomstoneLayer = stoneLayerNoiseGloomstone[z + x * 16] + rand.nextDouble() * 0.2 > (double) 3.0F;
				basaltThicknessLevel = (int) (stoneLayerNoiseBasalt[z + x] + rand.nextInt() * (double) 0.5F);
				netherrackThicknessLevel = (int) (stoneLayerNoiseNetherrack[z + x] + rand.nextInt() * (double) 0.5F);
				gloomstoneThicknessLevel = (int) (stoneLayerNoiseGloomstone[z + x] + rand.nextInt() * (double) 0.5F);
				int currentLayerDepth = -1;
				short topBlock = -1;
				short fillerBlock = -1;
				Biome lastBiome = null;

				for (int y = maxY; y >= minY; --y) {
					Biome biome = chunk.getBlockBiome(x, y, z);
					if (biome == null) {
						biome = this.world.getBiomeProvider().getBiome(chunkX * 16 + x, y >> 3, chunkZ * 16 + z);
					}

					int block = result.getBlock(x, y, z);
					if ((biome != lastBiome || topBlock == -1 || fillerBlock == -1) && block == 0) {
						topBlock = (short) biome.getSurfaceProperties().getTopBlock().id();
						fillerBlock = (short) biome.getSurfaceProperties().getFillerBlock().id();
					}

					lastBiome = biome;
					if (block == 0) {
						currentLayerDepth = -1;
					} else if (block == worldFillBlock) {
						if (currentLayerDepth == -1) {
							if (soilThickness <= 0) {
								topBlock = 0;
								fillerBlock = (short) Blocks.NETHERRACK.id();
							} else {
								boolean biomeGeneratesSulfur = biome.hasTag(BiomeTags.HAS_SULFUR_POOLS);
								if (y >= minY + oceanY - 4 && y <= minY + oceanY + 1) {
									topBlock = (short) biome.getSurfaceProperties().getTopBlock().id();
									fillerBlock = (short) biome.getSurfaceProperties().getFillerBlock().id();
									if (generateGravelBeach) {
										topBlock = (short) Blocks.BRIMSAND.id();
										fillerBlock = (short) Blocks.BRIMSAND.id();
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

							if (y < minY + oceanY && topBlock == 0) {
								topBlock = (short) oceanBlock;
							}

							currentLayerDepth = soilThickness + 2;
							if (y >= minY + oceanY - 1) {
								result.setBlock(x, y, z, topBlock);
							} else {
								result.setBlock(x, y, z, fillerBlock);
							}
						} else {
							if (currentLayerDepth <= 0) {
								if (biome != Biomes.NETHER_OLD_WORLD) {
									if (y >= minY + basaltThicknessLevel - rand.nextInt(3) && y <= maxY - basaltThicknessLevel + rand.nextInt(3) && generateBasaltLayer) {
										result.setBlock(x, y, z, Blocks.COBBLE_BASALT.id());
									} else if (y >= minY + netherrackThicknessLevel - rand.nextInt(3) && y <= maxY - netherrackThicknessLevel + rand.nextInt(3) && generateNetherrackLayer) {
										result.setBlock(x, y, z, Blocks.NETHERRACK.id());
									} else if (y >= minY + gloomstoneThicknessLevel - rand.nextInt(3) && y <= maxY - gloomstoneThicknessLevel + rand.nextInt(3) && generateGloomstoneLayer) {
										result.setBlock(x, y, z, Blocks.COBBLE_GLOOMSTONE.id());
									}
								}
							} else {
								--currentLayerDepth;
								result.setBlock(x, y, z, fillerBlock);

								if (currentLayerDepth == 0 && biome.hasTag(BiomeTags.HAS_SULFUR_POOLS) && fillerBlock == Blocks.BRIMSAND.id()) {
									currentLayerDepth = rand.nextInt(4) + 2;
								}
							}
						}
					}
				}
			}
		}

	}
}
