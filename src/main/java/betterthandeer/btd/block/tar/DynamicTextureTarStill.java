package betterthandeer.btd.block.tar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DynamicTextureTarStill extends DynamicTexture {
	private float[] arr1;
	private float[] arr2;
	private float[] arr3;
	private float[] arr4;

	public DynamicTextureTarStill(@NonNull IconCoordinate targetTexture) {
		super(targetTexture);
	}

	public void postInit() {
		this.initTexture();
		this.arr1 = new float[this.targetTexture.getArea()];
		this.arr2 = new float[this.targetTexture.getArea()];
		this.arr3 = new float[this.targetTexture.getArea()];
		this.arr4 = new float[this.targetTexture.getArea()];
	}

	public void update() {
		byte[] imageData = this.getImageData(this.layerColor);

		for(int i = 0; i < this.targetTexture.width; ++i) {
			for(int j = 0; j < this.targetTexture.height; ++j) {
				float var3 = 0.0F;
				int x2 = (int)(MathHelper.sin((float)j * (float)Math.PI * 2.0F / (float)this.targetTexture.width) * 5.5F);
				int y2 = (int)(MathHelper.sin((float)i * (float)Math.PI * 2.0F / (float)this.targetTexture.height) * 5.5F);

				for(int g = i - 1; g <= i + 1; ++g) {
					for(int b = j - 1; b <= j + 1; ++b) {
						int var8 = pmod(g + x2, this.targetTexture.width);
						int var9 = pmod(b + y2, this.targetTexture.height);
						var3 += this.arr1[var8 + var9 * this.targetTexture.width];
					}
				}

				this.arr2[i + j * this.targetTexture.width] = var3 / 10.0F + (this.arr3[pmod(i, this.targetTexture.width) + pmod(j, this.targetTexture.height) * this.targetTexture.width] + this.arr3[pmod(i + 1, this.targetTexture.width) + pmod(j, this.targetTexture.height) * this.targetTexture.width] + this.arr3[pmod(i + 1, this.targetTexture.width) + pmod(j + 1, this.targetTexture.height) * this.targetTexture.width] + this.arr3[pmod(i, this.targetTexture.width) + pmod(j + 1, this.targetTexture.height) * this.targetTexture.width]) / 4.0F * 0.8F;

				int idx = i + j * this.targetTexture.width;
				this.arr3[idx] += this.arr4[idx] * 0.01F;
				if (this.arr3[idx] < 0.0F) {
					this.arr3[idx] = 0.0F;
				}

				this.arr4[idx] -= 0.06F;
				if (Math.random() < 0.004) {
					this.arr4[idx] = 1.5F;
				}
			}
		}

		float[] temp = this.arr2;
		this.arr2 = this.arr1;
		this.arr1 = temp;

		for(int i = 0; i < this.targetTexture.getArea(); ++i) {
			float brightness = this.arr1[i] * 2.0F;
			if (brightness > 1.0F) brightness = 1.0F;
			if (brightness < 0.0F) brightness = 0.0F;

			int r = (int)(brightness * brightness * 50.0F);
			int g = (int)(brightness * brightness * 40.0F);
			int b = (int)(brightness * brightness * 60.0F);
			int a = 255;

			imageData[i * 4] = (byte)r;
			imageData[i * 4 + 1] = (byte)g;
			imageData[i * 4 + 2] = (byte)b;
			imageData[i * 4 + 3] = (byte)a;
		}
	}
}
