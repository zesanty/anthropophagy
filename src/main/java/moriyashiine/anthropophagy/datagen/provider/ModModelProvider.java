/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.datagen.provider;

import moriyashiine.anthropophagy.common.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {
		generators.generateFlatItem(ModItems.WOODEN_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.STONE_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.COPPER_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.IRON_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.GOLDEN_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.DIAMOND_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.NETHERITE_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
		generators.generateFlatItem(ModItems.FLESH, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.COOKED_FLESH, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.CORRUPT_FLESH, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.PIGLUTTON_HEART, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.TETHERED_HEART, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.PIGLUTTON_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
	}
}
