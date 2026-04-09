package betterthandeer.btd.mixin;

import betterthandeer.btd.world.NewChunkDecoratorNether;
import betterthandeer.btd.world.NewSurfaceGeneratorNether;
import betterthandeer.btd.world.NewTerrainGeneratorNether;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkDecoratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.SurfaceGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.TerrainGeneratorNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGeneratorNether.class)
public abstract class ChangeNetherDecoratorMixin {

	@Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/core/world/generate/chunk/perlin/nether/ChunkDecoratorNether"))
	private static ChunkDecoratorNether replaceNetherDecorator(World world) {
		return new NewChunkDecoratorNether(world);
	}

	@Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/core/world/generate/chunk/perlin/nether/TerrainGeneratorNether"))
	private static TerrainGeneratorNether replaceNetherTerrainGenerator(World world) {
		return new NewTerrainGeneratorNether(world);
	}

//	@Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/core/world/generate/chunk/perlin/nether/SurfaceGeneratorNether"))
//	private static SurfaceGeneratorNether replaceSurfaceGeneratorNether(World world) {
//		return new NewSurfaceGeneratorNether(world);
//	}
}
