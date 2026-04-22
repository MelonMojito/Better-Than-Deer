package betterthandeer.btd.block.conduit;

import betterthandeer.btd.block.BTDBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.Shaders;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import org.joml.Vector3dc;

public class TileEntityRendererConduitRubyglass extends TileEntityRenderer<TileEntityConduitRubyglass> {

	private static final long MAGIC_PRIME_X = 3129871L;
	private static final long MAGIC_PRIME_Y = 116129781L;
	private long fastRandomSeed;

	@Override
	public void doRender(TessellatorGeneral tessellator, TileEntityConduitRubyglass conduitTileEntity, double x, double y, double z, float partialTick) {
		Player player = Minecraft.getMinecraft().thePlayer;

		if (!shouldRender(conduitTileEntity, player, partialTick))
			return;

		Side[] openSides = conduitTileEntity.openSides;
		if (openSides == null || openSides.length == 0)
			return;

		float powerRatio = conduitTileEntity.powerRatio;
		int strength = conduitTileEntity.strength;

		// pre-calculate colors and thicknesses so it's not happening every frame
		final float baseRed = 1.0f;
		final float baseBlue = 0.04f + (0.2f * powerRatio);
		final float baseGreen = 0.3f * powerRatio;

		final float innerGreen = Math.min(1.0f, baseGreen + 0.25f);
		final float innerBlue = Math.min(1.0f, baseBlue + 0.25f);
		final float coreGreen = Math.min(1.0f, baseGreen + 0.55f);
		final float coreBlue = Math.min(1.0f, baseBlue + 0.55f);

		final float weakCoreGreen = coreGreen * 0.5f;
		final float weakCoreBlue = coreBlue * 0.5f;

		final double baseThickness = 0.02 + (0.05 * (powerRatio * powerRatio));
		final double outerThickness = baseThickness * 3.0;
		final double innerThickness = baseThickness * 1.5;
		final double coreThickness = baseThickness * 0.4;

		//for corner joint connections
		double coreDirectionX = 0;
		double coreDirectionY = 0;
		double coreDirectionZ = 0;
		for (Side side : openSides) {
			coreDirectionX += side.getOffsetX();
			coreDirectionY += side.getOffsetY();
			coreDirectionZ += side.getOffsetZ();
		}

		setupGL(x, y, z);

		if (conduitTileEntity.worldObj == null) return;


		long baseDivisor = 90L;
		long slowdownFactor = 4L;
		long dynamicDivisor = baseDivisor + ((10 - strength) * slowdownFactor);
		long timePulsar = System.currentTimeMillis() / dynamicDivisor;

		long rawSeed = timePulsar
			^ (long) conduitTileEntity.tilePos.x * MAGIC_PRIME_X
			^ (long) conduitTileEntity.tilePos.y * MAGIC_PRIME_Y
			^ (long) conduitTileEntity.tilePos.z * 31298717L;

		//uses SplitMix64
		rawSeed = (rawSeed ^ (rawSeed >>> 30)) * 0xbf58476d1ce4e5b9L;
		rawSeed = (rawSeed ^ (rawSeed >>> 27)) * 0x94d049bb133111ebL;
		this.fastRandomSeed = rawSeed ^ (rawSeed >>> 31);

		// calculate relative camera position to the block
		double cameraX = player.x - conduitTileEntity.tilePos.x;
		double cameraY = (player.y + player.getHeadHeight()) - conduitTileEntity.tilePos.y;
		double cameraZ = player.z - conduitTileEntity.tilePos.z;

		tessellator.startDrawingQuads();
		for (Side side : openSides) {
			renderBeamSide(
				tessellator, side, cameraX, cameraY, cameraZ, strength,
				baseRed, baseBlue, baseGreen, innerGreen, innerBlue,
				coreGreen, coreBlue, weakCoreGreen, weakCoreBlue,
				outerThickness, innerThickness, coreThickness,
				coreDirectionX, coreDirectionY, coreDirectionZ
			);
		}
		tessellator.draw();

		releaseGL();
	}

	private double nextJitterFormula(int strength) {
		fastRandomSeed = (fastRandomSeed * 25214903917L + 11L) & ((1L << 48) - 1);
		double baseJitter = (((double) fastRandomSeed / (1L << 48)) - 0.5) * 0.22;

		//calmer when off/weak
		return strength == 0 ? baseJitter * 0.25 : baseJitter;
	}

