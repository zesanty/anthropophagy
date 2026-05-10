/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.init;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.world.entity.Piglutton;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<Piglutton> PIGLUTTON = registerEntityType("piglutton", EntityType.Builder.of(Piglutton::new, MobCategory.MONSTER).sized(3, 2.6F).canSpawnFarFromPlayer().notInPeaceful(), Piglutton.createAttributes());

	public static void init() {
		SpawnPlacements.register(PIGLUTTON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Piglutton::checkPigluttonSpawnRules);
		if (ModConfig.enablePiglutton) {
			BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_FOREST), PIGLUTTON.getCategory(), PIGLUTTON, 1, 1, 1);
		}
	}
}
