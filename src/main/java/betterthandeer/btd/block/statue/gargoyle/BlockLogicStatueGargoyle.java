package betterthandeer.btd.block.statue.gargoyle;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class BlockLogicStatueGargoyle extends BlockLogic {
	public final @Nullable Supplier<@NonNull Item> droppedItem;

	public BlockLogicStatueGargoyle(@NonNull Block<?> block, @NonNull Material material, @Nullable Supplier<@NonNull Item> droppedItem) {
		super(block, material);
		this.droppedItem = droppedItem;
		block.withEntity(TileEntityStatueGargoyle::new);
	}

	@Override
	public boolean onInteracted(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Player player, @Nullable Side side, double xHit, double yHit) {
		TileEntityStatueGargoyle statueEntity = this.getTileEntity(world, tilePos);
		if (statueEntity == null) {
			return false;
		}
		statueEntity.nextPose();
		world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, (double) tilePos.x() + (double) 0.5F, (double) tilePos.y() + (double) 0.5F, (double) tilePos.z() + (double) 0.5F,
			"btd:scrape", 0.7F, 0.2F + world.rand.nextFloat() * 0.4F);
		return true;
	}

	@Nullable
	public TileEntityStatueGargoyle getTileEntity(@NonNull World world, @NonNull TilePosc tilePos) {
		Block<?> block = world.getBlockType(tilePos);
		if (block.getLogic() instanceof BlockLogicStatueGargoyle) {
			TileEntity tileEntity = world.getTileEntity(tilePos);
			return !(tileEntity instanceof TileEntityStatueGargoyle tileEntityStatueGargoyle) ? null : tileEntityStatueGargoyle;
		} else {
			return null;
		}
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public @NotNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, @NonNull TilePosc tilePos, int data, @Nullable TileEntity tileEntity) {
		if (droppedItem == null) {
			return null;
		} else {
			return dropCause != EnumDropCause.IMPROPER_TOOL ? new ItemStack[]{new ItemStack(droppedItem.get())} : null;
		}
	}


	@Override
	public int getPistonPushReaction(@NonNull World world, @NonNull TilePosc tilePos) {
		return 1;
	}
}
