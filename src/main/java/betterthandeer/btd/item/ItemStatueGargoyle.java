package betterthandeer.btd.item;

import betterthandeer.btd.block.statue.gargoyle.TileEntityStatueGargoyle;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class ItemStatueGargoyle extends Item {
	protected final @NonNull Block<?> statueBlock;

	public ItemStatueGargoyle(@NonNull String name, @NonNull String namespaceId, int id, @NonNull Block<?> statueBlock) {
		super(name, namespaceId, id);
		this.maxStackSize = 64;
		this.statueBlock = statueBlock;
	}

	@Override
	public boolean onUseOnBlock(@NonNull ItemStack selfStack, @NonNull World world, @Nullable Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xHit, double yHit) {
		if (!world.canPlaceInsideBlock(blockPos)) {
			blockPos = blockPos.add(side.getDirection(), new TilePos());
		}

		if (!this.statueBlock.canPlaceAt(world, blockPos)) {
			return false;
		} else {
			world.noNeighborUpdate = true;
			world.setBlockTypeDataNotify(blockPos, this.statueBlock, MathHelper.floor((double) ((player.yRot + 180.0F) * 16.0F / 360.0F) + (double) 0.5F) & 15);
			world.noNeighborUpdate = false;
			world.notifyBlocksOfNeighborChange(blockPos, this.statueBlock);
			world.playBlockSoundEffect(player, (float) blockPos.x() + 0.5F, (float) blockPos.y() + 0.5F, (float) blockPos.z() + 0.5F, this.statueBlock, EnumBlockSoundEffectType.PLACE);
			this.statueBlock.onPlacedByMob(world, blockPos, side, player, xHit, yHit);

			TileEntity tileEntity = world.getTileEntity(blockPos);
			if (tileEntity instanceof TileEntityStatueGargoyle tileEntityStatueGargoyle) {
				if (side == Side.BOTTOM) {
					tileEntityStatueGargoyle.setPose(TileEntityStatueGargoyle.Pose.UPSIDE_DOWN);
				}
				if (selfStack.getData().containsKey("tileEntityData")) {
					tileEntityStatueGargoyle.readAdditionalData(selfStack.getData().getCompound("tileEntityData"));
				}
			}

			selfStack.consumeItem(player);
			return true;
		}
	}

	@Override
	public void onUseByActivator(@NonNull ItemStack selfStack, @NonNull World world, @NonNull TileEntityActivator activator, @NonNull Random random, @NonNull TilePosc blockPos, @NonNull Direction direction, double offX, double offY, double offZ) {
		if (!world.canPlaceInsideBlock(blockPos)) {
			blockPos = blockPos.add(direction, new TilePos());
		}

		if (this.statueBlock.canPlaceAt(world, blockPos)) {
			world.noNeighborUpdate = true;
			world.setBlockTypeNotify(blockPos, this.statueBlock);
			world.noNeighborUpdate = false;
			world.notifyBlocksOfNeighborChange(blockPos, this.statueBlock);
			world.playBlockSoundEffect(null, (float) blockPos.x() + 0.5F, (float) blockPos.y() + 0.5F, (float) blockPos.z() + 0.5F, this.statueBlock, EnumBlockSoundEffectType.PLACE);
			this.statueBlock.onPlacedOnSide(world, blockPos, direction.getSide(), 0.5F, 0.5F);
			if (this.statueBlock.isEntityTile && selfStack.getData().containsKey("tileEntityData")) {
				TileEntity tileEntity = world.getTileEntity(blockPos);
				if (tileEntity != null) {
					tileEntity.readAdditionalData(selfStack.getData().getCompound("tileEntityData"));
				}
			}

			selfStack.consumeItem(null);
		}
	}
}
