package betterthandeer.btd.block;

import betterthandeer.btd.block.statue.gargoyle.TileEntityStatueGargoyle;
import net.minecraft.core.util.collection.NamespaceID;

import static net.minecraft.core.block.entity.TileEntityDispatcher.addMapping;

public class BTDTileEntities {
	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeTileEntities();
		}
	}

	public static void initializeTileEntities() {
		addMapping(TileEntityStatueGargoyle.class, NamespaceID.fromPool("btd", "statue_gargoyle"));
	}
}
