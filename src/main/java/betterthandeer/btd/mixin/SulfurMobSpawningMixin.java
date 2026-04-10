package betterthandeer.btd.mixin;

import betterthandeer.btd.entity.bat.MobBat;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.nether.BiomeSulfurPools;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeSulfurPools.class)
public abstract class SulfurMobSpawningMixin extends Biome {

	protected SulfurMobSpawningMixin(@NotNull String key) {
		super(key);
	}

	@Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
	private void onBiomeNetherInit(String key, CallbackInfo ci) {
		this.spawnableMonsterList.add(new SpawnListEntry(MobBat.class, 10));
	}
}
