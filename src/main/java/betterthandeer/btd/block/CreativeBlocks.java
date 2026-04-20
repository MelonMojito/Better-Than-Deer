package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;

import java.util.List;

public final class CreativeBlocks {

	private static final DyeColor[] RAINBOW_ORDER = {
		DyeColor.RED,
		DyeColor.ORANGE,
		DyeColor.YELLOW,
		DyeColor.LIME,
		DyeColor.GREEN,
		DyeColor.CYAN,
		DyeColor.LIGHT_BLUE,
		DyeColor.BLUE,
		DyeColor.PURPLE,
		DyeColor.MAGENTA,
		DyeColor.PINK,
		DyeColor.BROWN,
		DyeColor.WHITE,
		DyeColor.SILVER,
		DyeColor.GRAY,
		DyeColor.BLACK
	};

	public static void populate(List<ItemStack> list) {

		addStoneTypes(list);

		addWoodTypes(list);

		//WOOL
		addPainted(list, Blocks.WOOL, painted(Blocks.WOOL));

		addNaturalTypes(list);

		addOrganicTypes(list);


		//WORKSTATIONS
		add(list, Blocks.WORKBENCH);

		add(list, Blocks.FURNACE_STONE_IDLE);
		add(list, Blocks.FURNACE_BLAST_IDLE);

		add(list, Blocks.TROMMEL_IDLE);

		add(list, Blocks.CHEST_PLANKS_OAK);
		addPainted(list, Blocks.CHEST_PLANKS_OAK_PAINTED, painted(Blocks.CHEST_PLANKS_OAK_PAINTED));

		add(list, Blocks.BOOKSHELF_PLANKS_OAK);
		add(list, Blocks.LADDER_OAK);
		add(list, Blocks.TORCH_COAL);

		add(list, Blocks.GLASS);
		add(list, Blocks.TRAPDOOR_GLASS);

		add(list, Blocks.GLASS_TINTED);

		add(list, Blocks.GLASS_STEEL);


		addRedstoneTypes(list);

		addOreTypes(list);

		addStorageTypes(list);

		add(list, Blocks.PAPER_WALL);
		add(list, Blocks.FENCE_PAPER_WALL);

		add(list, Blocks.FENCE_CHAINLINK);
		add(list, Blocks.FENCE_STEEL);

	}

