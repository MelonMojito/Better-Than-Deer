package betterthandeer.btd.block.acid;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelFluid;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.BlockLogicFluidFlowing;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class BlockModelFluidAcid<T extends BlockLogicFluid> extends BlockModelFluid<T> {
	public final Random random = new Random();
	public final IconCoordinate flowing;
	public final IconCoordinate still;
	private final @NonNull Vector3d br = new Vector3d();
	private final @NonNull Vector3d bl = new Vector3d();
	private final @NonNull Vector3d tr = new Vector3d();
	private final @NonNull Vector3d a = new Vector3d();
	private final @NonNull Vector3d b = new Vector3d();
	private final @NonNull Vector3d norm = new Vector3d();

	public BlockModelFluidAcid(Block<T> block) {
		super(block, "btd:block/acid_still", "btd:block/acid_flowing");
		this.still = TextureRegistry.getTexture("btd:block/acid_still");
		this.flowing = TextureRegistry.getTexture("btd:block/acid_flowing");
		if (block.getLogic() instanceof BlockLogicFluidFlowing) {
			this.setAllTextures(this.flowing);
			this.setTex(this.still, Side.TOP);
		} else {
			this.setTex(this.still, Side.TOP, Side.BOTTOM);
			this.setTex(this.flowing, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST);
		}

	}

	@Override
	public boolean render(@NonNull TessellatorGeneral tessellator, @NonNull WorldSource worldSource, @NonNull TilePosc tilePos) {
		int color = BlockColorDispatcher.getInstance().getDispatch(this.block).getWorldColor(worldSource, tilePos, 0);
		tessellator.setColor1i(color);
		TilePos queryPos = new TilePos();
		int x = tilePos.x();
		int y = tilePos.y();
		int z = tilePos.z();
		AABBdc bounds = this.block.getBoundsFromState(worldSource, tilePos);
		boolean renderTop = this.shouldSideBeRendered(worldSource, bounds, tilePos.up(queryPos), Side.TOP);
		boolean flag1 = this.shouldSideBeRendered(worldSource, bounds, tilePos.down(queryPos), Side.BOTTOM);
		boolean[] sideRenderFlags = new boolean[]{this.shouldSideBeRendered(worldSource, bounds, tilePos.north(queryPos), Side.NORTH), this.shouldSideBeRendered(worldSource, bounds, tilePos.south(queryPos), Side.SOUTH), this.shouldSideBeRendered(worldSource, bounds, tilePos.west(queryPos), Side.WEST), this.shouldSideBeRendered(worldSource, bounds, tilePos.east(queryPos), Side.EAST)};
		if (!renderTop && !flag1 && !sideRenderFlags[0] && !sideRenderFlags[1] && !sideRenderFlags[2] && !sideRenderFlags[3]) {
			return false;
		} else {
			boolean didRender = false;
			float yOff = 0.001F;
			int meta = worldSource.getBlockData(tilePos);
			float h = this.block.getLogic().getFluidHeight(worldSource, tilePos);
			float hs = this.block.getLogic().getFluidHeight(worldSource, tilePos.add(0, 0, 1, queryPos));
			float hes = this.block.getLogic().getFluidHeight(worldSource, tilePos.add(1, 0, 1, queryPos));
			float he = this.block.getLogic().getFluidHeight(worldSource, tilePos.add(1, 0, 0, queryPos));
			if (renderBlocks.renderAllFaces || renderTop) {
				didRender = true;
				IconCoordinate tex = this.getBlockTextureFromSideAndMetadata(Side.TOP, meta);
				float rotation = (float) this.block.getLogic().getSlopeAngle(worldSource, tilePos);
				if (rotation > -999.0F) {
					tex = this.getBlockTextureFromSideAndMetadata(Side.NORTH, meta);
				} else {
					rotation = 0.0F;
				}

				byte light = this.block.getLightIndex(worldSource, tilePos);
				byte lightUp = this.block.getLightIndex(worldSource, tilePos.up(queryPos));
				tessellator.setLightmapCoord1i(LightIndexHelper.max(light, lightUp));
				if (tex == this.flowing) {
					float cos = org.joml.Math.cos(-rotation + ((float) java.lang.Math.PI / 2F));
					float sin = Math.sin(-rotation + ((float) java.lang.Math.PI / 2F));
					this.br.set(x, (float) y + h, z);
					this.bl.set(x, (float) y + hs, z + 1);
					this.tr.set(x + 1, (float) y + hes, z + 1);
					this.br.sub(this.bl, this.a);
					this.tr.sub(this.bl, this.b);
					this.b.cross(this.a, this.norm).normalize();
					tessellator.setNormal(this.norm);
					tessellator.addVertexWithUV(x, (float) y + h, z, tex.getSubIconU((double) -0.25F * (double) cos - (double) -0.25F * (double) sin + (double) 0.5F), tex.getSubIconV((double) -0.25F * (double) sin + (double) -0.25F * (double) cos + (double) 0.5F));
					tessellator.addVertexWithUV(x, (float) y + hs, z + 1, tex.getSubIconU((double) -0.25F * (double) cos - (double) 0.25F * (double) sin + (double) 0.5F), tex.getSubIconV((double) -0.25F * (double) sin + (double) 0.25F * (double) cos + (double) 0.5F));
					tessellator.addVertexWithUV(x + 1, (float) y + hes, z + 1, tex.getSubIconU((double) 0.25F * (double) cos - (double) 0.25F * (double) sin + (double) 0.5F), tex.getSubIconV((double) 0.25F * (double) sin + (double) 0.25F * (double) cos + (double) 0.5F));
					tessellator.addVertexWithUV(x + 1, (float) y + he, z, tex.getSubIconU((double) 0.25F * (double) cos - (double) -0.25F * (double) sin + (double) 0.5F), tex.getSubIconV((double) 0.25F * (double) sin + (double) -0.25F * (double) cos + (double) 0.5F));
				} else {
					tessellator.setNormal(0.0F, 1.0F, 0.0F);
					tessellator.addVertexWithUV(x, (float) y + h, z, tex.getIconUMin(), tex.getIconVMax());
					tessellator.addVertexWithUV(x, (float) y + hs, z + 1, tex.getIconUMax(), tex.getIconVMax());
					tessellator.addVertexWithUV(x + 1, (float) y + hes, z + 1, tex.getIconUMax(), tex.getIconVMin());
					tessellator.addVertexWithUV(x + 1, (float) y + he, z, tex.getIconUMin(), tex.getIconVMin());

					this.random.setSeed(this.getPositionalSeed(tilePos));

					tessellator.setColor1i(color);
				}
			}

			if (renderBlocks.renderAllFaces || flag1) {
				tessellator.setLightmapCoord1i(this.block.getLightIndex(worldSource, tilePos.down(queryPos)));
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderBlocks.renderBottomFace(tessellator, bounds, x, (float) y + yOff, z, this.getBlockTextureFromSideAndMetadata(Side.TOP, 0));
				didRender = true;
			}

			TilePos posB = new TilePos(0, 0, 0);

			for (int side = 0; side < 4; ++side) {
				posB.set(x, y, z);
				switch (side) {
					case 1 -> posB.set(x, y, z + 1);
					case 2 -> posB.set(x - 1, y, z);
					case 3 -> posB.set(x + 1, y, z);
					default -> posB.set(x, y, z - 1);
				}

				IconCoordinate texture = this.getBlockTextureFromSideAndMetadata(Side.getSideById(side + 2), meta);
				if (renderBlocks.renderAllFaces || sideRenderFlags[side]) {
					float z1;
					float x2;
					float z2;
					float f13;
					float f15;
					float x1;
					switch (side) {
						case 0:
							f13 = h;
							f15 = he;
							x1 = (float) x + 1.0E-4F;
							x2 = (float) (x + 1) - 1.0E-4F;
							z1 = (float) z + 1.0E-4F;
							z2 = (float) z + 1.0E-4F;
							break;
						case 1:
							f13 = hes;
							f15 = hs;
							x1 = (float) (x + 1) - 1.0E-4F;
							x2 = (float) x + 1.0E-4F;
							z1 = (float) (z + 1) - 1.0E-4F;
							z2 = (float) (z + 1) - yOff;
							break;
						case 2:
							f13 = hs;
							f15 = h;
							x1 = (float) x + 1.0E-4F;
							x2 = (float) x + 1.0E-4F;
							z1 = (float) (z + 1) - 1.0E-4F;
							z2 = (float) z + 1.0E-4F;
							break;
						default:
							f13 = he;
							f15 = hes;
							x1 = (float) (x + 1) - 1.0E-4F;
							x2 = (float) (x + 1) - 1.0E-4F;
							z1 = (float) z + 1.0E-4F;
							z2 = (float) (z + 1) - 1.0E-4F;
					}

					didRender = true;
					double u1 = texture.getSubIconU(0.0F);
					double u2 = texture.getSubIconU(0.5F);
					double v1 = texture.getSubIconV(0.5F - f13 / 2.0F);
					double v2 = texture.getSubIconV(0.5F - f15 / 2.0F);
					double v3 = texture.getSubIconV(0.5F);
					tessellator.setLightmapCoord1i(this.block.getLightIndex(worldSource, posB));
					switch (side) {
						case 1 -> tessellator.setNormal(0.0F, 0.0F, 1.0F);
						case 2 -> tessellator.setNormal(-1.0F, 0.0F, 0.0F);
						case 3 -> tessellator.setNormal(1.0F, 0.0F, 0.0F);
						default -> tessellator.setNormal(0.0F, 0.0F, -1.0F);
					}

					tessellator.addVertexWithUV(x1, (float) y + f13, z1, u1, v1);
					tessellator.addVertexWithUV(x2, (float) y + f15, z2, u2, v2);
					tessellator.addVertexWithUV(x2, y, z2, u2, v3);
					tessellator.addVertexWithUV(x1, y, z1, u1, v3);
				}
			}

			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			return didRender;
		}
	}

}
