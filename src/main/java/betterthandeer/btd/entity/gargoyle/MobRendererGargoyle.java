package betterthandeer.btd.entity.gargoyle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererGargoyle extends MobRenderer<MobGargoyle> {
	public MobRendererGargoyle(float shadowSize) {
		super(shadowSize);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobGargoyle entity, float brightness, float partialTick, int layer) {
		if (layer == 1 && !entity.isHanging) {
			this.bindTexture("/assets/btd/textures/entity/gargoyle/eyes/" + entity.getTextureReference() + ".png");
			GLRenderer.setLightmapCoord2i(15, 15);
			GLRenderer.enableState(State.BLEND);
			GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
			GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		StaticEntityModel model = this.getModel("main");
		model.resetBones();

		BoneTransform head = model.getTransform("head");

		BoneTransform body = model.getTransform("body");

		BoneTransform wingLeft = model.getTransform("wingLeft");
		BoneTransform wingRight = model.getTransform("wingRight");

		BoneTransform wingOuterLeft = model.getTransform("wingOuterLeft");
		BoneTransform wingOuterRight = model.getTransform("wingOuterRight");

		BoneTransform legLeft = model.getTransform("legLeft");
		BoneTransform legRight = model.getTransform("legRight");

		BoneTransform armLeft = model.getTransform("armLeft");
		BoneTransform armRight = model.getTransform("armRight");

		float limbSwing = this.getLimbSwing(entity, partialTick);
		float limbYaw = this.getLimbYaw(entity, partialTick);
		float limbPitch = this.getLimbPitch(entity, partialTick);
		float bodyYaw = this.getBodyYaw(entity, partialTick);
		float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
		float headPitch = this.getHeadPitch(entity, partialTick);

		float time = this.getLimbPitch(entity, partialTick);
		float flap = MathHelper.sin(time * 1.5F);

		if (entity.isHanging) {

			body.rotX = 3.14;

			head.rotX = -3.14;

			wingLeft.rotY = 1F;
			wingRight.rotY = -1F;

			wingOuterLeft.rotY = 1.5F;
			wingOuterRight.rotY = -1.5F;

			armLeft.rotX = -0.5;
			armRight.rotX = -0.5;

			armLeft.rotZ = 0.3;
			armRight.rotZ = -0.3;
		} else {
			head.rotX = headPitch;
			head.rotY = headYaw;
			body.rotX = 45.0F + flap / 8;

			wingLeft.rotY = flap;
			wingRight.rotY = -flap;

			wingOuterLeft.rotY = flap / 2;
			wingOuterRight.rotY = -flap / 2;

			legLeft.rotX = -1;
			legRight.rotX = -1;

			armLeft.rotX = -1;
			armRight.rotX = -1;
		}

		return model;
	}

	@Override
	public void renderPreview(@NotNull TessellatorGeneral tessellator, @NotNull MobGargoyle gargoyle, double x, double y, double z, float yaw, float partialTick) {
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().translate(0.0F, 0.5F, 0.0F);
		gargoyle.isHanging = false;
		super.renderPreview(tessellator, gargoyle, x, y, z, yaw, partialTick);
		GLRenderer.popFrame();
	}


	@Override
	protected int maxRenderLayer(@NonNull MobGargoyle entity) {
		return 1;
	}
}
