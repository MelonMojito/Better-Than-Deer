package betterthandeer.btd.world.chunk.generator;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.CavesLargeFeature;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.LavaFloeLargeFeature;
import net.minecraft.core.world.generate.ShelfLargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import org.jspecify.annotations.NonNull;

public class NewChunkGeneratorNether extends ChunkGeneratorPerlin {
	public NewChunkGeneratorNether(@NonNull World world) {
		super(world,
			new NewChunkDecoratorNether(world),
			new NewTerrainGeneratorNether(world),
			new NewSurfaceGeneratorNether(world),

			new LargeFeature[]{
				new CavesLargeFeature(64, 256),
				new LavaFloeLargeFeature(),
				new RubyglassFloeLargeFeature(),
				new ShelfLargeFeature()});
	}
}
