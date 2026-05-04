/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.datagen.provider;

import moriyashiine.anthropophagy.api.datagen.FleshDropsProvider;
import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModFleshDropsProvider extends FleshDropsProvider {
	public ModFleshDropsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(Output output) {
		output.accept(EntityType.BOGGED, Items.BONE_MEAL);
		output.accept(EntityType.CAMEL_HUSK, Items.ROTTEN_FLESH);
		output.accept(EntityType.CAVE_SPIDER, Items.STRING);
		output.accept(EntityType.CHICKEN, Items.CHICKEN, Items.COOKED_CHICKEN);
		output.accept(EntityType.COD, Items.BONE_MEAL);
		output.accept(EntityType.COPPER_GOLEM, Items.COPPER_NUGGET);
		output.accept(EntityType.COW, Items.BEEF, Items.COOKED_BEEF);
		output.accept(EntityType.DROWNED, Items.ROTTEN_FLESH);
		output.accept(EntityType.ELDER_GUARDIAN, Items.PRISMARINE_SHARD);
		output.accept(EntityType.EVOKER, ModItems.CORRUPT_FLESH);
		output.accept(EntityType.GIANT, Items.ROTTEN_FLESH);
		output.accept(EntityType.GUARDIAN, Items.PRISMARINE_SHARD);
		output.accept(EntityType.HOGLIN, Items.PORKCHOP, Items.COOKED_PORKCHOP);
		output.accept(EntityType.HUSK, Items.ROTTEN_FLESH);
		output.accept(EntityType.ILLUSIONER, ModItems.CORRUPT_FLESH);
		output.accept(EntityType.IRON_GOLEM, Items.IRON_NUGGET);
		output.accept(EntityType.MANNEQUIN, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.MOOSHROOM, Items.BEEF, Items.COOKED_BEEF);
		output.accept(EntityType.PARCHED, Items.BONE_MEAL);
		output.accept(EntityType.PIG, Items.PORKCHOP, Items.COOKED_PORKCHOP);
		output.accept(EntityType.PIGLIN, Items.PORKCHOP, Items.COOKED_PORKCHOP);
		output.accept(EntityType.PIGLIN_BRUTE, Items.PORKCHOP, Items.COOKED_PORKCHOP);
		output.accept(EntityType.PILLAGER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.PUFFERFISH, Items.BONE_MEAL);
		output.accept(EntityType.PLAYER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.RABBIT, Items.RABBIT, Items.COOKED_RABBIT);
		output.accept(EntityType.RAVAGER, ModItems.CORRUPT_FLESH);
		output.accept(EntityType.SALMON, Items.BONE_MEAL);
		output.accept(EntityType.SHEEP, Items.MUTTON, Items.COOKED_MUTTON);
		output.accept(EntityType.SKELETON, Items.BONE_MEAL);
		output.accept(EntityType.SKELETON_HORSE, Items.BONE_MEAL);
		output.accept(EntityType.SNOW_GOLEM, Items.SNOWBALL);
		output.accept(EntityType.SPIDER, Items.STRING);
		output.accept(EntityType.STRAY, Items.BONE_MEAL);
		output.accept(EntityType.STRIDER, Items.STRING);
		output.accept(EntityType.TROPICAL_FISH, Items.BONE_MEAL);
		output.accept(EntityType.VILLAGER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.VINDICATOR, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.WANDERING_TRADER, ModItems.FLESH, ModItems.COOKED_FLESH);
		output.accept(EntityType.WITCH, ModItems.CORRUPT_FLESH);
		output.accept(EntityType.WITHER_SKELETON, Items.BONE_MEAL);
		output.accept(EntityType.ZOGLIN, Items.ROTTEN_FLESH);
		output.accept(EntityType.ZOMBIE, Items.ROTTEN_FLESH);
		output.accept(EntityType.ZOMBIE_HORSE, Items.ROTTEN_FLESH);
		output.accept(EntityType.ZOMBIE_NAUTILUS, Items.ROTTEN_FLESH);
		output.accept(EntityType.ZOMBIE_VILLAGER, Items.ROTTEN_FLESH);
		output.accept(EntityType.ZOMBIFIED_PIGLIN, Items.ROTTEN_FLESH);
	}

	@Override
	public String getName() {
		return Anthropophagy.MOD_ID + "_flesh_drops";
	}
}
