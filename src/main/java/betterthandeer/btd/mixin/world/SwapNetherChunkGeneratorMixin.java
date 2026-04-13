package betterthandeer.btd.mixin.world;

import betterthandeer.btd.world.NewChunkGeneratorNether;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.nether.WorldTypeNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = WorldTypeNether.class, remap = false)
public abstract class SwapNetherChunkGeneratorMixin {

	/**
	 * @author LukeisStuff
	 * @reason make the nether use our custom generation stuff
	 */
	@Overwrite
	public ChunkGenerator createChunkGenerator(World world) {
		return new NewChunkGeneratorNether(world);
	}
}
