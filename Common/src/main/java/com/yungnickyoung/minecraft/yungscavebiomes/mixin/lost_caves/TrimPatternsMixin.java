package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TrimPatternsModule;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrimPatterns.class)
public abstract class TrimPatternsMixin {
    @Shadow
    private static void register(BootstapContext<TrimPattern> $$0, Item $$1, ResourceKey<TrimPattern> $$2) {
        throw new AssertionError();
    }

    @Inject(method = "bootstrap", at = @At("RETURN"))
    private static void yungscavebiomes_registerCustomArmorTrims(BootstapContext<TrimPattern> context, CallbackInfo ci) {
//        register(context, ItemModule.ANCIENT_ARMOR_TRIM_SMITHING_TEMPLATE.get(), TrimPatternsModule.ANCIENT);
    }
}