	public static void addStoneTypes(List<ItemStack> list) {
		// STONE
		add(list, Blocks.STONE);
		add(list, Blocks.MOSS_STONE);

		add(list, Blocks.COBBLE_STONE);
		add(list, Blocks.STAIRS_COBBLE_STONE);
		add(list, Blocks.SLAB_COBBLE_STONE);
		add(list, Blocks.COBBLE_STONE_MOSSY);

		add(list, Blocks.BRICK_STONE);
		add(list, Blocks.STAIRS_BRICK_STONE);
		add(list, Blocks.SLAB_BRICK_STONE);

		add(list, Blocks.STONE_POLISHED);
		add(list, Blocks.SLAB_STONE_POLISHED);
		add(list, Blocks.STONE_CARVED);

		add(list, Blocks.BRICK_STONE_POLISHED);
		add(list, Blocks.STAIRS_BRICK_STONE_POLISHED);
		add(list, Blocks.SLAB_BRICK_STONE_POLISHED);
		add(list, Blocks.BRICK_STONE_POLISHED_MOSSY);


		//BASALT
		add(list, Blocks.BASALT);
		add(list, Blocks.MOSS_BASALT);

		add(list, Blocks.COBBLE_BASALT);
		add(list, Blocks.STAIRS_COBBLE_BASALT);
		add(list, Blocks.SLAB_COBBLE_BASALT);
		add(list, Blocks.COBBLE_BASALT_MOSSY);

		add(list, Blocks.BRICK_BASALT);
		add(list, Blocks.STAIRS_BRICK_BASALT);
		add(list, Blocks.SLAB_BRICK_BASALT);

		add(list, Blocks.BASALT_POLISHED);
		add(list, Blocks.SLAB_BASALT_POLISHED);
		add(list, Blocks.BASALT_CARVED);


		//LIMESTONE
		add(list, Blocks.LIMESTONE);
		add(list, Blocks.MOSS_LIMESTONE);

		add(list, Blocks.COBBLE_LIMESTONE);
		add(list, Blocks.STAIRS_COBBLE_LIMESTONE);
		add(list, Blocks.SLAB_COBBLE_LIMESTONE);
		add(list, Blocks.COBBLE_LIMESTONE_MOSSY);

		add(list, Blocks.BRICK_LIMESTONE);
		add(list, Blocks.STAIRS_BRICK_LIMESTONE);
		add(list, Blocks.SLAB_BRICK_LIMESTONE);

		add(list, Blocks.LIMESTONE_POLISHED);
		add(list, Blocks.SLAB_LIMESTONE_POLISHED);
		add(list, Blocks.LIMESTONE_CARVED);


		//GRANITE
		add(list, Blocks.GRANITE);
		add(list, Blocks.MOSS_GRANITE);

		add(list, Blocks.COBBLE_GRANITE);
		add(list, Blocks.STAIRS_COBBLE_GRANITE);
		add(list, Blocks.SLAB_COBBLE_GRANITE);
		add(list, Blocks.COBBLE_GRANITE_MOSSY);

		add(list, Blocks.BRICK_GRANITE);
		add(list, Blocks.STAIRS_BRICK_GRANITE);
		add(list, Blocks.SLAB_BRICK_GRANITE);

		add(list, Blocks.GRANITE_POLISHED);
		add(list, Blocks.SLAB_GRANITE_POLISHED);
		add(list, Blocks.GRANITE_CARVED);


		//PERMAFROST
		add(list, Blocks.PERMAFROST);

		add(list, Blocks.COBBLE_PERMAFROST);
		add(list, Blocks.STAIRS_COBBLE_PERMAFROST);
		add(list, Blocks.SLAB_COBBLE_PERMAFROST);

		add(list, Blocks.BRICK_PERMAFROST);
		add(list, Blocks.STAIRS_BRICK_PERMAFROST);
		add(list, Blocks.SLAB_BRICK_PERMAFROST);

		add(list, Blocks.PERMAFROST_POLISHED);
		add(list, Blocks.SLAB_PERMAFROST_POLISHED);
		add(list, Blocks.PERMAFROST_CARVED);


		//NETHERRACK
		add(list, Blocks.NETHERRACK);

		add(list, Blocks.COBBLE_NETHERRACK);
		add(list, Blocks.STAIRS_COBBLE_NETHERRACK);
		add(list, Blocks.SLAB_COBBLE_NETHERRACK);
		add(list, Blocks.COBBLE_NETHERRACK_CRYSTALLINE);

		add(list, Blocks.BRICK_NETHERRACK);
		add(list, Blocks.STAIRS_BRICK_NETHERRACK);
		add(list, Blocks.SLAB_BRICK_NETHERRACK);

		add(list, Blocks.NETHERRACK_POLISHED);
		add(list, Blocks.SLAB_NETHERRACK_POLISHED);
		add(list, Blocks.NETHERRACK_CARVED);


		//GLOOMSTONE
		add(list, Blocks.GLOOMSTONE);

		add(list, Blocks.COBBLE_GLOOMSTONE);
		add(list, Blocks.STAIRS_COBBLE_GLOOMSTONE);
		add(list, Blocks.SLAB_COBBLE_GLOOMSTONE);

		add(list, Blocks.BRICK_GLOOMSTONE);
		add(list, Blocks.STAIRS_BRICK_GLOOMSTONE);
		add(list, Blocks.SLAB_BRICK_GLOOMSTONE);

		add(list, Blocks.GLOOMSTONE_POLISHED);
		add(list, Blocks.SLAB_GLOOMSTONE_POLISHED);
		add(list, Blocks.GLOOMSTONE_CARVED);


		//MARBLE
		add(list, Blocks.MARBLE);

		add(list, Blocks.BRICK_MARBLE);
		add(list, Blocks.STAIRS_BRICK_MARBLE);
		add(list, Blocks.SLAB_BRICK_MARBLE);

		add(list, Blocks.PILLAR_MARBLE);
		add(list, Blocks.CAPSTONE_MARBLE);
		add(list, Blocks.SLAB_CAPSTONE_MARBLE);


		//SLATE
		add(list, Blocks.SLATE);
		add(list, Blocks.LAYER_SLATE);

		add(list, Blocks.BRICK_SLATE);
		add(list, Blocks.STAIRS_BRICK_SLATE);
		add(list, Blocks.SLAB_BRICK_SLATE);

		add(list, Blocks.SLATE_POLISHED);
		add(list, BTDBlocks.SLAB_SLATE_POLISHED);
		add(list, BTDBlocks.SLATE_CARVED);
	}

