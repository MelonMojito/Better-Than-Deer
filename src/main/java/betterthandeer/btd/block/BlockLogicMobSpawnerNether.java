package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicMobSpawner;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicMobSpawnerNether extends BlockLogicMobSpawner {
	public BlockLogicMobSpawnerNether(@NonNull Block<?> block) {
		super(block);
		block.withEntity(TileEntityMobSpawner::new);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			case SILK_TOUCH -> new ItemStack[]{new ItemStack(BTDBlocks.MOBSPAWNER_NETHER_DEACTIVATED)};
			default -> null;
		};
	}
}
