/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.datagen.provider;

import moriyashiine.anthropophagy.common.Anthropophagy;
import moriyashiine.anthropophagy.common.init.ModEntityTypes;
import moriyashiine.anthropophagy.common.init.ModItems;
import moriyashiine.anthropophagy.common.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
		HolderLookup.RegistryLookup<EntityType<?>> entityTypeLookup = registries.lookupOrThrow(Registries.ENTITY_TYPE);

		Advancement.Builder.advancement()
				.parent(Identifier.tryParse("husbandry/root"))
				.display(ModItems.FLESH,
						Component.translatable("advancements.anthropophagy.husbandry.consume_flesh.title"),
						Component.translatable("advancements.anthropophagy.husbandry.consume_flesh.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						true)
				.addCriterion("consume_flesh", ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(itemLookup, ModItemTags.FLESH)))
				.save(consumer, Anthropophagy.id("husbandry/consume_flesh").toString());

		Advancement.Builder.advancement()
				.parent(Identifier.tryParse("husbandry/root"))
				.display(ModItems.PIGLUTTON_HEART,
						Component.translatable("advancements.anthropophagy.husbandry.kill_piglutton.title"),
						Component.translatable("advancements.anthropophagy.husbandry.kill_piglutton.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						true)
				.addCriterion("killed_piglutton", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().of(entityTypeLookup, ModEntityTypes.PIGLUTTON)))
				.save(consumer, Anthropophagy.id("husbandry/kill_piglutton").toString());

		Advancement.Builder.advancement()
				.parent(Identifier.tryParse("husbandry/root"))
				.display(ModItems.IRON_KNIFE,
						Component.translatable("advancements.anthropophagy.husbandry.obtain_knife.title"),
						Component.translatable("advancements.anthropophagy.husbandry.obtain_knife.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("has_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemLookup, ModItemTags.KNIVES)))
				.save(consumer, Anthropophagy.id("husbandry/obtain_knife").toString());
	}
}