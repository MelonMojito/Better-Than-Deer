package betterthandeer.btd.mixin.world;

import betterthandeer.btd.block.conduit.ConduitHandler;
import betterthandeer.btd.block.conduit.ConduitWorldAccess;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.jetbrains.annotations.NotNull;

@Mixin(World.class)
public class WorldConduitMixin implements ConduitWorldAccess {

	@Unique
	private ConduitHandler conduitHandler;

	@Override
	public @NotNull ConduitHandler getConduitHandler() {
		if (this.conduitHandler == null) {
			World worldInstance = (World) (Object) this;
			this.conduitHandler = new ConduitHandler(worldInstance);
		}
		return this.conduitHandler;
	}
}
