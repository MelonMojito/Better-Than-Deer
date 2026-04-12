package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class BlockLogicEmber extends BlockLogic {
	public BlockLogicEmber(@NonNull Block<?> block, @NonNull Material material) {
		super(block, material);
	}

	@Override
	public void onEntityCollision(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		world.setBlockType(tilePos, Blocks.AIR);
		world.createExplosion(null, tilePos.x(), tilePos.y(), tilePos.z(), 3.0F, true, false);
		entity.hurt(null, 8, DamageType.BLAST);
		entity.maxFireTicks = 100;
		entity.remainingFireTicks = 100;
		entity.fling(1.0f, 1.0f, 1.0f, 0.0f);

	}

	@Override
	public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		this.onEntityCollision(world, tilePos, entity);
	}

	@Override
	public void animationTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand) {
		double xPos = (double) tilePos.x() + rand.nextDouble();
		double yPos = tilePos.y() + 0.1F;
		double zPos = (double) tilePos.z() + rand.nextDouble();
		world.spawnParticle("smoke", xPos, yPos, zPos, 0.0F, 0.25F, 0.0F, 0, false);
		world.spawnParticle("flame", xPos, yPos, zPos, 0.0F, 0.2F, 0.0F, 0, false);
	}
}
