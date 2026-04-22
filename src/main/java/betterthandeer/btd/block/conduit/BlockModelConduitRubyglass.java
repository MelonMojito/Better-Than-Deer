package betterthandeer.btd.block.conduit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.data.block.mojang.BlockModelMojangData;
import org.useless.dragonfly.models.block.StaticBlockModel;

import java.io.InputStream;

public class BlockModelConduitRubyglass extends BlockModelGeneric<BlockLogicConduitRubyglass> {

	private static final boolean DEV_MODE = false;

	private record ModelState(StaticBlockModel model, int rotX, int rotY, int rotZ) { }

	private final ModelState[] stateMap = new ModelState[BlockLogicConduitRubyglass.STATE_COUNT * 11];

	public BlockModelConduitRubyglass(Block<BlockLogicConduitRubyglass> block) {
		super(block, createPowerVariant("/assets/btd/models/block/conduit_rubyglass/conduit_core.json", "btd:dummy", 0));

		for (int strength = 0; strength <= 10; strength++) {

			StaticBlockModel modCore = createPowerVariant("/assets/btd/models/block/conduit_rubyglass/conduit_core.json", "btd:core_" + strength, strength);
			StaticBlockModel modCap = createPowerVariant("/assets/btd/models/block/conduit_rubyglass/conduit_cap.json", "btd:cap_" + strength, strength);
			StaticBlockModel modStraight = createPowerVariant("/assets/btd/models/block/conduit_rubyglass/conduit_straight.json", "btd:straight_" + strength, strength);
			StaticBlockModel modCorner = createPowerVariant("/assets/btd/models/block/conduit_rubyglass/conduit_corner.json", "btd:corner_" + strength, strength);

			int offset = strength * BlockLogicConduitRubyglass.STATE_COUNT;

			// 0: Core
			stateMap[offset] = new ModelState(modCore, 0, 0, 0);

			// 1-6: End Caps (Base port: DOWN)
			stateMap[offset + 1] = new ModelState(modCap, 0, 0, 0); // Bottom
			stateMap[offset + 2] = new ModelState(modCap, 2, 0, 0); // Top
			stateMap[offset + 3] = new ModelState(modCap, 1, 0, 0); // North
			stateMap[offset + 4] = new ModelState(modCap, 3, 0, 0); // South
			stateMap[offset + 5] = new ModelState(modCap, 0, 0, 3); // West
			stateMap[offset + 6] = new ModelState(modCap, 0, 0, 1); // East

			// 7-9: Straights (Base: Y-Axis)
			stateMap[offset + 7] = new ModelState(modStraight, 0, 0, 0); // Y-Axis
			stateMap[offset + 8] = new ModelState(modStraight, 1, 0, 0); // Z-Axis (North/South)
			stateMap[offset + 9] = new ModelState(modStraight, 0, 0, 1); // X-Axis (West/East)

			// 10-13: Bottom-Adjacent Corners (Base: DOWN + NORTH)
			stateMap[offset + 10] = new ModelState(modCorner, 0, 0, 0); // Bottom, North
			stateMap[offset + 11] = new ModelState(modCorner, 0, 2, 0); // Bottom, South (180y)
			stateMap[offset + 12] = new ModelState(modCorner, 0, 1, 0); // Bottom, West
			stateMap[offset + 13] = new ModelState(modCorner, 0, 3, 0); // Bottom, East

			// 14-17: Top-Adjacent Corners (Base flipped: TOP + SOUTH)
			stateMap[offset + 14] = new ModelState(modCorner, 2, 2, 0); // Top, North (Flipped base + 180y)
			stateMap[offset + 15] = new ModelState(modCorner, 2, 0, 0); // Top, South (Flipped base)
			stateMap[offset + 16] = new ModelState(modCorner, 2, 3, 0); // Top, West (FIXED swap: 270y)
			stateMap[offset + 17] = new ModelState(modCorner, 2, 1, 0); // Top, East (FIXED swap: 90y)

			// 18-21: Horizontal Side-to-Side Corners (Base: DOWN + NORTH)
			stateMap[offset + 18] = new ModelState(modCorner, 0, 0, 3); // North, West (Down -> West)
			stateMap[offset + 19] = new ModelState(modCorner, 0, 0, 1); // North, East (Down -> East)
			stateMap[offset + 20] = new ModelState(modCorner, 0, 2, 3); // South, West (South via Y, Down -> West)
			stateMap[offset + 21] = new ModelState(modCorner, 0, 2, 1); // South, East (South via Y, Down -> East)

			for (int i = 0; i < BlockLogicConduitRubyglass.STATE_COUNT; i++) {
				if (stateMap[offset + i] == null) {
					stateMap[offset + i] = stateMap[offset];
				}
			}
		}
	}

	private static StaticBlockModel createPowerVariant(String jsonPath, String newId, int strength) {
		InputStream stream = Minecraft.getMinecraft().texturePackList.getResourceAsStream(jsonPath);
		BlockModelMojangData.Builder builder = BlockModelMojangData.Cache.loadFromStream(stream);
		if (builder == null) throw new RuntimeException("Could not find base model at: " + jsonPath);

		int textureIndex = DEV_MODE ? (strength > 0 ? 10 : 0) : strength;

		builder.setTexture("closed", "btd:block/rubyglass/conduit/closed/" + textureIndex);
		builder.setTexture("plug", "btd:block/rubyglass/conduit/plug/" + textureIndex);
		builder.setTexture("open", "btd:block/rubyglass/conduit/open/" + textureIndex);
		builder.setTexture("straight", "btd:block/rubyglass/conduit/straight/" + textureIndex);
		builder.setTexture("corner", "btd:block/rubyglass/conduit/corner/" + textureIndex);
		builder.setTexture("particle", "btd:block/rubyglass/conduit/closed/" + textureIndex);

		return builder.build(Minecraft.getMinecraft().texturePackList, newId).asModel();
	}

	@Override
	public @NotNull StaticBlockModel getModelFromData(int data) {
		return stateMap[data].model;
	}

	@Override
	public boolean renderAttached(final @NotNull TessellatorGeneral tessellator, @NotNull final WorldSource worldSource, @NotNull TilePosc tilePos, boolean cullFaces, final @Nullable IconCoordinate overrideTexture) {
		int data = worldSource.getBlockData(tilePos);
		ModelState activeState = stateMap[data];
		// uvlock is successfully set to false here!
		return activeState.model.renderAttached(this, tessellator, worldSource, tilePos, activeState.rotX, activeState.rotY, activeState.rotZ, 0, 0, 0, false, cullFaces, overrideTexture);
	}
}
