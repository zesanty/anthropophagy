/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.datagen.provider;

import moriyashiine.anthropophagy.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModBlockTags.PIGLUTTON_BREAKABLE)
				.forceAddTag(BlockTags.FENCE_GATES)
				.forceAddTag(BlockTags.LOGS)
				.forceAddTag(BlockTags.PLANKS)
				.forceAddTag(BlockTags.WOODEN_FENCES);
	}
}
