package betterthandeer.btd.block.acid;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class BlockLogicSulfur extends BlockLogic {

	public BlockLogicSulfur(@NonNull Block<?> block) {
		super(block, Materials.STONE);
		block.setTicking(true);
	}

	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK, SILK_TOUCH -> new ItemStack[]{new ItemStack(this)};
			default -> new ItemStack[]{new ItemStack(BTDItems.SULFUR, 1 + world.rand.nextInt(3))};
		};
	}

	@Override
	public int tickDelay() {
		return 20;
	}

	@Override
	public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
		world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay() + world.rand.nextInt(5) - world.rand.nextInt(5));
	}

	@Override
	public void onPlacedByWorld(@NonNull World world, @NonNull TilePosc tilePos) {
		world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay() + world.rand.nextInt(5) - world.rand.nextInt(5));
	}

	public boolean canMelt(@NonNull World world, @NonNull TilePosc tilePos) {
		boolean canMelt = false;
		TilePos queryPos = new TilePos();

		for (Direction dir : Direction.directions) {
			Block<?> block = world.getBlockType(tilePos.add(dir, queryPos));
			Material adjacentMaterial = block.getMaterial();
			if (adjacentMaterial == Materials.LAVA) return false;
			if (adjacentMaterial == Materials.WATER) return false;

			canMelt |= adjacentMaterial == BTDBlocks.ACID;
		}

		return canMelt;
	}

	@Override
	public void updateTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand, boolean isRandomTick) {
		if (this.canMelt(world, tilePos)) {
			world.setBlockTypeNotify(tilePos, BTDBlocks.FLUID_ACID_FLOWING);
		}

	}
}
