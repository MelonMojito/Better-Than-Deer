package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.block.conduit.BlockLogicConduitRubyglass;
import net.minecraft.core.current.wire.Node;
import net.minecraft.core.current.wire.WireHandler;
import net.minecraft.core.current.wire.WireNode;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

@Mixin(value = WireHandler.class, remap = false)
public abstract class WireHandlerMixin {

	@Shadow @Final
	private World world;
	@Final
	@Shadow private static int POWER_MIN;
	@Final
	@Shadow private static int POWER_MAX;

	@Shadow
	private Node getNeighbor(Node node, int iDir) {
		return null;
	}

	@Shadow
	private boolean hasDirectSignalTo(WireNode wire, Node node, int except) {
		return false;
	}

	/**
	 * @author MelonMojito
	 * @reason Incorporating Rubyglass Conduit logic into the standard wire power check.
	 */
	@Overwrite
	private void findExternalPower(WireNode wire) {
		if (wire.removed || wire.shouldBreak || wire.externalPower >= POWER_MAX) {
			return;
		}

		int maxExternalPower = POWER_MIN;

		for (int iDir = 0; iDir < WireHandler.Directions.ALL.length; iDir++) {
			Node neighboringNode = getNeighbor(wire, iDir);

			if (neighboringNode.isWire()) {
				continue;
			}

			//conduit logic
			if (neighboringNode.state.is(BTDBlocks.CONDUIT_RUBYGLASS)) {
				int cData = neighboringNode.state.get();

				Side conduitFace = getSide(iDir);

				if (BlockLogicConduitRubyglass.isSideOpen(cData, conduitFace)) {
					int cStrength = cData / BlockLogicConduitRubyglass.STATE_COUNT;
					int convertedStrength = BlockLogicConduitRubyglass.signalConverter(cStrength, true);

					//step down the signal strength when passing from conduit to wire
					convertedStrength = Math.max(0, convertedStrength - 1);

					//if signal is less than current power, set to 0
					if (convertedStrength < wire.currentPower) {
						convertedStrength = 0;
					}

					if (convertedStrength > maxExternalPower) {
						maxExternalPower = convertedStrength;
					}
				}
				//this continue took me hours to find. I hate my life
				continue;
			}

			if (neighboringNode.isConductor() && hasDirectSignalTo(wire, neighboringNode, WireHandler.Directions.iOpposite(iDir))) {
				maxExternalPower = POWER_MAX;
			}
			if (neighboringNode.isSignalSource() && neighboringNode.state.hasSignal(this.world, neighboringNode.pos, WireHandler.Directions.ALL[iDir])) {
				maxExternalPower = POWER_MAX;
			}
		}

		wire.externalPower = maxExternalPower;

		if (wire.externalPower > wire.virtualPower) {
			wire.virtualPower = wire.externalPower;
		}
	}

	@Unique
	private static @NotNull Side getSide(int iDir) {
		Side wireSide = Side.NONE;
		wireSide = switch (iDir) {
			case WireHandler.Directions.WEST -> Side.WEST;
			case WireHandler.Directions.NORTH -> Side.NORTH;
			case WireHandler.Directions.EAST -> Side.EAST;
			case WireHandler.Directions.SOUTH -> Side.SOUTH;
			case WireHandler.Directions.DOWN -> Side.BOTTOM;
			case WireHandler.Directions.UP -> Side.TOP;
			default -> wireSide;
		};

		return wireSide.getOpposite();
	}

}

