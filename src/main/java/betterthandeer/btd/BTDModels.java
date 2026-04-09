package betterthandeer.btd;

import betterthandeer.btd.entity.bat.MobBat;
import betterthandeer.btd.entity.bat.MobRendererBat;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.util.ModelEntrypoint;

import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

public class BTDModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.BOULDER, loadDataModel("btd:block/boulder")).render3D(false));

		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_ACID_FLOWING, "btd:block/acid_still", "btd:block/acid_flowing").onRenderLayer(1));
		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_ACID_STILL, "btd:block/acid_still", "btd:block/acid_flowing").onRenderLayer(1));

		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.SULFUR, loadDataModel("btd:block/sulfur")));
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.BUCKET_ACID, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.EYE_BAT, "btd"));
	}

	@Override
	public void initEntityModels(EntityRendererDispatcher entityRendererDispatcher) {
		entityRendererDispatcher.assignRenderer(MobBat.class, new MobRendererBat(0.5F));
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
