package betterthandeer.btd.entity.jellyfish;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererJellyfish extends MobRenderer<MobJellyfish> {
	public MobRendererJellyfish(float shadowSize) {
		super(shadowSize);
	}

	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobJellyfish entity, float brightness, float partialTick, int layer) {
		StaticEntityModel model = this.getModel("main");
		model.resetBones();
		float limbPitch = this.getLimbPitch(entity, partialTick);


		float openAnimationSpeed =  limbPitch;
		model.getTransform("tentacle1").rotX = openAnimationSpeed;
		model.getTransform("tentacle2").rotX = openAnimationSpeed;
		model.getTransform("tentacle3").rotX = openAnimationSpeed;
		model.getTransform("tentacle4").rotX = openAnimationSpeed;
		model.getTransform("tentacle5").rotX = openAnimationSpeed;
		model.getTransform("tentacle6").rotX = openAnimationSpeed;
		model.getTransform("tentacle7").rotX = openAnimationSpeed;
		model.getTransform("tentacle8").rotX = openAnimationSpeed;

		float spinAnimationSpeed = (entity.tickCount + partialTick) * 0.2F;
		model.getTransform("tentacle1").rotY = spinAnimationSpeed;
		model.getTransform("tentacle2").rotY = spinAnimationSpeed;
		model.getTransform("tentacle3").rotY = spinAnimationSpeed;
		model.getTransform("tentacle4").rotY = spinAnimationSpeed;
		model.getTransform("tentacle5").rotY = spinAnimationSpeed;
		model.getTransform("tentacle6").rotY = spinAnimationSpeed;
		model.getTransform("tentacle7").rotY = spinAnimationSpeed;
		model.getTransform("tentacle8").rotY = spinAnimationSpeed;

		return model;
	}

	@Override
	protected float getLimbPitch(@NonNull MobJellyfish jellyfish, float partialTick) {
		return MathHelper.lerp(jellyfish.oldTentacleAngle, jellyfish.tentacleAngle, partialTick);
	}

	@Override
	protected void preRenderTransform(@NonNull MobJellyfish entity, double x, double y, double z, float _yaw, float partialTick) {
		GLRenderer.modelM4f().translate((float) x, (float) (y + (double) 0.5F), (float) z);
		GLRenderer.modelM4f().scale(0.0625F, 0.0625F, 0.0625F);
		float pitch = entity.xBodyRotO + (entity.xBodyRot - entity.xBodyRotO) * partialTick;
		float yaw = entity.zBodyRotO + (entity.zBodyRot - entity.zBodyRotO) * partialTick;
		GLRenderer.modelM4f().rotateY((float) Math.PI - this.getBodyYaw(entity, partialTick));
		GLRenderer.modelM4f().rotateX(org.joml.Math.toRadians(pitch));
		GLRenderer.modelM4f().rotateY(org.joml.Math.toRadians(yaw));
	}

	@Override
	public void renderPreview(@NonNull TessellatorGeneral tessellator, @NonNull MobJellyfish jellyfish, double x, double y, double z, float yaw, float partialTick) {
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().translate(0.0F, 1.0F, 0.0F);
		GLRenderer.modelM4f().scale(0.75F, 0.75F, 0.75F);
		super.renderPreview(tessellator, jellyfish, x, y, z, yaw, partialTick);
		GLRenderer.popFrame();
	}

}
