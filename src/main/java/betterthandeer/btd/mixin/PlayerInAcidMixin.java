package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerInAcidMixin extends Mob {

	@Shadow
	@Final
	@NotNull
	public ContainerInventory inventory;

	@Shadow
	public abstract void addStat(@Nullable Stat stat, int i);

	protected PlayerInAcidMixin(@NotNull World world) {
		super(world);
	}

	@Unique
	public boolean isInAcid() {
		return this.world.isMaterialInBB(this.bb, BTDBlocks.ACID);
	}

	@Inject(method = "onLivingUpdate", at = @At("TAIL"))
	private void onLivingUpdate(CallbackInfo ci) {
		if (this.world.isClientSide) return;

		if (this.isInAcid()) {
			this.damageArmorFromAcid();
		}
	}


	@Unique
	private void damageArmorFromAcid() {
		for (int slot = 0; slot < this.inventory.armorInventory.length; slot++) {
			ItemStack stack = this.inventory.armorInventory[slot];
			if (stack == null) continue;

			if (isQuiver(stack)) {
				continue;
			}

			int currentDamage = stack.getMetadata();

			int newDamage = currentDamage + 2;

			if (newDamage > stack.getMaxDamage()) {
				this.world.playSoundAtEntity(this, this, "random.break", 0.8F, 1.0F);
				this.inventory.armorInventory[slot] = null;

				this.addStat(stack.getItem().getStat("stat_broken"), 1);
			} else {
				stack.setMetadata(newDamage);
			}
		}
	}

	@Unique
	private boolean isQuiver(ItemStack stack) {
		if (stack == null) return false;
		return stack.itemID == Items.ARMOR_QUIVER.id || stack.itemID == Items.ARMOR_QUIVER_GOLD.id;
	}
}
