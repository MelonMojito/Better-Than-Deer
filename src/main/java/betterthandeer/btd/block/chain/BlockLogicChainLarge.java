package betterthandeer.btd.block.chain;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

import java.util.Optional;

public class BlockLogicChainLarge extends BlockLogic {

	/*
		So, the way I set up the metadata, each side is a bit.
		0000 0000
		DUNS WE||
			   Direction
	 */

	public static final int PLACEMENT_DOWN_UP = 0b00;
	public static final int PLACEMENT_NORTH_SOUTH = 0b01;
	public static final int PLACEMENT_WEST_EAST = 0b10;

	public BlockLogicChainLarge(@NotNull Block<?> block) {
		super(block, Materials.METAL);
	}

	@Override
	public boolean blocksLight() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isClimbable(@NotNull World world, @NotNull TilePosc tilePos) {
		return true;
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	public void updateData(World world, TilePosc tile) {
		int meta = world.getBlockData(tile) & 0b0000_0011;

		for (Direction dir : Direction.directions) {
			var connected = world.getBlockType(new TilePos(tile).add(dir)) == this.block ? 1 : 0;

			if ( (meta&0b11) == PLACEMENT_DOWN_UP && (dir == Direction.DOWN || dir == Direction.UP)) {
				connected = 1;
			}

			if ( (meta&0b11) == PLACEMENT_WEST_EAST && (dir == Direction.WEST || dir == Direction.EAST)) {
				connected = 1;
			}

			if ( (meta&0b11) == PLACEMENT_NORTH_SOUTH && (dir == Direction.SOUTH || dir == Direction.NORTH)) {
				connected = 1;
			}

			meta = meta | ( connected << (7 - dir.getId()) );
		}

		if (usedAxis(meta) > 1) {
			for (Direction dir : Direction.directions) {
				var blockType = world.getBlockType(new TilePos(tile).add(dir));
				if ( !blockType.solid() && blockType != this.block) {
					meta = meta & ~( 1 << (7 - dir.getId()) );
				}
			}
		}

		if (meta != world.getBlockData(tile)) {
			world.setBlockDataNotify(tile, meta);
		}
	}

	@Override
	public void onNeighborChanged(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Block<?> block) {
		updateData(world, tilePos);
	}

	@Override
	public void onPlacedOnSide(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Side side, double xHit, double yHit) {
		var dir = side.getDirection();

		if (dir == Direction.WEST || dir == Direction.EAST) {
			world.setBlockDataNotify(tilePos, PLACEMENT_WEST_EAST);
		}

		else if (dir == Direction.NORTH || dir == Direction.SOUTH) {
			world.setBlockDataNotify(tilePos, PLACEMENT_NORTH_SOUTH);
		}

		else {
			world.setBlockDataNotify(tilePos, PLACEMENT_DOWN_UP);
		}

		updateData(world, tilePos);
	}

	@Override
	public void onPlacedByWorld(@NotNull World world, @NotNull TilePosc tilePos) {
		updateData(world, tilePos);
	}

	public static int usedAxis(int metadata) {
		int usedAxis = 0;

		for (int offset = 2; offset < 8; offset += 2) {
			if ((metadata & (0b11 << offset)) > 0) {
				usedAxis++;
			}
		}

		return usedAxis;
	}

	public static boolean shouldDisplayLinking(int metadata) {
		return usedAxis(metadata) > 1;
	}

	@Override
	public @Nullable AABBdc getCollisionAABB(@NotNull WorldSource source, @NotNull TilePosc tilePos) {
		return null;
	}

	@Override
	public @NotNull AABBdc getBoundsFromState(@NotNull WorldSource source, @NotNull TilePosc tilePos) {
		if (shouldDisplayLinking(source.getBlockData(tilePos))) {
			return super.getBoundsFromState(source, tilePos);
		}

		int data = source.getBlockData(tilePos);

		if ((data & 0b1100_0000) > 1) {
			return new AABBd(0.2, 0, 0.2, 0.8, 1, 0.8);
		}

		if ((data & 0b0000_1100) > 1) {
			return new AABBd(0, 0.2, 0.2, 1, 0.8, 0.8);
		}

		if ((data & 0b0011_0000) > 1) {
			return new AABBd(0.2, 0.2, 0, 0.8, 0.8, 1);
		}

		return new AABBd(0.2, 0.2, 0.2, 0.8, 0.8, 0.8);
	}

	public void onEntityCollision(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Entity entity) {
		entity.xd *= 0.4;
		entity.zd *= 0.4;
	}

	@Override
	public @NotNull Item asItem() {
		return BTDItems.CHAIN_LARGE;
	}

	@Override
	public @NotNull ItemStack getDefaultStack() {
		return new ItemStack(BTDItems.CHAIN_LARGE);
	}

	@Override
	public @NotNull ItemStack @Nullable [] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return new ItemStack[]{this.getDefaultStack()};
	}

