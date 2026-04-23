package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.block.chain.BlockModelChainLarge;
import betterthandeer.btd.block.ice.rubyglass.BlockModelGenericIceRubyGlass;
import betterthandeer.btd.block.rock.BlockModelGenericRocks;
import betterthandeer.btd.block.tar.BlockModelGenericAsphalt;
import betterthandeer.btd.entity.arrow.flaming.EntityRendererArrowFlaming;
import betterthandeer.btd.entity.arrow.flaming.ProjectileArrowFlaming;
import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.entity.gargoyle.MobRendererGargoyle;
import betterthandeer.btd.entity.leecher.MobLeecher;
import betterthandeer.btd.entity.leecher.MobRendererLeecher;
import betterthandeer.btd.entity.leecher.ProjectileLightningball;
import betterthandeer.btd.entity.rock.ProjectileRock;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.block.model.generic.*;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tileentity.TileEntityRendererStatue;
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

		dispatcher.addDispatch(new BlockModelGenericRocks<>(BTDBlocks.OVERLAY_ROCKS));

		dispatcher.addDispatch((new BlockModelGenericIceRubyGlass<>(BTDBlocks.ICE_RUBYGLASS, loadDataModel("btd:block/ice_rubyglass/0"))).forceCullSelf(true));

		dispatcher.addDispatch(new BlockModelCrystalBud<>(BTDBlocks.RUBYGLASS_GROWTH_BOTTOM, loadDataModel("btd:block/growth_bottom")).render3D(false));
		dispatcher.addDispatch(new BlockModelCrystalBud<>(BTDBlocks.RUBYGLASS_GROWTH_TOP, loadDataModel("btd:block/growth_top")).render3D(false));


		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.SLATE_CARVED,
			loadDataModel("btd:block/carved_slate")));
		dispatcher.addDispatch(new BlockModelGenericSlab<>(BTDBlocks.SLAB_SLATE_POLISHED,
			loadDataModel("btd:block/slab/polished_slate/lower"),
			loadDataModel("btd:block/slab/polished_slate/upper"),
			loadDataModel("btd:block/slab/polished_slate/full")));


		dispatcher.addDispatch(new BlockModelGenericAxis<>(BTDBlocks.LOG_SCORCHED,
			loadDataModel("btd:block/log/scorched")));


		dispatcher.addDispatch(new BlockModelChainLarge<>(BTDBlocks.CHAIN_LARGE));

		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_SLATE_LOWER)).setAllTextures("minecraft:block/slate_top"));
		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_SLATE_UPPER)).setAllTextures("minecraft:block/slate_top"));

		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_PERMAFROST_LOWER)).setAllTextures("minecraft:block/permafrost"));
		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_PERMAFROST_UPPER)).setAllTextures("minecraft:block/permafrost"));

		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_NETHERRACK_LOWER)).setAllTextures("minecraft:block/netherrack"));
		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_NETHERRACK_UPPER)).setAllTextures("minecraft:block/netherrack"));

		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_GLOOMSTONE_LOWER)).setAllTextures("minecraft:block/gloomstone"));
		dispatcher.addDispatch((new BlockModelEmpty<>(BTDBlocks.STATUE_GLOOMSTONE_UPPER)).setAllTextures("minecraft:block/gloomstone"));

		TileEntityRendererStatue.BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_SLATE_LOWER, "/assets/btd/textures/entity/statue/slate.png");
		TileEntityRendererStatue.BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_PERMAFROST_LOWER, "/assets/btd/textures/entity/statue/permafrost.png");
		TileEntityRendererStatue.BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_NETHERRACK_LOWER, "/assets/btd/textures/entity/statue/netherrack.png");
		TileEntityRendererStatue.BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_GLOOMSTONE_LOWER, "/assets/btd/textures/entity/statue/gloomstone.png");

		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_TAR_FLOWING, "btd:block/tar_still", "btd:block/tar_flowing"));
		dispatcher.addDispatch(new BlockModelFluid<>(BTDBlocks.FLUID_TAR_STILL, "btd:block/tar_still", "btd:block/tar_flowing"));

		dispatcher.addDispatch(new BlockModelGenericAsphalt<>(BTDBlocks.ASPHALT, loadDataModel("btd:block/asphalt/black")));

		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.BRIMSTONE,
			loadDataModel("btd:block/brimstone")));

		dispatcher.addDispatch(new BlockModelGenericSlab<>(BTDBlocks.SLAB_BRIMSTONE,
			loadDataModel("btd:block/slab/brimstone/lower"),
			loadDataModel("btd:block/slab/brimstone/upper"),
			loadDataModel("btd:block/slab/brimstone/full")));


		dispatcher.addDispatch(new BlockModelGenericStairs<>(BTDBlocks.STAIRS_BRIMSTONE,
			loadDataModel("btd:block/stairs/brimstone")));


		dispatcher.addDispatch(new BlockModelGeneric<>(BTDBlocks.BRICK_BRIMSTONE,
			loadDataModel("btd:block/brick_brimstone")));

		dispatcher.addDispatch(new BlockModelGenericSlab<>(BTDBlocks.SLAB_BRICK_BRIMSTONE,
			loadDataModel("btd:block/slab/brick_brimstone/lower"),
			loadDataModel("btd:block/slab/brick_brimstone/upper"),
			loadDataModel("btd:block/slab/brick_brimstone/full")));


		dispatcher.addDispatch(new BlockModelGenericStairs<>(BTDBlocks.STAIRS_BRICK_BRIMSTONE,
			loadDataModel("btd:block/stairs/brick_brimstone")));


	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.BUCKET_ACID, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.EYE_GARGOYLE, "btd").setFullBright());
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.LEATHER_GHAST, "btd").setFullBright());
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.AMMO_ROCK, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.RUBYGLASS_GROWTH, "btd").setFullBright());

		dispatcher.addDispatch(new ItemModelStandard(BTDItems.SULFUR, "btd"));

		dispatcher.addDispatch(new ItemModelStandard(BTDItems.AMMO_ARROW_FLAMING, "btd").setFullBright());

		dispatcher.addDispatch((new ItemModelBlock((ItemBlock<?>) BTDBlocks.BOULDER.asItem())).setFullBright());

		dispatcher.addDispatch(new ItemModelStandard(BTDItems.CHAIN_LARGE, "btd"));

		dispatcher.addDispatch(new ItemModelStandard(BTDItems.STATUE_SLATE, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.STATUE_PERMAFROST, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.STATUE_NETHERRACK, "btd"));
		dispatcher.addDispatch(new ItemModelStandard(BTDItems.STATUE_GLOOMSTONE, "btd"));

		dispatcher.addDispatch(new ItemModelStandard(BTDItems.AMMO_LIGHTNINGBALL, "btd").setFullBright());
	}

	@Override
	public void initEntityModels(EntityRendererDispatcher entityRendererDispatcher) {
		entityRendererDispatcher.assignRenderer(MobGargoyle.class, new MobRendererGargoyle(0.5F));
		entityRendererDispatcher.assignRenderer(MobLeecher.class, new MobRendererLeecher(0.5F));

		entityRendererDispatcher.assignRenderer(ProjectileRock.class, new EntityRendererSprite<>(BTDItems.AMMO_ROCK));
		entityRendererDispatcher.assignRenderer(ProjectileArrowFlaming.class, new EntityRendererArrowFlaming());

		entityRendererDispatcher.assignRenderer(ProjectileLightningball.class, new EntityRendererSprite<>(BTDItems.AMMO_LIGHTNINGBALL).setScale(2.0F).setFullBright());
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
