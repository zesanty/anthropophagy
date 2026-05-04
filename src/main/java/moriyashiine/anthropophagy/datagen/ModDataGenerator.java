/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.datagen;

import moriyashiine.anthropophagy.datagen.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModAdvancementProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModEntityLootSubProvider::new);
		pack.addProvider(ModEntityTypeTagsProvider::new);
		pack.addProvider(ModFleshDropsProvider::new);
		pack.addProvider(ModItemTagsProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModSoundsProvider::new);
	}
}
