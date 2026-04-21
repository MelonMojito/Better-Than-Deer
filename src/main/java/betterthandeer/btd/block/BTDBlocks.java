package betterthandeer.btd.block;

import betterthandeer.btd.block.acid.FluidAcid;
import betterthandeer.btd.block.chain.BlockLogicChainLarge;
import betterthandeer.btd.block.ice.rubyglass.BlockLogicIceRubyglass;
import betterthandeer.btd.block.rock.BlockLogicOverlayRocks;
import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.MaterialLiquid;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;
import static net.minecraft.core.block.Blocks.register;

public class BTDBlocks implements BlockInitEntrypoint {

	private static int blockID = 3000;

	public static Block<?> BOULDER;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_FLOWING;
	public static @NonNull Block<BlockLogicFluid> FLUID_ACID_STILL;
	public static @NonNull Material ACID = (new MaterialLiquid(MaterialColor.paintedYellow)).setConductivity(5).destroyOnPush();

	public static Block<?> SULFUR;

	public static Block<?> EMBER;

	public static Block<?> CHAIN_LARGE;

	public static Block<?> OVERLAY_ROCKS;

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

	public static String blockKey(String string) {
		return MOD_ID + ":block/" + string;
	}

	@SuppressWarnings("unchecked")
	public static void initializeBlocks() {

		BOULDER = register("boulder", blockKey("boulder"), blockID++, BlockLogicBoulder::new)
			.withHardness(0.0F)
			.withLightEmission(10)
			.withLitInteriorSurface(true)
			.setTicking(true)
			.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE);


		FLUID_ACID_FLOWING = (Block<BlockLogicFluid>) (Object) register("fluid.acid.flowing", blockKey("fluid_acid_flowing"), blockID++, block -> new BlockLogicFluidFlowing(block, ACID, new FluidAcid(), FLUID_ACID_STILL))
			.withHardness(100.0F)
			.withLightBlock(6)
			.withDisabledStats()
			.withTags(BlockTags.PLACE_OVERWRITES, BlockTags.NOT_IN_CREATIVE_MENU);

		FLUID_ACID_STILL = (Block<BlockLogicFluid>) (Object) register("fluid.acid.still", blockKey("fluid_acid_still"), blockID++, block -> new BlockLogicFluidStill(block, ACID, new FluidAcid(), FLUID_ACID_FLOWING))
			.withHardness(100.0F)
			.withLightBlock(6)
			.withDisabledStats()
			.setStatParent(() -> FLUID_ACID_FLOWING)
			.withTags(BlockTags.PLACE_OVERWRITES, BlockTags.NOT_IN_CREATIVE_MENU);

