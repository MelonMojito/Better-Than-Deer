package betterthandeer.btd.mixin;

import net.minecraft.core.entity.monster.MobMonsterArmored;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobZombiePig.class)
public abstract class ZombiePigArmorMixin extends MobMonsterArmored<HumanArmorShape> {

	protected ZombiePigArmorMixin(@NotNull World world) {
		super(world);
	}

	public int getNumArmorSlots() {
		return HumanArmorShape.values().length;
	}

	public @Nullable HumanArmorShape getArmorSlotByIndex(int index) {
		return index >= 0 && index < HumanArmorShape.values().length ? HumanArmorShape.values()[index] : null;
	}


}
