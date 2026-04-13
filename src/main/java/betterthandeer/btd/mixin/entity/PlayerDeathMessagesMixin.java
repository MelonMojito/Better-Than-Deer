package betterthandeer.btd.mixin.entity;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.entity.gargoyle.MobGargoyle;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerDeathMessagesMixin extends Mob {

	@Shadow
	@Final
	public static TextFormatting deathMsgColor;

	protected PlayerDeathMessagesMixin(@NotNull World world) {
		super(world);
	}

	@Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
	private void onGetDeathMessage(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
		String playerName = this.getDisplayName();

		if (this.world.isMaterialInBB(this.bb, Materials.WATER) && world.dimension == Dimension.NETHER) {
			cir.setReturnValue(playerName + deathMsgColor + " became part of the broth.");
		}

		if (this.world.isMaterialInBB(this.bb, BTDBlocks.ACID)) {
			cir.setReturnValue(playerName + deathMsgColor + " thought he was acid proof.");
		}
		if (entityKilledBy instanceof MobGargoyle) {
			cir.setReturnValue(playerName + deathMsgColor + " met the batman.");
		}
	}
}
