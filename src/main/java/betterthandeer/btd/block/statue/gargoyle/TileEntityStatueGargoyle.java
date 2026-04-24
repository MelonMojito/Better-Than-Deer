package betterthandeer.btd.block.statue.gargoyle;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import org.jspecify.annotations.NonNull;

public class TileEntityStatueGargoyle extends TileEntity {
	private TileEntityStatueGargoyle.@NonNull Pose pose;

	public TileEntityStatueGargoyle() {
		this.pose = TileEntityStatueGargoyle.Pose.DEFAULT;
	}

	public void writeAdditionalData(@NonNull CompoundTag compoundTag) {
		compoundTag.putByte("Pose", (byte) this.pose.ordinal());

	}

	public void readAdditionalData(@NonNull CompoundTag compoundTag) {
		byte poseByte = compoundTag.getByteOrDefault("Pose", (byte) TileEntityStatueGargoyle.Pose.DEFAULT.ordinal());
		if (poseByte < 0 || poseByte >= TileEntityStatueGargoyle.Pose.values().length) {
			poseByte = (byte) TileEntityStatueGargoyle.Pose.DEFAULT.ordinal();
		}

		this.pose = TileEntityStatueGargoyle.Pose.values()[poseByte];

	}

	public TileEntityStatueGargoyle.@NonNull Pose getPose() {
		return this.pose;
	}

	public void setPose(TileEntityStatueGargoyle.@NonNull Pose pose) {
		this.pose = pose;
		this.setChanged();
	}

	public void nextPose() {
		this.setPose(TileEntityStatueGargoyle.Pose.values()[(this.getPose().ordinal() + 1) % TileEntityStatueGargoyle.Pose.values().length]);
	}

	@Override
	public Packet getDescriptionPacket() {
		return new PacketTileEntityData(this);
	}

	public enum Pose {
		DEFAULT(0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F),

		UPSIDE_DOWN(0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 3.14159F,
			0.0F, 0.0F, 3.14159F);

		public final float leftArmPitch;
		public final float rightArmPitch;
		public final float leftLegPitch;
		public final float rightLegPitch;
		public final float headPitch;
		public final float headYaw;
		public final float headRoll;
		public final float bodyPitch;
		public final float bodyYaw;
		public final float bodyRoll;

		Pose(final float leftArmPitch, final float rightArmPitch, final float leftLegPitch, final float rightLegPitch,
		     final float headPitch, final float headYaw, final float headRoll,
		     final float bodyPitch, final float bodyYaw, final float bodyRoll) {
			this.leftArmPitch = leftArmPitch;
			this.rightArmPitch = rightArmPitch;
			this.leftLegPitch = leftLegPitch;
			this.rightLegPitch = rightLegPitch;
			this.headPitch = headPitch;
			this.headYaw = headYaw;
			this.headRoll = headRoll;
			this.bodyPitch = bodyPitch;
			this.bodyYaw = bodyYaw;
			this.bodyRoll = bodyRoll;
		}
	}
}
