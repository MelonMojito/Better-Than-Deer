package betterthandeer.btd.world.chunk.feature;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherPatchBelow extends WorldFeature {
	private final int blockId;

	@MethodParametersAnnotation(
		names = {"blockId"}
	)

	public WorldFeatureNetherPatchBelow(int blockId) {
		this.blockId = blockId;
	}

	public boolean place(World world, Random random, int x, int y, int z) {
		while (world.getBlockId(x, y, z) == 0 && y > 0) {
			--y;
		}

		for (int i = 0; i < 8; ++i) {
			int x1 = x + random.nextInt(4) - random.nextInt(4);
			int y1 = y + random.nextInt(4) - random.nextInt(4);
			int z1 = z + random.nextInt(4) - random.nextInt(4);

			int targetY = y1 - 1;

			if (world.isAirBlock(x1, y1, z1) && world.getBlockId(x1, targetY, z1) == Blocks.BLOCK_ASH.id()) {

				boolean surroundedByAsh =
					world.getBlockId(x1 + 1, targetY, z1) == Blocks.BLOCK_ASH.id() &&
						world.getBlockId(x1 - 1, targetY, z1) == Blocks.BLOCK_ASH.id() &&
						world.getBlockId(x1, targetY, z1 + 1) == Blocks.BLOCK_ASH.id() &&
						world.getBlockId(x1, targetY, z1 - 1) == Blocks.BLOCK_ASH.id();

				if (surroundedByAsh && Blocks.blocksList[this.blockId].canBlockStay(world, x1, y1, z1)) {
					world.setBlock(x1, targetY, z1, this.blockId);
					world.setBlock(x1, y1, z1, Blocks.AIR.id());
				}
			}
		}

		return true;
	}
}
