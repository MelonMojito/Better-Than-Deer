package betterthandeer.btd.entity;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
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
		EntityDispatcher.getInstance().addMapping(MobGargoyle.class, NamespaceID.fromPool(MOD_ID, "gargoyle"), MobGargoyle::new, "guidebook.section.mob.gargoyle.name");

		EntityDispatcher.getInstance().addMapping(ProjectileRock.class, NamespaceID.fromPool(MOD_ID, "rock"), ProjectileRock::new);

		EntityDispatcher.getInstance().addMapping(ProjectileArrowFlaming.class, NamespaceID.fromPool(MOD_ID, "arrow_flaming"), ProjectileArrowFlaming::new);
	}
}
