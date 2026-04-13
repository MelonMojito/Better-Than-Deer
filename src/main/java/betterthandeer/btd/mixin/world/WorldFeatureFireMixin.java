package betterthandeer.btd.mixin.world;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFire;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(WorldFeatureFire.class)
public class WorldFeatureFireMixin extends WorldFeature {

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		for (int i = 0; i < 32; ++i) {
			int x1 = x + random.nextInt(8) - random.nextInt(8);
			int y1 = y + random.nextInt(4) - random.nextInt(4);
			int z1 = z + random.nextInt(8) - random.nextInt(8);
			if (world.isAirBlock(x1, y1, z1) && (world.getBlock(x1, y1 - 1, z1).hasTag(BlockTags.NETHER_SURFACE_BLOCK))) {
				world.setBlockWithNotify(x1, y1, z1, Blocks.FIRE.id());
				world.setBlock(x1, y1 - 1, z1, Blocks.COBBLE_NETHERRACK.id());
			}
		}

		return true;
	}
}
