package betterthandeer.btd.world.chunk.feature;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;

import java.util.Random;

public class WorldFeatureRubyglassGrowthPatch extends WorldFeature {

	public WorldFeatureRubyglassGrowthPatch() {
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		for (int i1 = 0; i1 < 20; ++i1) {
			int x1 = x + random.nextInt(4) - random.nextInt(3);
			int y1 = y + random.nextInt(4) - random.nextInt(3);
			int z1 = z + random.nextInt(4) - random.nextInt(3);

			if (world.isAirBlock(x1, y1, z1)) {
				TilePos bottomPos = new TilePos(x1, y1, z1);
				TilePos temp = new TilePos();

				for (Side side : Side.values()) {
					Direction growthDir = side.getDirection();
					TilePos supportPos = bottomPos.add(growthDir.getOpposite(), temp);
					if (world.isBlockNormalCube(supportPos)) {

						TilePos topPos = bottomPos.add(growthDir, new TilePos());

						if (world.isAirBlock(topPos)) {
							int meta = BlockLogicRotatable.setDirection(0, growthDir);

							world.noNeighborUpdate = true;

							world.setBlockAndMetadata(bottomPos.x(), bottomPos.y(), bottomPos.z(), BTDBlocks.RUBYGLASS_GROWTH_BOTTOM.id(), meta);
							world.setBlockAndMetadata(topPos.x(), topPos.y(), topPos.z(), BTDBlocks.RUBYGLASS_GROWTH_TOP.id(), meta);

							world.noNeighborUpdate = false;

							world.notifyBlocksOfNeighborChange(bottomPos, BTDBlocks.RUBYGLASS_GROWTH_BOTTOM);
							world.notifyBlocksOfNeighborChange(topPos, BTDBlocks.RUBYGLASS_GROWTH_TOP);

							break;
						}
					}
				}
			}
		}

		return true;
	}
}
