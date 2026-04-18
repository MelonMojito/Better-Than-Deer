package betterthandeer.btd.block.conduit;

import net.minecraft.core.world.pos.TilePos;

public class ConduitNode {
	TilePos tilePos;
	int currentPower;

	ConduitNode(TilePos tilePos, int currentPower) {
		this.tilePos = tilePos;
		this.currentPower = currentPower;
	}
}
