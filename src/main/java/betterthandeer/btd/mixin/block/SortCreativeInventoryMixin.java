package betterthandeer.btd.mixin.block;

import betterthandeer.btd.CreativeBlocks;
import betterthandeer.btd.CreativeItems;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.MenuInventoryCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MenuInventoryCreative.class)
public abstract class SortCreativeInventoryMixin {

	@Shadow
	public static List<ItemStack> creativeItems;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void rebuildCreativeList(CallbackInfo ci) {
		creativeItems.clear();
		CreativeBlocks.populate(creativeItems);
		CreativeItems.populate(creativeItems);
	}


}
