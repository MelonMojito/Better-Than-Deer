package betterthandeer.btd.mixin;

import betterthandeer.btd.BTDItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.monster.MobGhast;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobGhast.class)
public class GhastDropsMixin extends MobFlying {
	public GhastDropsMixin(World world) {
		super(world);
	}

	@Override
	public void onDeath(Entity entityKilledBy) {
		super.onDeath(entityKilledBy);
		dropItem(Items.GUNPOWDER.getDefaultStack(), random.nextInt(8));
		dropItem(BTDItems.LEATHER_GHAST.getDefaultStack(), random.nextInt(2));
	}


}
