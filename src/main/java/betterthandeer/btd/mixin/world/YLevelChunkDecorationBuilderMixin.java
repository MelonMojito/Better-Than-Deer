package betterthandeer.btd.mixin.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.*;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.Random;

@Mixin(value = ChunkDecorationBuilder.class, remap = false)
public abstract class YLevelChunkDecorationBuilderMixin implements ChunkDecoration {

	@Shadow
	@NonNull
	private Biome[] biomeMask;

	@Shadow
	private @NonNull PlacementMethod placementMethod;

	@Shadow
	@Final
	private WorldFeatureInterface worldFeature;

	@Shadow
	private @NonNull PositionSelector positionSelector;

	public void placeDecoration(@NonNull World world, @NonNull Chunk chunk, int worldX, int worldZ, int minY, int maxY, int rangeY, @NonNull Random rand) {
		int checkY = minY + (maxY - minY) / 2;

		checkY = Math.max(world.getWorldType().getMinY(world), Math.min(world.getWorldType().getMaxY(world) - 1, checkY));

		if (this.biomeMask.length == 0 || Arrays.asList(this.biomeMask).contains(world.getBlockBiome(new TilePos(worldX, checkY, worldZ)))) {
			this.placementMethod.placeFeature(new PlaceableFeature(this.worldFeature, this.positionSelector, minY, maxY, rangeY), world, chunk, rand);
		}
	}

}
