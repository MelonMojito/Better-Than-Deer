package betterthandeer.btd.block.chain;

import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

public class BlockModelChainLarge<T extends BlockLogic> extends BlockModelGeneric<T> {
	protected final StaticBlockModel[] models;
	protected final StaticBlockModel linkage;

	public BlockModelChainLarge(@NonNull Block<T> block) {
		super(block, BlockModelDispatcher.loadDataModel("btd:block/chain/large/item"));

		this.models = new StaticBlockModel[6];
		for (Direction direction : Direction.directions) {
			this.models[direction.getId()] = BlockModelDispatcher.loadDataModel("btd:block/chain/large/" + direction.name().toLowerCase()).asModel();
		}

		this.linkage = BlockModelDispatcher.loadDataModel("btd:block/chain/large/linking").asModel();
	}

	@Override
	public boolean renderAttached(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
		int metadata = worldSource.getBlockData(tilePos);

		for (Direction dir : Direction.directions) {
			if ( (( metadata >> (7 - dir.getId()) ) & 1) == 0) continue;
			var model = this.models[dir.getId()];
			model.renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
		}

		if (BlockLogicChainLarge.shouldDisplayLinking(metadata)) {
			this.linkage.renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, 0.0F, 0.0F, 0.0F, false, cullFaces, overrideTexture);
		}

		return true;
	}
}
