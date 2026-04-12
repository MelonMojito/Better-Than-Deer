package betterthandeer.btd.mixin;

import net.minecraft.core.entity.monster.MobZombie;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobZombiePig.class)
public class ZombiePigSwordMixin extends MobZombie {
	@Shadow
	@Final
	private static ItemStack DEFAULT_HELD_ITEM;

	public ZombiePigSwordMixin(@NotNull World world) {
		super(world);
	}

	@Override
	public @Nullable ItemStack getHeldItem() {
		return DEFAULT_HELD_ITEM;
	}

}
