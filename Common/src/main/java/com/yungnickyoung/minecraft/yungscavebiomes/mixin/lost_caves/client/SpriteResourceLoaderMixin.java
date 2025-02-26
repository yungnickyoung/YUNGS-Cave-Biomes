package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.google.common.collect.ImmutableList;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.PalettedPermutationsAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.SpriteResourceLoaderAccessor;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SpriteResourceLoader.class)
public abstract class SpriteResourceLoaderMixin {
    /**
     * Adds the Ancient armor trim textures to the armor_trims texture atlas.
     */
    @Inject(method = "load", at = @At("RETURN"))
    private static void yungscavebiomes_addAncientArmorTrimTextures(ResourceManager resourceManager, ResourceLocation id, CallbackInfoReturnable<SpriteResourceLoader> cir) {
        if (id.equals(new ResourceLocation("minecraft", "armor_trims"))) {
            SpriteResourceLoader resourceLoader = cir.getReturnValue();
            for (SpriteSource source : ((SpriteResourceLoaderAccessor) resourceLoader).getSources()) {
                if (source instanceof PalettedPermutationsAccessor palettedPermutations && palettedPermutations.getPaletteKey().equals(new ResourceLocation("minecraft", "trims/color_palettes/trim_palette"))) {
                    List<ResourceLocation> textures = new ArrayList<>(palettedPermutations.getTextures());
                    textures.add(YungsCaveBiomesCommon.id("trims/models/armor/ancient"));
                    textures.add(YungsCaveBiomesCommon.id("trims/models/armor/ancient_leggings"));
                    palettedPermutations.setTextures(ImmutableList.copyOf(textures));
                }
            }
        }
    }
}
