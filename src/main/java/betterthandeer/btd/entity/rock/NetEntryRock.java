package betterthandeer.btd.entity.rock;

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

public class NetEntryRock implements IVehicleEntry<ProjectileRock>, ITrackedEntry<ProjectileRock> {
	public NetEntryRock() {
	}

	public @NonNull Class<ProjectileRock> getAppliedClass() {
		return ProjectileRock.class;
	}

	public int getTrackingDistance() {
		return 64;
	}

	public int getMovementPacketDelay() {
		return 10;
	}

	public boolean sendMotionUpdates() {
		return true;
	}

	public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, ProjectileRock trackedObject) {
	}

	public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
		return new ProjectileRock(world, x, y, z);
	}

	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, ProjectileRock trackedObject) {
		return new PacketAddEntity(trackedObject);
	}
}
