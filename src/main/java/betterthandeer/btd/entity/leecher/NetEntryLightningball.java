package betterthandeer.btd.entity.leecher;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NetEntryLightningball implements IVehicleEntry<ProjectileLightningball>, ITrackedEntry<ProjectileLightningball> {
	public NetEntryLightningball() {
	}

	public @NonNull Class<ProjectileLightningball> getAppliedClass() {
		return ProjectileLightningball.class;
	}

	public int getTrackingDistance() {
		return 128;
	}

	public int getMovementPacketDelay() {
		return 1;
	}

	public boolean sendMotionUpdates() {
		return true;
	}

	public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, ProjectileLightningball trackedObject) {
	}

	public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
		return new ProjectileLightningball(world, xd, yd, zd);
	}

	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, ProjectileLightningball trackedObject) {
		return new PacketAddEntity(trackedObject, -1, -1, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}
}
