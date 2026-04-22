package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryDyeing;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryUndyeing;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DyeColor;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.List;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("matcher");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("motion_sensor");
		RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("pebbles_to_cobblestone");

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(BTDBlocks.LOG_SCORCHED)
			.create("scorched_log_to_black_wooden_planks", new ItemStack(Blocks.PLANKS_OAK_PAINTED, 4, 15));

		RecipeBuilderShaped templateStatue = new RecipeBuilderShaped(MOD_ID, "B", "B", "S");
		templateStatue.addInput('B', Blocks.SLATE).addInput('S', BTDBlocks.SLAB_SLATE_POLISHED).create("statue_slate", new ItemStack(BTDItems.STATUE_SLATE, 1));
		templateStatue.addInput('B', Blocks.PERMAFROST).addInput('S', Blocks.SLAB_PERMAFROST_POLISHED).create("statue_permafrost", new ItemStack(BTDItems.STATUE_PERMAFROST, 1));
		templateStatue.addInput('B', Blocks.NETHERRACK).addInput('S', Blocks.SLAB_NETHERRACK_POLISHED).create("statue_netherrack", new ItemStack(BTDItems.STATUE_NETHERRACK, 1));
		templateStatue.addInput('B', Blocks.GLOOMSTONE).addInput('S', Blocks.SLAB_GLOOMSTONE_POLISHED).create("statue_gloomstone", new ItemStack(BTDItems.STATUE_GLOOMSTONE, 1));

		RecipeBuilder.Shaped(MOD_ID, "SS", "SS")
			.addInput('S', Blocks.BRIMSAND)
			.create("brimstone", new ItemStack(BTDBlocks.BRIMSTONE, 2));

		RecipeBuilder.Shaped(MOD_ID, "SSS")
			.addInput('S', BTDBlocks.BRIMSTONE)
			.create("brimstone_slab", new ItemStack(BTDBlocks.SLAB_BRIMSTONE, 6));

		RecipeBuilder.Shaped(MOD_ID, "S  ", "SS ", "SSS")
			.addInput('S', BTDBlocks.BRIMSTONE)
			.create("brimstone_stairs", new ItemStack(BTDBlocks.STAIRS_BRIMSTONE, 6));

		RecipeBuilder.Shaped(MOD_ID, "SS", "SS")
			.addInput('S', BTDBlocks.BRIMSTONE)
			.create("brimstone_bricks", new ItemStack(BTDBlocks.BRICK_BRIMSTONE, 4));

		RecipeBuilder.Shaped(MOD_ID, "SSS")
			.addInput('S', BTDBlocks.BRICK_BRIMSTONE)
			.create("brick_brimstone_slab", new ItemStack(BTDBlocks.SLAB_BRICK_BRIMSTONE, 6));

		RecipeBuilder.Shaped(MOD_ID, "S  ", "SS ", "SSS")
			.addInput('S', BTDBlocks.BRICK_BRIMSTONE)
			.create("brick_brimstone_stairs", new ItemStack(BTDBlocks.STAIRS_BRICK_BRIMSTONE, 6));

		RecipeBuilder.Shaped(MOD_ID, "SSS")
			.addInput('S', Blocks.SLATE_POLISHED)
			.create("polished_slate_slab", new ItemStack(BTDBlocks.SLAB_SLATE_POLISHED, 6));

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

		RecipeBuilder.Shaped(MOD_ID, " S ", "SAS", " S ")
			.addInput('S', BTDItems.SULFUR)
			.addInput('A', Items.AMMO_ARROW)
			.create("flaming_arrow", new ItemStack(BTDItems.AMMO_ARROW_FLAMING, 4));

		RecipeBuilder.Shaped(MOD_ID, "SS", "SS")
			.addInput('S', BTDItems.SULFUR)
			.create("sulfur_block", new ItemStack(BTDBlocks.SULFUR, 1));


		Registries.RECIPES.addCustomRecipe(
			"btd:workbench/asphalt_dyeing",
			new RecipeEntryDyeing(
				new RecipeSymbol("btd:asphalt"),
				BTDBlocks.ASPHALT.getDefaultStack(), false, false
			)
		);
		Registries.RECIPES.addCustomRecipe(
			"btd:workbench/asphalt_undyeing",
			new RecipeEntryUndyeing(
				new RecipeSymbol("btd:asphalt"),
				new ItemStack(BTDBlocks.ASPHALT.asItem(), 1, 15)
			)
		);


		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("cobble_basalt_to_olivine");
		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("cobble_granite_to_quartz");
		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("cobble_limestone_to_marble");
		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("cobble_netherrack_to_magma");
		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("cobble_stone_to_slate");
		RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("log_to_petrified_pillar");


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, "minecraft:logs")
			.setInput(1, Blocks.BLOCK_ASH)
			.create("log_to_scorched_log", new ItemStack(BTDBlocks.LOG_SCORCHED, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, Blocks.COBBLE_STONE)
			.setInput(1, Blocks.COBBLE_STONE)
			.create("cobble_stone_to_slate", new ItemStack(Blocks.SLATE, 1));

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_STONE)
			.create("cobble_stone_to_stone", new ItemStack(Blocks.STONE, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, Blocks.COBBLE_BASALT)
			.setInput(1, Blocks.COBBLE_BASALT)
			.create("cobble_basalt_to_olivine", new ItemStack(Items.OLIVINE, 1));

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_BASALT)
			.create("cobble_basalt_to_basalt", new ItemStack(Blocks.BASALT, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, Blocks.COBBLE_GRANITE)
			.setInput(1, Blocks.COBBLE_GRANITE)
			.create("cobble_granite_to_quartz", new ItemStack(Items.QUARTZ, 1));

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_GRANITE)
			.create("cobble_granite_to_granite", new ItemStack(Blocks.GRANITE, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, Blocks.COBBLE_LIMESTONE)
			.setInput(1, Blocks.COBBLE_LIMESTONE)
			.create("cobble_limestone_to_marble", new ItemStack(Blocks.MARBLE, 1));

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_LIMESTONE)
			.create("cobble_limestone_to_limestone", new ItemStack(Blocks.LIMESTONE, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(0, Blocks.COBBLE_NETHERRACK)
			.setInput(1, Blocks.COBBLE_NETHERRACK)
			.create("cobble_netherrack_to_magma", new ItemStack(Blocks.MAGMA, 1));

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_NETHERRACK)
			.create("cobble_netherrack_to_netherrack", new ItemStack(Blocks.NETHERRACK, 1));


		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.COBBLE_GLOOMSTONE)
			.create("cobble_gloomstone_to_gloomstone", new ItemStack(Blocks.GLOOMSTONE, 1));


		RecipeBuilder.ModifyTrommel("minecraft", "brimsand").deleteRecipe();
		RecipeBuilder.ModifyTrommel("minecraft", "soul_sand").addEntry(new WeightedRandomLootObject(BTDItems.AMMO_ROCK.getDefaultStack(), 1, 2), 15.0);

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

		Registries.ITEM_GROUPS.getItem("minecraft:logs").add(BTDBlocks.LOG_SCORCHED.getDefaultStack());

		List<ItemStack> asphalt = Registries.stackListOf(BTDBlocks.ASPHALT);
		for (DyeColor dyeColor : DyeColor.values()) {
			asphalt.add(new ItemStack(BTDBlocks.ASPHALT, 1, dyeColor.blockMeta));
		}

		Registries.ITEM_GROUPS.register("btd:asphalt", asphalt);
	}
}
