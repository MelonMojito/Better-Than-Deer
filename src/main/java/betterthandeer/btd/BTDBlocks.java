package betterthandeer.btd;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDBlocks implements BlockInitEntrypoint {

	public static Block<?> BOULDER;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_FLOWING;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_STILL;
	public static @NonNull Material ACID = (new MaterialLiquid(MaterialColor.paintedYellow)).setConductivity(5).destroyOnPush();

	public static Block<?> SULFUR;

	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeBlocks();
		}
	}

	public static void initializeBlocks() {
		BOULDER = new BlockBuilder(MOD_ID)
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE)
			.setTicking(true)
			.setUseInternalLight()
			.setLuminance(10)
			.build("boulder", 3000, BlockLogicBoulder::new);

		FLUID_ACID_FLOWING = new BlockBuilder(MOD_ID)
			.setHardness(100.0F)
			.setUseInternalLight()
			.setLightOpacity(6)
			.setVisualUpdateOnMetadata()
			.setDisableStats()
			.setTags(BlockTags.PLACE_OVERWRITES, BlockTags.NOT_IN_CREATIVE_MENU)
			.build("fluid.acid.flowing", 3001, block -> new BlockLogicFluidFlowing(block, ACID, new FluidAcid(), FLUID_ACID_STILL));

		FLUID_ACID_STILL = new BlockBuilder(MOD_ID)
			.setHardness(100.0F)
			.setUseInternalLight()
			.setLightOpacity(6)
			.setVisualUpdateOnMetadata()
			.setDisableStats()
			.setStatParent(() -> FLUID_ACID_FLOWING)
			.setTags(BlockTags.PLACE_OVERWRITES, BlockTags.NOT_IN_CREATIVE_MENU)
			.build("fluid.acid.still", 3002, block -> new BlockLogicFluidStill(block, ACID, new FluidAcid(), FLUID_ACID_FLOWING));

		SULFUR = new BlockBuilder(MOD_ID)
			.setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH)
			.build("sulfur", 3005, block -> new BlockLogic(block, Materials.SAND));

	}

	@Override
	public void afterBlockInit() {

	}
}
