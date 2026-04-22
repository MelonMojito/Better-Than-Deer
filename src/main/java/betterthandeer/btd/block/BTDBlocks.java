package betterthandeer.btd.block;

import betterthandeer.btd.block.conduit.BlockLogicConduitRubyglass;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
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

	public static Block<?> EMBER;

	public static Block<?> CHAIN_LARGE;

	public static Block<?> OVERLAY_ROCKS;

	public static Block<?> CONDUIT_RUBYGLASS;
	public static Block<?> ICE_RUBYGLASS;

	public static Block<?> RUBYGLASS_GROWTH_BOTTOM;
	public static Block<?> RUBYGLASS_GROWTH_TOP;

	public static Block<?> SLATE_CARVED;
	public static Block<?> SLAB_SLATE_POLISHED;

	public static Block<?> LOG_SCORCHED;
	public static @NonNull Block<BlockLogicStatue> STATUE_SLATE_LOWER;
	public static @NonNull Block<BlockLogicStatue> STATUE_SLATE_UPPER;
	public static @NonNull Block<BlockLogicStatue> STATUE_PERMAFROST_LOWER;
	public static @NonNull Block<BlockLogicStatue> STATUE_PERMAFROST_UPPER;
	public static @NonNull Block<BlockLogicStatue> STATUE_NETHERRACK_LOWER;
	public static @NonNull Block<BlockLogicStatue> STATUE_NETHERRACK_UPPER;
	public static @NonNull Block<BlockLogicStatue> STATUE_GLOOMSTONE_LOWER;
	public static @NonNull Block<BlockLogicStatue> STATUE_GLOOMSTONE_UPPER;

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
			.build("sulfur", 3005, block -> new BlockLogicSulfur(block, Materials.STONE));


		EMBER = new BlockBuilder(MOD_ID)
			.setHardness(1.0f)
			.setOverrideColor(MaterialColor.paintedBlack)
			.setLuminance(5)
			.setUseInternalLight()
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH)
			.build("ember", 3006, block -> new BlockLogicEmber(block, Materials.STONE));


		OVERLAY_ROCKS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(0.0F)
			.setVisualUpdateOnMetadata()
			.setStatParent(() -> BTDItems.AMMO_ROCK)
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.OVERRIDE_FRICTION)
			.build("overlay.rocks", 3010, b -> new BlockLogicOverlayRocks(b, Materials.DECORATION));


		ICE_RUBYGLASS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GLASS)
			.setHardness(1.5F)
			.setLightOpacity(3)
			.setLuminance(10)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.SKATEABLE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.CAVES_CUT_THROUGH)
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


		SLATE_CARVED = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.0F)
			.setResistance(10.0F)
			.setDisableStats()
			.setTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
			.build("slate.carved", 3020, block -> new BlockLogic(block, Materials.SLATE));

		SLAB_SLATE_POLISHED = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setVisualUpdateOnMetadata()
			.setUseInternalLight()
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.build("slab.slate.carved", 3021, block -> new BlockLogicSlab(block, SLATE_CARVED));


		LOG_SCORCHED = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setHardness(1.5F)
			.setVisualUpdateOnMetadata()
			.setFlammability(0, 0)
			.setTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_AXE)
			.build("log.scorched", 3025, BlockLogicLog::new);


		CHAIN_LARGE = new BlockBuilder(MOD_ID)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setVisualUpdateOnMetadata()
			.setBlockSound(BlockSounds.METAL)
			.setHardness(5.0F)
			.setResistance(10.0F)
			.setOverrideColor(MaterialColor.iron)
			.build("chain.large", 3015, block -> new BlockLogicChainLarge(block));

		STATUE_SLATE_LOWER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_SLATE)
			.build("statue.slate.lower", 3050, block -> new BlockLogicStatue(block, Materials.SLATE, true, () -> BTDItems.STATUE_SLATE));

		STATUE_SLATE_UPPER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_SLATE)
			.build("statue.slate.upper", 3051, block -> new BlockLogicStatue(block, Materials.SLATE, false, () -> BTDItems.STATUE_SLATE));

		STATUE_PERMAFROST_LOWER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_PERMAFROST)
			.build("statue.permafrost.lower", 3052, block -> new BlockLogicStatue(block, Materials.PERMAFROST, true, () -> BTDItems.STATUE_PERMAFROST));
		STATUE_PERMAFROST_UPPER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_PERMAFROST)
			.build("statue.permafrost.upper", 3053, block -> new BlockLogicStatue(block, Materials.PERMAFROST, false, () -> BTDItems.STATUE_PERMAFROST));

		STATUE_NETHERRACK_LOWER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_NETHERRACK)
			.build("statue.netherrack.lower", 3054, block -> new BlockLogicStatue(block, Materials.NETHERRACK, true, () -> BTDItems.STATUE_NETHERRACK));
		STATUE_NETHERRACK_UPPER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_NETHERRACK)
			.build("statue.netherrack.upper", 3055, block -> new BlockLogicStatue(block, Materials.NETHERRACK, false, () -> BTDItems.STATUE_NETHERRACK));

		STATUE_GLOOMSTONE_LOWER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_GLOOMSTONE)
			.build("statue.gloomstone.lower", 3056, block -> new BlockLogicStatue(block, Materials.GLOOMSTONE, true, () -> BTDItems.STATUE_GLOOMSTONE));
		STATUE_GLOOMSTONE_UPPER = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
			.setStatParent(() -> BTDItems.STATUE_GLOOMSTONE)
			.build("statue.gloomstone.upper", 3057, block -> new BlockLogicStatue(block, Materials.GLOOMSTONE, false, () -> BTDItems.STATUE_GLOOMSTONE));


		CONDUIT_RUBYGLASS = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.STONE)
			.setHardness(0.3F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.PREVENT_MOB_SPAWNS)
			.setOverrideColor(MaterialColor.rubyglass)
			.setVisualUpdateOnMetadata()
			.build("rubyglass.conduit", 3058, block -> new BlockLogicConduitRubyglass(block, Materials.STONE))
		;

	}

	@Override
	public void afterBlockInit() {

	}
}
