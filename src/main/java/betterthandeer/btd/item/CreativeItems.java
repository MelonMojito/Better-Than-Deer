package betterthandeer.btd.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DyeColor;

import java.util.List;

public final class CreativeItems {

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

	private static void addBucketVariants(List<ItemStack> out, Item item) {
		if (item instanceof ItemBucket itemBucket) {
			out.add(new ItemStack(item));
			for (net.minecraft.core.util.collection.NamespaceID stateId : ItemBucket.getRegisteredStateIds()) {
				if (!ItemBucket.STATE_EMPTY.equals(stateId)) {
					ItemStack itemStack = new ItemStack(item, 1);
					ItemBucket.setState(itemStack, stateId);
					ItemBucket.setCharges(itemStack, itemBucket.maxCharges);
					out.add(itemStack);
				}
			}
		}
	}

	public static void populate(List<ItemStack> out) {

		//TOOLS
		out.add(new ItemStack(Items.TOOL_SHOVEL_WOOD));
		out.add(new ItemStack(Items.TOOL_PICKAXE_WOOD));
		out.add(new ItemStack(Items.TOOL_AXE_WOOD));
		out.add(new ItemStack(Items.TOOL_HOE_WOOD));
		out.add(new ItemStack(Items.TOOL_SWORD_WOOD));

		out.add(new ItemStack(Items.TOOL_SHOVEL_STONE));
		out.add(new ItemStack(Items.TOOL_PICKAXE_STONE));
		out.add(new ItemStack(Items.TOOL_AXE_STONE));
		out.add(new ItemStack(Items.TOOL_HOE_STONE));
		out.add(new ItemStack(Items.TOOL_SWORD_STONE));

		out.add(new ItemStack(Items.TOOL_SHOVEL_IRON));
		out.add(new ItemStack(Items.TOOL_PICKAXE_IRON));
		out.add(new ItemStack(Items.TOOL_AXE_IRON));
		out.add(new ItemStack(Items.TOOL_HOE_IRON));
		out.add(new ItemStack(Items.TOOL_SWORD_IRON));

		out.add(new ItemStack(Items.TOOL_SHOVEL_GOLD));
		out.add(new ItemStack(Items.TOOL_PICKAXE_GOLD));
		out.add(new ItemStack(Items.TOOL_AXE_GOLD));
		out.add(new ItemStack(Items.TOOL_HOE_GOLD));
		out.add(new ItemStack(Items.TOOL_SWORD_GOLD));

		out.add(new ItemStack(Items.TOOL_SHOVEL_DIAMOND));
		out.add(new ItemStack(Items.TOOL_PICKAXE_DIAMOND));
		out.add(new ItemStack(Items.TOOL_AXE_DIAMOND));
		out.add(new ItemStack(Items.TOOL_HOE_DIAMOND));
		out.add(new ItemStack(Items.TOOL_SWORD_DIAMOND));

		out.add(new ItemStack(Items.TOOL_SHOVEL_STEEL));
		out.add(new ItemStack(Items.TOOL_PICKAXE_STEEL));
		out.add(new ItemStack(Items.TOOL_AXE_STEEL));
		out.add(new ItemStack(Items.TOOL_HOE_STEEL));
		out.add(new ItemStack(Items.TOOL_SWORD_STEEL));


		//MISC TOOLS
		out.add(new ItemStack(Items.TOOL_FIRESTRIKER_IRON));
		out.add(new ItemStack(Items.TOOL_FIRESTRIKER_STEEL));

		out.add(new ItemStack(Items.TOOL_SHEARS));
		out.add(new ItemStack(Items.TOOL_SHEARS_STEEL));

		addBucketVariants(out, Items.BUCKET_IRON);
		addBucketVariants(out, Items.BUCKET_STEEL);

		out.add(new ItemStack(Items.PAINTBRUSH));

		out.add(new ItemStack(Items.TOOL_FISHINGROD));

		out.add(new ItemStack(Items.TOOL_BOW));
		out.add(new ItemStack(Items.AMMO_ARROW));
		out.add(new ItemStack(Items.AMMO_ARROW_GOLD));
		out.add(new ItemStack(Items.AMMO_ARROW_PURPLE));
		out.add(new ItemStack(BTDItems.AMMO_ARROW_FLAMING));

		out.add(new ItemStack(Items.HANDCANNON_UNLOADED));
		out.add(new ItemStack(Items.AMMO_CHARGE_EXPLOSIVE));

		out.add(new ItemStack(Items.AMMO_FIREBALL));

		out.add(new ItemStack(BTDItems.AMMO_LIGHTNINGBALL));

		out.add(new ItemStack(Items.TOOL_COMPASS));
		out.add(new ItemStack(Items.TOOL_CLOCK));
		out.add(new ItemStack(Items.TOOL_CALENDAR));
		out.add(new ItemStack(Items.MAP));

		out.add(new ItemStack(Items.ROPE));
		out.add(new ItemStack(BTDItems.CHAIN_LARGE));

		out.add(new ItemStack(Items.LABEL));

		out.add(new ItemStack(Items.WAND_MONSTER_SPAWNER));
		out.add(new ItemStack(Items.WAND_NBT));


		//ARMOR
		out.add(new ItemStack(Items.ARMOR_HELMET_LEATHER));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_LEATHER));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_LEATHER));
		out.add(new ItemStack(Items.ARMOR_BOOTS_LEATHER));

		out.add(new ItemStack(Items.ARMOR_HELMET_CHAINMAIL));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_CHAINMAIL));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_CHAINMAIL));
		out.add(new ItemStack(Items.ARMOR_BOOTS_CHAINMAIL));

		out.add(new ItemStack(Items.ARMOR_HELMET_IRON));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_IRON));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_IRON));
		out.add(new ItemStack(Items.ARMOR_BOOTS_IRON));

		out.add(new ItemStack(Items.ARMOR_HELMET_GOLD));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_GOLD));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_GOLD));
		out.add(new ItemStack(Items.ARMOR_BOOTS_GOLD));

		out.add(new ItemStack(Items.ARMOR_HELMET_DIAMOND));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_DIAMOND));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_DIAMOND));
		out.add(new ItemStack(Items.ARMOR_BOOTS_DIAMOND));

		out.add(new ItemStack(Items.ARMOR_HELMET_STEEL));
		out.add(new ItemStack(Items.ARMOR_CHESTPLATE_STEEL));
		out.add(new ItemStack(Items.ARMOR_LEGGINGS_STEEL));
		out.add(new ItemStack(Items.ARMOR_BOOTS_STEEL));

		out.add(new ItemStack(Items.ARMOR_QUIVER));
		out.add(new ItemStack(Items.ARMOR_QUIVER_GOLD));
		out.add(new ItemStack(Items.ARMOR_BOOTS_ICESKATES));

		out.add(new ItemStack(Items.ARMOR_WOLF_LEATHER));
		out.add(new ItemStack(Items.ARMOR_WOLF_CHAINMAIL));
		out.add(new ItemStack(Items.ARMOR_WOLF_IRON));
		out.add(new ItemStack(Items.ARMOR_WOLF_GOLD));
		out.add(new ItemStack(Items.ARMOR_WOLF_DIAMOND));
		out.add(new ItemStack(Items.ARMOR_WOLF_STEEL));


		//FOOD
		out.add(new ItemStack(Items.FOOD_APPLE));
		out.add(new ItemStack(Items.FOOD_APPLE_GOLD));

		out.add(new ItemStack(Items.FOOD_CHERRY));

		out.add(new ItemStack(Items.FOOD_PORKCHOP_RAW));
		out.add(new ItemStack(Items.FOOD_PORKCHOP_COOKED));

		out.add(new ItemStack(Items.FOOD_VENISON_RAW));
		out.add(new ItemStack(Items.FOOD_VENISON_COOKED));

		out.add(new ItemStack(Items.FOOD_FISH_RAW));
		out.add(new ItemStack(Items.FOOD_FISH_COOKED));

		out.add(new ItemStack(Items.DUST_SUGAR));
		out.add(new ItemStack(Items.EGG_CHICKEN));

		out.add(new ItemStack(Items.SEEDS_WHEAT));
		out.add(new ItemStack(Items.SEEDS_PUMPKIN));

		out.add(new ItemStack(Items.WHEAT));
		out.add(new ItemStack(Items.DOUGH));
		out.add(new ItemStack(Items.FOOD_BREAD));

		out.add(new ItemStack(Items.FOOD_COOKIE));
		out.add(new ItemStack(Items.FOOD_CAKE));
		out.add(new ItemStack(Items.FOOD_PUMPKIN_PIE));

		out.add(new ItemStack(Items.BOWL));
		out.add(new ItemStack(Items.FOOD_STEW_MUSHROOM));


		//ORE
		out.add(new ItemStack(Items.AMMO_PEBBLE));
		out.add(new ItemStack(BTDItems.AMMO_ROCK));

		out.add(new ItemStack(Items.COAL));
		out.add(new ItemStack(Items.COAL, 1, 1));
		out.add(new ItemStack(Items.NETHERCOAL));
		out.add(new ItemStack(Items.OLIVINE));

		out.add(new ItemStack(Items.ORE_RAW_IRON));
		out.add(new ItemStack(Items.INGOT_IRON));

		out.add(new ItemStack(Items.DIAMOND));

		out.add(new ItemStack(Items.INGOT_STEEL_CRUDE));
		out.add(new ItemStack(Items.INGOT_STEEL));

		out.add(new ItemStack(Items.ORE_RAW_GOLD));
		out.add(new ItemStack(Items.INGOT_GOLD));

		out.add(new ItemStack(Items.DUST_REDSTONE));

		out.add(new ItemStack(Items.REPEATER));

		out.add(new ItemStack(Items.QUARTZ));

		out.add(new ItemStack(Items.FLINT));
		out.add(new ItemStack(Items.CLAY));
		out.add(new ItemStack(Items.BRICK_CLAY));

		out.add(new ItemStack(Items.DUST_GLOWSTONE));

		out.add(new ItemStack(Items.RUBYGLASS_CRYSTAL));

		out.add(new ItemStack(BTDItems.SULFUR));


		//Materials
		out.add(new ItemStack(Items.STICK));
		out.add(new ItemStack(Items.AMMO_SNOWBALL));
		out.add(new ItemStack(Items.SUGARCANE));
		out.add(new ItemStack(Items.PAPER));
		out.add(new ItemStack(Items.BOOK));
		for (DyeColor color : RAINBOW_ORDER) {
			int meta = color.itemMeta;
			out.add(new ItemStack(Items.DYE, 1, meta));
		}

		//Mob Drops
		out.add(new ItemStack(Items.CLOTH));
		out.add(new ItemStack(Items.STRING));
		out.add(new ItemStack(Items.FEATHER_CHICKEN));
		out.add(new ItemStack(Items.GUNPOWDER));
		out.add(new ItemStack(Items.BONE));
		out.add(new ItemStack(Items.CHAINLINK));
		out.add(new ItemStack(Items.SLIMEBALL));
		out.add(new ItemStack(Items.LEATHER));
		out.add(new ItemStack(BTDItems.LEATHER_GHAST));
		out.add(new ItemStack(BTDItems.EYE_GARGOYLE));

		//Placeables
		out.add(new ItemStack(Items.PAINTING));

		out.add(new ItemStack(Items.DOOR_OAK));
		for (DyeColor color : RAINBOW_ORDER) {
			int meta = color.itemMeta;
			out.add(new ItemStack(Items.DOOR_OAK_PAINTED, 1, meta));
		}

		out.add(new ItemStack(Items.DOOR_GLASS));
		out.add(new ItemStack(Items.DOOR_IRON));
		out.add(new ItemStack(Items.DOOR_STEEL));

		out.add(new ItemStack(Items.SIGN));
		for (DyeColor color : RAINBOW_ORDER) {
			int meta = color.itemMeta;
			out.add(new ItemStack(Items.SIGN_PAINTED, 1, meta));
		}

		out.add(new ItemStack(Items.FLAG));
		out.add(new ItemStack(Items.BED));
		out.add(new ItemStack(Items.SEAT));

		out.add(new ItemStack(Items.BASKET));

		out.add(new ItemStack(Items.JAR));

		out.add(new ItemStack(Items.LANTERN_FIREFLY_GREEN));
		out.add(new ItemStack(Items.LANTERN_FIREFLY_BLUE));
		out.add(new ItemStack(Items.LANTERN_FIREFLY_ORANGE));
		out.add(new ItemStack(Items.LANTERN_FIREFLY_RED));

		out.add(new ItemStack(Items.JAR_BUTTERFLY_BLUE));
		out.add(new ItemStack(Items.JAR_BUTTERFLY_ORANGE));
		out.add(new ItemStack(Items.JAR_BUTTERFLY_PINK));
		out.add(new ItemStack(Items.JAR_BUTTERFLY_SILVER));

		out.add(new ItemStack(Items.STATUE_STONE));
		out.add(new ItemStack(Items.STATUE_BASALT));
		out.add(new ItemStack(Items.STATUE_LIMESTONE));
		out.add(new ItemStack(Items.STATUE_GRANITE));
		out.add(new ItemStack(BTDItems.STATUE_PERMAFROST));
		out.add(new ItemStack(Items.STATUE_MARBLE));
		out.add(new ItemStack(BTDItems.STATUE_SLATE));
		out.add(new ItemStack(BTDItems.STATUE_NETHERRACK));
		out.add(new ItemStack(BTDItems.STATUE_GLOOMSTONE));
		out.add(new ItemStack(Items.STATUE_PIGMAN));

		out.add(new ItemStack(BTDItems.RUBYGLASS_GROWTH));

		out.add(new ItemStack(Items.SADDLE));

		out.add(new ItemStack(Items.BOAT));

		out.add(new ItemStack(Items.MINECART));
		out.add(new ItemStack(Items.MINECART_CHEST));
		out.add(new ItemStack(Items.MINECART_FURNACE));

		// Records
		out.add(new ItemStack(Items.RECORD_13));
		out.add(new ItemStack(Items.RECORD_CAT));

		out.add(new ItemStack(Items.RECORD_BLOCKS));
		out.add(new ItemStack(Items.RECORD_CHIRP));
		out.add(new ItemStack(Items.RECORD_FAR));
		out.add(new ItemStack(Items.RECORD_MALL));
		out.add(new ItemStack(Items.RECORD_MELLOHI));
		out.add(new ItemStack(Items.RECORD_STAL));
		out.add(new ItemStack(Items.RECORD_STRAD));
		out.add(new ItemStack(Items.RECORD_WARD));
		out.add(new ItemStack(Items.RECORD_WAIT));
		out.add(new ItemStack(Items.RECORD_DOG));
	}
}
