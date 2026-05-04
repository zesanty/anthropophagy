/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.anthropophagy.api.datagen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.anthropophagy.common.reloadlistener.FleshDropsReloadListener;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class FleshDropsProvider extends FabricCodecDataProvider<FleshDropsProvider.DataGenFleshDrop> {
	public FleshDropsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture, PackOutput.Target.DATA_PACK, FleshDropsReloadListener.DIRECTORY, DataGenFleshDrop.CODEC);
	}

	@Override
	protected final void configure(BiConsumer<Identifier, DataGenFleshDrop> provider, HolderLookup.Provider registries) {
		configure((typeId, rawId, cookedId) -> provider.accept(typeId, new DataGenFleshDrop(rawId, cookedId)));
	}

	protected abstract void configure(Output output);

	@FunctionalInterface
	protected interface Output {
		void accept(Identifier typeId, Identifier rawId, Identifier cookedId);

		default void accept(Identifier typeId, Identifier dropId) {
			accept(typeId, dropId, dropId);
		}

		default void accept(EntityType<?> type, Identifier rawId, Identifier cookedId) {
			accept(BuiltInRegistries.ENTITY_TYPE.getKey(type), rawId, cookedId);
		}

		default void accept(EntityType<?> type, Identifier dropId) {
			accept(type, dropId, dropId);
		}

		default void accept(EntityType<?> type, Item raw, Item cooked) {
			accept(type, BuiltInRegistries.ITEM.getKey(raw), BuiltInRegistries.ITEM.getKey(cooked));
		}

		default void accept(EntityType<?> type, Item drop) {
			accept(type, drop, drop);
		}
	}

	protected record DataGenFleshDrop(Identifier raw, Identifier cooked) {
		private static final Codec<DataGenFleshDrop> SINGLE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Identifier.CODEC.fieldOf("drop").forGetter(DataGenFleshDrop::raw)
		).apply(instance, drop -> new DataGenFleshDrop(drop, drop)));
		private static final Codec<DataGenFleshDrop> BOTH_CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Identifier.CODEC.fieldOf("raw").forGetter(DataGenFleshDrop::raw),
				Identifier.CODEC.fieldOf("cooked").forGetter(DataGenFleshDrop::cooked)
		).apply(instance, DataGenFleshDrop::new));
		public static final Codec<DataGenFleshDrop> CODEC = Codec.either(SINGLE_CODEC, BOTH_CODEC).xmap(Either::unwrap, entry -> entry.raw() == entry.cooked() ? Either.left(entry) : Either.right(entry));
	}
}
