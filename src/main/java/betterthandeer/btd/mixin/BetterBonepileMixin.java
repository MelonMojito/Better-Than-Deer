package betterthandeer.btd.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicBonepile;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicBonepile.class)
public class BetterBonepileMixin extends BlockLogicFlower {

	public BetterBonepileMixin(@NonNull Block<?> block) {
		super(block);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		int quantity = world.rand.nextInt(3) + 1;
		return switch (dropCause) {
			case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			default -> world.rand.nextInt(2) == 0 ? new ItemStack[]{new ItemStack(Items.BONE, quantity)} : null;
		};
	}
}
