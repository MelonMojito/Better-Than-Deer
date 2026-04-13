package betterthandeer.btd.mixin;

import betterthandeer.btd.ITagDuck;
import net.minecraft.core.data.tag.Tag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(value = Tag.class, remap = false)
public abstract class MixinTag<E> implements ITagDuck<E> {

	@Shadow
	@Final
	protected Set<E> elements;

	@Override
	public void btd$untag(E element) {
		this.elements.remove(element);
	}
}
