/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.event;

import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.objects.enums.PreventionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CannibalEquipmentEvent implements PreventEquipmentUsageEvent {
	@Override
	public PreventionResult getPreventionResult(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		if (slot.isArmor()) {
			CannibalLevelComponent cannibalLevelComponent = ModEntityComponents.CANNIBAL_LEVEL.getNullable(entity);
			if (cannibalLevelComponent != null && cannibalLevelComponent.cannotEquip(stack)) {
				return PreventionResult.PREVENT;
			}
		}
		return PreventionResult.PASS;
	}
}
