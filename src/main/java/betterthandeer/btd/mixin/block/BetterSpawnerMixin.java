package betterthandeer.btd.mixin.block;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicMobSpawner;
import net.minecraft.core.block.BlockLogicMobSpawnerDeactivated;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicMobSpawner.class)
public class BetterSpawnerMixin extends BlockLogicMobSpawnerDeactivated {

	public BetterSpawnerMixin(@NonNull Block<?> block) {
		super(block);
	}

	@Override
	public void onRemoved(@NonNull World world, @NonNull TilePosc tilePos, int data) {
		if (!world.isClientSide) {
			TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner) world.getTileEntity(tilePos);
			if (tileEntityMobSpawner == null) {
				return;
			}

			int amountToDrop = world.rand.nextInt(10) + 10;

			for (int l = 0; l < amountToDrop; ++l) {
				String mobInSpawner = tileEntityMobSpawner.getMobId();
				if (mobInSpawner != null) {
					ItemStack var10000;
					switch (mobInSpawner) {
						case "minecraft:zombie" -> var10000 = new ItemStack(Items.CLOTH);
						case "minecraft:skeleton" -> var10000 = world.rand.nextInt(2) == 0 ? new ItemStack(Items.BONE) : new ItemStack(Items.AMMO_ARROW);
						case "minecraft:zombie_armored" -> var10000 = new ItemStack(Items.CHAINLINK);
						case "minecraft:spider" -> var10000 = new ItemStack(Items.STRING);
						case "minecraft:snowman" -> var10000 = new ItemStack(Items.AMMO_SNOWBALL);
						case "btd:gargoyle" -> var10000 = new ItemStack(BTDItems.EYE_GARGOYLE);
						default -> var10000 = null;
					}

					ItemStack itemstack = var10000;
					if (itemstack != null) {
						float rx = world.rand.nextFloat() * 0.8F + 0.1F;
						float ry = world.rand.nextFloat() * 0.8F + 0.1F;
						float rz = world.rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int qty = world.rand.nextInt(21) + 10;
							if (qty > itemstack.stackSize) {
								qty = itemstack.stackSize;
							}

							itemstack.stackSize -= qty;
							EntityItem entityitem = new EntityItem(world, (float) tilePos.x() + rx, (float) tilePos.y() + ry, (float) tilePos.z() + rz, new ItemStack(itemstack.itemID, qty, itemstack.getMetadata()));
							float f3 = 0.05F;
							entityitem.xd = (float) world.rand.nextGaussian() * f3;
							entityitem.yd = (float) world.rand.nextGaussian() * f3 + 0.2F;
							entityitem.zd = (float) world.rand.nextGaussian() * f3;
							world.entityJoinedWorld(entityitem);
						}
					}
				}
			}
		}
		super.onRemoved(world, tilePos, data);
	}

	@Override
	public @NonNull ItemStack @Nullable [] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, @Nullable TileEntity tileEntity) {
		return switch (dropCause) {
			case PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			case SILK_TOUCH -> new ItemStack[]{new ItemStack(Blocks.MOBSPAWNER_DEACTIVATED)};
			default -> null;
		};
	}

}
