package betterthandeer.btd.mixin.world;

import betterthandeer.btd.block.BTDBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.camera.CameraUtil;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(FogManager.class)
public abstract class AcidFogMixin {

	@Shadow
	public float fogRed;
	@Shadow
	public float fogGreen;
	@Shadow
	public float fogBlue;

	@Shadow
	@Final
	@NonNull
	public Minecraft mc;

	@Inject(method = "updateFogColor", at = @At("TAIL"))
	private void updateFogColor(float partialTick, CallbackInfo ci) {
		World world = this.mc.currentWorld;

		if (CameraUtil.isUnderLiquid(this.mc.activeCamera, world, BTDBlocks.ACID, partialTick)) {
			this.fogRed = 0.2f;
			this.fogGreen = 0.2f;
			this.fogBlue = 0.1f;
		}
	}

	@Inject(method = "setupFog", at = @At("TAIL"))
	private void setupFog(int fogMode, float farPlaneDistance, float partialTick, FogManager.FogState dest, CallbackInfoReturnable<FogManager.FogState> cir) {
		World world = this.mc.currentWorld;
		TilePos cameraTile = this.mc.activeCamera.getTilePos();

		if (world.getBlockBiome(cameraTile) == Biomes.NETHER_OLD_WORLD) {
			dest.fogStart = farPlaneDistance * 0.15F;
			dest.fogEnd   = farPlaneDistance * 0.45F;
		}

		if (CameraUtil.isUnderLiquid(this.mc.activeCamera, world, BTDBlocks.ACID, partialTick)) {
			dest.fogMode = FogManager.FogState.Mode.EXP;
			dest.fogDensity = 1.2F;
			dest.fogStart = 0.0f;
			dest.fogEnd = 0.1f;
		}
	}
}
