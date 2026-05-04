/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.event;

import moriyashiine.anthropophagy.common.ModConfig;
import moriyashiine.anthropophagy.common.component.entity.CannibalLevelComponent;
import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.anthropophagy.common.tag.ModItemTags;
import moriyashiine.anthropophagy.common.world.entity.Piglutton;
import moriyashiine.anthropophagy.common.world.item.FleshItem;
import moriyashiine.strawberrylib.api.event.EatFoodEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CannibalEatingEvent implements EatFoodEvent {
	@Override
	public void eat(Level level, LivingEntity user, ItemStack stack, FoodProperties properties) {
		CannibalLevelComponent cannibalLevelComponent = ModEntityComponents.CANNIBAL_LEVEL.getNullable(user);
		TetheredComponent tetheredComponent = ModEntityComponents.TETHERED.getNullable(user);
		if (cannibalLevelComponent == null || tetheredComponent == null) {
			return;
		}
		if (stack.is(ModItemTags.FLESH)) {
			if (!tetheredComponent.isTethered()) {
				if (cannibalLevelComponent.getCannibalLevel() < CannibalLevelComponent.MAX_LEVEL) {
					cannibalLevelComponent.setCannibalLevel(Math.min(CannibalLevelComponent.MAX_LEVEL, cannibalLevelComponent.getCannibalLevel() + 2));
					cannibalLevelComponent.updateAttributes();
				}
				if (!level.isClientSide() && cannibalLevelComponent.getCannibalLevel() == 20 || cannibalLevelComponent.getCannibalLevel() == 21) {
					user.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200));
					user.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200));
				}
			}
			if (ModConfig.enablePiglutton) {
				Piglutton.attemptSpawn(user, cannibalLevelComponent.getCannibalLevel(), FleshItem.isOwnerPlayer(stack) && user.getName().getString().equals(FleshItem.getOwnerName(stack)));
			}
		} else {
			if (!tetheredComponent.isTethered() && cannibalLevelComponent.getCannibalLevel() > 0) {
				cannibalLevelComponent.setCannibalLevel(Math.max(0, cannibalLevelComponent.getCannibalLevel() - 1));
				cannibalLevelComponent.updateAttributes();
			}
			if (!level.isClientSide() && cannibalLevelComponent.getCannibalLevel() >= 20) {
				ModEntityComponents.playerCannibalLevel = cannibalLevelComponent.getCannibalLevel();
			}
		}
	}
}
