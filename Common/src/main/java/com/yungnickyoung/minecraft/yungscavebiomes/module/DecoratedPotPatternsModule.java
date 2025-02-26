package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class DecoratedPotPatternsModule {
    private static final Map<Item, ResourceKey<String>> ITEM_TO_POT_TEXTURE = new HashMap<>();

    public static final ResourceKey<String> HOURGLASS = create("hourglass_pottery_pattern", ItemModule.HOURGLASS_POTTERY_SHERD);
    public static final ResourceKey<String> CLOCK = create("clock_pottery_pattern", ItemModule.CLOCK_POTTERY_SHERD);

    public static void init() {}

    @Nullable
    public static ResourceKey<String> getResourceKey(Item item) {
        return ITEM_TO_POT_TEXTURE.get(item);
    }

    private static ResourceKey<String> create(String name, AutoRegisterItem autoRegisterItem) {
        ResourceKey<String> resourceKey = ResourceKey.create(Registries.DECORATED_POT_PATTERNS, YungsCaveBiomesCommon.id(name));
        ITEM_TO_POT_TEXTURE.put(autoRegisterItem.get(), resourceKey);
        return resourceKey;
    }
}
