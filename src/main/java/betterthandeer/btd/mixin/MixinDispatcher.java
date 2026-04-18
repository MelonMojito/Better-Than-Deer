package betterthandeer.btd.mixin;

import net.minecraft.client.util.dispatch.Dispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Dispatcher.class)
public interface MixinDispatcher {

	@Accessor("dispatches")
	Map<Object, Object> getDispatches();
}
