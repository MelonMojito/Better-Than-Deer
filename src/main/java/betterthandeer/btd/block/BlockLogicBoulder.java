package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class BlockLogicBoulder extends BlockLogicFlower {
	public BlockLogicBoulder(@NonNull Block<?> block) {
		super(block);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	}

	@Override
	public void onEntityCollision(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		if (entity instanceof Player) {
			world.createExplosion(null, tilePos.x(), tilePos.y(), tilePos.z(), 1.5F, true, false);
			entity.hurt(null, 2, DamageType.BLAST);
			entity.hurt(null, 2, DamageType.FIRE);
			entity.maxFireTicks = 100;
			entity.remainingFireTicks = 100;
			entity.fling(1.0f, 1.0f, 1.0f, 0.0f);
		}
	}

//	@Override
//	public void animationTick(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
//		double xPos = (double) tilePos.x() + rand.nextDouble();
//		double yPos = tilePos.y() + 0.1F;
//		double zPos = (double) tilePos.z() + rand.nextDouble();
//		world.spawnParticle("smoke", xPos, yPos, zPos, 0.0F, 0.25F, 0.0F, 0, false);
//		world.spawnParticle("flame", xPos, yPos, zPos, 0.0F, 0.2F, 0.0F, 0, false);
//	}

	@Override
	public boolean canStay(@NonNull World world, @NonNull TilePosc tilePos) {
		return this.mayPlaceOn(world.getBlockType(tilePos.down(new TilePos())));
	}

	@Override
	public boolean mayPlaceOn(@NonNull Block<?> block) {
		return block.isSolidRender();
	}

	@Override
	public @NotNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, @NonNull TilePosc tilePos, int data, @Nullable TileEntity tileEntity) {
		return this.getBreakResult(world, dropCause, data, tileEntity);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		ItemStack[] var10000;
		switch (dropCause) {
			case SILK_TOUCH:
			case PICK_BLOCK:
				var10000 = new ItemStack[]{new ItemStack(this)};
				break;
			default:
				var10000 = null;
		}

		return var10000;
	}
}
