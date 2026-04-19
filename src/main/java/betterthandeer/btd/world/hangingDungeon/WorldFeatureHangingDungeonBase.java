package betterthandeer.btd.world.hangingDungeon;

import betterthandeer.btd.BTDHelpers;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

import org.joml.Vector2d;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldFeatureHangingDungeonBase implements WorldFeatureInterface {

	public final int maxDrop;
	public final int minDrop;
	public final int size;
	public final int clumping;

	public final WeightedRandomBag<Block<?>> base;
	public final WeightedRandomBag<Block<?>> ropes;

	public final WeightedRandomBag<WeightedRandomLootObject> lootTable;

	public WorldFeatureHangingDungeonBase(
		int size,
		int maxDrop,
		int minDrop,
		int clumping,
		WeightedRandomBag<Block<?>> base,
		WeightedRandomBag<Block<?>> rope,
		WeightedRandomBag<WeightedRandomLootObject> lootTable
	) {
		assert size >= 7;
		// You don't want less than 7 because it will look ass.

		assert size % 2 != 0;
		// You don't want an even number because the spawner will be crooked.

		this.size = size;
		this.maxDrop = maxDrop;
		this.minDrop = minDrop;
		this.clumping = clumping;
		this.ropes = rope;
		this.base = base;
		this.lootTable = lootTable;
	}

	@Override
	public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePosc) {
		// 1. find the highest block from current Y.
		// 2. check of an empty column on the 4 corners
		// 3. place all ropes to a random height
		// 4. fill platform

		final TilePos[] corners = {
			new TilePos(tilePosc.x(), tilePosc.y(), tilePosc.z()),
			new TilePos(tilePosc.x() + this.size - 1, tilePosc.y(), tilePosc.z()),
			new TilePos(tilePosc.x(), tilePosc.y(), tilePosc.z() + this.size - 1),
			new TilePos(tilePosc.x()+ this.size - 1, tilePosc.y(), tilePosc.z() + this.size - 1),
		};

		for (TilePos tilePos : corners) {
			for (int currY = tilePos.y(); currY < world.getWorldType().getMaxY(world); currY++) {
				if (currY > world.getWorldType().getMaxY(world)) {
					return false;
				}

				var block = new TilePos(tilePos);
				block.y = currY;

				if (world.getBlockType(block).solid()) {
					tilePos.y = currY;
					break;
				}
			}
		}

		int minMaxHeight = Arrays.stream(corners).map(TilePos::y).min(Integer::compareTo).get();
		int maxMaxHeight = Arrays.stream(corners).map(TilePos::y).max(Integer::compareTo).get();

		if (minMaxHeight - tilePosc.y() < this.minDrop) {
			return false;
		}

		var minHeight = minMaxHeight - MathHelper.clamp(random.nextInt(minMaxHeight - tilePosc.y()), this.minDrop, this.maxDrop);

		if (maxMaxHeight - minHeight > this.maxDrop * 2) {
			return false;
		}

		for (TilePos corner : corners) {
			for (int y = minHeight; y < corner.y(); y++) {
				var block = new TilePos(corner);
				block.y = y;

				var variant = ropes.getRandom(random);
				world.setBlockTypeDataNotify(block, variant, 0);
			}
		}

		for (int x = corners[0].x(); x <= corners[3].x(); x++) {
			for (int z = corners[0].z(); z <= corners[3].z(); z++) {
				var block = new TilePos(x, minHeight, z);
				var variant = base.getRandom(random);

				world.setBlockTypeData(block, variant, 0);
			}
		}

		final int half = this.size / 2;
		var center = new TilePos(tilePosc.x() + half, minHeight + 1, tilePosc.z() + half);

		world.setBlockTypeDataNotify(center, Blocks.MOBSPAWNER, 0);
		var spawner = (TileEntityMobSpawner) world.getTileEntity(center);
		if (spawner != null) {
			spawner.setMobId("btd:gargoyle");
		}

		final var positions = new ArrayList<TilePos>();
		positions.add(new TilePos(half, 0, 0));
		positions.add(new TilePos(-half, 0, 0));
		positions.add(new TilePos(0, 0, half));
		positions.add(new TilePos(0, 0, -half));

		for (int ii = 0; ii < 2; ii++) {
			var chestPos = positions.remove(random.nextInt(positions.size()-1));
			chestPos.add(center.x(), center.y(), center.z());

			world.setBlockTypeData(chestPos, Blocks.CHEST_PLANKS_OAK_PAINTED, DyeColor.RED.blockMeta * 16 + random.nextInt(3));

			var chest = (TileEntityChest) world.getTileEntity(chestPos);

			if (chest != null) {
				for (int i = 0; i < 3 + random.nextInt(8); i++) {
					chest.setItem((int) Math.floor(chest.getContainerSize() * random.nextFloat()), this.lootTable.getRandom(random).getItemStack(random));
				}
			}
		}

		if (random.nextInt(clumping) == 0) {
			var vec2 = new Vector2d(1, 0)
				.mul(BTDHelpers.rotateVec2(Math.PI * 2 * random.nextFloat()))
				.mul(this.size * 2 + (this.size * random.nextFloat()));

			var block = new TilePos(
				tilePosc.x() + Math.floor(vec2.x()),
				tilePosc.y() - 2 + random.nextInt(5),
				tilePosc.z() + Math.floor(vec2.y())
			);

			this.place(world, random, block);
		}

		return true;
	}
}
