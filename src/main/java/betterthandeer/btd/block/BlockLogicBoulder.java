package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicBoulder extends BlockLogicFlower {
	public BlockLogicBoulder(@NonNull Block<?> block) {
		super(block);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	}

	@Override
	public void onEntityCollision(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		entity.maxFireTicks = 100;
		entity.remainingFireTicks = 100;
	}

	@Override
	public boolean canStay(@NonNull World world, @NonNull TilePosc tilePos) {
		return this.mayPlaceOn(world.getBlockType(tilePos.down(new TilePos())));
	}

	@Override
	public boolean mayPlaceOn(@NonNull Block<?> block) {
		return block.isSolidRender();
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			default -> null;
		};
	}
}
