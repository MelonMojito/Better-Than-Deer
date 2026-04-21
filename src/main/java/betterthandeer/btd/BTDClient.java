package betterthandeer.btd;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.item.BTDItems;
import betterthandeer.btd.mixin.MixinDispatcher;
import betterthandeer.btd.block.acid.ParticleAcidBoiling;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.*;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.particle.ParticleDispatcher;
import net.minecraft.client.render.particle.ParticleEntry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.util.Map;

import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

public class BTDClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void onInitializeClient() {
		BetterThanDeerMod.LOGGER.info("Better Than Deer Client initialized.");
	}

	@Override
	public void beforeClientStart() {
		ParticleDispatcher.getInstance().addDispatch("acidboiling", new ParticleEntry() {
			public Particle newParticle(@NonNull World world, double x, double y, double z, double motionX, double motionY, double motionZ, int data) {
				return new ParticleAcidBoiling(world, x, y, z, motionX, motionY, motionZ, true);
			}
		});
	}

	@Override
	public void afterClientStart() {
		changeVanillaTextures();

		MobInfoRegistry.register(MobGargoyle.class, "guidebook.section.mob.gargoyle.name", "guidebook.section.mob.gargoyle.desc", 16, 200, new MobInfoRegistry.MobDrop[]{
			new MobInfoRegistry.MobDrop(new ItemStack(BTDItems.EYE_GARGOYLE), 1.0F, 0, 2)});
	}


	public void changeVanillaTextures() {
		ItemModelDispatcher itemModelDispatcher = ItemModelDispatcher.getInstance();
		BlockModelDispatcher blockModelDispatcher = BlockModelDispatcher.getInstance();
		Map<Object, Object> dispatches = ((MixinDispatcher) (Object) blockModelDispatcher).getDispatches();

		dispatches.put(Blocks.BLOCK_ASH, new BlockModelGeneric<>(
			Blocks.BLOCK_ASH,
			loadDataModel("btd:block/block_ash")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.BLOCK_ASH.asItem()));

		dispatches.put(Blocks.LAYER_ASH, new BlockModelGenericLayer<>(
			Blocks.LAYER_ASH, "btd:block/layer/ash"));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.LAYER_ASH.asItem()));



		dispatches.put(Blocks.COBBLE_BASALT, new BlockModelGeneric<>(
			Blocks.COBBLE_BASALT,
			loadDataModel("btd:block/cobbled_basalt")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.COBBLE_BASALT.asItem()));

		dispatches.put(Blocks.STAIRS_COBBLE_BASALT, new BlockModelGenericStairs<>(
			Blocks.STAIRS_COBBLE_BASALT,
			loadDataModel("btd:block/stairs/cobbled_basalt")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.STAIRS_COBBLE_BASALT.asItem()));

		dispatches.put(Blocks.SLAB_COBBLE_BASALT, new BlockModelGenericSlab<>(
			Blocks.SLAB_COBBLE_BASALT,
			loadDataModel("btd:block/slab/cobbled_basalt/lower"),
			loadDataModel("btd:block/slab/cobbled_basalt/upper"),
			loadDataModel("btd:block/slab/cobbled_basalt/full")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.SLAB_COBBLE_BASALT.asItem()));

		dispatches.put(Blocks.ORE_NETHERCOAL_BASALT, new BlockModelGeneric<>(
			Blocks.ORE_NETHERCOAL_BASALT,
			loadDataModel("btd:block/ore/nethercoal/basalt")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.ORE_NETHERCOAL_BASALT.asItem()).setFullBright());


		dispatches.put(Blocks.RUBYGLASS_NODE, new BlockModelGeneric<>(
			Blocks.RUBYGLASS_NODE,
			loadDataModel("btd:block/node")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.RUBYGLASS_NODE.asItem()));

		dispatches.put(Blocks.BLOCK_RUBYGLASS, new BlockModelGeneric<>(
			Blocks.BLOCK_RUBYGLASS,
			loadDataModel("btd:block/block_rubyglass")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.BLOCK_RUBYGLASS.asItem()));

		dispatches.put(Blocks.COBBLE_NETHERRACK_CRYSTALLINE, new BlockModelGeneric<>(
			Blocks.COBBLE_NETHERRACK_CRYSTALLINE,
			loadDataModel("btd:block/crystalline")));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.COBBLE_NETHERRACK_CRYSTALLINE.asItem()));

		dispatches.put(Blocks.RUBYGLASS_SPROUT, new BlockModelCrystalBud<>(
			Blocks.RUBYGLASS_SPROUT,
			loadDataModel("btd:block/sprout"))
			.render3D(false));
		itemModelDispatcher.addDispatch(new ItemModelBlock((ItemBlock<?>) Blocks.RUBYGLASS_SPROUT.asItem()).setFullBright());

		itemModelDispatcher.addDispatch(new ItemModelStandard(Items.RUBYGLASS_CRYSTAL, "minecraft").setFullBright());
	}
}
