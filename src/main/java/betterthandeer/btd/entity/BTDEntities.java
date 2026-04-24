package betterthandeer.btd.entity;

import betterthandeer.btd.entity.arrow.flaming.ProjectileArrowFlaming;
import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.entity.leecher.MobLeecher;
import betterthandeer.btd.entity.leecher.ProjectileLightningball;
import betterthandeer.btd.entity.rock.ProjectileRock;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.factories.EntityFactory;
import net.minecraft.core.util.collection.NamespaceID;

import static betterthandeer.btd.BetterThanDeerMod.MOD_ID;

public class BTDEntities {
	private static boolean hasInit = false;

	public static EntityFactory<MobGargoyle> FACTORY_GARGOYLE;
	public static EntityFactory<MobLeecher> FACTORY_LEECHER;

	public static EntityFactory<ProjectileRock> FACTORY_PROJECTILE_ROCK;
	public static EntityFactory<ProjectileArrowFlaming> FACTORY_PROJECTILE_ARROW_FLAMING;
	public static EntityFactory<ProjectileLightningball> FACTORY_PROJECTILE_LIGHTNINGBALL;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeEntities();
		}
	}

	public static void initializeEntities() {
		EntityDispatcher dispatcher = EntityDispatcher.getInstance();

		FACTORY_GARGOYLE = dispatcher.addMapping(
			MobGargoyle.class,
			NamespaceID.fromPool(MOD_ID, "gargoyle"),
			MobGargoyle::new,
			"guidebook.section.mob.gargoyle.name"
		);

		FACTORY_LEECHER = dispatcher.addMapping(
			MobLeecher.class,
			NamespaceID.fromPool(MOD_ID, "leecher"),
			MobLeecher::new,
			"guidebook.section.mob.leecher.name"
		);

		FACTORY_PROJECTILE_ROCK = dispatcher.addMapping(
			ProjectileRock.class,
			NamespaceID.fromPool(MOD_ID, "rock"),
			ProjectileRock::new
		);

		FACTORY_PROJECTILE_ARROW_FLAMING = dispatcher.addMapping(
			ProjectileArrowFlaming.class,
			NamespaceID.fromPool(MOD_ID, "arrow_flaming"),
			ProjectileArrowFlaming::new
		);

		FACTORY_PROJECTILE_LIGHTNINGBALL = dispatcher.addMapping(
			ProjectileLightningball.class,
			NamespaceID.fromPool(MOD_ID, "lightningball"),
			ProjectileLightningball::new
		);

	}
}
