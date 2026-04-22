package betterthandeer.btd.block.conduit;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicWireRedstone;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockLogicConduitRubyglass extends BlockLogic {

	public static final Side[][] STATE_TABLE = {
		{},                         // 0
		{Side.BOTTOM}, {Side.TOP},  // 1-2
		{Side.NORTH}, {Side.SOUTH}, // 3-4
		{Side.WEST}, {Side.EAST},   // 5-6

		{Side.BOTTOM, Side.TOP},    // 7 Y-Axis
		{Side.NORTH, Side.SOUTH},   // 8 Z-Axis
		{Side.WEST, Side.EAST},     // 9 X-Axis

		{Side.BOTTOM, Side.NORTH}, {Side.BOTTOM, Side.SOUTH}, // 10-11
		{Side.BOTTOM, Side.WEST}, {Side.BOTTOM, Side.EAST},   // 12-13
		{Side.TOP, Side.NORTH}, {Side.TOP, Side.SOUTH},       // 14-15
		{Side.TOP, Side.WEST}, {Side.TOP, Side.EAST},         // 16-17
		{Side.NORTH, Side.WEST}, {Side.NORTH, Side.EAST},     // 18-19
		{Side.SOUTH, Side.WEST}, {Side.SOUTH, Side.EAST}      // 20-21
	};

	//PROPAGATION_LENGTH can ONLY BE A MAXIMUM OF 10. Anymore and the data will become mangled.
	//if changed after implementation, data of existing conduits will become mangled without a converter.
	//TODO There are a couple of hardcoded segments of code that do not account for PROPAGATION_LENGTH
	public static final int PROPAGATION_LENGTH = 10;
	public static final int STATE_COUNT = 22;

	public BlockLogicConduitRubyglass(@NotNull Block<?> block, @NotNull Material material) {
		super(block, material);
		block.withEntity(TileEntityConduitRubyglass::new);
	}

	@Override
	public int tickDelay() {
		return 0;
	}

	@Override
	public void updateTick(final @NotNull World world, final @NotNull TilePosc tilePos, final @NotNull Random rand, final boolean isRandomTick) {
		onNeighborChanged(world, tilePos, this.block);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public boolean isSignalSource() {return true;}

	@Override
	public void onNeighborChanged(final @NotNull World world, final @NotNull TilePosc cTilePos, final @NotNull Block<?> block) {
		if (world.isClientSide) return;
		((ConduitWorldAccess) world).getConduitHandler().onConduitUpdated(cTilePos);
	}

	@Override
	public void onPlacedByWorld(@NotNull World world, @NotNull TilePosc tilePos) {
		super.onPlacedByWorld(world, tilePos);
		if (!world.isClientSide) {
			((ConduitWorldAccess) world).getConduitHandler().onConduitAdded(tilePos);
		}
	}

	@Override
	public void onRemoved(@NotNull World world, @NotNull TilePosc tilePos, int data) {
		super.onRemoved(world, tilePos, data);
		if (!world.isClientSide) {
			((ConduitWorldAccess) world).getConduitHandler().onConduitRemoved(tilePos, data);
		}
	}


	/**
	 * Runs when a mob places this block. <div></div>
	 *      n = neighbor (ex. nTilePos = neighboring blocks TilePos) <div>
	 *      c = conduit (ex. cStrength = this conduit Signal Strength) <div>
	 *      valid = a conduit with an open side, facing this conduit. <div></div>
	 *      <div></div>
	 *
	 *              Smart placement logic for if the conduit is placed against another. <div>
	 *         Automatically connects open sides properly based on these rules: <div></div>
	 *
	 *           If there is ONLY ONE valid neighboring conduit facing this conduit-to-be-placed: <div>
	 *               automatically connect to it no matter how the conduit is placed down, <div></div>
	 *               and open the side facing the player.
	 *
	 *           If there is MORE THAN ONE valid neighboring conduit facing this conduit, and the player IS NOT sneaking: <div>
	 *               connect to both valid conduits facing this conduit. <div></div>
	 *
	 *           If the player IS sneaking:
	 *               connect the conduit to the side they are placing it against, assuming it is a valid open side. <div></div>
	 *
	 *
	 */
	@Override
	public void onPlacedByMob(final @NotNull World world, final @NotNull TilePosc cTilePos, final @NotNull Side sidePlacedOnto, final @NotNull Mob mob, final double xHit, final double yHit) {
		List<Side> validSides = new ArrayList<>();

		for (Side side : Side.values()) {
			TilePos nTilePos = new TilePos(cTilePos.x() + side.getOffsetX(), cTilePos.y() + side.getOffsetY(), cTilePos.z() + side.getOffsetZ());

			if (world.getBlockType(nTilePos) == this.block) {
				int nData = world.getBlockData(nTilePos);

				if (isSideOpen(nData, side.getOpposite())) {
					validSides.add(side);
				}
			}
		}

		int stateToPlaceAs = 0;
		int numOfValidSides = validSides.size();
		Side sideFacingPlayer = Direction.getDirection(mob).getOpposite().getSide();

		if (numOfValidSides == 1) {
			Side autoConnectedSide = validSides.get(0);

			if (sideFacingPlayer != autoConnectedSide && sideFacingPlayer != Side.NONE) {
				stateToPlaceAs = getStateFromTwoSides(autoConnectedSide, sideFacingPlayer);
			} else {
				stateToPlaceAs = getNextState(0, autoConnectedSide);
			}
		} else if (numOfValidSides == 2) {
			if (mob.isSneaking()) {
				Side autoConnectedSide = sidePlacedOnto.getOpposite();

				TilePos nTilePos = new TilePos(cTilePos).add(autoConnectedSide);

				boolean isMatchingNeighbor = world.getBlockType(nTilePos) == this.block;
				boolean isNeighborSideOpen = isSideOpen(world.getBlockData(nTilePos), sidePlacedOnto);

				if (isMatchingNeighbor && isNeighborSideOpen) {
					stateToPlaceAs = getNextState(0, autoConnectedSide);
				}
			} else {
				stateToPlaceAs = getStateFromTwoSides(validSides.get(0), validSides.get(1));
			}
		} else if (numOfValidSides > 2) {
			Side autoConnectedSide = sidePlacedOnto.getOpposite();

			TilePos nTilePos = new TilePos(cTilePos).add(autoConnectedSide);

			boolean isMatchingNeighbor = world.getBlockType(nTilePos) == this.block;
			boolean isNeighborSideOpen = isSideOpen(world.getBlockData(nTilePos), sidePlacedOnto);

			if (isMatchingNeighbor && isNeighborSideOpen) {
				if (sideFacingPlayer != autoConnectedSide && sideFacingPlayer != Side.NONE) {
					stateToPlaceAs = getStateFromTwoSides(autoConnectedSide, sideFacingPlayer);
				} else {
					stateToPlaceAs = getNextState(0, autoConnectedSide);
				}
			}
		}

		if (stateToPlaceAs != 0) {
			world.setBlockDataNotify(cTilePos, stateToPlaceAs);
			this.onNeighborChanged(world, cTilePos, this.block);
		}
	}

	@Override
	public boolean onInteracted(final @NotNull World world, final @NotNull TilePosc cTilePos, final @NotNull Player player, final @Nullable Side side, final double xHit, final double yHit) {
		if(side == null) return false;

		boolean holdingNothing = player.getHeldItem() == null;
		boolean holdingConduit = false;
		if(!holdingNothing){
			holdingConduit = player.getHeldItem().itemID == BTDBlocks.CONDUIT_RUBYGLASS.getDefaultStack().itemID;
		}


		if(holdingNothing || holdingConduit) {
			int cData = world.getBlockData(cTilePos);
			int cState = cData % STATE_COUNT;
			int cStrength = cData / STATE_COUNT;

			if(holdingConduit){
				for(Side s : getOpenSides(cData)){
					if(s == side) return false;
				}
			}

			int nextState = getNextState(cState, side);

			if (nextState != cState) {
				world.setBlockDataNotify(cTilePos, cStrength * STATE_COUNT + nextState);
				onNeighborChanged(world, cTilePos, this.block);

				//TODO better custom sounds these are placeholder
				world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, cTilePos.x(), cTilePos.y(), cTilePos.z(), "tile.activator.use", 1, 1.2f);
				world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, cTilePos.x(), cTilePos.y(), cTilePos.z(), "note.hat", 0.7f, 0.4f);
				//world.playBlockEvent(cTilePos, LevelListener.EVENT_CONDUIT_PARTICLES, packEventConduitData(side, cStrength));

				return true;
			}
			return false;
		}

		return false;
	}

	public static byte packEventConduitData(Side side, int strength) {
		if (side == null) side = Side.NONE;
		int s = Math.max(0, Math.min(10, strength));
		int sideId = side.getId() & 0x07;
		return (byte) ((sideId << 4) | (s & 0x0F));
	}

	@Override
	public boolean isEmittingSignal(final @NotNull WorldSource source, final @NotNull TilePosc tilePos, final @NotNull Side side) {
		int data = source.getBlockData(tilePos);
		int currentPower = data / STATE_COUNT;

		if (currentPower == 0) {
			return false;
		}

		//can only emit signal out of an open face
		Side conduitFace = side.getOpposite();
		return isSideOpen(data, conduitFace);
	}


	/**
	 * Dynamically determines the next state of a conduit based on a queried side (interacted with, placed against, trying to connect to).
	 * @param currentState The current state of the conduit
	 * @param sideQueried The side that was queried.
	 * @return The new conduit state based on the interaction
	 */
	private int getNextState(int currentState, @NotNull Side sideQueried) {
		Side[] nestState = STATE_TABLE[currentState];

		for (Side side : nestState) {
			if (side == sideQueried) {
				if (nestState.length == 1) return 0;
				Side remainingSide = (nestState[0] == sideQueried) ? nestState[1] : nestState[0];
				return remainingSide.getId() + 1;
			}
		}

		if (nestState.length == 0) {
			return sideQueried.getId() + 1;
		}
		else if (nestState.length == 1) {
			return getStateFromTwoSides(nestState[0], sideQueried);
		}

		return currentState;
	}

	/**
	 * Determines a state that contains the two provided sides.
	 * @param s1 The first open side
	 * @param s2 The second open side
	 * @return The conduit state that matches the two side parameters or 0 if no match is found
	 */
	private int getStateFromTwoSides(@NotNull Side s1, @NotNull Side s2) {
		for (int i = 7; i < STATE_TABLE.length; i++) {
			Side[] pair = STATE_TABLE[i];

			boolean match1 = (pair[0] == s1 || pair[1] == s1);
			boolean match2 = (pair[0] == s2 || pair[1] == s2);

			if (match1 && match2) {
				return i;
			}
		}
		return 0;
	}



	/**
	 * Determines the incoming signal strength from a neighboring block on any given side of the conduit and converts it. <div></div>
	 * n = neighbor (ex. nTilePos = neighboring blocks TilePos) <div></div>
	 * c = conduit (ex. cStrength = this conduit Signal Strength) <div></div>
	 * @param world The world
	 * @param cTilePos The position of the conduit
	 * @param side The side to check
	 * @return A converted signal value from 0 to 7
	 */
	public static int getAndConvertNeighborSignal(@NotNull WorldSource world, @NotNull TilePosc cTilePos, @NotNull Side side) {

		TilePos nTilePos = new TilePos(cTilePos.x() + side.getOffsetX(), cTilePos.y() + side.getOffsetY(), cTilePos.z() + side.getOffsetZ());
		Block<?> nBlockType = world.getBlockType(nTilePos);

		int cStrength = world.getBlockData(cTilePos) / STATE_COUNT;

		if(nBlockType == Blocks.WIRE_REDSTONE) {
			int nData = world.getBlockData(nTilePos);
			int nStrength = nData & BlockLogicWireRedstone.MASK_POWER;
			return signalConverter(nStrength, false);
		}

		if (nBlockType == BTDBlocks.CONDUIT_RUBYGLASS) {
			int nData = world.getBlockData(nTilePos);
			if (isSideOpen(nData, side.getOpposite())) {
				int nStrength = nData / STATE_COUNT;

				if (nStrength <= cStrength) return 0;

				return nStrength - 1;
			}
			return 0;
		}

		if (nBlockType.isSignalSource()) {
			if (nBlockType.isEmittingSignal(world, nTilePos, side)) {
				return PROPAGATION_LENGTH;
			}
		}
		return 0;
	}

	/**
	 * Determines a conduit power level output toward a given side, or 0 if that side is closed. <div></div>
	 *      c = conduit (ex. cStrength = this conduit Signal Strength) <div></div>
	 * @param cData The metadata of the conduit.
	 * @param sideTowardReceiver The side of the conduit facing the block asking for power.
	 * @return Conduit power (0-{@link #PROPAGATION_LENGTH})
	 */
	public static int getOutputPower(int cData, @NotNull Side sideTowardReceiver) {
		return isSideOpen(cData, sideTowardReceiver) ? cData / STATE_COUNT : 0;
	}

	/**
	 * Determines if a specific side on a conduit is open. <div></div>
	 *      c = conduit (ex. cStrength = this conduit Signal Strength) <div></div>
	 * @param cData The metadata of the conduit to check.
	 * @param side The side to check.
	 * @return true if the side is open, false otherwise.
	 */
	public static boolean isSideOpen(int cData, @NotNull Side side) {
		int cState = cData % STATE_COUNT;

		if (cState == 0) {
			return false;
		}

		Side[] openSides = STATE_TABLE[cState];

		for (Side s : openSides) {
			if (s == side) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Determines which sides on a given conduit are open. <div></div>
	 *      c = conduit (ex. cStrength = this conduit Signal Strength) <div></div>
	 * @param cData The metadata of the conduit to check.
	 * @return a {@link Side} {@link Array} of sides that are open.
	 */
	public static Side[] getOpenSides(int cData){
		return STATE_TABLE[cData % STATE_COUNT];
	}

	public static final int[] REDSTONE_TO_CONDUIT = {0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10};
	public static final int[] CONDUIT_TO_REDSTONE = {0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15};

	/**
	 * Converts signal strengths between redstone and conduit.
	 * @param strength The signal strength to convert
	 * @param conduitToRedstone Whether to convert conduit -> redstone or vice versa
	 * @return The converted signal strength (0-10)->(0-15) or (0-15)->(0-10)
	 */
	public static int signalConverter(int strength, boolean conduitToRedstone) {
		return conduitToRedstone
			? CONDUIT_TO_REDSTONE[Math.max(0, Math.min(PROPAGATION_LENGTH, strength))]
			: REDSTONE_TO_CONDUIT[Math.max(0, Math.min(15, strength))];
	}
}


