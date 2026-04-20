package betterthandeer.btd.entity.arrow.flaming;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetEntryArrowFlaming implements IVehicleEntry<ProjectileArrowFlaming>, ITrackedEntry<ProjectileArrowFlaming> {
	public NetEntryArrowFlaming() {
	}

	public @NotNull Class<ProjectileArrowFlaming> getAppliedClass() {
		return ProjectileArrowFlaming.class;
	}

	public int getTrackingDistance() {
		return 64;
	}

	public int getMovementPacketDelay() {
		return 20;
	}

	public boolean sendMotionUpdates() {
		return false;
	}

	public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, ProjectileArrowFlaming trackedObject) {
	}

	public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
		return new ProjectileArrowFlaming(world, x, y, z);
	}

	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, ProjectileArrowFlaming trackedObject) {
		Mob entityliving = trackedObject.owner;
		return new PacketAddEntity(trackedObject, 0, entityliving == null ? -1 : entityliving.id, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}
}
