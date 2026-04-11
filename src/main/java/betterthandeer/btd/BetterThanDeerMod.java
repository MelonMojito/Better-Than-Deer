package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.entity.BTDEntities;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.BlockLogicOreNetherCoal;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ItemInitEntrypoint;

import static net.minecraft.core.data.registry.Registries.NAMESPACES;

public class BetterThanDeerMod implements ModInitializer, GameStartEntrypoint, ItemInitEntrypoint {
	public static final String MOD_ID = "btd";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Better Than Deer initialized.");
		Blocks.COBBLE_BASALT.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);
		Blocks.BASALT.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);

		Blocks.RUBYGLASS_SPROUT.withTags(BlockTags.NOT_IN_CREATIVE_MENU);

		Blocks.BLOCK_ASH.withSound(BlockSounds.SAND);

		Blocks.BONE_PILE.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.PLANTABLE_IN_JAR, BlockTags.MINEABLE_BY_PICKAXE);
		Blocks.SOULCATCHER.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.PLANTABLE_IN_JAR, BlockTags.MINEABLE_BY_SHOVEL);

		Blocks.NETHERRACK.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN, BlockTags.CAVES_CUT_THROUGH, BlockTags.NETHER_MOBS_SPAWN, BlockTags.NETHER_SURFACE_BLOCK);

		Blocks.GLOOMSTONE.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);
		Blocks.COBBLE_GLOOMSTONE.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);

		BlockLogicOreNetherCoal.variantMap.put(Blocks.BASALT.id(), Blocks.ORE_NETHERCOAL_BASALT.id());
		BlockLogicOreNetherCoal.variantMap.put(Blocks.BRIMSAND.id(), Blocks.ORE_NETHERCOAL_BASALT.id());

		BlockLogicOreNetherCoal.variantMap.put(Blocks.NETHERRACK.id(), Blocks.ORE_NETHERCOAL_NETHERRACK.id());

		BlockLogicOreNetherCoal.variantMap.put(Blocks.SLATE.id(), Blocks.ORE_NETHERCOAL_GLOOMSTONE.id());
	}

	@Override
	public void beforeGameStart() {
		NAMESPACES.register(MOD_ID, MOD_ID);


		BTDEntities.init();
		BTDBlocks.init();
		BTDItems.init();
	}

	@Override
	public void afterGameStart() {
	}

	@Override
	public void afterItemInit() {

	}
}
