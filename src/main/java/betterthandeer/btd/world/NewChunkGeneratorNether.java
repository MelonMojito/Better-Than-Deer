package betterthandeer.btd.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.*;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkDecoratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.SurfaceGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.nether.TerrainGeneratorNether;
import org.jetbrains.annotations.NotNull;

public class NewChunkGeneratorNether extends ChunkGeneratorPerlin {
	public NewChunkGeneratorNether(@NotNull World world) {
		super(world, new NewChunkDecoratorNether(world), new NewTerrainGeneratorNether(world), new NewSurfaceGeneratorNether(world),
			new LargeFeature[]{new CavesLargeFeature(64, 256), new LavaFloeLargeFeature(), new RubyglassFloeLargeFeature(), new ShelfLargeFeature()});
	}
}
