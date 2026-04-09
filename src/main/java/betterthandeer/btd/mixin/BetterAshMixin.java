package betterthandeer.btd.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicAsh;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicAsh.class)
public class BetterAshMixin extends BlockLogic {
	public BetterAshMixin(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
	}


	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(this)};
	}
}
