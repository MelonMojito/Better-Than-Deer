package betterthandeer.btd.block.rock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericRocks<T extends BlockLogic> extends BlockModelGeneric<T> {
	public final @NonNull StaticBlockModel[] models = new StaticBlockModel[3];

	public BlockModelGenericRocks(@NonNull Block<T> block) {
		super(block, BlockModelDispatcher.loadDataModel("btd:block/rocks/0"));

		for(int i = 0; i < 3; ++i) {
			this.models[i] = BlockModelDispatcher.loadDataModel("btd:block/rocks/" + i).asModel();
		}

	}

	@Override
	public @NonNull StaticBlockModel getModelFromData(int data) {
		return this.models[data % 3];
	}
}
