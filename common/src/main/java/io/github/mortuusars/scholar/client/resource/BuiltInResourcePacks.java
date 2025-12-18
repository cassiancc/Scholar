package io.github.mortuusars.scholar.client.resource;

import io.github.mortuusars.scholar.Scholar;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BuiltInResourcePacks {
    public static List<Pack> get() {
        List<Pack> packs = new java.util.ArrayList<>();

        packs.add(new Pack(
                Scholar.resource("colored_books"),
                Component.translatable("resourcepack.scholar.colored_books.name"),
                new Activation(ActivationType.DEFAULT_ENABLED)));

        return packs;
    }

    public record Pack(ResourceLocation id, Component name, Activation activation) {}

    public record Activation(ActivationType fabric, ActivationType forge) {
        public Activation(ActivationType type) {
            this(type, type);
        }
    }

    public enum ActivationType {
        DEFAULT_DISABLED,
        DEFAULT_ENABLED,
        ALWAYS_ENABLED;
    }
}
