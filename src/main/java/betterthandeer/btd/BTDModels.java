package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.block.BlockModelGenericRocks;
import betterthandeer.btd.entity.ProjectileRock;
import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.entity.gargoyle.MobRendererGargoyle;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.block.model.generic.BlockModelCrystalBud;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.util.ModelEntrypoint;

import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

public class BTDModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.BOULDER, loadDataModel("btd:block/boulder")).render3D(false));

		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_ACID_FLOWING, "btd:block/acid_still", "btd:block/acid_flowing").onRenderLayer(1));
		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_ACID_STILL, "btd:block/acid_still", "btd:block/acid_flowing").onRenderLayer(1));

		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.SULFUR, loadDataModel("btd:block/sulfur")));

		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.EMBER, loadDataModel("btd:block/ember")));

		dispatcher.addDispatch(new BlockModelCrystalBud<>(BTDBlocks.RUBYGLASS_SPROUT, loadDataModel("btd:block/sprout")).render3D(false));

		dispatcher.addDispatch(new BlockModelGenericRocks<>(BTDBlocks.OVERLAY_ROCKS));
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.BUCKET_ACID, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.EYE_GARGOYLE, "btd").setFullBright());
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.LEATHER_GHAST, "btd").setFullBright());
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.AMMO_ROCK, "btd"));

		dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) BTDBlocks.BOULDER.asItem())).setFullBright());
		dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) BTDBlocks.RUBYGLASS_SPROUT.asItem())).setFullBright());
	}

	@Override
	public void initEntityModels(EntityRendererDispatcher entityRendererDispatcher) {
		entityRendererDispatcher.assignRenderer(MobGargoyle.class, new MobRendererGargoyle(0.5F));

		entityRendererDispatcher.assignRenderer(ProjectileRock.class, new EntityRendererSprite<>(BTDItems.AMMO_ROCK));
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
