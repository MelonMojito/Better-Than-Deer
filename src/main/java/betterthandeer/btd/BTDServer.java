package betterthandeer.btd;

import net.fabricmc.api.DedicatedServerModInitializer;

public class BTDServer implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		BetterThanDeerMod.LOGGER.info("Better Than Deer Server initialized.");
	}
}