	public static void addWoodTypes(List<ItemStack> list) {
		add(list, Blocks.PLANKS_OAK);
		addPainted(list, Blocks.PLANKS_OAK_PAINTED, painted(Blocks.PLANKS_OAK_PAINTED));

		add(list, Blocks.STAIRS_PLANKS_OAK);
		addPainted(list, Blocks.STAIRS_PLANKS_PAINTED, painted(Blocks.STAIRS_PLANKS_PAINTED));
		add(list, Blocks.SLAB_PLANKS_OAK);
		addPainted(list, Blocks.SLAB_PLANKS_PAINTED, painted(Blocks.SLAB_PLANKS_PAINTED));

		add(list, Blocks.FENCE_PLANKS_OAK);
		addPainted(list, Blocks.FENCE_PLANKS_OAK_PAINTED, painted(Blocks.FENCE_PLANKS_OAK_PAINTED));

		add(list, Blocks.FENCE_GATE_PLANKS_OAK);
		addPainted(list, Blocks.FENCE_GATE_PLANKS_OAK_PAINTED, painted(Blocks.FENCE_GATE_PLANKS_OAK_PAINTED));

		add(list, Blocks.TRAPDOOR_PLANKS_OAK);
		addPainted(list, Blocks.TRAPDOOR_PLANKS_PAINTED, painted(Blocks.TRAPDOOR_PLANKS_PAINTED));
	}

