package betterthandeer.btd.world;

import betterthandeer.btd.block.BTDBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ParticleAcidBoiling extends Particle {
	private float sinTimer = 0.0F;
	private boolean isReversed;
	private final boolean needsWater;
	private final TilePos qpos = new TilePos();
	private final @NonNull IconCoordinate popping = TextureRegistry.getTexture("btd:particle/acid_boiling_pop");

	public ParticleAcidBoiling(World world, double x, double y, double z, double xa, double ya, double za, boolean needsWater) {
		super(world, x, y, z, xa, ya, za);
		this.needsWater = needsWater;
		this.rCol = 1.0F;
		this.gCol = 1.0F;
		this.bCol = 1.0F;
		this.tex = TextureRegistry.getTexture("btd:particle/acid_boiling");
		this.size *= random.nextFloat() * 0.7F + 0.3F;
		this.xd = xa * 0.2 + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
		this.yd = ya * 0.2 + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
		this.zd = za * 0.2 + (Math.random() * (double) 2.0F - (double) 1.0F) * (double) 0.02F;
		this.lifetime = (int) ((double) 16.0F / (Math.random() * 0.4 + 0.1));
		this.isReversed = random.nextInt(2) == 0;
	}

	@Override
	public void tick() {
		++this.sinTimer;
		if (this.sinTimer > 100.0F) {
			this.sinTimer = 0.0F;
		}

		this.cachedLightmapCoord = this.calcLightIndex(1.0F);
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		double expand = 0.05;
		boolean inWater = this.world.getBlockMaterial(this.qpos.set((int) this.x, (int) this.y, (int) this.z)) == BTDBlocks.ACID;
		if (inWater) {
			this.yd = 0.2;
			this.xd = MathHelper.sin(this.sinTimer * 0.95F) / 30.0F * (float) (this.isReversed ? -1 : 1);
			this.zd = MathHelper.cos(this.sinTimer * 0.95F) / 30.0F * (float) (this.isReversed ? -1 : 1);
		} else {
			this.yd = MathHelper.clamp(this.yd, -expand, expand);
		}

		this.move(this.xd, this.yd, this.zd);
		this.xd *= 0.85;
		this.yd *= 0.85;
		this.zd *= 0.85;
		if (this.needsWater && !inWater) {
			this.lifetime = MathHelper.clamp(this.lifetime, 0, 8);
		}

		if (this.lifetime == 6) {
			this.tex = this.popping;
			this.world.spawnParticle("arrowtrail", this.x, this.y, this.z, 0.0F, 0.0F, 0.0F, 1, false);
		} else if (this.lifetime <= 0) {
			this.remove();
		}

		--this.lifetime;
	}
}
