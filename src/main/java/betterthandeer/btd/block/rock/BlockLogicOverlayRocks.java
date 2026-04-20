package betterthandeer.btd.block.rock;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.support.ISupport;
import net.minecraft.core.block.support.PartialSupport;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BlockLogicOverlayRocks extends BlockLogic {
	public static final int MAX_PEBBLES = 3;
	public static final int MASK_PEBBLE_AMOUNT = 3;

	public BlockLogicOverlayRocks(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}

	public static boolean canSpawnOn(@Nullable Block<?> block) {
		return block != null && block.hasTag(BlockTags.NETHER_SURFACE_BLOCK);
	}

	@Override
	public boolean canPlaceAt(@NonNull World world, @NonNull TilePosc tilePos) {
		Block<?> block = world.getBlockType(tilePos.down(new TilePos()));
		return block.getMaterial().isSolid();
	}

	@Override
	public boolean canStay(@NonNull World world, @NonNull TilePosc tilePos) {
		return this.canPlaceAt(world, tilePos);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public @Nullable AABBdc getCollisionAABB(@NonNull WorldSource source, @NonNull TilePosc tilePos) {
		return null;
	}

	@Override
	public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
		if (!this.canStay(world, tilePos)) {
			this.dropWithCause(world, EnumDropCause.WORLD, tilePos, world.getBlockData(tilePos), null, null);
			world.setBlockTypeNotify(tilePos, Blocks.AIR);
		}

	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public @NonNull ISupport getSupport(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Side side) {
		return PartialSupport.INSTANCE;
	}

	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return dropCause == EnumDropCause.PICK_BLOCK ? new ItemStack[]{new ItemStack(BTDItems.AMMO_ROCK, 1)} : new ItemStack[]{new ItemStack(BTDItems.AMMO_ROCK, data + 1)};
	}

	public static int setCount(int data, int count) {
		return data & -4 | count & 3;
	}

	public static int getStackCount(int data) {
		return data & 3;
	}
}
