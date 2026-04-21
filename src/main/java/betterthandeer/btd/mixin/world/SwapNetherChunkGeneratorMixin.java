package betterthandeer.btd.mixin.world;

import betterthandeer.btd.world.chunk.generator.NewBiomeProviderNether;
import betterthandeer.btd.world.chunk.generator.NewChunkGeneratorNether;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.nether.WorldTypeNether;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldTypeNether.class)
public abstract class SwapNetherChunkGeneratorMixin extends WorldType {

	protected SwapNetherChunkGeneratorMixin(Properties properties) {
		super(properties);
	}

	@Override
	public ChunkGenerator createChunkGenerator(World world) {
		return new NewChunkGeneratorNether(world);
	}

	@Override
	public @NonNull BiomeProvider createBiomeProvider(World world) {
		return new NewBiomeProviderNether(world);
	}

	@Override
	public @NonNull Biome @NonNull [] allBiomes() {
		return NewBiomeProviderNether.allBiomes();
	}
}
