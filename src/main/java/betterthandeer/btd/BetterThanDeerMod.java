package betterthandeer.btd;

import betterthandeer.btd.block.BTDBlocks;
import betterthandeer.btd.entity.BTDEntities;
import betterthandeer.btd.entity.NetEntryRock;
import betterthandeer.btd.item.BTDItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.BlockLogicOreNetherCoal;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.crafting.LookupFuelFurnaceBlast;
import net.minecraft.core.entity.monster.ArmorBag;
import net.minecraft.core.entity.monster.ArmorBags;
import net.minecraft.core.entity.monster.MobZombiePig;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.sound.BlockSounds;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ItemInitEntrypoint;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.core.data.registry.Registries.NAMESPACES;

public class BetterThanDeerMod implements ModInitializer, GameStartEntrypoint, ItemInitEntrypoint {
	public static final String MOD_ID = "btd";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	protected static final @NonNull Map<@NonNull HumanArmorShape, @NonNull ArmorBag<@NonNull HumanArmorShape>> ZOMBIEPIG_ARMOR_BAGS = ArmorBags.register(MobZombiePig.class, new HashMap<>());

	@Override
	public void onInitialize() {
		LOGGER.info("Better Than Deer initialized.");
		Blocks.COBBLE_BASALT.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);
		Blocks.BASALT.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);

		Blocks.RUBYGLASS_SPROUT.withTags(BlockTags.NOT_IN_CREATIVE_MENU);

		Blocks.BLOCK_ASH.withSound(BlockSounds.SAND);

		Blocks.BONE_PILE.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_PICKAXE);
		Blocks.SOULCATCHER.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.PLANTABLE_IN_JAR, BlockTags.MINEABLE_BY_SHOVEL);

		Blocks.NETHERRACK.withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN, BlockTags.CAVES_CUT_THROUGH, BlockTags.NETHER_MOBS_SPAWN, BlockTags.NETHER_SURFACE_BLOCK);

		Blocks.GLOOMSTONE.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);
		Blocks.COBBLE_GLOOMSTONE.withTags(BlockTags.NETHER_SURFACE_BLOCK, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NETHER_MOBS_SPAWN, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH);

		BlockLogicOreNetherCoal.variantMap.put(Blocks.BASALT.id(), Blocks.ORE_NETHERCOAL_BASALT.id());
		BlockLogicOreNetherCoal.variantMap.put(Blocks.BRIMSAND.id(), Blocks.ORE_NETHERCOAL_BASALT.id());

		BlockLogicOreNetherCoal.variantMap.put(Blocks.NETHERRACK.id(), Blocks.ORE_NETHERCOAL_NETHERRACK.id());

		BlockLogicOreNetherCoal.variantMap.put(Blocks.SLATE.id(), Blocks.ORE_NETHERCOAL_GLOOMSTONE.id());

		ZOMBIEPIG_ARMOR_BAGS.put(HumanArmorShape.HEAD, (new ArmorBag<>(HumanArmorShape.HEAD))
			.addEntry(null, 400, 1.0F)
			.addEntry(Items.ARMOR_HELMET_LEATHER, 30, 1.0F)
			.addEntry(Items.ARMOR_HELMET_GOLD, 10, 1.0F)
			.addEntry(Items.ARMOR_HELMET_DIAMOND, 5, 1.0F)
			.addEntry(Items.ARMOR_HELMET_STEEL, 1, 1.0F));

		ZOMBIEPIG_ARMOR_BAGS.put(HumanArmorShape.CHEST, (new ArmorBag<>(HumanArmorShape.CHEST))
			.addEntry(null, 400, 1.0F)
			.addEntry(Items.ARMOR_CHESTPLATE_LEATHER, 30, 1.0F)
			.addEntry(Items.ARMOR_CHESTPLATE_GOLD, 10, 1.0F)
			.addEntry(Items.ARMOR_CHESTPLATE_DIAMOND, 5, 1.0F)
			.addEntry(Items.ARMOR_CHESTPLATE_STEEL, 1, 1.0F));

		ZOMBIEPIG_ARMOR_BAGS.put(HumanArmorShape.LEGS, (new ArmorBag<>(HumanArmorShape.LEGS))
			.addEntry(null, 400, 1.0F)
			.addEntry(Items.ARMOR_LEGGINGS_LEATHER, 30, 1.0F)
			.addEntry(Items.ARMOR_LEGGINGS_GOLD, 10, 1.0F)
			.addEntry(Items.ARMOR_LEGGINGS_DIAMOND, 5, 1.0F)
			.addEntry(Items.ARMOR_LEGGINGS_STEEL, 1, 1.0F));

		ZOMBIEPIG_ARMOR_BAGS.put(HumanArmorShape.BOOTS, (new ArmorBag<>(HumanArmorShape.BOOTS))
			.addEntry(null, 400, 1.0F)
			.addEntry(Items.ARMOR_BOOTS_LEATHER, 30, 1.0F)
			.addEntry(Items.ARMOR_BOOTS_GOLD, 10, 1.0F)
			.addEntry(Items.ARMOR_BOOTS_DIAMOND, 5, 1.0F)
			.addEntry(Items.ARMOR_BOOTS_STEEL, 1, 1.0F));
	}

	@Override
	public void beforeGameStart() {
		NAMESPACES.register(MOD_ID, MOD_ID);

		BTDEntities.init();
		BTDBlocks.init();
		BTDItems.init();

		NetEntityHandler.registerNetworkEntry(new NetEntryRock(), 150);
	}

	@Override
	public void afterGameStart() {
		LookupFuelFurnaceBlast.instance.addFuelEntry(Items.OLIVINE.id, 150);
	}

	@Override
	public void afterItemInit() {

	}
}
