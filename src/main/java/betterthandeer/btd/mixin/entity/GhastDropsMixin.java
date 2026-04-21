package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.item.BTDItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.monster.MobGhast;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobGhast.class)
public class GhastDropsMixin extends MobFlying {
	public GhastDropsMixin(World world) {
		super(world);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		this.remainingFireTicks = 0;
	}

	@Override
	public void onDeath(Entity entityKilledBy) {
		super.onDeath(entityKilledBy);
		dropItem(Items.GUNPOWDER.getDefaultStack(), random.nextInt(8));
		dropItem(BTDItems.LEATHER_GHAST.getDefaultStack(), random.nextInt(2));
	}


}