	public boolean onInteracted(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Player player, @Nullable Side side, double xHit, double yHit) {
		ItemStack heldItem = player.getHeldItem();

		if (heldItem == null) {
			this.pickupChain(world, tilePos, player);
			return true;
		}

		else if (heldItem.getItem().equals(BTDItems.CHAIN_LARGE)) {
			return this.placeChain(heldItem, world, player, tilePos, Optional.ofNullable(side).orElse(Side.TOP));
		}

		return false;
	}

	public void pickupChain(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Player player) {
		if (!world.isClientSide && world.getBlockType(tilePos) == this.block) {
			TilePos queryPos = new TilePos(tilePos);
			int highestRope = tilePos.y();

			while(queryPos.y > 0) {
				--queryPos.y;
				Block<?> block = world.getBlockType(queryPos);
				if (block != this.block) {
					++queryPos.y;
					break;
				}
			}

			int lowestRope = queryPos.y;
			int freeSpace = 0;

			for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
				ItemStack stack = player.inventory.mainInventory[i];
				if (stack == null) {
					freeSpace += BTDItems.CHAIN_LARGE.getItemStackLimit(null);
				} else if (stack.getItem().equals(BTDItems.CHAIN_LARGE)) {
					freeSpace += BTDItems.CHAIN_LARGE.getItemStackLimit(null) - stack.stackSize;
				}
			}

			freeSpace = Math.min(freeSpace, 1);
			int ropesCollected = 0;

			for(queryPos.y = lowestRope; queryPos.y <= highestRope && ropesCollected < freeSpace; ++queryPos.y) {
				world.setBlockTypeNotify(queryPos, Blocks.AIR);
				++ropesCollected;
			}

			if (player.getGamemode().hasBlockConsumption()) {
				ItemStack stack = new ItemStack(BTDItems.CHAIN_LARGE, ropesCollected);
				player.inventory.insertItem(stack, true);
				if (stack.stackSize > 0) {
					player.dropPlayerItem(stack);
				}
			}

			world.playBlockSoundEffect(player, (double)tilePos.x() + (double)0.5F, (double)highestRope + (double)0.5F, (double)tilePos.z() + (double)0.5F, this.block, EnumBlockSoundEffectType.PLACE);
		}
	}

	public boolean placeChain(
			@NotNull ItemStack selfStack,
			@NotNull World world,
			@Nullable Player player,
			@NotNull TilePosc blockPos,
			@NotNull Side side
		) {

		TilePos bp = new TilePos(blockPos);

		if (player != null && !player.isSneaking()) {
			TilePos tempPos = new TilePos(bp);

			while(tempPos.y > 0) {
				--tempPos.y;

				if (world.canBlockIdBePlacedAt(this.block.id(), tempPos, false, side)) {
					bp.y = tempPos.y;
					break;
				}

				if (world.getBlockType(tempPos) != this.block) {
					if (side != Side.TOP) {
						return false;
					}

					++tempPos.y;
					break;
				}
			}

			bp.set(tempPos);
		}

		if (!world.canPlaceInsideBlock(bp)) {
			bp = bp.add(side.getDirection(), new TilePos());
		}

		if (bp.y >= 0 && bp.y < world.getHeightBlocks()) {
			if (world.canBlockIdBePlacedAt(this.block.id(), bp, false, side) && selfStack.consumeItem(player)) {

				world.setBlockType(bp, this.block);
				world.playBlockSoundEffect(player, (float)bp.x + 0.5F, (float)bp.y + 0.5F, (float)bp.z + 0.5F, this.block, EnumBlockSoundEffectType.PLACE);

				return true;
			}

			else return false;
		}

		else return false;
	}
}
