package betterthandeer.btd.item;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.item.*;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDItems {

	private static int itemID = 20000;

	public static Item BUCKET_ACID;

	public static Item EYE_GARGOYLE;

	public static Item LEATHER_GHAST;

	public static Item AMMO_ROCK;

	public static Item RUBYGLASS_GROWTH;

	public static Item SULFUR;

	public static Item AMMO_ARROW_FLAMING;

	public static Item CHAIN_LARGE;
	public static Item CHAIN_STEEL_LARGE;

	public static Item STATUE_SLATE;
	public static Item STATUE_PERMAFROST;
	public static Item STATUE_NETHERRACK;
	public static Item STATUE_GLOOMSTONE;

	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeItems();
		}
	}

	public static String itemKey(String string) {
		return MOD_ID + ":item/" + string;
	}

	public static void initializeItems() {
		BUCKET_ACID = new ItemBucket("bucket.acid", itemKey("bucket_acid"), itemID++, BTDBlocks.FLUID_ACID_FLOWING).setContainerItem(Items.BUCKET);

		EYE_GARGOYLE = new Item("eye.gargoyle", itemKey("eye_gargoyle"), itemID++);

		LEATHER_GHAST = new Item("leather.ghast", itemKey("leather_ghast"), itemID++);

		AMMO_ROCK = new ItemRock("ammo.rock", itemKey("ammo_rock"), itemID++);

		RUBYGLASS_GROWTH = new ItemRubyglassGrowth("rubyglass.growth", itemKey("rubyglass_growth"), itemID++, BTDBlocks.RUBYGLASS_GROWTH_BOTTOM, BTDBlocks.RUBYGLASS_GROWTH_TOP);

		SULFUR = new Item("sulfur", itemKey("sulfur"), itemID++);

		AMMO_ARROW_FLAMING = new Item("ammo.arrow.flaming", itemKey("ammo_arrow_flaming"), itemID++);

		CHAIN_LARGE = new ItemPlaceable("chain.large", itemKey("chain_large"), itemID++, BTDBlocks.CHAIN_LARGE);
		CHAIN_STEEL_LARGE = new ItemPlaceable("chain.steel.large", itemKey("chain_steel_large"), itemID++, BTDBlocks.CHAIN_STEEL_LARGE);

		STATUE_SLATE = new ItemStatue("statue.slate", itemKey("statue_slate"), itemID++, BTDBlocks.STATUE_SLATE_LOWER, BTDBlocks.STATUE_SLATE_UPPER);
		STATUE_PERMAFROST = new ItemStatue("statue.permafrost", itemKey("statue_permafrost"), itemID++, BTDBlocks.STATUE_PERMAFROST_LOWER, BTDBlocks.STATUE_PERMAFROST_UPPER);
		STATUE_NETHERRACK = new ItemStatue("statue.netherrack", itemKey("statue_netherrack"), itemID++, BTDBlocks.STATUE_NETHERRACK_LOWER, BTDBlocks.STATUE_NETHERRACK_UPPER);
		STATUE_GLOOMSTONE = new ItemStatue("statue.gloomstone", itemKey("statue_gloomstone"), itemID++, BTDBlocks.STATUE_GLOOMSTONE_LOWER, BTDBlocks.STATUE_GLOOMSTONE_UPPER);
	}
}
