package betterthandeer.btd.world;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.BlockLogicOreNetherCoal;
import net.minecraft.core.block.BlockLogicOreRubyglass;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.chunk.ChunkDecorationBuilder;
import net.minecraft.core.world.generate.chunk.PlacementMethod;
import net.minecraft.core.world.generate.chunk.PositionSelectors;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkDecoratorNether;
import net.minecraft.core.world.generate.feature.*;
import net.minecraft.core.world.noise.FractalNoise2D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import net.minecraft.core.world.noise.WorleyNoise;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class NewChunkDecoratorNether extends ChunkDecoratorNether {
	private final @NonNull FractalNoise2D<ImprovedPerlinNoise> crystalDensityNoise;
	private final @NonNull WorleyNoise pillarNoise;
	private final WorleyNoise.@NonNull Result worleyResult;

	public NewChunkDecoratorNether(@NonNull World world) {
		super(world);
		this.pillarNoise = new WorleyNoise(new Random(world.getRandomSeed()));
		this.crystalDensityNoise = new FractalNoise2D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 8, 74));
		this.worleyResult = new WorleyNoise.Result();
	}

	@Override
	public void registerDecorations() {

		// Rubyglass Features
		this.register("btd:decoration/nether/default/rubyglass_crystal_ceiling", (new ChunkDecorationBuilder(new WorldFeatureRubyglassCrystal(true)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST})
			.withPositionSelector(PositionSelectors.UpperHeightRangeUniform)
			.withPlacementMethod((feature, world, chunk, random) -> {
				int x = chunk.pos.x * 16;
				int z = chunk.pos.z * 16;
				int density = (int) ((this.crystalDensityNoise.getValue((double) x * (double) 0.5F, (double) z * (double) 0.5F) - (double) 6.0F + (double) 30.0F) / (double) 6.0F);

				for (int i = 0; i < density + 4; ++i) {
					feature.placeFeature(world, chunk, random);
				}

			}));

		this.register("btd:decoration/nether/default/rubyglass_crystal_floor", (new ChunkDecorationBuilder(new WorldFeatureRubyglassCrystal(false, 20, 25)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST})
			.withPositionSelector(PositionSelectors.UpperHeightRangeUniform)
			.withPlacementMethod((feature, world, chunk, random) -> {
				int x = chunk.pos.x * 16;
				int z = chunk.pos.z * 16;
				int density = (int) ((this.crystalDensityNoise.getValue((double) x * (double) 0.5F, (double) z * (double) 0.5F) - (double) 6.0F + (double) 30.0F) / (double) 6.0F);

				for (int i = 0; i < density + 4; ++i) {
					feature.placeFeature(world, chunk, random);
				}

			}));

		this.register("btd:decoration/nether/default/rubyglass_crystal_floor_2", (new ChunkDecorationBuilder(new WorldFeatureRubyglassCrystal(false, 15, 20)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_CRYSTAL_PLAINS})
			.withPositionSelector(PositionSelectors.UpperHeightRangeUniform)
			.withPlacementMethod(new PlacementMethod.TriesPerChunk(4)));

		this.register("btd:decoration/nether/default/rubyglass_node", (new ChunkDecorationBuilder(new WorldFeatureOre(BlockLogicOreRubyglass.variantMap, 5)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_CRYSTAL_PLAINS})
			.withPositionSelector(PositionSelectors.HeightRangeUniform)
			.withPlacementMethod(new PlacementMethod.TriesPerChunk(10)));

		this.register("btd:decoration/nether/default/rubyglass_sprout_patch", (new ChunkDecorationBuilder(new WorldFeatureRubyglassSproutPatch()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_CRYSTAL_PLAINS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod.TriesPerChunk(20)));


		// Water Features
		this.register("btd:decoration/nether/default/nether_water_spring", (new ChunkDecorationBuilder(new WorldFeatureNewNetherSpring(Blocks.FLUID_WATER_FLOWING.id())))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_CRYSTAL_PLAINS})
			.withPositionSelector(PositionSelectors.HeightRangeBiasedTop)
			.withPlacementMethod(new PlacementMethod.TriesPerChunk(30)));

		// Lava Features
		this.register("btd:decoration/nether/default/nether_lava_spring", (new ChunkDecorationBuilder(new WorldFeatureNewNetherSpring(Blocks.FLUID_LAVA_FLOWING.id())))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_CRAG, Biomes.NETHER_SHELF})
			.withPositionSelector(PositionSelectors.HeightRangeBiasedTop)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(30)));

		this.register("btd:decoration/nether/default/nether_lava_spring_2", (new ChunkDecorationBuilder(new WorldFeatureNewNetherSpring(Blocks.FLUID_LAVA_FLOWING.id())))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeBiasedTop)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(20)));

		this.register("btd:decoration/nether/default/lava_lake", (new ChunkDecorationBuilder(new WorldFeatureLake(Blocks.FLUID_LAVA_STILL.id())))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_CRAG, Biomes.NETHER_CRYSTAL_PLAINS, Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_SHELF, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.UpperHeightRangeUniform)
			.withPlacementMethod(new PlacementMethod
				.ChanceToPlace(4)));

		this.register("btd:decoration/nether/default/lava_pool_terrace", (new ChunkDecorationBuilder(new WorldFeatureTerrace(Blocks.FLUID_LAVA_STILL, Blocks.COBBLE_BASALT, 4, 4)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS})
			.withPositionSelector(PositionSelectors.MinY)
			.withPlacementMethod(new PlacementMethod.ChanceToPlace(3)));


		// Pillar Features
		this.register("btd:decoration/nether/default/pillar_netherrack", (new ChunkDecorationBuilder(new WorldFeaturePillar(Blocks.COBBLE_NETHERRACK.id(), false)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRYSTAL_PLAINS, Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_CRAG, Biomes.NETHER_SHELF})
			.withPositionSelector((world, chunk, random, minY, maxY, rangeY) -> {
				int x = chunk.pos.x() * 16 + random.nextInt(16);
				int z = chunk.pos.z() * 16 + random.nextInt(16);
				int y = 200;
				this.pillarNoise.getValue(Math.floor((float) x / 32.0F), Math.floor((float) z / 32.0F), this.worleyResult);
				int xPillar = (int) (this.worleyResult.center.x * (double) 32.0F);
				int zPillar = (int) (this.worleyResult.center.y * (double) 32.0F);
				return new TilePos(xPillar, y, zPillar);
			})
			.withPlacementMethod(new PlacementMethod
				.ChanceToPlace(4)));

		this.register("btd:decoration/nether/default/pillar_cobble_basalt", (new ChunkDecorationBuilder(new WorldFeaturePillar(Blocks.COBBLE_BASALT.id(), true)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector((world, chunk, random, minY, maxY, rangeY) -> {
				int x = chunk.pos.x() * 16 + random.nextInt(16);
				int z = chunk.pos.z() * 16 + random.nextInt(16);
				int y = 200;
				this.pillarNoise.getValue(Math.floor((float) x / 32.0F), Math.floor((float) z / 32.0F), this.worleyResult);
				int xPillar = (int) (this.worleyResult.center.x * (double) 32.0F);
				int zPillar = (int) (this.worleyResult.center.y * (double) 32.0F);
				return new TilePos(xPillar, y, zPillar);
			})
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(4)));

		this.register("btd:decoration/nether/default/pillar_slate", (new ChunkDecorationBuilder(new WorldFeaturePillar(Blocks.SLATE.id(), true)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_OLD_WORLD})
			.withPositionSelector((world, chunk, random, minY, maxY, rangeY) -> {
				int x = chunk.pos.x() * 16 + random.nextInt(16);
				int z = chunk.pos.z() * 16 + random.nextInt(16);
				int y = 200;
				this.pillarNoise.getValue(Math.floor((float) x / 32.0F), Math.floor((float) z / 32.0F), this.worleyResult);
				int xPillar = (int) (this.worleyResult.center.x * (double) 32.0F);
				int zPillar = (int) (this.worleyResult.center.y * (double) 32.0F);
				return new TilePos(xPillar, y, zPillar);
			})
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(4)));


		// Sulfur biome features

		this.register("btd:decoration/nether/default/boulder_magma", new ChunkDecorationBuilder(new WorldFeatureBoulder(Blocks.MAGMA, Blocks.COBBLE_BASALT))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

		this.register("btd:decoration/nether/default/boulder_sulfur", new ChunkDecorationBuilder(new WorldFeatureBoulder(BTDBlocks.SULFUR, Blocks.BRIMSAND))
			.withBiomeMask(new Biome[]{Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

		this.register("minecraft:decoration/nether/default/sulfur_pool_terrace", new ChunkDecorationBuilder(new WorldFeatureTerrace(BTDBlocks.FLUID_ACID_STILL, Blocks.BRIMSAND))
			.withBiomeMask(new Biome[]{Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.MinY)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(1)));

		this.register("btd:decoration/nether/default/sulfur_pool_floor_vent", new ChunkDecorationBuilder(new WorldFeatureFloorVent())
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(12)));

		this.register("btd:decoration/nether/default/sulfur_pool_spire", new ChunkDecorationBuilder(new WorldFeatureThermalSpire())
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(8)));

		this.register("btd:decoration/nether/default/nether_roof_spire", new ChunkDecorationBuilder(new WorldFeatureRoofSpire(Blocks.COBBLE_NETHERRACK))
			.withBiomeMask(new Biome[]{Biomes.NETHER_CRAG, Biomes.NETHER_SHELF})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

		this.register("btd:decoration/nether/default/volcano_roof_spire", new ChunkDecorationBuilder(new WorldFeatureRoofSpire(Blocks.BASALT))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

		this.register("btd:decoration/nether/default/sulfur_roof_spire", new ChunkDecorationBuilder(new WorldFeatureRoofSpire(BTDBlocks.SULFUR))
			.withBiomeMask(new Biome[]{Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

		this.register("btd:decoration/nether/default/oldworld_roof_spire", new ChunkDecorationBuilder(new WorldFeatureRoofSpire(Blocks.SLATE))
			.withBiomeMask(new Biome[]{Biomes.NETHER_OLD_WORLD})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));


		// Nethercoal Features

		this.register("btd:decoration/nether/default/nether_coal_ore", (new ChunkDecorationBuilder(new WorldFeatureOre(BlockLogicOreNetherCoal.variantMap, 12)))
			.withPositionSelector(PositionSelectors.HeightRangeUniform)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(10)));

		this.register("btd:decoration/nether/default/nether_coal_ore_2", (new ChunkDecorationBuilder(new WorldFeatureOre(BlockLogicOreNetherCoal.variantMap, 12)))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS})
			.withPositionSelector(PositionSelectors.HeightRangeUniform)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(20)));


		// Glowstone Features

		this.register("btd:decoration/nether/default/glowstone_1", (new ChunkDecorationBuilder(new WorldFeatureGlowstone()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_CRAG, Biomes.NETHER_CRYSTAL_PLAINS, Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_SHELF, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod((feature, world, chunk, random) -> {
				int max = random.nextInt(random.nextInt(10) + 1);

				for (int i = 0; i < max; ++i) {
					feature.placeFeature(world, chunk, random);
				}

			}));
		this.register("btd:decoration/nether/default/glowstone_2", (new ChunkDecorationBuilder(new WorldFeatureGlowstone()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_CRAG, Biomes.NETHER_CRYSTAL_PLAINS, Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_SHELF, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(10)));

		this.register("btd:decoration/nether/default/glowstone_sulfur", (new ChunkDecorationBuilder(new WorldFeatureGlowstone()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(20)));


		// Fire Features

		this.register("btd:decoration/nether/default/patch_fire_1", (new ChunkDecorationBuilder(new WorldFeatureFire()))
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(4)));

		this.register("btd:decoration/nether/default/patch_fire_2", (new ChunkDecorationBuilder(new WorldFeatureFire()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(4)));


		// Bone Piles and Soulcatchers

		this.register("btd:decoration/nether/default/old_world_patch_bone_pile", (new ChunkDecorationBuilder(new WorldFeatureNetherPatch(Blocks.BONE_PILE.id())))
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(4)));

		this.register("btd:decoration/nether/default/patch_bone_pile", (new ChunkDecorationBuilder(new WorldFeatureNetherPatch(Blocks.BONE_PILE.id())))
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(1)));

		this.register("btd:decoration/nether/default/patch_boulder", new ChunkDecorationBuilder(new WorldFeatureNetherPatch(BTDBlocks.BOULDER.id()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_VOLCANIC_ISLANDS, Biomes.NETHER_CRAG, Biomes.NETHER_CRYSTAL_PLAINS, Biomes.NETHER_CRYSTAL_FOREST, Biomes.NETHER_SHELF, Biomes.NETHER_SULFUR_POOLS})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(1)));

		this.register("btd:decoration/nether/default/patch_boulder_oldworld", new ChunkDecorationBuilder(new WorldFeatureNetherPatchBelow(BTDBlocks.EMBER.id()))
			.withBiomeMask(new Biome[]{Biomes.NETHER_OLD_WORLD})
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(16)));

		this.register("btd:decoration/nether/default/patch_soul_catcher", (new ChunkDecorationBuilder(new WorldFeatureNetherPatch(Blocks.SOULCATCHER.id())))
			.withPositionSelector(PositionSelectors.HeightRangeUniformFromOcean)
			.withPlacementMethod(new PlacementMethod
				.TriesPerChunk(2)));

	}


}
