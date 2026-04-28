package betterthandeer.btd.block;

import betterthandeer.btd.BetterThanDeerMod;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;

import java.lang.reflect.Field;

public abstract class BTDBlockTags {

	public static final Tag<Block<?>> IS_ACID = Tag.of("is_acid");

	static {
		for (Field field : BTDBlockTags.class.getDeclaredFields()) {
			if (field.getType().equals(Tag.class)) {
				try {
					@SuppressWarnings("unchecked")
					Tag<Block<?>> tag = (Tag<Block<?>>) field.get(null);
					BlockTags.TAG_LIST.add(tag);
				} catch (Exception e) {
					BetterThanDeerMod.LOGGER.error("Failed to add tag '{}'!", field.getName(), e);
				}
			}
		}
	}

}
