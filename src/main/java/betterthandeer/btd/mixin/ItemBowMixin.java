package betterthandeer.btd.mixin;

import betterthandeer.btd.entity.arrow.flaming.ProjectileArrowFlaming;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.entity.projectile.ProjectileArrowGolden;
import net.minecraft.core.entity.projectile.ProjectileArrowPurple;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemBow.class)
public abstract class ItemBowMixin extends Item {
	protected ItemBowMixin(@NotNull NamespaceID namespaceId, @NotNull String translationKey, int id) {
		super(namespaceId, translationKey, id);
	}


	@Override
	public @Nullable ItemStack onUse(@NotNull ItemStack selfStack, @NotNull World world, @NotNull Player player) {
		ItemStack quiverSlot = player.getItemInArmorSlot(HumanArmorShape.CHEST);
		if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER.id && quiverSlot.getMetadata() < quiverSlot.getMaxDamage()) {
			quiverSlot.damageItem(1, player);
			selfStack.damageItem(1, player);
			world.playSoundAtEntity(player, player, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new ProjectileArrow(world, player, true, 0));
			}
		} else if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) {
			selfStack.damageItem(1, player);
			world.playSoundAtEntity(player, player, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new ProjectileArrowPurple(world, player, false));
			}
		} else if (player.inventory.consumeInventoryItem(Items.AMMO_ARROW_GOLD.id)) {
			selfStack.damageItem(1, player);
			world.playSoundAtEntity(player, player, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new ProjectileArrowGolden(world, player, true));
			}
		} else if (player.inventory.consumeInventoryItem(Items.AMMO_ARROW.id)) {
			selfStack.damageItem(1, player);
			world.playSoundAtEntity(player, player, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new ProjectileArrow(world, player, true, 0));
			}
		} else if (player.inventory.consumeInventoryItem(BTDItems.AMMO_ARROW_FLAMING.id)) {
			selfStack.damageItem(1, player);
			world.playSoundAtEntity(player, player, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new ProjectileArrowFlaming(world, player, true));
			}
		}

		return selfStack;
	}

}
