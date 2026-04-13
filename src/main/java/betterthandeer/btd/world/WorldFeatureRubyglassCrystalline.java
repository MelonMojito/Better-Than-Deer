package betterthandeer.btd.world;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.generate.voxelizer.ConvexShapeVoxelizer;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Random;

public class WorldFeatureRubyglassCrystalline implements WorldFeatureInterface {
	private static final float BASE_PCTG = 0.5F;
	private final boolean isCeiling;
	private final int minHeight;
	private final int heightMod;

	public WorldFeatureRubyglassCrystalline(boolean isCeiling, int minHeight, int heightMod) {
		this.isCeiling = isCeiling;
		this.minHeight = minHeight;
		this.heightMod = heightMod;
	}

	public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePos) {
		TilePos ti = new TilePos(tilePos.x() + random.nextInt(2) - random.nextInt(2), tilePos.y(), tilePos.z() + random.nextInt(2) - random.nextInt(2));
		int floorY = -1;
		int checksForGround = 0;
		boolean foundGround = false;
		boolean canGenerate = false;
		int groundDepth = 0;

		while (true) {
			if (this.isCeiling) {
				++ti.y;
			} else {
				--ti.y;
			}

			if (checksForGround > 64 || ti.y < 0 || ti.y >= world.getHeightBlocks()) {
				break;
			}

			Block<?> block = world.getBlockType(ti);
			if (!foundGround) {
				if (block == Blocks.COBBLE_NETHERRACK) {
					floorY = ti.y;
					foundGround = true;
				}
			} else {
				++groundDepth;
				if (block != Blocks.COBBLE_NETHERRACK) {
					break;
				}

				if (groundDepth >= 6) {
					canGenerate = true;
					break;
				}
			}

			++checksForGround;
		}

		Block<?> block = world.getBlockType(ti);
		if (canGenerate && (block == Blocks.COBBLE_NETHERRACK || block == BTDBlocks.ICE_RUBYGLASS || block == Blocks.SOULSAND)) {
			if (this.isCeiling) {
				++floorY;
			} else {
				--floorY;
			}

			this.placeCrystal(world, random, new TilePos(ti.x, floorY, ti.z));
			return true;
		} else {
			return false;
		}
	}

	private float randomOffset(int length, @NonNull Random random) {
		int base = length / 16;
		return random.nextFloat() * (float) base;
	}

	private void placeCrystal(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePos) {
		float r = random.nextFloat();
		int length = this.minHeight + MathHelper.floor(Math.pow(r, 4.0F) * (double) this.heightMod);
		int base = length / 4;
		float rx = 0.0F;
		float ry = 0.0F;
		Block<?> column = Blocks.RUBYGLASS_COLUMN;
		Block<?> bed = Blocks.COBBLE_NETHERRACK_CRYSTALLINE;
		Block<?> node = Blocks.RUBYGLASS_NODE;
		TilePos ti = new TilePos(tilePos);
		ti.y = this.isCeiling ? ti.y + base / 2 : ti.y - base / 2;
		if (this.hasSpace(world, ti, base)) {
			float minBase = -((float) base / 2.0F);
			float maxBase = (float) base / 2.0F;
			float randX = this.randomOffset(length, random);
			float randZ = this.randomOffset(length, random);
			Vector3f[] pts = new Vector3f[]{new Vector3f(minBase - randX, 0.0F, minBase - randZ), new Vector3f(maxBase + randX, 0.0F, minBase - randZ), new Vector3f(minBase - randX, 0.0F, maxBase + randZ), new Vector3f(maxBase + randX, 0.0F, maxBase + randZ), new Vector3f(minBase - randX, (float) length, minBase - randZ), new Vector3f(maxBase + randX, (float) length, minBase - randZ), new Vector3f(minBase - randX, (float) length, maxBase + randZ), new Vector3f(maxBase + randX, (float) length, maxBase + randZ), new Vector3f(0.0F, (float) length + maxBase * 4.0F * (random.nextFloat() * 0.5F + 0.5F), 0.0F)};
			if (this.isCeiling) {
				rx = (float) Math.PI;
			}

			rx += (float) ((random.nextDouble() - (double) 0.5F) * (Math.PI / 2D)) * 0.8F;
			ry += (float) (random.nextDouble() * (double) 2.0F * Math.PI);
			Matrix4f matrix = Objects.requireNonNull((new Matrix4f()).rotateY(ry).rotateX(rx));
			int[][] faces = new int[][]{{0, 1, 3}, {0, 1, 5}, {1, 3, 7}, {3, 2, 6}, {2, 0, 4}, {4, 5, 8}, {5, 7, 8}, {7, 6, 8}, {6, 4, 8}};
			ConvexShapeVoxelizer voxelizer = new ConvexShapeVoxelizer(pts, faces, matrix, (worldPos, replacing, faceDistances) -> {
				if (replacing == Blocks.BEDROCK) {
					return null;
				} else {
					boolean ore = random.nextInt(100) == 0;
					boolean generateBed = false;
					float distancePctg = faceDistances[0] / (float) length;
					if (distancePctg < 0.5F) {
						float innerDistancePctg = distancePctg / 0.5F;
						if ((double) innerDistancePctg < (double) random.nextFloat() * (double) 0.75F) {
							generateBed = true;
						}
					}

					float minDistToSide = 10.0F;

					for (int i = 1; i <= 4; ++i) {
						minDistToSide = Math.min(faceDistances[i], minDistToSide);
					}

					float distFromCenter = maxBase - minDistToSide;
					float distanceToCenterPctg = minDistToSide / maxBase;
					if ((double) distFromCenter > (double) 0.5F) {
						return generateBed ? bed : (ore ? node : column);
					} else {
						float centerNoise = (float) Math.sin((double) distanceToCenterPctg * Math.PI * (double) 3.0F) * 0.5F;
						float finalNoise = random.nextFloat() + centerNoise;
						return (double) finalNoise > (double) 0.5F ? node : column;
					}
				}
			});
			voxelizer.voxelize(world, ti);
		}
	}

	private boolean hasSpace(@NonNull World world, @NonNull TilePosc tilePos, int base) {
		TilePos ti = new TilePos();

		for (ti.y = tilePos.y(); ti.y < tilePos.y() + base; ++ti.y) {
			for (ti.x = tilePos.x() - base; ti.x < tilePos.x() + base; ++ti.x) {
				for (ti.z = tilePos.z() - base; ti.z < tilePos.z() + base; ++ti.z) {
					if (world.getBlockType(ti) == Blocks.RUBYGLASS_COLUMN) {
						return false;
					}
				}
			}
		}

		return true;
	}
}
