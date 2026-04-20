package betterthandeer.btd.mixin.block;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFire;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLogicFire.class)
public abstract class BurnLogMixin {

	@Inject(method = "getBurnResult", at = @At("HEAD"), cancellable = true)
	private void convertToScorchedLog(World world, TilePosc tilePos, CallbackInfoReturnable<Block<?>> cir) {
		Block<?> blockAtPos = world.getBlockType(tilePos);
		if (blockAtPos.getLogic() instanceof BlockLogicLog) cir.setReturnValue(BTDBlocks.LOG_SCORCHED);
	}
}
