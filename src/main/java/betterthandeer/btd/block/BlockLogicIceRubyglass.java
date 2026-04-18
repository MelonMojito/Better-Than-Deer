package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class BlockLogicIceRubyglass extends BlockLogicTransparent {
	public BlockLogicIceRubyglass(@NonNull Block<?> block) {
		super(block, Materials.ICE);
		block.friction = 0.98F;
	}

	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK, SILK_TOUCH -> new ItemStack[]{new ItemStack(this)};
			default -> null;
		};
	}

	@Override
	public void onEntityCollision(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		if (world.isClientSide) return;

		int data = world.getBlockData(tilePos);
		int nextData = data + 1;

		if (nextData >= 3) {
			world.setBlockType(tilePos, Blocks.AIR);
		} else {
			world.setBlockDataNotify(tilePos, nextData);
		}
	}

	@Override
	public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		this.onEntityCollision(world, tilePos, entity);
	}

	@Override
	public float getAmbientOcclusionStrength(@NonNull WorldSource source, @NonNull TilePosc tilePos) {
		return 0.0F;
	}

	@Override
	public void updateTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand, boolean isRandomTick) {
		if (!world.isClientSide && world.dimension == Dimension.NETHER && rand.nextInt(10) == 0) {
			int dir = rand.nextInt(6);
			Direction direction = Direction.getDirectionById(dir);
			TilePos checkPos = tilePos.add(direction, new TilePos());
			Block<?> checkBlock = world.getBlockType(checkPos);
			if (checkBlock == Blocks.AIR) {
				world.spawnParticle("rubyglassLightning", tilePos.x(), tilePos.y(), tilePos.z(), 0.0F, 0.0F, 0.0F, dir, 1600.0F, true);
			}
		}
	}

	@Override
	public int getPistonPushReaction(@NonNull World world, @NonNull TilePosc tilePos) {
		return 0;
	}
}
