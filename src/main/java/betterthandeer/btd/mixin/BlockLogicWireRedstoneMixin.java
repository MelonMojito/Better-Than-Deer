package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.block.conduit.BlockLogicConduitRubyglass;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicWireRedstone;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.support.ISupportable;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockLogicWireRedstone.class, remap = false)
public abstract class BlockLogicWireRedstoneMixin extends BlockLogic implements ISupportable {

	public BlockLogicWireRedstoneMixin(@NotNull Block<?> block, @NotNull Material material) {
		super(block, material);
	}

	@Inject(method = "checkTarget", at = @At("HEAD"), cancellable = true)
	private void checkTarget(World world, TilePos tilePos, int sourceStrength, CallbackInfoReturnable<Integer> cir) {
		if (world.getBlockType(tilePos) == BTDBlocks.CONDUIT_RUBYGLASS) {
			int cStrength = world.getBlockData(tilePos) / BlockLogicConduitRubyglass.STATE_COUNT;
			cir.setReturnValue(Math.max(BlockLogicConduitRubyglass.signalConverter(cStrength, true), sourceStrength));
		}
	}

	@Inject(method = "shouldConnectTo(Lnet/minecraft/core/world/WorldSource;Lnet/minecraft/core/world/pos/TilePos;Lnet/minecraft/core/util/helper/Side;)Z", at = @At("HEAD"), cancellable = true)
	private static void shouldConnectTo(WorldSource source, TilePos tilePos, Side side, CallbackInfoReturnable<Boolean> cir){
		final Block<?> block = source.getBlockType(tilePos);
		if (block == BTDBlocks.CONDUIT_RUBYGLASS) {
			cir.setReturnValue(BlockLogicConduitRubyglass.isSideOpen(source.getBlockData(tilePos), side.getOpposite()));
		}
	}

	/**
	 * @author MelonMojito
	 * @reason Fuck you
	 */
	@Overwrite
	private static boolean isSolidWallForWire(WorldSource world, TilePos pos, Side faceToCheck){
		if (world.isBlockNormalCube(pos)) return true;
		if (world.getBlockType(pos) == BTDBlocks.CONDUIT_RUBYGLASS) {
			return !BlockLogicConduitRubyglass.isSideOpen(world.getBlockData(pos), faceToCheck);
		}
		return false;
	}

	@Inject(method = "isBlockingConduit", at = @At("HEAD"), cancellable = true)
	private static void isBlockingConduit(WorldSource world, TilePos pos, Side openSide, CallbackInfoReturnable<Boolean> cir){
		if (world.getBlockType(pos) == BTDBlocks.CONDUIT_RUBYGLASS) {
			cir.setReturnValue(BlockLogicConduitRubyglass.isSideOpen(world.getBlockData(pos), openSide));
		}
	}

	/**
	 * @author MelonMojito
	 * @reason Fuck you x2
	 */
	@Overwrite
	public static boolean isClimbableWall(WorldSource world, TilePos wallTilePos, Side directionToWall){
		if (world.isBlockNormalCube(wallTilePos)) {
			return true;
		}
		if (world.getBlockType(wallTilePos) == BTDBlocks.CONDUIT_RUBYGLASS) {
			return !BlockLogicConduitRubyglass.isSideOpen(world.getBlockData(wallTilePos), directionToWall.getOpposite());
		}
		return false;
	}
}
