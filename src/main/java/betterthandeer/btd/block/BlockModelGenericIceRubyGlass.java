package betterthandeer.btd.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.data.block.BlockModelData;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericIceRubyGlass<T extends BlockLogic> extends BlockModelGeneric<T> {
	public final StaticBlockModel[] models = new StaticBlockModel[4];

	public BlockModelGenericIceRubyGlass(@NotNull Block<T> block, @NotNull StaticBlockModel staticModel) {
		super(block, staticModel);
		loadModels();
	}

	public BlockModelGenericIceRubyGlass(@NotNull Block<T> block, @NotNull BlockModelData staticModel) {
		super(block, staticModel);
		loadModels();
	}

	private void loadModels() {
		for (int i = 0; i < 4; i++) {
			this.models[i] = BlockModelDispatcher.loadDataModel("btd:block/ice_rubyglass/" + i).asModel();
		}
	}

	@Override
	public @NotNull StaticBlockModel getModelFromData(int data) {
		return this.models[data & 3];
	}
}
