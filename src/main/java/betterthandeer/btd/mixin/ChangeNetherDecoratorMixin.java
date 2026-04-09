package betterthandeer.btd.mixin;

import betterthandeer.btd.world.NewChunkDecoratorNether;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkDecoratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkGeneratorNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGeneratorNether.class)
public abstract class ChangeNetherDecoratorMixin {

	@Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/core/world/generate/chunk/perlin/nether/ChunkDecoratorNether"))
	private static ChunkDecoratorNether replaceNetherDecorator(World world) {
		return new NewChunkDecoratorNether(world);
	}
}
