package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Sheets.class)
public abstract class SheetsMixin {
    @Shadow
    private static Material createDecoratedPotMaterial(ResourceKey<String> $$0) {
        return null;
    }

    @Unique
    private static final Map<ResourceKey<String>, Material> CUSTOM_DECORATED_POT_MATERIALS = Map.ofEntries(
            Map.entry(DecoratedPotPatternsModule.HOURGLASS, createDecoratedPotMaterial(DecoratedPotPatternsModule.HOURGLASS)),
            Map.entry(DecoratedPotPatternsModule.CLOCK, createDecoratedPotMaterial(DecoratedPotPatternsModule.CLOCK))
    );

    @Inject(method = "getDecoratedPotMaterial", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_createCustomPotteryMaterials(ResourceKey<String> key, CallbackInfoReturnable<Material> cir) {
        if (key == null) return;
        if (CUSTOM_DECORATED_POT_MATERIALS.containsKey(key)) {
            cir.setReturnValue(CUSTOM_DECORATED_POT_MATERIALS.get(key));
        }
    }
}
