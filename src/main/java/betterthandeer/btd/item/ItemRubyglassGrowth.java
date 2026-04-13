package betterthandeer.btd.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ItemRubyglassGrowth extends Item {

	protected final @NonNull Block<?> bottomBlock;
	protected final @NonNull Block<?> topBlock;

	public ItemRubyglassGrowth(@NonNull String translationKey, @NonNull String namespaceId, int id, @NonNull Block<?> bottomBlock, @NonNull Block<?> topBlock) {
		super(translationKey, namespaceId, id);
		this.maxStackSize = 64;
		this.bottomBlock = bottomBlock;
		this.topBlock = topBlock;
	}

	@Override
	public boolean onUseOnBlock(@NonNull ItemStack selfStack, @NonNull World world, @Nullable Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xHit, double yHit) {
		if (player == null) {
			return false;
		}

		TilePos placePos = new TilePos(blockPos);
		if (!world.canPlaceInsideBlock(placePos)) {
			placePos = placePos.add(side.getDirection(), new TilePos());
		}

		Direction growthDir = side.getDirection();
		int meta = BlockLogicRotatable.setDirection(0, growthDir);

		TilePos topPos = placePos.add(growthDir, new TilePos());

		if (!world.canPlaceInsideBlock(placePos) || !world.canPlaceInsideBlock(topPos)) {
			return false;
		}

		if (!bottomBlock.getLogic().canPlaceAt(world, placePos)) {
			return false;
		}

		world.noNeighborUpdate = true;

		world.setBlockTypeDataNotify(placePos, bottomBlock, meta);
		world.setBlockTypeDataNotify(topPos, topBlock, meta);

		world.noNeighborUpdate = false;

		world.notifyBlocksOfNeighborChange(placePos, bottomBlock);
		world.notifyBlocksOfNeighborChange(topPos, topBlock);

		world.playBlockSoundEffect(player, (float) placePos.x() + 0.5F, (float) placePos.y() + 0.5F, (float) placePos.z() + 0.5F, bottomBlock, EnumBlockSoundEffectType.PLACE);

		bottomBlock.onPlacedOnSide(world, placePos, side, xHit, yHit);
		topBlock.onPlacedOnSide(world, topPos, side, xHit, yHit);

		selfStack.consumeItem(player);
		return true;
	}
}
