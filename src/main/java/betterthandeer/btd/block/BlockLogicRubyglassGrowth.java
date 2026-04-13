package betterthandeer.btd.block;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicRubyglassGrowth extends BlockLogic {
	public final boolean isTop;

	public BlockLogicRubyglassGrowth(Block<?> block, boolean isTop) {
		super(block, Materials.PLANT);
		this.isTop = isTop;
		block.setTicking(true);
	}

	/**
	 * Helper to check if the block at targetPos can support this growth.
	 * Bottoms need a solid block; Tops need the Bottom growth block.
	 */
	private boolean isSupported(World world, TilePosc targetPos, Direction myDir) {
		if (!isTop) {
			return world.isBlockNormalCube(targetPos);
		} else {
			Block<?> bottomBlock = BTDBlocks.RUBYGLASS_GROWTH_BOTTOM;
			if (world.getBlockType(targetPos) == bottomBlock) {
				int bottomMeta = world.getBlockData(targetPos);
				return metaToDirection(bottomMeta) == myDir;
			}
			return false;
		}
	}

	@Override
	public void onPlacedOnSide(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side, double xHit, double yHit) {
		TilePos temp = new TilePos();
		Direction placedDir = side.getDirection();

		if (!isSupported(world, tilePos.add(placedDir.getOpposite(), temp), placedDir)) {
			world.setBlockDataNotify(tilePos, BlockLogicRotatable.setDirection(0, this.getOrientation(world, tilePos)));
		} else {
			world.setBlockDataNotify(tilePos, BlockLogicRotatable.setDirection(0, placedDir));
		}
	}

	private Direction getOrientation(World world, TilePosc tilePos) {
		TilePos temp = new TilePos();
		for (Side side : Side.values()) {
			Direction dir = side.getDirection();
			if (isSupported(world, tilePos.add(dir.getOpposite(), temp), dir)) {
				return dir;
			}
		}
		return Side.TOP.getDirection();
	}

	@Override
	public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
		if (!canStay(world, tilePos)) {
			world.setBlockTypeNotify(tilePos, Blocks.AIR);
		}
	}

	@Override
	public void onRemoved(@NonNull World world, @NonNull TilePosc tilePos, int data) {
		Direction dir = metaToDirection(data);
		TilePos otherPos = new TilePos();

		if (this.isTop) {
			tilePos.add(dir.getOpposite(), otherPos);
			if (world.getBlockType(otherPos) == BTDBlocks.RUBYGLASS_GROWTH_BOTTOM) {
				world.setBlockTypeNotify(otherPos, Blocks.AIR);
			}
		} else {
			tilePos.add(dir, otherPos);
			if (world.getBlockType(otherPos) == BTDBlocks.RUBYGLASS_GROWTH_TOP) {
				world.setBlockTypeNotify(otherPos, Blocks.AIR);
			}
		}
		super.onRemoved(world, tilePos, data);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public @Nullable AABBd getCollisionAABB(@NonNull WorldSource source, @NonNull TilePosc tilePos) {
		return null;
	}

	@Override
	public boolean canPlaceAt(@NonNull World world, @NonNull TilePosc tilePos) {
		TilePos temp = new TilePos();
		for (Side side : Side.values()) {
			Direction dir = side.getDirection();
			if (isSupported(world, tilePos.add(dir.getOpposite(), temp), dir)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canStay(@NonNull World world, @NonNull TilePosc tilePos) {
		int meta = world.getBlockData(tilePos);
		Direction dir = metaToDirection(meta);
		TilePos temp = new TilePos();
		return isSupported(world, tilePos.add(dir.getOpposite(), temp), dir);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @org.jspecify.annotations.Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(BTDItems.RUBYGLASS_GROWTH)};
			default -> world.rand.nextInt(25) == 0 ? new ItemStack[]{new ItemStack(Items.RUBYGLASS_CRYSTAL, 1)} : null;
		};
	}

	public static Direction metaToDirection(int meta) {
		return Direction.getDirectionById(meta & 7);
	}
}
