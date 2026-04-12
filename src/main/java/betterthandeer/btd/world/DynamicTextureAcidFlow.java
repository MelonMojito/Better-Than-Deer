package betterthandeer.btd.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.type.tag.WorldTypeTags;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DynamicTextureAcidFlow extends DynamicTexture {

	private final Minecraft mc = Minecraft.getMinecraft();
	private float[] current;
	private float[] next;
	private float[] heat;
	private float[] heata;
	private int ticks = 0;

	public DynamicTextureAcidFlow(@NonNull IconCoordinate targetTexture) {
		super(targetTexture);
	}

	@Override
	public void postInit() {
		this.initTexture();
		this.current = new float[this.targetTexture.getArea()];
		this.next = new float[this.targetTexture.getArea()];
		this.heat = new float[this.targetTexture.getArea()];
		this.heata = new float[this.targetTexture.getArea()];
	}

	@Override
	public void update() {
		++this.ticks;

		for (int x = 0; x < this.targetTexture.width; ++x) {
			for (int y = 0; y < this.targetTexture.height; ++y) {
				float pow = 0.0F;
				for (int k = y - 2; k <= y; ++k) {
					int xi = pmod(x, this.targetTexture.width);
					int yi = pmod(k, this.targetTexture.height);
					pow += this.current[xi + yi * this.targetTexture.width];
				}
				this.next[x + y * this.targetTexture.width] = pow / 3.2F + this.heat[x + y * this.targetTexture.width] * 0.8F;
			}
		}

		for (int x = 0; x < this.targetTexture.width; ++x) {
			for (int y = 0; y < this.targetTexture.height; ++y) {
				this.heat[x + y * this.targetTexture.width] += this.heata[x + y * this.targetTexture.width] * 0.05F;
				if (this.heat[x + y * this.targetTexture.width] < 0.0F)
					this.heat[x + y * this.targetTexture.width] = 0.0F;

				this.heata[x + y * this.targetTexture.width] -= 0.3F;
				if (Math.random() < 0.2)
					this.heata[x + y * this.targetTexture.width] = 0.5F;
			}
		}

		float[] tmp = this.next;
		this.next = this.current;
		this.current = tmp;

		byte[] imageData = this.getImageData(this.layerColor);

		for (int i = 0; i < this.targetTexture.getArea(); ++i) {
			float pow = this.current[pmod(i - this.ticks * this.targetTexture.width, this.targetTexture.getArea())];
			pow = MathHelper.clamp(pow, 0.0F, 1.0F);
			float pp = pow * pow;

			int r = (int) (180 + pp * 65);
			int g = (int) (140 + pp * 80);
			int b = (int) (40 + pp * 30);
			int a = (int) (170 + pp * 65);

			if (this.mc.currentWorld != null && this.mc.currentWorld.getWorldType().hasTag(WorldTypeTags.NETHER)) {
				a = (int) (200 + pp * 55);
			}

			r = MathHelper.clamp(r, 0, 255);
			g = MathHelper.clamp(g, 0, 255);
			b = MathHelper.clamp(b, 0, 255);
			a = MathHelper.clamp(a, 0, 255);

			imageData[i * 4] = (byte) r;
			imageData[i * 4 + 1] = (byte) g;
			imageData[i * 4 + 2] = (byte) b;
			imageData[i * 4 + 3] = (byte) a;
		}
	}
}
