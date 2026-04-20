package betterthandeer.btd.entity.arrow.flaming;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class EntityRendererArrowFlaming extends EntityRenderer<ProjectileArrowFlaming> {
	public EntityRendererArrowFlaming() {
	}

	public void render(@NotNull TessellatorGeneral tessellator, @NotNull ProjectileArrowFlaming arrow, double x, double y, double z, float yaw, float partialTick) {
		int prime = 900;
		if ((float) arrow.ticksInGround + partialTick > (float) prime) {
			int pastPrime = arrow.ticksInGround - prime;
			double toDeath = (double) 1.0F - (double) ((float) pastPrime + partialTick) * (double) 4.0F / (double) 1200.0F;
			if (Math.cos((double) ((float) pastPrime + partialTick) * Math.PI / ((double) 2.5F + (double) 5.5F * toDeath)) > (double) 0.0F) {
				return;
			}
		}

		this.bindTexture("/assets/btd/textures/entity/arrowflaming.png");
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
		GLRenderer.modelM4f().rotateY(org.joml.Math.toRadians(org.joml.Math.lerp(arrow.yRotO, arrow.yRot, partialTick) - 90.0F));
		GLRenderer.modelM4f().rotateZ(org.joml.Math.toRadians(org.joml.Math.lerp(arrow.xRotO, arrow.xRot, partialTick)));
		float bodyMinU = 0.0F;
		float bodyMaxU = 0.5F;
		float bodyMinV = (float) (0) / 32.0F;
		float bodyMaxV = (float) (5) / 32.0F;
		float tailMinU = 0.0F;
		float tailMaxU = 0.15625F;
		float tailMinV = (float) (5) / 32.0F;
		float tailMaxV = (float) (10) / 32.0F;
		float scale = 0.05625F;
		float shakeAmount = (float) arrow.shake - partialTick;
		if (shakeAmount > 0.0F) {
			float shakeAngle = -MathHelper.sin(shakeAmount * 3.0F) * shakeAmount;
			GLRenderer.modelM4f().rotateZ(org.joml.Math.toRadians(shakeAngle));
		}

		GLRenderer.modelM4f().rotateX(org.joml.Math.toRadians(45.0F));
		GLRenderer.modelM4f().scale(scale, scale, scale);
		GLRenderer.modelM4f().translate(-4.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0F, -2.0F, -2.0F, tailMinU, tailMinV);
		tessellator.addVertexWithUV(-7.0F, -2.0F, 2.0F, tailMaxU, tailMinV);
		tessellator.addVertexWithUV(-7.0F, 2.0F, 2.0F, tailMaxU, tailMaxV);
		tessellator.addVertexWithUV(-7.0F, 2.0F, -2.0F, tailMinU, tailMaxV);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0F, 2.0F, -2.0F, tailMinU, tailMinV);
		tessellator.addVertexWithUV(-7.0F, 2.0F, 2.0F, tailMaxU, tailMinV);
		tessellator.addVertexWithUV(-7.0F, -2.0F, 2.0F, tailMaxU, tailMaxV);
		tessellator.addVertexWithUV(-7.0F, -2.0F, -2.0F, tailMinU, tailMaxV);
		tessellator.draw();

		for (int i = 0; i < 4; ++i) {
			GLRenderer.modelM4f().rotateX(((float) Math.PI / 2F));
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8.0F, -2.0F, 0.0F, bodyMinU, bodyMinV);
			tessellator.addVertexWithUV(8.0F, -2.0F, 0.0F, bodyMaxU, bodyMinV);
			tessellator.addVertexWithUV(8.0F, 2.0F, 0.0F, bodyMaxU, bodyMaxV);
			tessellator.addVertexWithUV(-8.0F, 2.0F, 0.0F, bodyMinU, bodyMaxV);
			tessellator.draw();
		}

		GLRenderer.popFrame();
	}
}
