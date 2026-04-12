package betterthandeer.btd.world;

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
		while(world.getBlockId(x, y, z) == 0 && y > 0) {
			--y;
		}

		for(int i = 0; i < 8; ++i) {
			int x1 = x + random.nextInt(4) - random.nextInt(4);
			int y1 = y + random.nextInt(4) - random.nextInt(4);
			int z1 = z + random.nextInt(4) - random.nextInt(4);
			if (world.isAirBlock(x1, y1, z1) && world.getBlockId(x1, y1 - 1, z1) == Blocks.BLOCK_ASH.id() && Blocks.blocksList[this.blockId].canBlockStay(world, x1, y1, z1)) {
				world.setBlock(x1, y1 - 1, z1, this.blockId);
			}
		}

		return true;
	}
}
