package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerInAcidMixin extends Mob {

	@Shadow
	@Final
	@NonNull
	public ContainerInventory inventory;

	@Shadow
	public abstract void addStat(@Nullable Stat stat, int i);

	@Shadow
	@Final
	public static TextFormatting deathMsgColor;

	protected PlayerInAcidMixin(@NonNull World world) {
		super(world);
	}

	@Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
	private void onGetDeathMessage(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
		if (this.world.getBlockMaterial(MathHelper.floor(this.x), MathHelper.floor(this.y), MathHelper.floor(this.z)) == BTDBlocks.ACID) {
			String var6 = this.getDisplayName();
			cir.setReturnValue(var6 + deathMsgColor + " was chemically corroded.");
		}
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
