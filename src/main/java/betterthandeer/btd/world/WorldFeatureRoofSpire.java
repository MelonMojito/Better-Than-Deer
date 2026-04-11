package betterthandeer.btd.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;
import org.joml.Vector3i;

import java.util.Random;

public class WorldFeatureRoofSpire extends WorldFeature {
	private final Block<?> boulderBlock;

	public WorldFeatureRoofSpire(Block<?> boulderBlock) {
		this.boulderBlock = boulderBlock;
	}

	public boolean place(World world, Random random, int x, int y, int z) {
		TilePos pos = new TilePos(x, y, z);

		while (world.isAirBlock(pos) && pos.y < 235) {
			pos.up();
		}

		if (this.hasSpace(world, pos.x, pos.y, pos.z)) {
			Vector3i highestOffset = new Vector3i(0, 0, 0);
			TilePos centerPos = new TilePos(pos.x, pos.y, pos.z);
			TilePos queryPos = new TilePos();

			for (int x1 = 0; x1 < 2; ++x1) {
				for (int z1 = 0; z1 < 2; ++z1) {
					int height = random.nextInt(6) + 4;
					if (height > highestOffset.y) {
						highestOffset.set(x1, height, z1);
					}

					for (int y1 = 0; y1 < height; ++y1) {
						centerPos.add(x1, -y1, z1, queryPos);
						world.setBlockType(queryPos, boulderBlock);
					}
				}
			}

			for (int y1 = 0; y1 < 2; ++y1) {
				centerPos.add(highestOffset.x, -(highestOffset.y + y1), highestOffset.z, queryPos);
				world.setBlockType(queryPos, boulderBlock);
			}
		}

		return true;
	}

	private boolean hasSpace(World world, int xc, int y, int zc) {
		TilePos queryPos = new TilePos();

		for (queryPos.y = y - 3; queryPos.y > y - 9; --queryPos.y) {
			for (queryPos.x = xc - 3; queryPos.x < xc + 3; ++queryPos.x) {
				for (queryPos.z = zc - 3; queryPos.z < zc + 3; ++queryPos.z) {
					if (!world.isAirBlock(queryPos) && !world.getBlockType(queryPos).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
						return false;
					}
				}
			}
		}

		return true;
	}
}
