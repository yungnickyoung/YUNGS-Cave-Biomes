package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.item.PricklyPeachItem;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class ItemModule {
    @AutoRegister("ice_cube_spawn_egg")
    public static final AutoRegisterItem ICE_CUBE_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getIceCubeSpawnEggItem());

    @AutoRegister("sand_snapper_spawn_egg")
    public static final AutoRegisterItem SAND_SNAPPER_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getSandSnapperSpawnEggItem());

    @AutoRegister("prickly_peach")
    public static final AutoRegisterItem PRICKLY_PEACH_ITEM = AutoRegisterItem.of(() -> new PricklyPeachItem(
                new Item.Properties()
                        .food(new FoodProperties.Builder()
                                .nutrition(4)
                                .saturationMod(0.3F)
                                .build())));

    @AutoRegister("ancient_armor_trim_smithing_template")
    public static final AutoRegisterItem ANCIENT_ARMOR_TRIM_SMITHING_TEMPLATE = AutoRegisterItem.of(() ->
            SmithingTemplateItem.createArmorTrimTemplate(TrimPatternsModule.ANCIENT)
    );

    @AutoRegister("hourglass_pottery_sherd")
    public static final AutoRegisterItem HOURGLASS_POTTERY_SHERD = AutoRegisterItem.of(() -> new Item(new Item.Properties()));

    @AutoRegister("clock_pottery_sherd")
    public static final AutoRegisterItem CLOCK_POTTERY_SHERD = AutoRegisterItem.of(() -> new Item(new Item.Properties()));

    /**
     * Methods with the AutoRegister annotations will be executed after registration.
     *
     * For Fabric, this means the method is executed during mod initialization as normal.
     * For Forge, the method is queued to execute in common setup.
     *
     * Any methods used with the AutoRegister annotation must be static and take no arguments.
     * Note that the annotation value is ignored.
     */
    @AutoRegister("init")
    private static void addCompostables() {
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.PRICKLY_VINES.get().asItem(), 0.5F);
        AutoRegisterUtils.addCompostableItem(() -> BlockModule.PRICKLY_PEACH_CACTUS.get().asItem(), 0.5F);
        AutoRegisterUtils.addCompostableItem(() -> ItemModule.PRICKLY_PEACH_ITEM.get(), 0.65F);
    }
}
