/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.common.world.entity;

import moriyashiine.anthropophagy.common.init.ModEntityTypes;
import moriyashiine.anthropophagy.common.init.ModSoundEvents;
import moriyashiine.anthropophagy.common.tag.ModBlockTags;
import moriyashiine.anthropophagy.common.tag.ModEntityTypeTags;
import moriyashiine.anthropophagy.common.world.entity.ai.goal.*;
import moriyashiine.anthropophagy.common.world.entity.ai.navigation.PigluttonPathNavigation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class Piglutton extends Monster {
	private static final int DAMAGE_THRESHOLD = 20;

	private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Piglutton.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ATTACK_INDEX = SynchedEntityData.defineId(Piglutton.class, EntityDataSerializers.INT);

	public boolean canAttack = false;
	public int overhealAmount = 0, stalkTicks = 0;
	private float damageTaken = 0;
	private int fleeingTicks = 0;

	private int attackTicks = 0, eatingTicks = 0;

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackLeftAnimationState = new AnimationState();
	public final AnimationState attackRightAnimationState = new AnimationState();
	public final AnimationState attackTusksAnimationState = new AnimationState();
	public final AnimationState eatAnimationState = new AnimationState();

	public Piglutton(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		xpReward = 30;
		setPathfindingMalus(PathType.LEAVES, 0);
		setPathfindingMalus(PathType.WATER, -1);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 80)
				.add(Attributes.ARMOR, 14)
				.add(Attributes.ATTACK_DAMAGE, 32)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
				.add(Attributes.FOLLOW_RANGE, 64)
				.add(Attributes.STEP_HEIGHT, 1);
	}

	public static boolean checkPigluttonSpawnRules(EntityType<Piglutton> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		return random.nextInt(8) == 0 && Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new PigluttonPathNavigation(this, level, 3);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		setEating(input.getBooleanOr("Eating", false));
		setAttackIndex(input.getIntOr("AttackIndex", 0));
		canAttack = input.getBooleanOr("CanAttack", false);
		damageTaken = input.getFloatOr("DamageTaken", 0);
		overhealAmount = input.getIntOr("OverhealAmount", 0);
		stalkTicks = input.getIntOr("StalkTicks", 0);
		fleeingTicks = input.getIntOr("FleeingTicks", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("Eating", isEating());
		output.putInt("AttackIndex", getAttackIndex());
		output.putBoolean("CanAttack", canAttack);
		output.putFloat("DamageTaken", damageTaken);
		output.putInt("OverhealAmount", overhealAmount);
		output.putInt("StalkTicks", stalkTicks);
		output.putInt("FleeingTicks", fleeingTicks);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(EATING, false);
		entityData.define(ATTACK_INDEX, 0);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new EatFleshGoal(this));
		goalSelector.addGoal(2, new StalkGoal(this));
		goalSelector.addGoal(2, new FleeGoal(this));
		goalSelector.addGoal(3, new PigluttonMeleeAttackGoal(this, 1, true));
		goalSelector.addGoal(4, new PigluttonWanderAroundFarGoal(this, 1 / 6F));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target, _) -> !isBusy() && target.is(ModEntityTypeTags.PIGLUTTON_TARGETS)));
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			idleAnimationState.startIfStopped(tickCount);
			int index = getAttackIndex();
			attackLeftAnimationState.animateWhen(attackTicks > 0 && index == 0, tickCount);
			attackRightAnimationState.animateWhen(attackTicks > 0 && index == 1, tickCount);
			attackTusksAnimationState.animateWhen(attackTicks > 0 && index == 2, tickCount);
			eatAnimationState.animateWhen(eatingTicks > 0, tickCount);
		}
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		if (fleeingTicks > 0 && --fleeingTicks % 20 == 0) {
			SLibUtils.playSound(this, ModSoundEvents.ENTITY_PIGLUTTON_FLEE, getSoundVolume() * 2, getVoicePitch());
		}
		if (attackTicks > 0 && --attackTicks == 0 && getTarget() != null && distanceTo(getTarget()) < 4.5 * getScale()) {
			doHurtTarget(level, getTarget());
		}
		if (eatingTicks > 0) {
			eatingTicks--;
			if (eatingTicks <= 35 && eatingTicks >= 15 && eatingTicks % 5 == 0) {
				EatFleshGoal.playEffects(this, getMainHandItem(), getEyePosition().add(getLookAngle().scale(2).scale(getScale())));
			}
			if (eatingTicks == 15) {
				EatFleshGoal.heal(level, this, getMainHandItem(), !hasCustomName());
			}
			if (eatingTicks == 14) {
				getMainHandItem().shrink(1);
			}
			if (eatingTicks == 0) {
				setEating(false);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (level() instanceof ServerLevel level && (horizontalCollision || (verticalCollision && !verticalCollisionBelow)) && level.getGameRules().get(GameRules.MOB_GRIEFING)) {
			AABB box = getBoundingBox().inflate(0.2);
			for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
				BlockState state = level.getBlockState(pos);
				float destroySpeed = state.getDestroySpeed(level, pos);
				if (destroySpeed >= 0 && (destroySpeed < 0.5F || state.is(ModBlockTags.PIGLUTTON_BREAKABLE))) {
					level.destroyBlock(pos, true);
				}
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_PIGLUTTON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_PIGLUTTON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_PIGLUTTON_DEATH;
	}

	@Override
	protected AABB getAttackBoundingBox(double horizontalExpansion) {
		return super.getAttackBoundingBox(horizontalExpansion).inflate(0.25, 0, 0.25);
	}

	@Override
	protected float getDamageAfterMagicAbsorb(DamageSource source, float damage) {
		float damageTaken = super.getDamageAfterMagicAbsorb(source, damage);
		this.damageTaken += damageTaken;
		fleeingTicks = 0;
		if (this.damageTaken >= DAMAGE_THRESHOLD) {
			this.damageTaken = 0;
			fleeingTicks = 160;
		}
		return damageTaken;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Override
	public float getSecondsToDisableBlocking() {
		return 5;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor == EATING) {
			if (entityData.get(EATING)) {
				eatingTicks = 65;
			} else {
				eatingTicks = 0;
			}
		}
		if (accessor == ATTACK_INDEX && !firstTick) {
			attackTicks = 10;
		}
	}

	@Override
	public void setXRot(float xRot) {
		if (eatingTicks == 0) {
			super.setXRot(xRot);
		}
	}

	@Override
	public void setYRot(float yRot) {
		if (eatingTicks == 0) {
			super.setYRot(yRot);
		}
	}

	@Override
	public void setYBodyRot(float yBodyRot) {
		if (eatingTicks == 0) {
			super.setYBodyRot(yBodyRot);
		}
	}

	@Override
	public void setYHeadRot(float yHeadRot) {
		if (eatingTicks == 0) {
			super.setYHeadRot(yHeadRot);
		}
	}

	public int getAttackIndex() {
		return entityData.get(ATTACK_INDEX);
	}

	private void setAttackIndex(int index) {
		entityData.set(ATTACK_INDEX, index);
	}

	public boolean isEating() {
		return entityData.get(EATING);
	}

	public void setEating(boolean eating) {
		entityData.set(EATING, eating);
	}

	public void attack() {
		int index = getAttackIndex();
		index++;
		if (index > 2) {
			index = 0;
		}
		setAttackIndex(index);
	}

	public boolean isFleeing() {
		return fleeingTicks > 0;
	}

	public boolean isBusy() {
		return isFleeing() || eatingTicks > 0;
	}

	public static void attemptSpawn(LivingEntity living, int cannibalLevel, boolean ownFlesh) {
		if (living.level().isClientSide()) {
			return;
		}
		float chance = (Math.min(90, cannibalLevel) - 40) / 800F;
		if (ownFlesh) {
			chance *= 3;
		}
		if (living.getRandom().nextFloat() < chance) {
			Piglutton piglutton = ModEntityTypes.PIGLUTTON.create(living.level(), EntitySpawnReason.TRIGGERED);
			if (piglutton != null) {
				final int minH = 16, maxH = 32;
				for (int i = 0; i < 8; i++) {
					int dX = living.getRandom().nextIntBetweenInclusive(minH, maxH) * (living.getRandom().nextBoolean() ? 1 : -1);
					int dY = living.getRandom().nextIntBetweenInclusive(-6, 6);
					int dZ = living.getRandom().nextIntBetweenInclusive(minH, maxH) * (living.getRandom().nextBoolean() ? 1 : -1);
					if (piglutton.randomTeleport(living.getX() + dX, living.getY() + dY, living.getZ() + dZ, false)) {
						living.level().addFreshEntity(piglutton);
						piglutton.setTarget(living);
						SLibUtils.playSound(piglutton, ModSoundEvents.ENTITY_PIGLUTTON_SPAWN, 3.5F, 1);
						return;
					}
				}
			}
		}
	}
}
