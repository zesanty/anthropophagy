/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.client.event;

import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.client.AddNightVisionScaleEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class CannibalNightVisionEvent implements AddNightVisionScaleEvent {
	@Override
	public float addScale(LivingEntity entity) {
		CannibalLevelComponent cannibalLevelComponent = ModEntityComponents.CANNIBAL_LEVEL.getNullable(entity);
		return cannibalLevelComponent != null ? Mth.clamp(Mth.lerp((cannibalLevelComponent.getCannibalLevel() - CannibalLevelComponent.MIN_FUNCTIONAL_LEVEL) / 20F, 0, 1F), 0, 1) : 0;
	}
}
