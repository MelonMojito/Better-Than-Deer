package betterthandeer.btd.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.BlockLogicSoulcatcher;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicSoulcatcher.class)
public class BetterSoulcatcherMixin extends BlockLogicFlower {

	public BetterSoulcatcherMixin(@NonNull Block<?> block) {
		super(block);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(this)};
	}

}
