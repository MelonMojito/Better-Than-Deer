package betterthandeer.btd;

import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import betterthandeer.btd.item.BTDItems;
import betterthandeer.btd.world.ParticleAcidBoiling;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.particle.Particle;
import net.minecraft.client.render.particle.ParticleDispatcher;
import net.minecraft.client.render.particle.ParticleEntry;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class BTDClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void onInitializeClient() {
		BetterThanDeerMod.LOGGER.info("Better Than Deer Client initialized.");
	}

	@Override
	public void beforeClientStart() {
		ParticleDispatcher.getInstance().addDispatch("acidboiling", new ParticleEntry() {
			public Particle newParticle(@NonNull World world, double x, double y, double z, double motionX, double motionY, double motionZ, int data) {
				return new ParticleAcidBoiling(world, x, y, z, motionX, motionY, motionZ, true);
			}
		});
	}

	@Override
	public void afterClientStart() {
		MobInfoRegistry.register(MobGargoyle.class, "guidebook.section.mob.gargoyle.name", "guidebook.section.mob.gargoyle.desc", 16, 200, new MobInfoRegistry.MobDrop[]{
			new MobInfoRegistry.MobDrop(new ItemStack(BTDItems.EYE_GARGOYLE), 1.0F, 0, 2)});
	}
}
