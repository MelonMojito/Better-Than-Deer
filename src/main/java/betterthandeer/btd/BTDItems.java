package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.Items;

public class BTDItems {

	public static Item BUCKET_ACID;

	public static Item EYE_GARGOYLE;

	private static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeItems();
		}
	}

	public static void initializeItems() {
		BUCKET_ACID = new ItemBucket("bucket.acid", "btd:item/bucket_acid", 20000, BTDBlocks.FLUID_ACID_FLOWING).setContainerItem(Items.BUCKET);

		EYE_GARGOYLE = new Item("eye.gargoyle", "btd:item/eye_gargoyle", 20001);
	}
}
