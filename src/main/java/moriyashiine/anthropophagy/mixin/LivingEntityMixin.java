/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.mixin;

import moriyashiine.anthropophagy.common.component.entity.TetheredComponent;
import moriyashiine.anthropophagy.common.init.ModEntityComponents;
import moriyashiine.anthropophagy.common.init.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
	private void anthropophagy$dropTetheredHeart(ServerLevel level, DamageSource source, boolean killedByPlayer, CallbackInfo ci) {
		TetheredComponent tetheredComponent = ModEntityComponents.TETHERED.getNullable(this);
		if (tetheredComponent != null && tetheredComponent.isTethered()) {
			spawnAtLocation(level, ModItems.PIGLUTTON_HEART);
		}
	}
}
