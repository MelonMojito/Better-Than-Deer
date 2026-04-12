package betterthandeer.btd.block;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.sound.BlockSounds;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import java.util.function.Supplier;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDBlocks implements BlockInitEntrypoint {

	public static Block<?> BOULDER;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_FLOWING;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_STILL;
	public static @NonNull Material ACID = (new MaterialLiquid(MaterialColor.paintedYellow)).setConductivity(5).destroyOnPush();

	public static Block<?> SULFUR;

	public static Block<?> EMBER;

	public static Block<?> RUBYGLASS_SPROUT;
	public static Block<?> BLOCK_RUBYGLASS;
	public static Block<?> COBBLE_NETHERRACK_CRYSTALLINE;

	public static Block<?> OVERLAY_ROCKS;

	public static Block<?> ICE_RUBYGLASS;

	public static Block<?> RUBYGLASS_GROWTH_BOTTOM;
	public static Block<?> RUBYGLASS_GROWTH_TOP;

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
			.setHardness(1.0f)
			.setOverrideColor(MaterialColor.paintedYellow)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH)
			.build("sulfur", 3005, block -> new BlockLogic(block, Materials.STONE));


		EMBER = new BlockBuilder(MOD_ID)
			.setHardness(1.0f)
			.setOverrideColor(MaterialColor.paintedBlack)
			.setLuminance(5)
			.setUseInternalLight()
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH)
			.build("ember", 3006, block -> new BlockLogicEmber(block, Materials.STONE));


		RUBYGLASS_SPROUT = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setVisualUpdateOnMetadata()
			.setLuminance(6)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setOverrideColor(MaterialColor.rubyglass)
			.build("rubyglass.sprout", 3007, BlockLogicRubyglassSprout::new);

		BLOCK_RUBYGLASS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setHardness(3.0F)
			.setUseInternalLight()
			.setLuminance(7)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setOverrideColor(MaterialColor.rubyglass)
			.build("block.rubyglass", 3008, block -> new BlockLogic(block, Materials.STONE));

		COBBLE_NETHERRACK_CRYSTALLINE = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(0.4F)
			.setUseInternalLight()
			.setLuminance(7)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN, BlockTags.GROWS_RUBYGLASS, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.CAVES_CUT_THROUGH, BlockTags.NETHER_MOBS_SPAWN)
			.setOverrideColor(MaterialColor.rubyglass)
			.build("cobble.netherrack.crystalline", 3009, block -> new BlockLogic(block, Materials.NETHERRACK));


		OVERLAY_ROCKS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(0.0F)
			.setVisualUpdateOnMetadata()
			.setStatParent(() -> BTDItems.AMMO_ROCK)
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.OVERRIDE_FRICTION)
			.build("overlay.rocks", 3010, b -> new BlockLogicOverlayRocks(b, Materials.DECORATION));


		ICE_RUBYGLASS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setHardness(3.0F)
			.setLightOpacity(3)
			.setLuminance(10)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.SKATEABLE)
			.setOverrideColor(MaterialColor.rubyglass)
			.build("ice.rubyglass", 3011, BlockLogicIceRubyglass::new);

		RUBYGLASS_GROWTH_BOTTOM = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setVisualUpdateOnMetadata()
			.setLuminance(6)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setOverrideColor(MaterialColor.rubyglass)
			.setStatParent(() -> BTDItems.RUBYGLASS_GROWTH)
			.build("rubyglass.growth.bottom", 3013, block -> new BlockLogicRubyglassGrowth(block, false));

		RUBYGLASS_GROWTH_TOP = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setVisualUpdateOnMetadata()
			.setLuminance(6)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setOverrideColor(MaterialColor.rubyglass)
			.setStatParent(() -> BTDItems.RUBYGLASS_GROWTH)
			.build("rubyglass.growth.top", 3014, block -> new BlockLogicRubyglassGrowth(block, true));


	}

	@Override
	public void afterBlockInit() {

	}
}
