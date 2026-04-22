package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicBrimstone extends BlockLogic {
	public BlockLogicBrimstone(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
	}

	@Override
	public ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return dropCause == EnumDropCause.PISTON_CRUSH ? new ItemStack[]{new ItemStack(Blocks.BRIMSAND, world.rand.nextInt(2) + 1)} : super.getBreakResult(world, dropCause, data, tileEntity);
	}
}
