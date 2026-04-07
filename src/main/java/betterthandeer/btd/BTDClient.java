package betterthandeer.btd;

import net.fabricmc.api.ClientModInitializer;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class BTDClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void onInitializeClient() {
		BetterThanDeerMod.LOGGER.info("Better Than Deer Client initialized.");
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {

	}
}
