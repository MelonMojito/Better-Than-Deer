package betterthandeer.btd.item;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.entity.rock.ProjectileRock;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.IDispensable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class ItemRock extends Item implements IDispensable {
	public ItemRock(String name, String namespaceId, int id) {
		super(name, namespaceId, id);
		this.maxStackSize = 64;
	}

	@Override
	public @Nullable ItemStack onUse(@NonNull ItemStack selfStack, @NonNull World world, @NonNull Player player) {
		selfStack.consumeItem(player);
		world.playSoundAtEntity(player, player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			world.entityJoinedWorld(new ProjectileRock(world, player));
		}

		return selfStack;
	}

	@Override
	public boolean onUseOnBlock(@NonNull ItemStack selfStack, @NonNull World world, @Nullable Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xHit, double yHit) {
		if (player == null) {
			return false;
		} else {
			Block<?> block = world.getBlockType(blockPos);
			int meta = world.getBlockData(blockPos);
			if (block != BTDBlocks.OVERLAY_ROCKS && block.hasTag(BlockTags.PLACE_OVERWRITES)) {
				block = Blocks.AIR;
				meta = 0;
			}

			if (selfStack.stackSize <= 0) {
				return false;
			} else if (blockPos.y() == world.getHeightBlocks() - 1 && BTDBlocks.OVERLAY_ROCKS.getMaterial().isSolid()) {
				return false;
			} else {
				if (block == BTDBlocks.OVERLAY_ROCKS && side == Side.TOP) {
					int newMeta = meta + 1;
					if (!world.canPlaceOnSurfaceOfBlock(blockPos.down(new TilePos()))) {
						return false;
					}

					if (newMeta < 3) {
						world.setBlockTypeDataNotify(blockPos, BTDBlocks.OVERLAY_ROCKS, newMeta);
						world.playBlockSoundEffect(player, (float) blockPos.x() + 0.5F, (float) blockPos.y() + 0.5F, (float) blockPos.z() + 0.5F, BTDBlocks.OVERLAY_ROCKS, EnumBlockSoundEffectType.PLACE);
						selfStack.consumeItem(player);
						return true;
					}
				}

				if (block != Blocks.AIR) {
					blockPos = blockPos.add(side.getDirection(), new TilePos());
					block = world.getBlockType(blockPos);
					meta = world.getBlockData(blockPos);
				}

				if (block == BTDBlocks.OVERLAY_ROCKS) {
					int newMeta = meta + 1;
					AABBdc bbBox = new AABBd(blockPos.x(), blockPos.y(), blockPos.z(), (float) blockPos.x() + 1.0F, (float) blockPos.y() + (float) (2 * (newMeta + 1)) / 16.0F, (float) blockPos.z() + 1.0F);
					if (!world.checkIfAABBIsClear(bbBox) || !world.canPlaceOnSurfaceOfBlock(blockPos.down(new TilePos()))) {
						return false;
					}

					if (newMeta < 3) {
						world.setBlockTypeDataNotify(blockPos, BTDBlocks.OVERLAY_ROCKS, newMeta);
						world.playBlockSoundEffect(player, (float) blockPos.x() + 0.5F, (float) blockPos.y() + 0.5F, (float) blockPos.z() + 0.5F, BTDBlocks.OVERLAY_ROCKS, EnumBlockSoundEffectType.PLACE);
						selfStack.consumeItem(player);
						return true;
					}
				}

				if (world.canBlockIdBePlacedAt(BTDBlocks.OVERLAY_ROCKS.id(), blockPos, false, side) && world.canPlaceOnSurfaceOfBlock(blockPos.down(new TilePos())) && world.setBlockTypeNotify(blockPos, BTDBlocks.OVERLAY_ROCKS)) {
					BTDBlocks.OVERLAY_ROCKS.onPlacedByMob(world, blockPos, side, player, xHit, yHit);
					world.playBlockSoundEffect(player, (float) blockPos.x() + 0.5F, (float) blockPos.y() + 0.5F, (float) blockPos.z() + 0.5F, BTDBlocks.OVERLAY_ROCKS, EnumBlockSoundEffectType.PLACE);
					selfStack.consumeItem(player);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public void onUseByActivator(@NonNull ItemStack selfStack, @NonNull World world, @NonNull TileEntityActivator activator, @NonNull Random random, @NonNull TilePosc blockPos, @NonNull Direction direction, double offX, double offY, double offZ) {
		ProjectileRock projectileRock = new ProjectileRock(world, (double) blockPos.x() + offX, (double) blockPos.y() + offY, (double) blockPos.z() + offZ);
		projectileRock.setHeading((double) direction.getOffsetX() * 0.6, direction.getOffsetY() == 0 ? 0.1 : (double) direction.getOffsetY() * 0.6, (float) direction.getOffsetZ() * 0.6F, 1.1F, 6.0F);
		world.entityJoinedWorld(projectileRock);
		--selfStack.stackSize;
	}

	@Override
	public void onDispensed(@NonNull ItemStack selfStack, @NonNull World world, @NonNull Random random, @NonNull Direction direction, double x, double y, double z) {
		if (selfStack.consumeItem(null)) {
			ProjectileRock projectileRock = new ProjectileRock(world, x, y, z);
			projectileRock.setHeading(direction.getOffsetX(), (double) direction.getOffsetY() + 0.1, direction.getOffsetZ(), 1.1F, 6.0F);
			world.entityJoinedWorld(projectileRock);
		}

	}
}

