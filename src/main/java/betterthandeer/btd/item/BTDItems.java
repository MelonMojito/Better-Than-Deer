package betterthandeer.btd.item;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemStatue;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.block.ItemBlock;

public class BTDItems {

	public static Item BUCKET_ACID;

	public static Item EYE_GARGOYLE;

	public static Item LEATHER_GHAST;

	public static Item AMMO_ROCK;

	public static Item RUBYGLASS_GROWTH;

	public static Item SULFUR;

	public static Item AMMO_ARROW_FLAMING;

	public static Item CHAIN_LARGE;
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

	public static void initializeItems() {
		BUCKET_ACID = new ItemBucket("bucket.acid", "btd:item/bucket_acid", 20000, BTDBlocks.FLUID_ACID_FLOWING).setContainerItem(Items.BUCKET);

		EYE_GARGOYLE = new Item("eye.gargoyle", "btd:item/eye_gargoyle", 20001);

		LEATHER_GHAST = new Item("leather.ghast", "btd:item/leather_ghast", 20002);

		AMMO_ROCK = new ItemRock("ammo.rock", "btd:item/ammo_rock", 20003);

		RUBYGLASS_GROWTH = new ItemRubyglassGrowth("rubyglass.growth", "btd:item/rubyglass_growth", 20004, BTDBlocks.RUBYGLASS_GROWTH_BOTTOM, BTDBlocks.RUBYGLASS_GROWTH_TOP);

		SULFUR = new Item("sulfur", "btd:item/sulfur", 20005);

		AMMO_ARROW_FLAMING = new Item("ammo.arrow.flaming", "btd:item/ammo_arrow_flaming", 20006);

		CHAIN_LARGE = new ItemPlaceable("chain.large", "btd:item/chain_large", 20007, BTDBlocks.CHAIN_LARGE);
		STATUE_SLATE = new ItemStatue("statue.slate", "btd:item/statue_slate", 20050, BTDBlocks.STATUE_SLATE_LOWER, BTDBlocks.STATUE_SLATE_UPPER);
		STATUE_PERMAFROST = new ItemStatue("statue.permafrost", "btd:item/statue_permafrost", 20051, BTDBlocks.STATUE_PERMAFROST_LOWER, BTDBlocks.STATUE_PERMAFROST_UPPER);
		STATUE_NETHERRACK = new ItemStatue("statue.netherrack", "btd:item/statue_netherrack", 20052, BTDBlocks.STATUE_NETHERRACK_LOWER, BTDBlocks.STATUE_NETHERRACK_UPPER);
		STATUE_GLOOMSTONE = new ItemStatue("statue.gloomstone", "btd:item/statue_gloomstone", 20053, BTDBlocks.STATUE_GLOOMSTONE_LOWER, BTDBlocks.STATUE_GLOOMSTONE_UPPER);
	}
}
