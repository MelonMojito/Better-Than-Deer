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
public class DynamicTextureAcidStill extends DynamicTexture {

	private final Minecraft mc = Minecraft.getMinecraft();
	private float @NonNull [] current;
	private float @NonNull [] next;
	private float @NonNull [] heat;
	private float @NonNull [] heata;
	private final @NonNull IconCoordinate boilingTexture;

	public DynamicTextureAcidStill(@NonNull IconCoordinate targetTexture, @NonNull IconCoordinate boilingTexture) {
		super(targetTexture);
		this.boilingTexture = boilingTexture;
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
		for (int x = 0; x < this.targetTexture.width; ++x) {
			for (int y = 0; y < this.targetTexture.height; ++y) {
				float pow = 0.0F;
				for (int xx = x - 1; xx <= x + 1; ++xx) {
					int xi = pmod(xx, this.targetTexture.width);
					int yi = pmod(y, this.targetTexture.height);
					pow += this.current[xi + yi * this.targetTexture.width];
				}
				this.next[x + y * this.targetTexture.width] = pow / 3.3F + this.heat[x + y * this.targetTexture.width] * 0.8F;
			}
		}

		for (int x = 0; x < this.targetTexture.width; ++x) {
			for (int y = 0; y < this.targetTexture.height; ++y) {
				this.heat[x + y * this.targetTexture.width] += this.heata[x + y * this.targetTexture.width] * 0.05F;
				if (this.heat[x + y * this.targetTexture.width] < 0.0F) {
					this.heat[x + y * this.targetTexture.width] = 0.0F;
				}
				this.heata[x + y * this.targetTexture.width] -= 0.1F;
				if (Math.random() < 0.05) {
					this.heata[x + y * this.targetTexture.width] = 0.5F;
				}
			}
		}

		float[] tmp = this.next;
		this.next = this.current;
		this.current = tmp;

		byte[] imageData = this.getImageData(this.layerColor);

		for (int i = 0; i < this.targetTexture.getArea(); ++i) {
			float pow = this.current[i];
			pow = MathHelper.clamp(pow, 0.0F, 1.0F);
			float pp = pow * pow;

			int r = (int) (180 + pp * 65);
			int g = (int) (140 + pp * 80);
			int b = (int) (40 + pp * 30);

			int a = (int) (180 + pp * 60);

			if (this.mc.currentWorld != null && this.mc.currentWorld.getWorldType().hasTag(WorldTypeTags.NETHER)) {
				a = (int) (210 + pp * 45);
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
