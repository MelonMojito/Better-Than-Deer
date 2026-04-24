package betterthandeer.btd.block.statue.gargoyle;

import betterthandeer.btd.block.BTDBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import org.joml.Math;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class TileEntityRendererStatueGargoyle extends TileEntityRenderer<TileEntityStatueGargoyle> {
	public static final @NonNull Map<@NonNull Block<?>, @NonNull String> BLOCK_SKIN_MAP = new HashMap<>();

	public TileEntityRendererStatueGargoyle() {
	}

	private @NonNull StaticEntityModel model() {
		return this.getModel("main");
	}

	public void renderAt(@NonNull TessellatorGeneral tessellator, int meta, @NonNull Block<?> lowerBlock, TileEntityStatueGargoyle.@NonNull Pose pose, double x, double y, double z) {
		StaticEntityModel model = this.model();
		GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindTexture(BLOCK_SKIN_MAP.getOrDefault(lowerBlock, (String) BLOCK_SKIN_MAP.get(BTDBlocks.STATUE_GARGOYLE_NETHERRACK)));
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().translate((float) (x + (double) 0.5F), (float) y, (float) (z + (double) 0.5F));
		float rotation = (float) (meta * 360) / 16.0F;
		GLRenderer.modelM4f().rotateY(Math.toRadians(-rotation));
		GLRenderer.disableState(State.CULL_FACE);
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().scale(0.0625F, 0.0625F, -0.0625F);
		model.resetBones();
		this.renderModel(tessellator, model, pose);

		GLRenderer.popFrame();
		GLRenderer.enableState(State.CULL_FACE);
		GLRenderer.popFrame();
	}

	private void renderModel(@NonNull TessellatorGeneral tessellator, @NonNull StaticEntityModel model, TileEntityStatueGargoyle.@NonNull Pose pose) {
		model.getTransform("armLeft").rotX = pose.leftArmPitch;
		model.getTransform("armRight").rotX = pose.rightArmPitch;
		model.getTransform("legLeft").rotX = pose.leftLegPitch;
		model.getTransform("legRight").rotX = pose.rightLegPitch;

		model.getTransform("head").rotX = pose.headPitch;
		model.getTransform("head").rotY = pose.headYaw;
		model.getTransform("head").rotZ = pose.headRoll;

		model.getTransform("body").rotX = pose.bodyPitch;
		model.getTransform("body").rotY = pose.bodyYaw;
		model.getTransform("body").rotZ = pose.bodyRoll;

		model.getTransform("head").posY = pose.headPos;
		model.getTransform("body").posY = pose.bodyPos;
		model.render();
	}

	public void doRender(@NonNull TessellatorGeneral tessellator, @NonNull TileEntityStatueGargoyle tileEntity, double x, double y, double z, float partialTick) {
		this.renderAt(tessellator, tileEntity.getBlockMeta(), tileEntity.getBlock(), tileEntity.getPose(), x, y, z);
		this.renderShadow(Minecraft.getMinecraft().currentWorld, tessellator, tileEntity.tilePos.x, tileEntity.tilePos.y, tileEntity.tilePos.z, x, y, z, 2.0F, partialTick);
	}

	private void renderShadow(@NonNull World world, @NonNull TessellatorGeneral tessellator, int bx, int by, int bz, double rx, double ry, double rz, float opacity, float partialTick) {
		GLRenderer.enableState(State.BLEND);
		GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		TextureManager textureManager = this.renderDispatcher.textureManager;
		textureManager.bindTexture(textureManager.loadTexture("/assets/minecraft/textures/misc/shadow.png"));
		float shadowSize = 0.5F;
		float shadowOffs = 0.4F;
		GLRenderer.setDepthMask(false);
		tessellator.startDrawingQuads();
		int blockId = world.getBlockId(bx, by - 1, bz);
		if (blockId > 0 && world.getBlockLightValue(bx, by, bz) > 3) {
			this.renderShadowOnBlock(world, tessellator, Blocks.blocksList[blockId], bx, by, bz, rx, ry + (double) shadowOffs, rz, opacity, shadowSize);
		}

		tessellator.draw();
		GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GLRenderer.disableState(State.BLEND);
		GLRenderer.setDepthMask(true);
	}

	private void renderShadowOnBlock(@NonNull World world, @NonNull TessellatorGeneral tessellator, @NonNull Block<?> block, int bx, int by, int bz, double rx, double ry, double rz, float opacity, float size) {
		if (block.isCubeShaped()) {
			double brightness = world.getLightBrightness(bx, by, bz);
			if (Global.accessor.isFullbrightEnabled()) {
				brightness = 1.0F;
			}

			double alpha = (double) opacity / (double) 2.0F * (double) 0.5F * brightness;
			if (!(alpha < (double) 0.0F)) {
				if (alpha > (double) 1.0F) {
					alpha = 1.0F;
				}

				tessellator.setColor4f(1.0F, 1.0F, 1.0F, (float) alpha);
				AABBdc aabb = block.getCollisionBoundingBoxFromPool(world, bx, by - 1, bz);
				if (aabb != null) {
					double xMin = rx - (double) 0.5F;
					double xMax = rx + (aabb.maxX() - aabb.minX()) - (double) 0.5F;
					double yMin = ry + (aabb.maxY() - aabb.minY()) - 1.39;
					double zMin = rz - (double) 0.5F;
					double zMax = rz + (aabb.maxZ() - aabb.minZ()) - (double) 0.5F;
					float f2 = (float) ((rx - xMin) / (double) 2.0F / (double) size + (double) 0.5F);
					float f3 = (float) ((rx - xMax) / (double) 2.0F / (double) size + (double) 0.5F);
					float f4 = (float) ((rz - zMin) / (double) 2.0F / (double) size + (double) 0.5F);
					float f5 = (float) ((rz - zMax) / (double) 2.0F / (double) size + (double) 0.5F);
					tessellator.addVertexWithUV(xMin + (double) 0.5F, yMin, zMin + (double) 0.5F, f2, f4);
					tessellator.addVertexWithUV(xMin + (double) 0.5F, yMin, zMax + (double) 0.5F, f2, f5);
					tessellator.addVertexWithUV(xMax + (double) 0.5F, yMin, zMax + (double) 0.5F, f3, f5);
					tessellator.addVertexWithUV(xMax + (double) 0.5F, yMin, zMin + (double) 0.5F, f3, f4);
				}
			}
		}
	}

	static {
		BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_GARGOYLE_NETHERRACK, "/assets/btd/textures/entity/statue_gargoyle/netherrack.png");
		BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_GARGOYLE_GLOOMSTONE, "/assets/btd/textures/entity/statue_gargoyle/gloomstone.png");
		BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_GARGOYLE_BASALT, "/assets/btd/textures/entity/statue_gargoyle/basalt.png");
		BLOCK_SKIN_MAP.put(BTDBlocks.STATUE_GARGOYLE_SLATE, "/assets/btd/textures/entity/statue_gargoyle/slate.png");
	}
}
