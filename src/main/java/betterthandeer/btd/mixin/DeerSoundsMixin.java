package betterthandeer.btd.mixin;

import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobDeer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobDeer.class)
public abstract class DeerSoundsMixin extends MobAnimal {

	protected DeerSoundsMixin(World world) {
		super(world);
	}

	@Override
	public String getLivingSound() {
		return "btd:mob.deer";
	}

	@Override
	protected String getHurtSound() {
		return "btd:mob.deerhurt";
	}

	@Override
	protected String getDeathSound() {
		return "btd:mob.deerdeath";
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}
}