	public static void addOrganicTypes(List<ItemStack> list) {
		//LOGS
		add(list, Blocks.LOG_OAK);
		add(list, Blocks.LOG_OAK_MOSSY);
		add(list, Blocks.LOG_PINE);
		add(list, Blocks.LOG_BIRCH);
		add(list, Blocks.LOG_CHERRY);
		add(list, Blocks.LOG_EUCALYPTUS);
		add(list, Blocks.LOG_THORN);
		add(list, Blocks.LOG_PALM);
		add(list, BTDBlocks.LOG_SCORCHED);


		//LEAVES
		add(list, Blocks.LEAVES_OAK);
		add(list, Blocks.LAYER_LEAVES_OAK);
		add(list, Blocks.LEAVES_OAK_RETRO);
		add(list, Blocks.LEAVES_PINE);
		add(list, Blocks.LEAVES_BIRCH);
		add(list, Blocks.LEAVES_CHERRY);
		add(list, Blocks.LEAVES_CHERRY_FLOWERING);
		add(list, Blocks.LEAVES_EUCALYPTUS);
		add(list, Blocks.LEAVES_THORN);
		add(list, Blocks.LEAVES_PALM);
		add(list, Blocks.LEAVES_SHRUB);
		add(list, Blocks.LEAVES_CACAO);


		//SAPLINGS
		add(list, Blocks.SAPLING_OAK);
		add(list, Blocks.SAPLING_OAK_RETRO);
		add(list, Blocks.SAPLING_PINE);
		add(list, Blocks.SAPLING_BIRCH);
		add(list, Blocks.SAPLING_CHERRY);
		add(list, Blocks.SAPLING_EUCALYPTUS);
		add(list, Blocks.SAPLING_THORN);
		add(list, Blocks.SAPLING_PALM);
		add(list, Blocks.SAPLING_SHRUB);
		add(list, Blocks.SAPLING_CACAO);


		//FLOWERS
		list.add(new ItemStack(Blocks.FLOWER_YELLOW, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_YELLOW, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_YELLOW, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_YELLOW, 1, 96);

		list.add(new ItemStack(Blocks.FLOWER_RED, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_RED, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_RED, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_RED, 1, 96);

		list.add(new ItemStack(Blocks.FLOWER_PINK, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_PINK, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_PINK, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_PINK, 1, 96);

		list.add(new ItemStack(Blocks.FLOWER_PURPLE, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_PURPLE, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_PURPLE, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_PURPLE, 1, 96);

		list.add(new ItemStack(Blocks.FLOWER_LIGHT_BLUE, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_LIGHT_BLUE, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_LIGHT_BLUE, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_LIGHT_BLUE, 1, 96);

		list.add(new ItemStack(Blocks.FLOWER_ORANGE, 1, 0));
//		list.add(new ItemStack(Blocks.FLOWER_ORANGE, 1, 32);
//		list.add(new ItemStack(Blocks.FLOWER_ORANGE, 1, 64);
//		list.add(new ItemStack(Blocks.FLOWER_ORANGE, 1, 96);

		add(list, Blocks.MUSHROOM_BROWN);
		add(list, Blocks.MUSHROOM_RED);

		//FOILAGE
		add(list, Blocks.TALLGRASS);
		add(list, Blocks.TALLGRASS_FERN);
		add(list, Blocks.DEADBUSH);
		add(list, Blocks.SPINIFEX);
		add(list, Blocks.ALGAE);
		add(list, Blocks.CACTUS);

		add(list, Blocks.PUMPKIN);
		add(list, Blocks.PUMPKIN_CARVED_IDLE);
		add(list, Blocks.PUMPKIN_CARVED_ACTIVE);

		add(list, Blocks.BLOCK_SUGARCANE);
		add(list, Blocks.BLOCK_SUGARCANE_BAKED);

		add(list, Blocks.SPONGE_DRY);
		add(list, Blocks.SPONGE_WET);

		add(list, Blocks.PUMICE_DRY);
		add(list, Blocks.PUMICE_WET);

		add(list, Blocks.COBWEB);

		add(list, Blocks.BONE_PILE);
		add(list, Blocks.SOULCATCHER);
		add(list, BTDBlocks.BOULDER);
	}

	public static void addNaturalTypes(List<ItemStack> list) {
		add(list, Blocks.GRASS);
		add(list, Blocks.GRASS_SCORCHED);
		add(list, Blocks.GRASS_RETRO);

		add(list, Blocks.DIRT);
		add(list, Blocks.PATH_DIRT);
		add(list, Blocks.FARMLAND_DIRT);

		add(list, Blocks.DIRT_SCORCHED);
		add(list, Blocks.DIRT_SCORCHED_RICH);

		add(list, Blocks.MUD);
		add(list, Blocks.MUD_BAKED);

		add(list, Blocks.SAND);

		add(list, Blocks.SANDSTONE);
		add(list, Blocks.STAIRS_SANDSTONE);
		add(list, Blocks.SLAB_SANDSTONE);

		add(list, Blocks.BRICK_SANDSTONE);
		add(list, Blocks.STAIRS_BRICK_SANDSTONE);
		add(list, Blocks.SLAB_BRICK_SANDSTONE);

		add(list, Blocks.GRAVEL);

		add(list, Blocks.BRIMSAND);

		add(list, BTDBlocks.SULFUR);

		add(list, Blocks.BLOCK_SNOW);
		add(list, Blocks.LAYER_SNOW);

		add(list, Blocks.BLOCK_ASH);
		add(list, Blocks.LAYER_ASH);

		add(list, Blocks.ICE);
		add(list, Blocks.PERMAICE);

		add(list, Blocks.BLOCK_CLAY);

		add(list, Blocks.BRICK_CLAY);
		add(list, Blocks.STAIRS_BRICK_CLAY);
		add(list, Blocks.SLAB_BRICK_CLAY);

		add(list, Blocks.OBSIDIAN);
		add(list, Blocks.BONESHALE);
		add(list, Blocks.BEDROCK);

		add(list, Blocks.MAGMA);
		add(list, Blocks.SOULSAND);
		add(list, Blocks.SOULSCHIST);

		add(list, Blocks.GLOWSTONE);

		add(list, Blocks.THERMAL_VENT);

		add(list, BTDBlocks.EMBER);
	}

	public static void addRedstoneTypes(List<ItemStack> list) {
		add(list, Blocks.TORCH_REDSTONE_ACTIVE);
		add(list, Blocks.LEVER_COBBLE_STONE);

		add(list, Blocks.BUTTON_STONE);
		add(list, Blocks.BUTTON_PLANKS);
		addPainted(list, Blocks.BUTTON_PLANKS_PAINTED, painted(Blocks.BUTTON_PLANKS_PAINTED));

		add(list, Blocks.PRESSURE_PLATE_STONE);
		add(list, Blocks.PRESSURE_PLATE_COBBLE_STONE);
		add(list, Blocks.PRESSURE_PLATE_PLANKS_OAK);
		addPainted(list, Blocks.PRESSURE_PLATE_PLANKS_OAK_PAINTED, painted(Blocks.PRESSURE_PLATE_PLANKS_OAK_PAINTED));

		add(list, Blocks.PISTON_BASE);
		add(list, Blocks.PISTON_BASE_STICKY);
		add(list, Blocks.PISTON_BASE_STEEL);

		add(list, Blocks.DISPENSER_COBBLE_STONE);
		add(list, Blocks.MOTION_SENSOR_IDLE);
		add(list, Blocks.ACTIVATOR_COBBLE_NETHERRACK);
		add(list, Blocks.MATCHER);

		add(list, Blocks.PUMPKIN_REDSTONE);

		add(list, Blocks.RUBYGLASS_COLUMN);
		add(list, Blocks.RUBYGLASS_NODE);
		add(list, Blocks.BLOCK_RUBYGLASS);
		add(list, Blocks.RUBYGLASS_CIRCUIT);
		add(list, Blocks.RUBYGLASS_SPROUT);
		add(list, BTDBlocks.ICE_RUBYGLASS);

		add(list, Blocks.BRAZIER_INACTIVE);

		add(list, Blocks.MESH);
		add(list, Blocks.MESH_GOLD);

		add(list, Blocks.MOBSPAWNER);
		add(list, Blocks.MOBSPAWNER_DEACTIVATED);

		add(list, Blocks.NOTEBLOCK);
		add(list, Blocks.JUKEBOX);

		add(list, Blocks.RAIL);
		add(list, Blocks.RAIL_POWERED);
		add(list, Blocks.RAIL_DETECTOR);

		add(list, Blocks.TNT);
		add(list, Blocks.SPIKES);

		add(list, Blocks.TRAPDOOR_IRON);
		add(list, Blocks.TRAPDOOR_STEEL);

		addPainted(list, Blocks.LAMP_IDLE, painted(Blocks.LAMP_IDLE));
	}

	public static void addOreTypes(List<ItemStack> list) {
		add(list, Blocks.ORE_COAL_STONE);
		add(list, Blocks.ORE_COAL_BASALT);
		add(list, Blocks.ORE_COAL_LIMESTONE);
		add(list, Blocks.ORE_COAL_GRANITE);
		add(list, Blocks.ORE_COAL_PERMAFROST);

		add(list, Blocks.ORE_IRON_STONE);
		add(list, Blocks.ORE_IRON_BASALT);
		add(list, Blocks.ORE_IRON_LIMESTONE);
		add(list, Blocks.ORE_IRON_GRANITE);
		add(list, Blocks.ORE_IRON_PERMAFROST);

		add(list, Blocks.ORE_GOLD_STONE);
		add(list, Blocks.ORE_GOLD_BASALT);
		add(list, Blocks.ORE_GOLD_LIMESTONE);
		add(list, Blocks.ORE_GOLD_GRANITE);
		add(list, Blocks.ORE_GOLD_PERMAFROST);

		add(list, Blocks.ORE_LAPIS_STONE);
		add(list, Blocks.ORE_LAPIS_BASALT);
		add(list, Blocks.ORE_LAPIS_LIMESTONE);
		add(list, Blocks.ORE_LAPIS_GRANITE);
		add(list, Blocks.ORE_LAPIS_PERMAFROST);

		add(list, Blocks.ORE_REDSTONE_STONE);
		add(list, Blocks.ORE_REDSTONE_BASALT);
		add(list, Blocks.ORE_REDSTONE_LIMESTONE);
		add(list, Blocks.ORE_REDSTONE_GRANITE);
		add(list, Blocks.ORE_REDSTONE_PERMAFROST);

		add(list, Blocks.ORE_DIAMOND_STONE);
		add(list, Blocks.ORE_DIAMOND_BASALT);
		add(list, Blocks.ORE_DIAMOND_LIMESTONE);
		add(list, Blocks.ORE_DIAMOND_GRANITE);
		add(list, Blocks.ORE_DIAMOND_PERMAFROST);

		add(list, Blocks.ORE_NETHERCOAL_BASALT);
		add(list, Blocks.ORE_NETHERCOAL_NETHERRACK);
		add(list, Blocks.ORE_NETHERCOAL_GLOOMSTONE);
	}

	public static void addStorageTypes(List<ItemStack> list) {
		add(list, Blocks.BLOCK_IRON);
		add(list, Blocks.BLOCK_STEEL);
		add(list, Blocks.BLOCK_GOLD);
		add(list, Blocks.BLOCK_LAPIS);
		add(list, Blocks.BLOCK_REDSTONE);
		add(list, Blocks.BLOCK_DIAMOND);
		add(list, Blocks.BLOCK_QUARTZ);

		add(list, Blocks.BLOCK_COAL);
		add(list, Blocks.BLOCK_CHARCOAL);
		add(list, Blocks.BLOCK_NETHER_COAL);
		add(list, Blocks.BLOCK_OLIVINE);


		add(list, Blocks.BRICK_IRON);
		add(list, Blocks.STAIRS_BRICK_IRON);
		add(list, Blocks.SLAB_BRICK_IRON);

		add(list, Blocks.BRICK_STEEL);
		add(list, Blocks.STAIRS_BRICK_STEEL);
		add(list, Blocks.SLAB_BRICK_STEEL);

		add(list, Blocks.BRICK_GOLD);
		add(list, Blocks.STAIRS_BRICK_GOLD);
		add(list, Blocks.SLAB_BRICK_GOLD);

		add(list, Blocks.BRICK_LAPIS);
		add(list, Blocks.STAIRS_BRICK_LAPIS);
		add(list, Blocks.SLAB_BRICK_LAPIS);

		add(list, Blocks.BRICK_DIAMOND);
		add(list, Blocks.STAIRS_BRICK_DIAMOND);
		add(list, Blocks.SLAB_BRICK_DIAMOND);

		add(list, Blocks.BRICK_QUARTZ);
		add(list, Blocks.STAIRS_BRICK_QUARTZ);
		add(list, Blocks.SLAB_BRICK_QUARTZ);

		add(list, Blocks.BRICK_OLIVINE);
		add(list, Blocks.STAIRS_BRICK_OLIVINE);
		add(list, Blocks.SLAB_BRICK_OLIVINE);
	}

	private static void addPainted(List<ItemStack> list, Block<?> block, IPainted painted) {
		for (DyeColor color : RAINBOW_ORDER) {
			list.add(new ItemStack(block, 1, painted.toMetadata(color)));
		}
	}

	private static void add(List<ItemStack> list, Block<?>... blocks) {
		for (Block<?> block : blocks) {
			list.add(new ItemStack(block));
		}
	}

	private static IPainted painted(Block<?> block) {
		return (IPainted) block.getLogic();
	}

}

