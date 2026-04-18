package betterthandeer.btd.block.conduit;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;

public class TileEntityConduitRubyglass extends TileEntity {

	public Side[] openSides = new Side[0];
	public int strength = 0;
	public float powerRatio = 0.0f;
	private int lastKnownData = -1;

	@Override
	public void tick() {
		int currentData = this.worldObj.getBlockData(tilePos);

		if (currentData != lastKnownData) {
			this.lastKnownData = currentData;
			this.openSides = BlockLogicConduitRubyglass.getOpenSides(currentData);
			this.strength = currentData / BlockLogicConduitRubyglass.STATE_COUNT;
			this.powerRatio = (float) this.strength / BlockLogicConduitRubyglass.PROPAGATION_LENGTH;
		}
	}

	@Override
	public void readAdditionalData(@NotNull CompoundTag compoundTag) {

	}

	@Override
	public void writeAdditionalData(@NotNull CompoundTag compoundTag) {

	}

}