	private boolean shouldRender(TileEntityConduitRubyglass conduitTileEntity, Player player, float partialTick) {
		if (player == null) return false;

		ItemStack boots = player.inventory.armorItemInSlot(HumanArmorShape.BOOTS);
		if (boots == null || !boots.getItem().equals(BTDBlocks.ICE_RUBYGLASS.asItem()))
			return false;

		return !shouldCull(conduitTileEntity, player, partialTick);
	}

	private void setupGL(double x, double y, double z) {
		GLRenderer.pushFrame();
		GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
		GLRenderer.globalSetLightEnabled(false);
		GLRenderer.setShader(Shaders.COLOR);
		GLRenderer.enableState(State.BLEND);
		GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
		GLRenderer.setDepthMask(false);
	}

	private void releaseGL() {
		GLRenderer.setDepthMask(true);
		GLRenderer.disableState(State.BLEND);
		GLRenderer.globalSetLightEnabled(true);
		GLRenderer.popFrame();
	}

	private void renderBeamSide(
		TessellatorGeneral tessellator,
		Side side, double cameraX, double cameraY, double cameraZ,
		int strength, float baseRed, float baseBlue, float baseGreen,
		float innerGreen, float innerBlue, float coreGreen, float coreBlue,
		float weakCoreGreen, float weakCoreBlue,
		double outerThickness, double innerThickness, double coreThickness,
		double coreDirectionX, double coreDirectionY, double coreDirectionZ) {

		double startX = 0.5, startY = 0.5, startZ = 0.5;
		double endX = 0.5 + (side.getOffsetX() * 0.5);
		double endY = 0.5 + (side.getOffsetY() * 0.5);
		double endZ = 0.5 + (side.getOffsetZ() * 0.5);

		int segments = 2;
		int totalPoints = segments + 1;

		double[] pointX = new double[totalPoints];
		double[] pointY = new double[totalPoints];
		double[] pointZ = new double[totalPoints];

		boolean jitterX = side.getOffsetX() == 0;
		boolean jitterY = side.getOffsetY() == 0;
		boolean jitterZ = side.getOffsetZ() == 0;

		// calculate coordinate points of the beam
		pointX[0] = startX;
		pointY[0] = startY;
		pointZ[0] = startZ;

		for (int i = 1; i < segments; i++) {
			double progress = i / (double) segments;
			pointX[i] = startX + (endX - startX) * progress;
			pointY[i] = startY + (endY - startY) * progress;
			pointZ[i] = startZ + (endZ - startZ) * progress;

			if (jitterX) pointX[i] += nextJitterFormula(strength);
			if (jitterY) pointY[i] += nextJitterFormula(strength);
			if (jitterZ) pointZ[i] += nextJitterFormula(strength);
		}

		pointX[segments] = endX;
		pointY[segments] = endY;
		pointZ[segments] = endZ;

		double[] rightVectorX = new double[totalPoints];
		double[] rightVectorY = new double[totalPoints];
		double[] rightVectorZ = new double[totalPoints];

		for (int i = 0; i < totalPoints; i++) {
			double directionX, directionY, directionZ;

			//joint connection
			if (i == 0) {
				//corners
				if (coreDirectionX == 0 && coreDirectionY == 0 && coreDirectionZ == 0) {
					directionX = side.getOffsetX();
					directionY = side.getOffsetY();
					directionZ = side.getOffsetZ();
				} else {
					directionX = coreDirectionX;
					directionY = coreDirectionY;
					directionZ = coreDirectionZ;
				}
			} else if (i == totalPoints - 1) {
				//edge of each block
				directionX = side.getOffsetX();
				directionY = side.getOffsetY();
				directionZ = side.getOffsetZ();
			} else {
				//between segments
				directionX = pointX[i + 1] - pointX[i - 1];
				directionY = pointY[i + 1] - pointY[i - 1];
				directionZ = pointZ[i + 1] - pointZ[i - 1];
			}

			double directionLength = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
			if (directionLength != 0) {
				directionX /= directionLength;
				directionY /= directionLength;
				directionZ /= directionLength;
			}

			double toCameraX = cameraX - pointX[i];
			double toCameraY = cameraY - pointY[i];
			double toCameraZ = cameraZ - pointZ[i];

			rightVectorX[i] = directionY * toCameraZ - directionZ * toCameraY;
			rightVectorY[i] = directionZ * toCameraX - directionX * toCameraZ;
			rightVectorZ[i] = directionX * toCameraY - directionY * toCameraX;

			double rightVectorLength = Math.sqrt(rightVectorX[i] * rightVectorX[i] + rightVectorY[i] * rightVectorY[i] + rightVectorZ[i] * rightVectorZ[i]);
			if (rightVectorLength != 0) {
				rightVectorX[i] /= rightVectorLength;
				rightVectorY[i] /= rightVectorLength;
				rightVectorZ[i] /= rightVectorLength;
			} else {
				rightVectorX[i] = 1;
				rightVectorY[i] = 0;
				rightVectorZ[i] = 0;
			}
		}

		//draw as continuous "ribbon"
		if (strength != 0) {
			tessellator.setColor4f(baseRed, baseGreen, baseBlue, 0.15f);
			drawRibbonLayer(tessellator, pointX, pointY, pointZ, rightVectorX, rightVectorY, rightVectorZ, outerThickness);
		}

		tessellator.setColor4f(baseRed, innerGreen, innerBlue, 0.4f);
		drawRibbonLayer(tessellator, pointX, pointY, pointZ, rightVectorX, rightVectorY, rightVectorZ, innerThickness);

		if (strength != 0) {
			tessellator.setColor4f(baseRed, coreGreen, coreBlue, 1.0f);
		} else {
			tessellator.setColor4f(1.0f, weakCoreGreen, weakCoreBlue, 1.0f);
		}
		drawRibbonLayer(tessellator, pointX, pointY, pointZ, rightVectorX, rightVectorY, rightVectorZ, coreThickness);
	}

