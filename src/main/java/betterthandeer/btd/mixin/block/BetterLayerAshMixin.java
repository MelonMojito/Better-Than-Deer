package betterthandeer.btd.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerAsh;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLogicLayerAsh.class)
public class BetterLayerAshMixin extends BlockLogicLayerBase {
	public BetterLayerAshMixin(@NonNull Block<? extends BlockLogicLayerBase> block, @Nullable Block<?> fullBlock, @NonNull Material material) {
		super(block, fullBlock, material);
	}

	@Override
	public @Nullable AABBdc getCollisionAABB(@NonNull WorldSource source, @NonNull TilePosc tilePos) {
		AABBdc aabb = this.getBounds();
		return (new AABBd(aabb)).setMax(aabb.maxX(), Math.max(aabb.maxY() - (double) 0.125F, tilePos.y()), aabb.maxZ());
	}
}
