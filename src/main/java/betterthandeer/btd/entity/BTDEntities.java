package betterthandeer.btd.entity;

import betterthandeer.btd.entity.bat.MobBat;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.util.collection.NamespaceID;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDEntities {
	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeEntities();
		}

	}

	public static void initializeEntities() {
		EntityDispatcher.getInstance().addMapping(MobBat.class, NamespaceID.fromPool(MOD_ID, "bat"), MobBat::new, "guidebook.section.mob.bat.name");
	}
}
