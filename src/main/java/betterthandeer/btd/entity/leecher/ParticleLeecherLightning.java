package betterthandeer.btd.entity.leecher;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.renderer.*;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class ParticleLeecherLightning extends Particle {
	private static final Random rand = new Random();
	private long seed = 0L;
	public float pitch;
	public float yaw;

	public ParticleLeecherLightning(@NonNull World world, double x, double y, double z, float pitch, float yaw) {
		super(world, x, y, z, 0.0F, 0.0F, 0.0F);
		this.lifetime = 2;
		this.rCol = 0.5F;
		this.gCol = 0.05F;
		this.bCol = 0.15F;
		this.size = 0.125F;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	@Override
	public void tick() {
		this.cachedLightmapCoord = this.calcLightIndex(1.0F);
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		}

		this.seed = org.joml.Random.newSeed();
	}

	@Override
	public void renderLit(float partialTick, double camOffX, double camOffY, double camOffZ) {
		TessellatorGeneral t = GLRenderer.getTessellator();
		GLRenderer.pushFrame();
		GLRenderer.setLightmapCoord1i(this.cachedLightmapCoord);
		GLRenderer.setColor3f(1.0F, 1.0F, 1.0F);
		GLRenderer.modelM4f().translate((float) (-camOffX), (float) (-camOffY), (float) (-camOffZ));
		GLRenderer.setShader(Shaders.COLOR_WORLD);
		GLRenderer.globalSetLightEnabled(false);
		GLRenderer.enableState(State.BLEND);
		GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE);
		double[] xOffs = new double[8];
		double[] zOffs = new double[8];
		double xOff = 0.0F;
		double zOff = 0.0F;
		rand.setSeed(this.seed);

		for (int h = 5; h >= 0; --h) {
			xOffs[h] = xOff;
			zOffs[h] = zOff;
			xOff += 0;
			zOff += 0;
		}

		for (int thickness = 0; thickness < 4; ++thickness) {
			rand.setSeed(this.seed);

			for (int p = 0; p < 1; ++p) {
				int hs = 7;
				int ht = 0;

				double xo0 = xOffs[hs] - xOff;
				double zo0 = zOffs[hs] - zOff;

				for (int h = hs; h >= ht; --h) {
					double xo1 = xo0;
					double zo1 = zo0;
					xo0 += rand.nextInt(11) - 5;
					zo0 += rand.nextInt(11) - 5;

					t.startDrawing(DrawMode.TRIANGLE_STRIP);
					t.setColor4f(this.rCol, this.gCol, this.bCol, 0.3F);
					double rr1 = 0.1 + (double) thickness * 0.2;
					rr1 *= (double) h * 0.1 + (double) 1.0F;

					double rr2 = 0.1 + (double) thickness * 0.2;
					rr2 *= (double) (h - 1) * 0.1 + (double) 1.0F;

					for (int s = 0; s < 5; ++s) {
						double xos1 = (double) 0.5F - rr1;
						double zos1 = (double) 0.5F - rr1;
						if (s == 1 || s == 2) {
							xos1 += rr1 * (double) 2.0F;
						}

						if (s == 2 || s == 3) {
							zos1 += rr1 * (double) 2.0F;
						}

						double xos2 = (double) 0.5F - rr2;
						double zos2 = (double) 0.5F - rr2;
						if (s == 1 || s == 2) {
							xos2 += rr2 * (double) 2.0F;
						}

						if (s == 2 || s == 3) {
							zos2 += rr2 * (double) 2.0F;
						}

						t.addVertex(xos2 + xo0, (h - hs) * 16.0, zos2 + zo0);
						t.addVertex(xos1 + xo1, (h - hs + 1) * 16.0, zos1 + zo1);
					}

					GLRenderer.pushFrame();
					GLRenderer.modelM4f().translate((float) this.x, (float) this.y, (float) this.z);
					GLRenderer.modelM4f().rotate(this.yaw, 0.0F, 1.0F, 0.0F);
					GLRenderer.modelM4f().rotate(this.pitch, 1.0F, 0.0F, 0.0F);
					GLRenderer.modelM4f().scale(this.size, this.size, this.size);
					t.draw();
					GLRenderer.popFrame();
				}
			}
		}

		GLRenderer.globalSetLightEnabled(true);
		GLRenderer.popFrame();
	}

	@Override
	public int getParticleTexture() {
		return 1;
	}

	@Override
	public byte calcLightIndex(float partialTick) {
		return LightIndexHelper.lightIndex2i(15, 15);
	}
}
