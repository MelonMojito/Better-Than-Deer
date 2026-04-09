package betterthandeer.btd.world;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNewNetherSpring extends WorldFeature {
	private final int blockId;

	@MethodParametersAnnotation(
		names = {"blockId"}
	)
	public WorldFeatureNewNetherSpring(int blockId) {
		this.blockId = blockId;
	}

	public boolean place(World world, Random random, int x, int y, int z) {
		if (world.getBlock(x, y + 1, z).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
			return false;
		} else if (world.getBlockId(x, y, z) != 0 && world.getBlock(x, y, z).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
			return false;
		} else {
			int l = 0;
			if (world.getBlock(x - 1, y, z).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
				++l;
			}

			if (world.getBlock(x + 1, y, z).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
				++l;
			}

			if (world.getBlock(x, y, z - 1).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
				++l;
			}

			if (world.getBlock(x, y, z + 1).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
				++l;
			}

			if (world.getBlock(x, y - 1, z).hasTag(BlockTags.NETHER_SURFACE_BLOCK)) {
				++l;
			}

			int i1 = 0;
			if (world.isAirBlock(x - 1, y, z)) {
				++i1;
			}

			if (world.isAirBlock(x + 1, y, z)) {
				++i1;
			}

			if (world.isAirBlock(x, y, z - 1)) {
				++i1;
			}

			if (world.isAirBlock(x, y, z + 1)) {
				++i1;
			}

			if (world.isAirBlock(x, y - 1, z)) {
				++i1;
			}

			if (l == 4 && i1 == 1) {
				world.setBlockWithNotify(x, y, z, this.blockId);
				world.scheduledUpdatesAreImmediate = true;
				Blocks.blocksList[this.blockId].updateTick(world, x, y, z, random, false);
				world.scheduledUpdatesAreImmediate = false;
			}

			return true;
		}
	}
}
