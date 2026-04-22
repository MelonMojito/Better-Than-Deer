package betterthandeer.btd.block.tar;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Fluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class FluidTar implements Fluid {
	public FluidTar() {
	}

	public int tickDelay() {
		return 10;
	}

	@Override
	public void animationTick(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
	}

	public boolean checkForHarden(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Material encountered) {
		return false;
	}

	public boolean shouldTick() {
		return false;
	}

	public void updateTickStill(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
	}

	public byte getFlowDecayMod(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePosc tilePos) {
		return 1;
	}

	public boolean canBecomeSource(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
		return false;
	}

	public void onFlowIntoBlock(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePos tilePos, int meta) {
		world.getBlockType(tilePos).dropWithCause(world, EnumDropCause.WORLD, tilePos, world.getBlockData(tilePos), null, null);
	}

	public boolean canSpreadTo(@NonNull BlockLogicFluid logicFluid, @NonNull World world, @NonNull TilePos tilePos, @NonNull Material material) {
		return material != Materials.LAVA && material != Materials.WATER && material != BTDBlocks.ACID;
	}
}
