package betterthandeer.btd.mixin;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.block.conduit.BlockLogicConduitRubyglass;
import net.minecraft.core.current.wire.Node;
import net.minecraft.core.current.wire.WireConnectionManager;
import net.minecraft.core.current.wire.WireHandler;
import net.minecraft.core.current.wire.WireNode;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.*;

@Mixin(value = WireConnectionManager.class, remap = false)
public abstract class WireConnectionManagerMixin {

	@Shadow @Final WireNode owner;
	@Shadow int total;
	@Shadow private int flowTotal;
	@Shadow int iFlowDir;

	@Shadow public abstract void clear();
	@Shadow public abstract void add(WireNode wire, int iDir, boolean offer, boolean accept);

	/**
	 * @author MelonMojito
	 * @reason Incorporating Rubyglass Conduit transparency/connection logic into wire management.
	 */
	@Overwrite
	void set(WireHandler.NodeProvider nodes) {
		if (total > 0) {
			clear();
		}

		Node ownerDown = nodes.getNeighbor(owner, WireHandler.Directions.DOWN);
		boolean belowIsConductor = ownerDown.isConductor();

		Node ownerUp = nodes.getNeighbor(owner, WireHandler.Directions.UP);
		boolean aboveIsConductor = ownerUp.isConductor();

		// Check if the block directly above the bottom wire is an open conduit facing down
		boolean aboveIsOpenDown = isConduitOpenTowards(ownerUp, WireHandler.Directions.DOWN);

		for (int iDir = 0; iDir < WireHandler.Directions.HORIZONTAL.length; iDir++) {
			Node neighbor = nodes.getNeighbor(owner, iDir);
			if (neighbor.isWire()) {
				add(neighbor.asWire(), iDir, true, true);
				continue;
			}

			boolean sideIsConductor = neighbor.isConductor();
			int iOppDir = WireHandler.Directions.iOpposite(iDir);

			// looking downwards logic
			boolean blockedDown = sideIsConductor ||
				isConduitOpenTowards(ownerDown, iDir) ||
				isConduitOpenTowards(neighbor, WireHandler.Directions.UP);

			if (!blockedDown) {
				Node node = nodes.getNeighbor(neighbor, WireHandler.Directions.DOWN);

				if (node.isWire()) {
					// Check if ownerDown is specifically the ruby glass conduit to determine offer power
					boolean offerDown = belowIsConductor ||
						(ownerDown.state.is(BTDBlocks.CONDUIT_RUBYGLASS) && !isConduitOpenTowards(ownerDown, iDir));
					add(node.asWire(), iDir, offerDown, true);
				}
			}

			// looking upwards logic
			boolean blockedUp = aboveIsConductor ||
				aboveIsOpenDown ||
				isConduitOpenTowards(neighbor, iOppDir);

			if (!blockedUp) {
				Node node = nodes.getNeighbor(neighbor, WireHandler.Directions.UP);

				if (node.isWire()) {
					boolean acceptUp = sideIsConductor ||
						(neighbor.state.is(BTDBlocks.CONDUIT_RUBYGLASS) && !isConduitOpenTowards(neighbor, iOppDir));
					add(node.asWire(), iDir, true, acceptUp);
				}
			}
		}

		if (total > 0) {
			iFlowDir = WireHandler.FLOW_IN_TO_FLOW_OUT[flowTotal];
		}
	}

	/**
	 * Checks if a node is a Rubyglass Conduit and if a specific directional port is open.
	 */
	@Unique
	private boolean isConduitOpenTowards(Node node, int iDir) {
		if (node.state.is(BTDBlocks.CONDUIT_RUBYGLASS)) {
			Side face = switch (iDir) {
				case WireHandler.Directions.WEST -> Side.WEST;
				case WireHandler.Directions.NORTH -> Side.NORTH;
				case WireHandler.Directions.EAST -> Side.EAST;
				case WireHandler.Directions.SOUTH -> Side.SOUTH;
				case WireHandler.Directions.DOWN -> Side.BOTTOM;
				case WireHandler.Directions.UP -> Side.TOP;
				default -> Side.NONE;
			};
			return face != Side.NONE && BlockLogicConduitRubyglass.isSideOpen(node.state.get(), face);
		}
		return false;
	}
}
