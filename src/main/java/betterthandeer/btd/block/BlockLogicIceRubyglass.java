package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockLogicIceRubyglass extends BlockLogicTransparent {
	public BlockLogicIceRubyglass(@NotNull Block<?> block) {
		super(block, Materials.ICE);
		block.friction = 0.98F;
	}

	@Override
	public ItemStack[] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK, SILK_TOUCH -> new ItemStack[]{new ItemStack(this)};
			default -> null;
		};
	}

	@Override
	public float getAmbientOcclusionStrength(@NotNull WorldSource source, @NotNull TilePosc tilePos) {
		return 0.0F;
	}

	@Override
	public void updateTick(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand, boolean isRandomTick) {
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
	public int getPistonPushReaction(@NotNull World world, @NotNull TilePosc tilePos) {
		return 0;
	}
}
