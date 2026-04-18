package betterthandeer.btd.world;

import betterthandeer.btd.block.BlockLogicOverlayRocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldFeatureRocks extends WorldFeature {
	private final @NotNull Block<?> block;
	private final int count;
	private final boolean varyStackSize;

	@MethodParametersAnnotation(
		names = {"blockId", "count", "varyStackSize"}
	)
	public WorldFeatureRocks(@NotNull Block<?> block, int count, boolean varyStackSize) {
		this.block = block;
		this.count = count;
		this.varyStackSize = varyStackSize;
	}

	public boolean place(@NotNull World world, @NotNull Random random, int x, int y, int z) {
		TilePos p = new TilePos();
		TilePos queryPos = new TilePos();

		for(int i = 0; i < this.count; ++i) {
			p.set(x + random.nextInt(8) - random.nextInt(8), y + random.nextInt(4) - random.nextInt(4), z + random.nextInt(8) - random.nextInt(8));
			if (world.isAirBlock(p) && BlockLogicOverlayRocks.canSpawnOn(world.getBlockType(p.down(queryPos)))) {
				int stackSize;
				if (this.varyStackSize) {
					stackSize = getStackSize(random);
				} else {
					stackSize = 0;
				}

				world.setBlockTypeData(p, this.block, BlockLogicOverlayRocks.setCount(0, stackSize));
			}
		}

		return true;
	}

	private static int getStackSize(@NotNull Random random) {
		int odds = random.nextInt(18);
		if (odds < 8) {
			return 0;
		} else {
			return odds < 14 ? 1 : 2;
		}
	}
}
