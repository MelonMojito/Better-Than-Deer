package betterthandeer.btd;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("matcher");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("motion_sensor");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("pebbles_to_cobblestone");

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Blocks.BLOCK_SUGARCANE)
			.create("block_of_sugarcane_to_sugarcane", new ItemStack(Items.SUGARCANE, 9));

		RecipeBuilder.Shaped(MOD_ID, "PP", "PP")
			.addInput('P', BTDItems.AMMO_ROCK)
			.create("rocks_to_netherrack", new ItemStack(Blocks.COBBLE_NETHERRACK, 2));

		RecipeBuilder.Shaped(MOD_ID, "PO", "OP")
			.addInput('P', BTDItems.AMMO_ROCK)
			.addInput('O', Blocks.OBSIDIAN)
			.create("rocks_to_gloomstone", new ItemStack(Blocks.COBBLE_GLOOMSTONE, 2));

		RecipeBuilder.Shaped(MOD_ID, "PP", "PP")
			.addInput('P', Items.AMMO_PEBBLE)
			.create("pebbles_to_cobblestone", new ItemStack(Blocks.COBBLE_STONE, 2));

		RecipeBuilder.Shaped(MOD_ID, "MMM", "MEM", "MRM")
			.addInput('M', Blocks.COBBLE_STONE_MOSSY)
			.addInput('E', BTDItems.EYE_GARGOYLE)
			.addInput('R', Items.DUST_REDSTONE)
			.create("motion_sensor", new ItemStack(Blocks.MOTION_SENSOR_IDLE, 1));

		RecipeBuilder.Shaped(MOD_ID, "NSN", "NEN", "NRN")
			.addInput('N', Blocks.COBBLE_NETHERRACK)
			.addInput('S', Blocks.SOULSAND)
			.addInput('E', BTDItems.EYE_GARGOYLE)
			.addInput('R', Items.DUST_REDSTONE)
			.create("matcher", new ItemStack(Blocks.MATCHER, 1));


		RecipeBuilder.ModifyTrommel("minecraft", "brimsand").deleteRecipe();

		RecipeBuilder.Trommel(MOD_ID)
			.setInput(Blocks.BRIMSAND)
			.addEntry(new WeightedRandomLootObject(Items.GUNPOWDER.getDefaultStack(), 1, 2), 25.0)
			.addEntry(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 1, 2), 10.0)
			.addEntry(new WeightedRandomLootObject(Items.DUST_GLOWSTONE.getDefaultStack(), 1, 2), 10.0)
			.addEntry(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 1, 2), 5.0)
			.addEntry(new WeightedRandomLootObject(Items.NETHERCOAL.getDefaultStack(), 1), 5.0)
			.addEntry(new WeightedRandomLootObject(Items.ORE_RAW_GOLD.getDefaultStack(), 1), 0.5)
			.addEntry(new WeightedRandomLootObject(BTDItems.AMMO_ROCK.getDefaultStack(), 2, 3), 35.0)
			.create("brimsand");

	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
		RecipeBuilder.getRecipeNamespace(MOD_ID);
	}
}
