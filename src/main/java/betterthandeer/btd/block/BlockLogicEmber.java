package betterthandeer.btd.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class BlockLogicEmber extends BlockLogic {
	public BlockLogicEmber(@NotNull Block<?> block, @NotNull Material material) {
		super(block, material);
	}

	@Override
	public void onEntityCollision(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity entity) {
		world.createExplosion(null, tilePos.x(), tilePos.y(), tilePos.z(), 3.0F, true, false);
		entity.hurt(null, 8, DamageType.BLAST);
		entity.maxFireTicks = 100;
		entity.remainingFireTicks = 100;
		entity.fling(1.0f, 1.0f, 1.0f, 0.0f);

	}

	@Override
	public void onEntityWalkedOn(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Entity entity) {
		this.onEntityCollision(world, tilePos, entity);
	}

	@Override
	public void animationTick(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
		double xPos = (double) tilePos.x() + rand.nextDouble();
		double yPos = tilePos.y() + 0.1F;
		double zPos = (double) tilePos.z() + rand.nextDouble();
		world.spawnParticle("smoke", xPos, yPos, zPos, 0.0F, 0.25F, 0.0F, 0, false);
		world.spawnParticle("flame", xPos, yPos, zPos, 0.0F, 0.2F, 0.0F, 0, false);
	}
}
