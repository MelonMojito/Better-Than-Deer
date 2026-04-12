package betterthandeer.btd.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRubyglassSprout;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicRubyglassSprout.class)
public class BetterRubyglassSproutMixin extends BlockLogic {

	public BetterRubyglassSproutMixin(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			default ->
				world.rand.nextInt(25) == 0 ? new ItemStack[]{new ItemStack(Items.RUBYGLASS_CRYSTAL, 1)} : null;
		};
	}
}
