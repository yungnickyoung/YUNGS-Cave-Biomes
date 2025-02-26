package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCreativeTab;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class CreativeTabModule {
    @AutoRegister("general")
    public static final AutoRegisterCreativeTab CREATIVE_TAB = AutoRegisterCreativeTab.builder()
            .title(Component.translatable("itemGroup.yungscavebiomes.general"))
            .iconItem(() -> new ItemStack(BlockModule.LAYERED_ANCIENT_SANDSTONE.get()))
            .entries((params, output) -> {
                output.accept(BlockModule.ICICLE.get());
                output.accept(BlockModule.FROST_LILY.get());
                output.accept(BlockModule.RARE_ICE.get());
                output.accept(BlockModule.ICE_SHEET.get());

                output.accept(BlockModule.ANCIENT_SAND.get());
                output.accept(BlockModule.SUSPICIOUS_ANCIENT_SAND.get());
                output.accept(BlockModule.ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.ANCIENT_SANDSTONE.getStairs());
                output.accept(BlockModule.ANCIENT_SANDSTONE.getSlab());
                output.accept(BlockModule.ANCIENT_SANDSTONE.getWall());
                output.accept(BlockModule.BRITTLE_ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.LAYERED_ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.CUT_ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.CUT_ANCIENT_SANDSTONE.getSlab());
                output.accept(BlockModule.CHISELED_ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.SMOOTH_ANCIENT_SANDSTONE.get());
                output.accept(BlockModule.SMOOTH_ANCIENT_SANDSTONE.getStairs());
                output.accept(BlockModule.SMOOTH_ANCIENT_SANDSTONE.getSlab());

                output.accept(BlockModule.BRITTLE_SANDSTONE.get());
                output.accept(BlockModule.LAYERED_SANDSTONE.get());

                output.accept(BlockModule.BRITTLE_RED_SANDSTONE.get());
                output.accept(BlockModule.LAYERED_RED_SANDSTONE.get());

                output.accept(BlockModule.PRICKLY_PEACH_CACTUS.get());
                output.accept(ItemModule.PRICKLY_PEACH_ITEM.get());
                output.accept(BlockModule.PRICKLY_VINES.get());

                output.accept(ItemModule.ANCIENT_ARMOR_TRIM_SMITHING_TEMPLATE.get());

                output.accept(ItemModule.HOURGLASS_POTTERY_SHERD.get());
                output.accept(ItemModule.CLOCK_POTTERY_SHERD.get());

                output.accept(ItemModule.ICE_CUBE_SPAWN_EGG.get());
                output.accept(ItemModule.SAND_SNAPPER_SPAWN_EGG.get());
            })
            .build();
}
