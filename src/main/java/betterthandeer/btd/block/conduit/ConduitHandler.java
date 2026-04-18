package betterthandeer.btd.block.conduit;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

import java.util.*;


/**
 * One-Dimensional Implementation of Alternate Current for rubyglass conduits. <div></div>
 * Handles the logic and updates for any given conduit network and seamlessly integrates with other redstone components. <div></div>
 *
 * @author Melon Mojito (@melon_mojito)
 */
public class ConduitHandler {
	private final World world;
	private boolean updating = false;
	private final Queue<TilePos> updateQueue = new LinkedList<>();

	public ConduitHandler(World world) {
		this.world = world;
	}

	public void onConduitUpdated(TilePosc pos) {
		queueUpdate(new TilePos(pos));
	}

	public void onConduitAdded(TilePosc pos) {
		queueUpdate(new TilePos(pos));
	}

	public void onConduitRemoved(TilePosc pos, int oldData) {
		//update the sides this conduit was connected to
		for (Side side : BlockLogicConduitRubyglass.getOpenSides(oldData)) {
			TilePos nPos = new TilePos(pos).add(side);
			if (world.getBlockType(nPos) == Blocks.RUBYGLASS_CONDUIT) {
				queueUpdate(nPos);
			}
		}
	}

	private void queueUpdate(TilePos pos) {
		updateQueue.add(pos);
		if (!updating) {
			processQueue();
		}
	}

	private void processQueue() {
		updating = true;
		try {
			while (!updateQueue.isEmpty()) {
				TilePos startPos = updateQueue.poll();
				// Ensure the block is still a conduit before updating the network,
				// as it could have been broken while sitting in the queue.
				if (world.getBlockType(startPos) == Blocks.RUBYGLASS_CONDUIT) {
					updateNetwork(startPos);
				}
			}
		} finally {
			updating = false;
		}
	}

	private void updateNetwork(TilePos startPos) {
		//build the network
		Set<TilePos> network = new HashSet<>();
		Queue<TilePos> toSearch = new LinkedList<>();
		toSearch.add(startPos);

		while (!toSearch.isEmpty()) {
			TilePos pos = toSearch.poll();
			if (network.add(pos)) {
				int data = world.getBlockData(pos);
				for (Side side : BlockLogicConduitRubyglass.getOpenSides(data)) {
					TilePos nPos = new TilePos(pos).add(side);
					if (world.getBlockType(nPos) == Blocks.RUBYGLASS_CONDUIT &&
						BlockLogicConduitRubyglass.isSideOpen(world.getBlockData(nPos), side.getOpposite())) {
						toSearch.add(nPos);
					}
				}
			}
		}

		//external sources: levers, buttons, etc.
		Map<TilePos, Integer> externalPower = new HashMap<>();
		for (TilePos tilePos : network) {
			int data = world.getBlockData(tilePos);
			int currentPower = data / BlockLogicConduitRubyglass.STATE_COUNT;
			int maxExternalPower = 0;

			for (Side side : BlockLogicConduitRubyglass.getOpenSides(data)) {
				TilePos nTilePos = new TilePos(tilePos).add(side);
				Block<?> nBlockType = world.getBlockType(nTilePos);

				if (nBlockType != Blocks.RUBYGLASS_CONDUIT) {
					int external = BlockLogicConduitRubyglass.getAndConvertNeighborSignal(world, tilePos, side);

					//break the recursive loop for redstone dust
					if (nBlockType == Blocks.WIRE_REDSTONE) {
						if (external < currentPower) {
							external = 0;
						}
					}

					if (external > maxExternalPower) maxExternalPower = external;
				}
			}
			externalPower.put(tilePos, maxExternalPower);
		}

		//propagate from roots with priority
		Map<TilePos, Integer> newPowerLevels = new HashMap<>();
		for (TilePos tilePos : network) newPowerLevels.put(tilePos, 0);

		PriorityQueue<ConduitNode> powerQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.currentPower, a.currentPower));
		for (Map.Entry<TilePos, Integer> entry : externalPower.entrySet()) {
			if (entry.getValue() > 0) {
				powerQueue.add(new ConduitNode(entry.getKey(), entry.getValue()));
			}
		}

		while (!powerQueue.isEmpty()) {
			ConduitNode node = powerQueue.poll();
			//ignore weaker path
			if (node.currentPower <= newPowerLevels.get(node.tilePos)) continue;

			newPowerLevels.put(node.tilePos, node.currentPower);

			//propagate power down the line
			if (node.currentPower > 1) {
				int data = world.getBlockData(node.tilePos);
				for (Side side : BlockLogicConduitRubyglass.getOpenSides(data)) {
					TilePos nPos = new TilePos(node.tilePos).add(side);
					if (network.contains(nPos)) {
						powerQueue.add(new ConduitNode(nPos, node.currentPower - 1));
					}
				}
			}
		}

		Set<TilePos> blocksToNotify = new HashSet<>();
		for (TilePos tilePos : network) {
			int oldData = world.getBlockData(tilePos);
			int oldPower = oldData / BlockLogicConduitRubyglass.STATE_COUNT;
			int newPower = newPowerLevels.get(tilePos);

			if (oldPower != newPower) {
				int state = oldData % BlockLogicConduitRubyglass.STATE_COUNT;

				//suppress recursive neighbor updates while data is being set
				world.noNeighborUpdate = true;
				world.setBlockDataNotify(tilePos, newPower * BlockLogicConduitRubyglass.STATE_COUNT + state);
				world.noNeighborUpdate = false;

				//queue updates ONLY for blocks facing the open sides that ARE NOT conduits in this network
				for (Side side : BlockLogicConduitRubyglass.getOpenSides(state)) {
					TilePos targetPos = new TilePos(tilePos).add(side);
					if (!network.contains(targetPos)) {
						blocksToNotify.add(targetPos);
					}
				}
			}
		}

		//notify all neighbors after the network is done with its logic/updates
		for (TilePos nTilePos : blocksToNotify) {
			Block<?> neighborBlockType = world.getBlockType(nTilePos);
			neighborBlockType.onNeighborChanged(world, nTilePos, Blocks.RUBYGLASS_CONDUIT);
		}
	}
}
