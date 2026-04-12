package betterthandeer.btd.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;
import org.joml.Vector2i;

import java.util.Random;

public class WorldFeatureBoulder extends WorldFeature {
	final double[][] RADIUS_MAPS = new double[][]{{(double)1.5F, (double)1.5F, (double)1.0F}, {(double)1.0F, (double)1.5F, (double)1.0F}};
	private final Block<?> boulderBlock;
	private final Block<?> extraBlock;

	public WorldFeatureBoulder(Block<?> boulderBlock, Block<?> extraBlock) {
		this.boulderBlock = boulderBlock;
		this.extraBlock = extraBlock;
	}

	public boolean place(World world, Random random, int x, int y, int z) {
		TilePos pos = new TilePos(x, y, z);

		while(world.isAirBlock(pos) && pos.y - 1 > 0) {
			pos.down();
		}

		double[] map = this.RADIUS_MAPS[random.nextInt(2)];
		if (this.hasSpace(world, pos.x, pos.y, pos.z)) {
			Vector2i centerXZ = new Vector2i(pos.x, pos.z);
			TilePos queryPos = new TilePos(pos.x, pos.y, pos.z);

			for(int i = 0; i < map.length; ++i) {
				this.generateRadius(world, centerXZ, queryPos, map[i], pos.y + i);
			}

			TilePos circlePos = new TilePos();

			for(circlePos.x = pos.x() - 2; circlePos.x < pos.x() + 2; ++circlePos.x) {
				for(circlePos.z = pos.z() - 2; circlePos.z < pos.z() + 2; ++circlePos.z) {
					for(circlePos.y = pos.y() - 4; circlePos.y < pos.y + 4; ++circlePos.y) {
						if (!world.isAirBlock(circlePos) && random.nextInt(8) == 0) {
							world.setBlockType(circlePos, extraBlock);
						}
					}
				}
			}
		}

		return true;
	}

	private Block<?> getBlockAt(Vector2i center, int x, int z) {
		return boulderBlock;
	}

	private void plotBlocks(World world, Vector2i center, TilePos queryPos, int x, int y, int z) {
		for(int xx = center.x - x; xx <= center.x + x; ++xx) {
			Block<?> block = this.getBlockAt(center, xx, center.y + z);
			world.setBlockType(queryPos.set(xx, queryPos.y, center.y + z), block);
			world.setBlockType(queryPos.set(xx, queryPos.y, center.y - z), block);
		}

		for(int xx = center.x - z; xx <= center.x + z; ++xx) {
			Block<?> block = this.getBlockAt(center, xx, center.y + x);
			world.setBlockType(queryPos.set(xx, y, center.y + x), block);
			world.setBlockType(queryPos.set(xx, y, center.y - x), block);
		}

	}

	private void generateRadius(World world, Vector2i center, TilePos queryPos, double r, int y) {
		int x = 0;
		int z = (int)r;
		double d = (double)3.0F - (double)2.0F * r;
		this.plotBlocks(world, center, queryPos, x, y, z);

		for(; z > x; this.plotBlocks(world, center, queryPos, x, y, z)) {
			++x;
			if (d > (double)0.0F) {
				--z;
				d = d + (double)(4 * (x - z)) + (double)10.0F;
			} else {
				d = d + (double)(4 * x) + (double)6.0F;
			}
		}

	}

	private boolean hasSpace(World world, int xc, int y, int zc) {
		TilePos queryPos = new TilePos();

		for(queryPos.y = y - 2; queryPos.y < y + 2; ++queryPos.y) {
			for(queryPos.x = xc - 2; queryPos.x < xc + 2; ++queryPos.x) {
				for(queryPos.z = zc - 2; queryPos.z < zc + 2; ++queryPos.z) {
					if (!world.isAirBlock(queryPos) && !world.getBlockType(queryPos).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
						return false;
					}
				}
			}
		}

		return true;
	}
}
