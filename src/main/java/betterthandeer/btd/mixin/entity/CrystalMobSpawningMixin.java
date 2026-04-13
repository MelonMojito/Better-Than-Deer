package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.nether.BiomeCrystalForest;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeCrystalForest.class)
public abstract class CrystalMobSpawningMixin extends Biome {

	protected CrystalMobSpawningMixin(@NonNull String key) {
		super(key);
	}

	@Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
	private void onBiomeNetherInit(String key, CallbackInfo ci) {
		this.spawnableMonsterList.add(new SpawnListEntry(MobGargoyle.class, 10));
	}
}
