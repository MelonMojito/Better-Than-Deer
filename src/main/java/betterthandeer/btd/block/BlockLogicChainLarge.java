package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
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
}
