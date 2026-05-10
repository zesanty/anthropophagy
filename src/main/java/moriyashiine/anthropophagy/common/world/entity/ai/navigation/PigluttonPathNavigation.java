/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PigluttonPathNavigation extends GroundPathNavigation {
	private final PathfinderMob mob;
	private final int timeOutSpeed;

	public PigluttonPathNavigation(PathfinderMob mob, Level level, int timeOutSpeed) {
		super(mob, level);
		this.mob = mob;
		this.timeOutSpeed = timeOutSpeed;
	}

	@Override
	public void tick() {
		BlockPos targetPos = getTargetPos();
		if (GoalUtils.isSolid(mob, mob.blockPosition()) && mob.getBlockStateOn().isCollisionShapeFullBlock(mob.level(), mob.getOnPos())) {
			mob.getJumpControl().jump();
		}
		if (isDone() || targetPos == null) {
			return;
		}
		tick += timeOutSpeed;
		followThePath();
		moveToOrStop(targetPos);
	}

	@Override
	protected void followThePath() {
		Vec3 currentPos = getTempMobPos();
		int index = path.getNextNodeIndex();
		Vec3 nodePos = Vec3.atBottomCenterOf(path.getNodePos(index));
		double dX = Math.abs(mob.getX() - nodePos.x());
		double dY = nodePos.y() - mob.getY();
		double dZ = Math.abs(mob.getZ() - nodePos.z());
		boolean isWithinReach = dX < maxDistanceToWaypoint && dZ < maxDistanceToWaypoint && (dY <= mob.maxUpStep() && dY > -3);
		if (isWithinReach || canCutCorner(path.getNode(index).type) && shouldJumpToNextNode(currentPos)) {
			path.advance();
		}
		doStuckDetection(currentPos);
	}

	private void moveToOrStop(BlockPos target) {
		maxDistanceToWaypoint = (float) Math.sqrt(180 * mob.getBbWidth());
		mob.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), speedModifier);
		if (mob.distanceToSqr(target.getX(), target.getY(), target.getZ()) <= maxDistanceToWaypoint) {
			mob.getNavigation().stop();
		}
	}

	private boolean shouldJumpToNextNode(Vec3 currentPos) {
		if (path.getNextNodeIndex() + 1 < path.getNodeCount()) {
			Vec3 currentNodePosition = Vec3.atBottomCenterOf(path.getNextNodePos());
			if (currentPos.closerThan(currentNodePosition, Mth.clamp(mob.getBbWidth(), 0, 1.5))) {
				if (canMoveDirectly(currentPos, path.getNextEntityPos(mob))) {
					return true;
				}
				Vec3 nextNodePosition = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
				Vec3 directionToNextNode = nextNodePosition.subtract(currentNodePosition);
				Vec3 dirFromCurrentPos = currentPos.subtract(currentNodePosition);
				return directionToNextNode.dot(dirFromCurrentPos) > 0;
			}
		}
		return false;
	}
}