package betterthandeer.btd.mixin;

import betterthandeer.btd.BTDClient;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotResult.class, remap = false)
public abstract class AchievementCraftingMixin {
	@Shadow
	private Player thePlayer;

	@Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;onCrafting(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;)V", shift = At.Shift.AFTER))
	public void addCraftingAchievements(ItemStack itemStack, CallbackInfo ci) {
		if (itemStack.itemID == Blocks.FURNACE_BLAST_IDLE.id()) {
			thePlayer.addStat(BTDClient.BLAST_FURNACE, 1);
		}
		if (itemStack.itemID == Items.INGOT_STEEL.id) {
			thePlayer.addStat(BTDClient.OBTAIN_STEEL, 1);
		}
	}
}
