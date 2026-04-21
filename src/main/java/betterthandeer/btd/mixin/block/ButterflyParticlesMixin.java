package betterthandeer.btd.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicJarButterfly;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.animal.MobButterfly;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BlockLogicJarButterfly.class)
public abstract class ButterflyParticlesMixin extends BlockLogic {

	@Shadow
	@Final
	private MobButterfly.@NonNull ButterflyEntry color;

	protected ButterflyParticlesMixin(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
	}

	@Override
	public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
		if (!world.isClientSide && rand.nextInt(500) == 0) {
			MobButterfly butterfly = new MobButterfly(world);
			butterfly.setColor(this.color.id);
			world.entityJoinedWorld(butterfly);
		}
	}
}
