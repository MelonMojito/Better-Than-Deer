spackage betterthandeer.btd.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicMobSpawner;
import net.minecraft.core.block.BlockLogicMobSpawnerDeactivated;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicMobSpawner.class)
public class BetterSpawnerMixin extends BlockLogicMobSpawnerDeactivated {

	public BetterSpawnerMixin(@NonNull Block<?> block) {
		super(block);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			case SILK_TOUCH -> new ItemStack[]{new ItemStack(Blocks.MOBSPAWNER_DEACTIVATED)};
			default -> null;
		};
	}

}
