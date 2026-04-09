package betterthandeer.btd.mixin;

import betterthandeer.btd.BTDBlocks;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.SurfaceProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biomes.class)
public abstract class UpdateNetherBiomesMixin {

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void patchNetherSurfaceBlocks(CallbackInfo ci) {
		Biomes.NETHER_VOLCANIC_ISLANDS.withSurfaceProperties(new SurfaceProperties.Builder()
			.withTopBlock(Blocks.BASALT)
			.withFillerBlock(Blocks.COBBLE_BASALT)
			.build());

//		Biomes.NETHER_SULFUR_POOLS.withSurfaceProperties(new SurfaceProperties.Builder()
//			.withTopBlock(BTDBlocks.SULFUR)
//			.withFillerBlock(Blocks.BRIMSAND)
//			.build());

//		NETHER_CRAG = register("minecraft:nether.crag", (new BiomeNether("nether.crag"))
//			.withDebugColor(6503997)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.COBBLE_NETHERRACK)
//				.withFillerBlock(Blocks.COBBLE_NETHERRACK)
//				.build()));
//
//		NETHER_VOLCANIC_ISLANDS = register("minecraft:nether.volcanic_islands", (new BiomeVolcanoIslands("nether.volcanic_islands"))
//			.withDebugColor(16753459).withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.BRIMSAND)
//				.withFillerBlock(Blocks.BRIMSAND)
//				.build()));
//
//		NETHER_SULFUR_POOLS = register("minecraft:nether.sulfur_pools", (new BiomeSulfurPools("nether.sulfur_pools"))
//			.withDebugColor(10721600)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.BRIMSAND)
//				.withFillerBlock(Blocks.BRIMSAND)
//				.build())
//			.withTags(BiomeTags.HAS_SULFUR_POOLS));
//
//		NETHER_CRYSTAL_PLAINS = register("minecraft:nether.crystal_plains", (new BiomeCrystalForest("nether.crystal_plains"))
//			.withDebugColor(11141120)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.COBBLE_NETHERRACK)
//				.withFillerBlock(Blocks.COBBLE_NETHERRACK)
//				.build())
//			.withTags(BiomeTags.HAS_CRYSTAL_VEINS));
//
//		NETHER_CRYSTAL_FOREST = register("minecraft:nether.crystal_forest", (new BiomeCrystalForest("nether.crystal_forest"))
//			.withDebugColor(14483456)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.COBBLE_NETHERRACK)
//				.withFillerBlock(Blocks.COBBLE_NETHERRACK)
//				.build())
//			.withTags(BiomeTags.HAS_CRYSTAL_VEINS));
//
//		NETHER_OLD_WORLD = register("minecraft:nether.old_world", (new BiomeNether("nether.old_world")).withDebugColor(6513792)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.BLOCK_ASH)
//				.withFillerBlock(Blocks.BLOCK_ASH)
//				.build())
//			.withTags(BiomeTags.HAS_SURFACE_ASH));
//
//		NETHER_OLD_WORLD_DESERT = register("minecraft:nether.old_world.desert", (new BiomeNether("nether.old_world"))
//			.withDebugColor(6047811)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.SOULSAND)
//				.withFillerBlock(Blocks.SOULSAND)
//				.build())
//			.withTags(BiomeTags.HAS_SURFACE_ASH));
//
//		NETHER_SHELF = register("minecraft:nether.shelf", (new BiomeNether("nether.shelf"))
//			.withDebugColor(9865839)
//			.withBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG)
//			.withSurfaceProperties((new SurfaceProperties.Builder())
//				.withTopBlock(Blocks.COBBLE_NETHERRACK)
//				.withFillerBlock(Blocks.COBBLE_NETHERRACK)
//				.build()));


	}
}