	private void drawRibbonLayer(
		TessellatorGeneral tessellator,
		double[] pointX, double[] pointY, double[] pointZ,
		double[] rightVectorX, double[] rightVectorY, double[] rightVectorZ,
		double thickness) {

		for (int i = 0; i < pointX.length - 1; i++) {
			int nextIndex = i + 1;

			double currentPointX = pointX[i];
			double currentPointY = pointY[i];
			double currentPointZ = pointZ[i];

			double currentRightOffsetX = rightVectorX[i] * thickness;
			double currentRightOffsetY = rightVectorY[i] * thickness;
			double currentRightOffsetZ = rightVectorZ[i] * thickness;

			double nextPointX = pointX[nextIndex];
			double nextPointY = pointY[nextIndex];
			double nextPointZ = pointZ[nextIndex];

			double nextRightOffsetX = rightVectorX[nextIndex] * thickness;
			double nextRightOffsetY = rightVectorY[nextIndex] * thickness;
			double nextRightOffsetZ = rightVectorZ[nextIndex] * thickness;

			tessellator.addVertex(currentPointX + currentRightOffsetX, currentPointY + currentRightOffsetY, currentPointZ + currentRightOffsetZ);
			tessellator.addVertex(nextPointX + nextRightOffsetX, nextPointY + nextRightOffsetY, nextPointZ + nextRightOffsetZ);
			tessellator.addVertex(nextPointX - nextRightOffsetX, nextPointY - nextRightOffsetY, nextPointZ - nextRightOffsetZ);
			tessellator.addVertex(currentPointX - currentRightOffsetX, currentPointY - currentRightOffsetY, currentPointZ - currentRightOffsetZ);
		}
	}

	private boolean shouldCull(TileEntityConduitRubyglass conduitTileEntity, Player player, float partialTick) {
		double deltaX = (conduitTileEntity.tilePos.x + 0.5) - player.x;
		double deltaY = (conduitTileEntity.tilePos.y + 0.5) - (player.y + player.getHeadHeight());
		double deltaZ = (conduitTileEntity.tilePos.z + 0.5) - player.z;

		double distanceSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
		if (distanceSquared > 250) return true;

		Vector3dc lookVector = player.getViewVector(partialTick);
		if (lookVector == null) return true;

		double dotProduct = (deltaX * lookVector.x() + deltaY * lookVector.y() + deltaZ * lookVector.z());
		return dotProduct < 0;
	}
}
