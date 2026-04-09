package betterthandeer.btd;

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

		RecipeBuilder.Shaped(MOD_ID, "MMM", "MEM", "MRM")
			.addInput('M', Blocks.COBBLE_STONE_MOSSY)
			.addInput('E', BTDItems.EYE_BAT)
			.addInput('R', Items.DUST_REDSTONE)
			.create("motion_sensor", new ItemStack(Blocks.MOTION_SENSOR_IDLE, 1));

		RecipeBuilder.Shaped(MOD_ID, "NSN", "NEN", "NRN")
			.addInput('N', Blocks.COBBLE_NETHERRACK)
			.addInput('S', Blocks.SOULSAND)
			.addInput('E', BTDItems.EYE_BAT)
			.addInput('R', Items.DUST_REDSTONE)
			.create("matcher", new ItemStack(Blocks.MATCHER, 1));


	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
		RecipeBuilder.getRecipeNamespace(MOD_ID);
	}
}
