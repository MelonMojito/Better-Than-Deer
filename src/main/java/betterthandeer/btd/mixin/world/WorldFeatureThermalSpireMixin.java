package betterthandeer.btd.mixin.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureThermalSpire;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldFeatureThermalSpire.class)
public class WorldFeatureThermalSpireMixin {

	@Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockType(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z"))
	private boolean placeMyCustomBlock(World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
		return world.setBlockType(tilePos, Blocks.THERMAL_VENT);
	}
}
