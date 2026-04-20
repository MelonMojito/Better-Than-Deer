package betterthandeer.btd.world.chunk.generator;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class WarrensCavesLargeFeature extends LargeFeature {
	private final int minY;
	private final int maxY;

	public WarrensCavesLargeFeature() {
		this.minY = 0;
		this.maxY = 256;
	}

	public WarrensCavesLargeFeature(int minY, int maxY) {
		this.minY = minY;
		this.maxY = maxY;
	}

	protected void generateHubRoom(@NonNull World world, @NonNull Random random, long seed, int baseChunkX, int baseChunkZ, ChunkGeneratorResult result, double blockX, double blockY, double blockZ) {
		this.generateCave(world, random, seed, baseChunkX, baseChunkZ, result, blockX, blockY, blockZ, 1.0F + random.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5F);
	}

	protected void generateCave(@NonNull World world, @NonNull Random random, long seed, int baseChunkX, int baseChunkZ, ChunkGeneratorResult result, double blockX, double blockY, double blockZ, float initialRadius, float yRot, float xRot, int startPos, int endPos, double heightMod) {
		double chunkMiddleX = baseChunkX * 16 + 8;
		double chunkMiddleZ = baseChunkZ * 16 + 8;
		float rotHorOffset = 0.0F;
		float rotVerOffset = 0.0F;
		Random r = new Random(seed);
		if (endPos <= 0) {
			int maxLength = this.getRadiusChunk() * 24 - 16;
			endPos = maxLength - r.nextInt(maxLength / 8);
		}

		boolean noRotate = r.nextInt(32) == 0;
		if (noRotate) {
			yRot = yRot / ((float) Math.PI * 2F) * 4.0F;
			yRot = (float) Math.round(yRot);
			yRot = yRot * ((float) Math.PI * 2F) / 4.0F;
		}

		int branchPos = r.nextInt(endPos / 2) + endPos / 4;

		for (boolean sharpRotVer = r.nextInt(6) == 0; startPos < endPos; ++startPos) {
			double width = (double) 1.5F + (double) (MathHelper.sin((float) startPos * (float) Math.PI / (float) endPos) * initialRadius * 1.0F);
			double height = width * heightMod;
			float xzScale = MathHelper.cos(xRot);
			float yOffset = MathHelper.sin(xRot);
			blockX += MathHelper.cos(yRot) * xzScale;
			blockY += yOffset;
			blockZ += MathHelper.sin(yRot) * xzScale;
			if (!noRotate) {
				if (sharpRotVer) {
					xRot *= 0.92F;
				} else {
					xRot *= 0.7F;
				}

				xRot += rotVerOffset * 0.1F;
				yRot += rotHorOffset * 0.1F;
				rotVerOffset *= 0.9F;
				rotHorOffset *= 0.75F;
				rotVerOffset += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 2.0F;
				rotHorOffset += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 4.0F;
				int flip = r.nextInt(5);
				if (flip == 0) {
					yRot = -yRot;
				}

				int addRandom = r.nextInt(4);
				if (addRandom == 0) {
					yRot += r.nextFloat() * (float) Math.PI - ((float) Math.PI / 2F);
				}
			}

			if (startPos == branchPos && initialRadius > 1.0F) {
				this.generateCave(world, random, r.nextLong(), baseChunkX, baseChunkZ, result, blockX, blockY, blockZ, r.nextFloat() * 0.5F + 0.5F, yRot - ((float) Math.PI / 2F), xRot / 3.0F, startPos, endPos, 1.0F);
				this.generateCave(world, random, r.nextLong(), baseChunkX, baseChunkZ, result, blockX, blockY, blockZ, r.nextFloat() * 0.5F + 0.5F, yRot + ((float) Math.PI / 2F), xRot / 3.0F, startPos, endPos, 1.0F);
				return;
			}

			if (r.nextInt(4) != 0) {
				double dxFromMiddle = blockX - chunkMiddleX;
				double dzFromMiddle = blockZ - chunkMiddleZ;
				double length = endPos - startPos;
				double maxRadius = initialRadius + 2.0F + 16.0F;
				if (dxFromMiddle * dxFromMiddle + dzFromMiddle * dzFromMiddle - length * length > maxRadius * maxRadius) {
					return;
				}

				if (!(blockX < chunkMiddleX - (double) 16.0F - width * (double) 2.0F) && !(blockZ < chunkMiddleZ - (double) 16.0F - width * (double) 2.0F) && !(blockX > chunkMiddleX + (double) 16.0F + width * (double) 2.0F) && !(blockZ > chunkMiddleZ + (double) 16.0F + width * (double) 2.0F)) {
					int minX = MathHelper.floor(blockX - width) - baseChunkX * 16 - 1;
					int maxX = MathHelper.floor(blockX + width) - baseChunkX * 16 + 1;
					int minY = MathHelper.floor(blockY - height) - 1;
					int maxY = MathHelper.floor(blockY + height) + 1;
					int minZ = MathHelper.floor(blockZ - width) - baseChunkZ * 16 - 1;
					int maxZ = MathHelper.floor(blockZ + width) - baseChunkZ * 16 + 1;
					if (minX < 0) {
						minX = 0;
					}

					if (maxX > 16) {
						maxX = 16;
					}

					if (minY < this.minY + 1) {
						minY = this.minY + 1;
					}

					if (maxY > this.maxY - 8) {
						maxY = this.maxY - 8;
					}

					if (minZ < 0) {
						minZ = 0;
					}

					if (maxZ > 16) {
						maxZ = 16;
					}

					boolean hasHitWater = false;

					for (int x = minX; !hasHitWater && x < maxX; ++x) {
						for (int z = minZ; !hasHitWater && z < maxZ; ++z) {
							for (int y = maxY + 1; !hasHitWater && y >= minY - 1; --y) {
								int blockId = result.getBlock(x, y, z);
								if (y >= this.minY && y < this.maxY) {
									if (Blocks.hasTag(blockId, BlockTags.IS_WATER)) {
										hasHitWater = true;
									}

									if (y != minY - 1 && x != minX && x != maxX - 1 && z != minZ && z != maxZ - 1) {
										y = minY;
									}
								}
							}
						}
					}

					if (!hasHitWater) {
						for (int x = minX; x < maxX; ++x) {
							double xPercentage = ((double) (x + baseChunkX * 16) + (double) 0.5F - blockX) / width;

							for (int z = minZ; z < maxZ; ++z) {
								double zPercentage = ((double) (z + baseChunkZ * 16) + (double) 0.5F - blockZ) / width;
								int yIndex = maxY;
								if (!(xPercentage * xPercentage + zPercentage * zPercentage >= (double) 1.0F)) {
									for (int y = maxY - 1; y >= minY; --y) {
										double yPercentage = ((double) y + (double) 0.5F - blockY) / height;
										if (yPercentage > -0.7 && xPercentage * xPercentage + yPercentage * yPercentage + zPercentage * zPercentage < (double) 1.0F) {
											int blockId = result.getBlock(x, yIndex, z);
											if (blockId == Blocks.SOULSCHIST.id()) {
												result.setBlock(x, yIndex, z, 0);
											}
										}

										--yIndex;
									}
								}
							}
						}
					}
				}
			}
		}

	}

	protected void doGeneration(@NonNull World world, @NonNull Random random, int chunkX, int chunkZ, int baseChunkX, int baseChunkZ, @NonNull ChunkGeneratorResult result) {
		int cavesToGenerate = random.nextInt(random.nextInt(random.nextInt(100) + 1) + 1);
		if (random.nextInt(15) != 0) {
			cavesToGenerate = 0;
		}

		int yRange = this.maxY - this.minY;

		for (int i = 0; i < cavesToGenerate; ++i) {
			double blockX = chunkX * 16 + random.nextInt(16);
			double blockY = this.minY + random.nextInt(random.nextInt(yRange - 8) + 8);
			double blockZ = chunkZ * 16 + random.nextInt(16);
			int numBranches = 1;
			if (random.nextInt(4) == 0) {
				this.generateHubRoom(world, random, random.nextLong(), baseChunkX, baseChunkZ, result, blockX, blockY, blockZ);
				numBranches += random.nextInt(4);
			}

			for (int l1 = 0; l1 < numBranches; ++l1) {
				float yRot = random.nextFloat() * (float) Math.PI * 2.0F;
				float xRot = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float initialRadius = random.nextFloat() * 1.5F + random.nextFloat();
				this.generateCave(world, random, random.nextLong(), baseChunkX, baseChunkZ, result, blockX, blockY, blockZ, initialRadius, yRot, xRot, 0, 0, 1.0F);
			}
		}

	}
}
