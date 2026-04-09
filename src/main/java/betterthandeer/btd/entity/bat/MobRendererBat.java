package betterthandeer.btd.entity.bat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererBat extends MobRenderer<MobBat> {
	public MobRendererBat(float shadowSize) {
		super(shadowSize);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobBat entity, float brightness, float partialTick, int layer) {
		if (layer == 1) {
			this.bindTexture("/assets/btd/textures/entity/bat/eyes/" + entity.getTextureReference() + ".png");
			GLRenderer.setLightmapCoord2i(15, 15);
			GLRenderer.enableState(State.BLEND);
			GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
			GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, (1.0F - entity.getBrightness(partialTick)) * 0.5F);
		}

		StaticEntityModel model = this.getModel("main");
		model.resetBones();

		float limbSwing = this.getLimbSwing(entity, partialTick);
		float limbYaw = this.getLimbYaw(entity, partialTick);
		float limbPitch = this.getLimbPitch(entity, partialTick);
		float bodyYaw = this.getBodyYaw(entity, partialTick);
		float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
		float headPitch = this.getHeadPitch(entity, partialTick);

		float time = this.getLimbPitch(entity, partialTick);
		float flap = MathHelper.sin(time * 1.5F);

		BoneTransform head = model.getTransform("head");
		head.rotX = headPitch;
		head.rotY = headYaw;

		BoneTransform body = model.getTransform("body");
		body.rotX = 45.0F + flap / 8;

		BoneTransform wing0 = model.getTransform("wing0");
		BoneTransform wing1 = model.getTransform("wing1");
		wing0.rotY = flap;
		wing1.rotY = -flap;

		BoneTransform wing0_1 = model.getTransform("wing0_1");
		BoneTransform wing1_1 = model.getTransform("wing1_1");
		wing0_1.rotY = flap / 2;
		wing1_1.rotY = -flap / 2;

		return model;
	}


	@Override
	protected int maxRenderLayer(@NonNull MobBat entity) {
		return 1;
	}
}
