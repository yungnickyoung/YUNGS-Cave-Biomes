package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.module.DecoratedPotPatternsModule;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin {
    @Inject(method = "getMaterial", at = @At("HEAD"), cancellable = true)
    private static void yungscavebiomes_renderCustomPotteryMaterial(Item item, CallbackInfoReturnable<Material> cir) {
        Material material = Sheets.getDecoratedPotMaterial(DecoratedPotPatternsModule.getResourceKey(item));
        if (material != null) {
            cir.setReturnValue(material);
        }
    }
}