		SULFUR = register("sulfur", blockKey("sulfur"), blockID++,
			block -> new BlockLogicSulfur(block, Materials.STONE))
			.withHardness(1.0f)
			.withOverrideColor(MaterialColor.paintedYellow)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH);

		EMBER = register("ember", blockKey("ember"), blockID++,
			block -> new BlockLogicEmber(block, Materials.STONE))
			.withHardness(1.0f)
			.withOverrideColor(MaterialColor.paintedBlack)
			.withLightEmission(5)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CAVES_CUT_THROUGH);

		OVERLAY_ROCKS = register("overlay.rocks", blockKey("overlay_rocks"), blockID++,
			b -> new BlockLogicOverlayRocks(b, Materials.DECORATION))
			.withSound(BlockSounds.STONE)
			.withHardness(0.0F)
			.setStatParent(() -> BTDItems.AMMO_ROCK)
			.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.OVERRIDE_FRICTION);

		ICE_RUBYGLASS = register("ice.rubyglass", blockKey("ice_rubyglass"), blockID++, BlockLogicIceRubyglass::new)
			.withSound(BlockSounds.GLASS)
			.withHardness(1.5F)
			.withLightBlock(3)
			.withLightEmission(10)
			.withOverrideColor(MaterialColor.rubyglass)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.SKATEABLE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.NETHER_SURFACE_BLOCK, BlockTags.CAVES_CUT_THROUGH);

		RUBYGLASS_GROWTH_BOTTOM = register("rubyglass.growth.bottom", blockKey("rubyglass_growth_bottom"), blockID++,
			block -> new BlockLogicRubyglassGrowth(block, false))
			.withSound(BlockSounds.GLASS)
			.withLightEmission(6)
			.withOverrideColor(MaterialColor.rubyglass)
			.setStatParent(() -> BTDItems.RUBYGLASS_GROWTH)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		RUBYGLASS_GROWTH_TOP = register("rubyglass.growth.top", blockKey("rubyglass_growth_top"), blockID++,
			block -> new BlockLogicRubyglassGrowth(block, true))
			.withSound(BlockSounds.GLASS)
			.withLightEmission(6)
			.withOverrideColor(MaterialColor.rubyglass)
			.setStatParent(() -> BTDItems.RUBYGLASS_GROWTH)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		SLATE_CARVED = register("slate.carved", blockKey("slate_carved"), blockID++,
			block -> new BlockLogic(block, Materials.SLATE))
			.withSound(BlockSounds.STONE)
			.withHardness(1.0F)
			.withBlastResistance(10.0F)
			.withDisabledStats()
			.withTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

		SLAB_SLATE_POLISHED = register("slab.slate.carved", blockKey("slab_slate_carved"), blockID++,
			block -> new BlockLogicSlab(block, SLATE_CARVED))
			.withSound(BlockSounds.STONE)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE);

		LOG_SCORCHED = register("log.scorched", blockKey("log_scorched"), blockID++, BlockLogicLog::new)
			.withSound(BlockSounds.WOOD)
			.withHardness(1.5F)
			.withTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_AXE);

		CHAIN_LARGE = register("chain.large", blockKey("chain_large"), blockID++, BlockLogicChainLarge::new)
			.withSound(BlockSounds.METAL)
			.withHardness(5.0F)
			.withBlastResistance(10.0F)
			.withOverrideColor(MaterialColor.iron)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE);

		STATUE_SLATE_LOWER = (Block<BlockLogicStatue>)(Object) register("statue.slate.lower", blockKey("statue_slate_lower"), blockID++,
			block -> new BlockLogicStatue(block, Materials.SLATE, true, () -> BTDItems.STATUE_SLATE))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_SLATE)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_SLATE_UPPER = (Block<BlockLogicStatue>)(Object) register("statue.slate.upper", blockKey("statue_slate_upper"), blockID++,
			block -> new BlockLogicStatue(block, Materials.SLATE, false, () -> BTDItems.STATUE_SLATE))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_SLATE)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_PERMAFROST_LOWER = (Block<BlockLogicStatue>)(Object) register("statue.permafrost.lower", blockKey("statue_permafrost_lower"), blockID++,
			block -> new BlockLogicStatue(block, Materials.PERMAFROST, true, () -> BTDItems.STATUE_PERMAFROST))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_PERMAFROST)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_PERMAFROST_UPPER = (Block<BlockLogicStatue>)(Object) register("statue.permafrost.upper", blockKey("statue_permafrost_upper"), blockID++,
			block -> new BlockLogicStatue(block, Materials.PERMAFROST, false, () -> BTDItems.STATUE_PERMAFROST))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_PERMAFROST)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_NETHERRACK_LOWER = (Block<BlockLogicStatue>)(Object) register("statue.netherrack.lower", blockKey("statue_netherrack_lower"), blockID++,
			block -> new BlockLogicStatue(block, Materials.NETHERRACK, true, () -> BTDItems.STATUE_NETHERRACK))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_NETHERRACK)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_NETHERRACK_UPPER = (Block<BlockLogicStatue>)(Object) register("statue.netherrack.upper", blockKey("statue_netherrack_upper"), blockID++,
			block -> new BlockLogicStatue(block, Materials.NETHERRACK, false, () -> BTDItems.STATUE_NETHERRACK))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_NETHERRACK)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_GLOOMSTONE_LOWER = (Block<BlockLogicStatue>)(Object) register("statue.gloomstone.lower", blockKey("statue_gloomstone_lower"), blockID++,
			block -> new BlockLogicStatue(block, Materials.GLOOMSTONE, true, () -> BTDItems.STATUE_GLOOMSTONE))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_GLOOMSTONE)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

		STATUE_GLOOMSTONE_UPPER = (Block<BlockLogicStatue>)(Object) register("statue.gloomstone.upper", blockKey("statue_gloomstone_upper"), blockID++,
			block -> new BlockLogicStatue(block, Materials.GLOOMSTONE, false, () -> BTDItems.STATUE_GLOOMSTONE))
			.withSound(BlockSounds.STONE)
			.withHardness(1.5F)
			.setStatParent(() -> BTDItems.STATUE_GLOOMSTONE)
			.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);
	}

	@Override
	public void afterBlockInit() {

	}
}
