package betterthandeer.btd.mixin;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.nether.BiomeNether;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeNether.class)
public abstract class NetherMobSpawningMixin extends Biome {

	protected NetherMobSpawningMixin(@NotNull String key) {
		super(key);
	}

	@Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
	private void onBiomeNetherInit(String key, CallbackInfo ci) {
		this.spawnableMonsterList.add(new SpawnListEntry(MobGargoyle.class, 10));
	}
}
