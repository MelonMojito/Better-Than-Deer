package betterthandeer.btd.mixin.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.worldtype.WorldTypeFXNether;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.pos.TilePos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldTypeFXNether.class, remap = false)
@Environment(EnvType.CLIENT)
public abstract class NetherParticlesMixin {

	@Inject(method = "playerAnimationTick", at = @At("TAIL"))
	private void addVolcanicFireParticles(World world, Player player, CallbackInfo ci) {
		TilePos tilePos = new TilePos(player);
		Biome localBiome = world.getBlockBiome(tilePos);

		if (localBiome == Biomes.NETHER_VOLCANIC_ISLANDS) {
			int radius = 12;

			for (int i = 0; i < 3; ++i) {
				double rx = player.x - (double) radius + world.rand.nextDouble() * (double) radius * (double) 2.0F;
				double ry = player.y - (double) radius + world.rand.nextDouble() * (double) radius * (double) 2.0F;
				double rz = player.z - (double) radius + world.rand.nextDouble() * (double) radius * (double) 2.0F;
				if (world.getBlockType(tilePos.set(MathHelper.floor(rx), MathHelper.floor(ry), MathHelper.floor(rz))) == Blocks.AIR) {
					world.spawnParticle("ashmote", rx, ry, rz, 0.0F, 0.0F, 0.0F, 0, false);
				}
			}
		}

		if (localBiome == Biomes.NETHER_SULFUR_POOLS) {

			for (int i = 0; i < 16; i++) {
				double rx = player.x - 12 + world.rand.nextDouble() * 24;
				double ry = player.y - 6 + world.rand.nextDouble() * 12;
				double rz = player.z - 12 + world.rand.nextDouble() * 24;

				if (world.getBlockType(new TilePos(rx, ry, rz)) == Blocks.AIR) {
					world.spawnParticle("largesmoke", rx, ry, rz, 0.0, 0.02, 0.0, 0, false);
				}
			}
		}


	}
}
