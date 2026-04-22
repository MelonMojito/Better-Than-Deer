package betterthandeer.btd.mixin.accessor;

import net.minecraft.client.render.dynamictexture.DynamicTextureCustom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DynamicTextureCustom.class)
public interface DynamicTextureCustomAccessor {

	@Accessor("frameData")
	byte[][] getFrameData();

	@Accessor("currentFrame")
	int getCurrentFrame();

	@Accessor("previousFrame")
	int getPreviousFrame();

	@Mutable
	@Accessor("frameData")
	void setFrameData(byte[][] frameData);

	@Accessor("currentFrame")
	void setCurrentFrame(int currentFrame);

	@Accessor("previousFrame")
	void setPreviousFrame(int previousFrame);
}
