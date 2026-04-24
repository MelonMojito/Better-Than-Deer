package betterthandeer.btd.world.chunk.feature.dungeon.hanging;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class WorldFeatureHangingDungeon {

	public static final WeightedRandomBag<WeightedRandomLootObject> lootNormal = new WeightedRandomBag<>();

	static {
		lootNormal.addEntry(new WeightedRandomLootObject(Items.INGOT_STEEL_CRUDE.getDefaultStack(), 1, 6), 50.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.FOOD_APPLE_GOLD.getDefaultStack()), 3.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 6, 24), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.DUST_GLOWSTONE.getDefaultStack(), 6, 24), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.NETHERCOAL.getDefaultStack(), 3, 9), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Blocks.TNT.getDefaultStack(), 4, 12), 50.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Blocks.RAIL.getDefaultStack(), 12, 32), 50.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Blocks.RAIL_POWERED.getDefaultStack(), 9, 16), 50.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.INGOT_GOLD.getDefaultStack(), 5, 8), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.INGOT_IRON.getDefaultStack(), 5, 10), 100.0F);

		for (int i = 0; i < 9; ++i) {
			lootNormal.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[Items.RECORD_13.id + i]), 1), 1.0F);
		}

		lootNormal.addEntry(new WeightedRandomLootObject(Blocks.PUMICE_DRY.getDefaultStack(), 2, 7), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 2, 7), 100.0F);
		lootNormal.addEntry(new WeightedRandomLootObject(null), 180.0F);
	}


	public static class RandomType implements WorldFeatureInterface {
		private final WorldFeatureInterface[] features;

		public RandomType() {
			this.features = new WorldFeatureInterface[] {
				new Basalt(),
				new Slate(),
				new Gloomstone(),
				new Netherrack()
			};
		}

		@Override
		public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePosc) {
			int index = random.nextInt(features.length);
			return features[index].place(world, random, tilePosc);
		}
	}

	public static class Basalt implements WorldFeatureInterface {
		private static final WeightedRandomBag<Block<?>> hangingDungeonBase = new WeightedRandomBag<>();

		static {
			hangingDungeonBase.addEntry(Blocks.BRICK_BASALT, 1);
			hangingDungeonBase.addEntry(Blocks.BASALT_CARVED, .75);
			hangingDungeonBase.addEntry(Blocks.BASALT_POLISHED, .10);
		}

		private static final WeightedRandomBag<Block<?>> hangingDungeonRope = new WeightedRandomBag<>();

		static {
			hangingDungeonRope.addEntry(BTDBlocks.CHAIN_LARGE, 1);
		}

		private static final WorldFeatureInterface dungeon = new WorldFeatureHangingDungeonBase(
			7,
			32,
			10,
			6,
			hangingDungeonBase, hangingDungeonRope,
			lootNormal
		);

		public Basalt() {
		}

		@Override
		public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
			return dungeon.place(world, random, tilePosc);
		}

	}

	public static class Slate implements WorldFeatureInterface {
		private static final WeightedRandomBag<Block<?>> hangingDungeonBase = new WeightedRandomBag<>();

		static {
			hangingDungeonBase.addEntry(Blocks.BRICK_SLATE, 1);
			hangingDungeonBase.addEntry(Blocks.SLATE_POLISHED, .75);
			hangingDungeonBase.addEntry(BTDBlocks.SLATE_CARVED, .10);
		}

		private static final WeightedRandomBag<Block<?>> hangingDungeonRope = new WeightedRandomBag<>();

		static {
			hangingDungeonRope.addEntry(BTDBlocks.CHAIN_STEEL_LARGE, 1);
		}

		private static final WorldFeatureInterface dungeon = new WorldFeatureHangingDungeonBase(
			7,
			32,
			10,
			6,
			hangingDungeonBase, hangingDungeonRope,
			lootNormal
		);

		public Slate() {
		}

		@Override
		public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePosc) {
			return dungeon.place(world, random, tilePosc);
		}

	}

	public static class Gloomstone implements WorldFeatureInterface {
		private static final WeightedRandomBag<Block<?>> hangingDungeonBase = new WeightedRandomBag<>();

		static {
			hangingDungeonBase.addEntry(Blocks.BRICK_GLOOMSTONE, 1);
			hangingDungeonBase.addEntry(Blocks.GLOOMSTONE_CARVED, .75);
			hangingDungeonBase.addEntry(Blocks.GLOOMSTONE_POLISHED, .10);
		}

		private static final WeightedRandomBag<Block<?>> hangingDungeonRope = new WeightedRandomBag<>();

		static {
			hangingDungeonRope.addEntry(BTDBlocks.CHAIN_STEEL_LARGE, 1);
		}

		private static final WorldFeatureInterface dungeon = new WorldFeatureHangingDungeonBase(
			7,
			32,
			10,
			6,
			hangingDungeonBase, hangingDungeonRope,
			lootNormal
		);

		public Gloomstone() {
		}

		@Override
		public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePosc) {
			return dungeon.place(world, random, tilePosc);
		}

	}

	public static class Netherrack implements WorldFeatureInterface {
		private static final WeightedRandomBag<Block<?>> hangingDungeonBase = new WeightedRandomBag<>();

		static {
			hangingDungeonBase.addEntry(Blocks.BRICK_NETHERRACK, 1);
			hangingDungeonBase.addEntry(Blocks.NETHERRACK_CARVED, .75);
			hangingDungeonBase.addEntry(Blocks.NETHERRACK_POLISHED, .10);
		}

		private static final WeightedRandomBag<Block<?>> hangingDungeonRope = new WeightedRandomBag<>();

		static {
			hangingDungeonRope.addEntry(BTDBlocks.CHAIN_STEEL_LARGE, 1);
		}

		private static final WorldFeatureInterface dungeon = new WorldFeatureHangingDungeonBase(
			7,
			32,
			10,
			6,
			hangingDungeonBase, hangingDungeonRope,
			lootNormal
		);

		public Netherrack() {
		}

		@Override
		public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePosc) {
			return dungeon.place(world, random, tilePosc);
		}

	}
}
