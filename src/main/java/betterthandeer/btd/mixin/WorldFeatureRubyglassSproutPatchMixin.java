package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureRubyglassSproutPatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldFeatureRubyglassSproutPatch.class)
public class WorldFeatureRubyglassSproutPatchMixin {

	@Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlock(IIII)Z"))
	private boolean placeMyCustomBlock(World world, int x, int y, int z, int id) {
		return world.setBlock(x, y, z, BTDBlocks.RUBYGLASS_SPROUT.id());
	}
}
