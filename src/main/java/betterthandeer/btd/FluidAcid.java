package betterthandeer.btd;

import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.Fluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.type.tag.WorldTypeTags;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FluidAcid implements Fluid {
	public FluidAcid() {
	}

	public int tickDelay() {
		return 16;
	}

	@Override
	public void animationTick(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
		int data = world.getBlockData(tilePos) & 15;
		TilePos queryPos = new TilePos();

		boolean isBottom = world.isBlockOpaqueCube(tilePos.down(queryPos));
		if (data > 0 && data < 8 && rand.nextInt(64) == 0) {
			world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (float) tilePos.x() + 0.5F, (float) tilePos.y() + 0.5F, (float) tilePos.z() + 0.5F, "liquid.water", rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F);
		}

		if (world.getWorldType().hasTag(WorldTypeTags.NETHER) && (isBottom || rand.nextInt(16) == 0) && data == 0 && rand.nextInt(2) == 0) {
			world.spawnParticle("acidboiling", (double) tilePos.x() + Math.random(), (float) tilePos.y() + 0.05F, (double) tilePos.z() + Math.random(), 0.0F, 0.0F, 0.0F, 0, false);
		}

		if (rand.nextInt(4) == 0 && world.getBlockType(tilePos.down(new TilePos())).solid() && !world.getBlockType(tilePos.down(new TilePos()).down()).solid()) {
			float off1 = rand.nextFloat() * 0.9F + 0.05F;
			float off2 = rand.nextFloat() * 0.9F + 0.05F;
			float lOff = 0.01F;
			world.spawnParticle("dripAcid", (float) tilePos.x() + off1, (float) (tilePos.y() - 1) - lOff, (float) tilePos.z() + off2, 0.0F, 0.0F, 0.0F, 0, false);
		}

	}

	public boolean checkForHarden(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Material encountered) {
		if (encountered == Materials.LAVA) {
			int data = world.getBlockData(tilePos) & 15;
			if (data == 0) {
				world.setBlockTypeNotify(tilePos, Blocks.AIR);
			}

			world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (float) tilePos.x() + 0.5F, (float) tilePos.y() + 0.5F, (float) tilePos.z() + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

			for (int i = 0; i < 8; ++i) {
				world.spawnParticle("largesmoke", (double) tilePos.x() + Math.random(), (double) tilePos.y() + 1.2, (double) tilePos.z() + Math.random(), 0.0F, 0.0F, 0.0F, 0, false);
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean shouldTick() {
		return false;
	}

	public void updateTickStill(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
	}

	public byte getFlowDecayMod(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePosc tilePos) {
		return 1;
	}

	public boolean canBecomeSource(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
		return false;
	}

	public void onFlowIntoBlock(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePos tilePos, int meta) {
		world.getBlockType(tilePos).dropWithCause(world, EnumDropCause.WORLD, tilePos, world.getBlockData(tilePos), null, null);
	}

	public boolean canSpreadTo(@NotNull BlockLogicFluid logicFluid, @NotNull World world, @NotNull TilePos tilePos, @NotNull Material material) {
		return material != Materials.LAVA && material != Materials.WATER;
	}
}

