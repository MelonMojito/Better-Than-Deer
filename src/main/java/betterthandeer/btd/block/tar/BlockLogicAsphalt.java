package betterthandeer.btd.block.tar;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicAsphalt extends BlockLogic implements IPainted {
	public BlockLogicAsphalt(@NonNull Block<?> block) {
		super(block, Materials.STONE);
	}

	@Override
	public int getPlacedData(@Nullable Player player, @NonNull ItemStack itemStack, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
		return itemStack.getMetadata() & 15;
	}

	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(this, 1, data)};
	}

	public @NonNull DyeColor fromMetadata(int meta) {
		return DyeColor.colorFromBlockMeta(meta);
	}

	public int toMetadata(@NonNull DyeColor color) {
		return color.blockMeta;
	}

	public int stripColorFromMetadata(int meta) {
		return 0;
	}

	public void removeDye(@NonNull World world, @NonNull TilePosc tilePos) {
		world.setBlockDataNotify(tilePos, 0);
	}
}
