/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.event;

import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.ModifyMovementEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class CannibalJumpBoostEvent implements ModifyMovementEvents.JumpDelta {
	@Override
	public Vec3 modify(Vec3 delta, LivingEntity entity) {
		if (entity.isShiftKeyDown()) {
			CannibalLevelComponent cannibalLevelComponent = ModEntityComponents.CANNIBAL_LEVEL.getNullable(entity);
			if (cannibalLevelComponent != null) {
				return delta.add(0, cannibalLevelComponent.getJumpBoost(), 0);
			}
		}
		return delta;
	}
}
