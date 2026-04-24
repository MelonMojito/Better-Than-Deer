package betterthandeer.btd.mixin.block;

import betterthandeer.btd.block.BTDBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TileEntityMobSpawner.class)
public class TileEntityMobSpawnerMixin {

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getBlockId(III)I"))
	private int wrapTickIdCheck(World instance, int x, int y, int z, Operation<Integer> original) {
		int id = original.call(instance, x, y, z);
		if (id == BTDBlocks.MOBSPAWNER_NETHER.id()) {
			return Blocks.MOBSPAWNER.id();
		}

		return id;
	}

	@WrapOperation(method = "countNearbySpawners", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getBlockType(Lnet/minecraft/core/world/pos/TilePosc;)Lnet/minecraft/core/block/Block;"))
	private Block<?> wrapTypeCheck(World instance, @NotNull TilePosc tilePos, Operation<Block<?>> original) {
		Block<?> block = original.call(instance, tilePos);

		if (block == BTDBlocks.MOBSPAWNER_NETHER) {
			return Blocks.MOBSPAWNER;
		}

		return block;
	}
}
