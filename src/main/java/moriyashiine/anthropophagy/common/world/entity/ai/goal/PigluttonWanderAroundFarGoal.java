/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.world.entity.ai.goal;

import moriyashiine.anthropophagy.common.world.entity.Piglutton;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class PigluttonWanderAroundFarGoal extends WaterAvoidingRandomStrollGoal {
	public PigluttonWanderAroundFarGoal(Piglutton mob, double speedModifier) {
		super(mob, speedModifier);
		this.interval = 60;
	}

	@Override
	public boolean canUse() {
		return ((Piglutton) mob).stalkTicks == 0 && super.canUse();
	}
}
