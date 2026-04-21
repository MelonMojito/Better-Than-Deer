package betterthandeer.btd.mixin.block;

import betterthandeer.btd.block.DynamicTextureAcidFlowing;
import betterthandeer.btd.block.DynamicTextureAcidStill;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public abstract class AddDynamicTexturesMixin {

	@Shadow
	protected abstract void addDynamicTexture(DynamicTexture texture);

	@Inject(method = "addNativeDynamicTextures", at = @At("TAIL"))
	private void addAcidDynamicTextures(CallbackInfo ci) {
		this.addDynamicTexture(new DynamicTextureAcidStill(TextureRegistry.getTexture("btd:block/acid_still")));
		this.addDynamicTexture(new DynamicTextureAcidFlowing(TextureRegistry.getTexture("btd:block/acid_flowing")));
	}
}
