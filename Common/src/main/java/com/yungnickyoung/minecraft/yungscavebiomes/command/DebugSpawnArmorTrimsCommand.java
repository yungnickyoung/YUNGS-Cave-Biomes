package com.yungnickyoung.minecraft.yungscavebiomes.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.TrimPatternsModule;
import net.minecraft.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public class DebugSpawnArmorTrimsCommand {
    private static final Map<Pair<ArmorMaterial, EquipmentSlot>, Item> MATERIAL_AND_SLOT_TO_ITEM = Util.make(Maps.newHashMap(), $$0 -> {
        $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.HEAD), Items.CHAINMAIL_HELMET);
        $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.CHEST), Items.CHAINMAIL_CHESTPLATE);
        $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.LEGS), Items.CHAINMAIL_LEGGINGS);
        $$0.put(Pair.of(ArmorMaterials.CHAIN, EquipmentSlot.FEET), Items.CHAINMAIL_BOOTS);
        $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.HEAD), Items.IRON_HELMET);
        $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.CHEST), Items.IRON_CHESTPLATE);
        $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.LEGS), Items.IRON_LEGGINGS);
        $$0.put(Pair.of(ArmorMaterials.IRON, EquipmentSlot.FEET), Items.IRON_BOOTS);
        $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.HEAD), Items.GOLDEN_HELMET);
        $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.CHEST), Items.GOLDEN_CHESTPLATE);
        $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.LEGS), Items.GOLDEN_LEGGINGS);
        $$0.put(Pair.of(ArmorMaterials.GOLD, EquipmentSlot.FEET), Items.GOLDEN_BOOTS);
        $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD), Items.NETHERITE_HELMET);
        $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST), Items.NETHERITE_CHESTPLATE);
        $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.LEGS), Items.NETHERITE_LEGGINGS);
        $$0.put(Pair.of(ArmorMaterials.NETHERITE, EquipmentSlot.FEET), Items.NETHERITE_BOOTS);
        $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD), Items.DIAMOND_HELMET);
        $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.CHEST), Items.DIAMOND_CHESTPLATE);
        $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.LEGS), Items.DIAMOND_LEGGINGS);
        $$0.put(Pair.of(ArmorMaterials.DIAMOND, EquipmentSlot.FEET), Items.DIAMOND_BOOTS);
        $$0.put(Pair.of(ArmorMaterials.TURTLE, EquipmentSlot.HEAD), Items.TURTLE_HELMET);
    });
    private static final List<ResourceKey<TrimPattern>> TRIM_PATTERNS = List.of(
            TrimPatternsModule.ANCIENT
    );

    private static final List<ResourceKey<TrimMaterial>> TRIM_MATERIALS = List.of(
            TrimMaterials.QUARTZ,
            TrimMaterials.IRON,
            TrimMaterials.NETHERITE,
            TrimMaterials.REDSTONE,
            TrimMaterials.COPPER,
            TrimMaterials.GOLD,
            TrimMaterials.EMERALD,
            TrimMaterials.DIAMOND,
            TrimMaterials.LAPIS,
            TrimMaterials.AMETHYST
    );

    private static final ToIntFunction<ResourceKey<TrimPattern>> TRIM_PATTERN_ORDER = Util.createIndexLookup(TRIM_PATTERNS);
    private static final ToIntFunction<ResourceKey<TrimMaterial>> TRIM_MATERIAL_ORDER = Util.createIndexLookup(TRIM_MATERIALS);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx, Commands.CommandSelection selection) {
        dispatcher.register(
                Commands.literal("ycb_armor_trims")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> spawnArmorTrims(context.getSource(), context.getSource().getPlayerOrException()))
        );
    }

    private static int spawnArmorTrims(CommandSourceStack source, Player player) {
        Level level = player.level();
        NonNullList<ArmorTrim> armorTrims = NonNullList.create();
        Registry<TrimPattern> patternRegistry = level.registryAccess().registryOrThrow(Registries.TRIM_PATTERN);
        Registry<TrimMaterial> materialRegistry = level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL);

        patternRegistry.stream()
                .filter(pattern -> pattern.assetId().getNamespace().equals(YungsCaveBiomesCommon.MOD_ID))
                .sorted(Comparator.comparing(p -> TRIM_PATTERN_ORDER.applyAsInt(patternRegistry.getResourceKey(p).orElse(null))))
                .forEachOrdered(
                        p -> materialRegistry.stream()
                                .sorted(Comparator.comparing(m -> TRIM_MATERIAL_ORDER.applyAsInt(materialRegistry.getResourceKey(m).orElse(null))))
                                .forEachOrdered(m -> armorTrims.add(new ArmorTrim(materialRegistry.wrapAsHolder(m), patternRegistry.wrapAsHolder(p))))
                );

        BlockPos pos = player.blockPosition().relative(player.getDirection(), 5);
        int numMats = ArmorMaterials.values().length - 1;
        double $$8 = 3.0;
        int trimIndex = 0;
        int materialIndex = 0;

        for (ArmorTrim trim : armorTrims) {
            for (ArmorMaterial material : ArmorMaterials.values()) {
                if (material != ArmorMaterials.LEATHER) {
                    double x = (double) pos.getX() + 0.5 - (double) (trimIndex % materialRegistry.size()) * 3.0;
                    double y = (double) pos.getY() + 0.5 + (double) (materialIndex % numMats) * 3.0;
                    double z = (double) pos.getZ() + 0.5 + (double) (trimIndex / materialRegistry.size() * 10);
                    ArmorStand armorStand = new ArmorStand(level, x, y, z);
                    armorStand.setYRot(180.0F);
                    armorStand.setNoGravity(true);

                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        Item armorItem = MATERIAL_AND_SLOT_TO_ITEM.get(Pair.of(material, slot));
                        if (armorItem != null) {
                            ItemStack armorItemStack = new ItemStack(armorItem);
                            ArmorTrim.setTrim(level.registryAccess(), armorItemStack, trim);
                            armorStand.setItemSlot(slot, armorItemStack);

                            if (armorItem instanceof ArmorItem $$20) {
                                if ($$20.getMaterial() == ArmorMaterials.TURTLE) {
                                    armorStand.setCustomName(
                                            trim.pattern().value().copyWithStyle(trim.material()).copy().append(" ").append(trim.material().value().description())
                                    );
                                    armorStand.setCustomNameVisible(true);
                                    continue;
                                }
                            }

                            armorStand.setInvisible(true);
                        }
                    }

                    level.addFreshEntity(armorStand);
                    materialIndex++;
                }
            }

            trimIndex++;
        }

        source.sendSuccess(() -> Component.literal("Armorstands with trimmed armor spawned around you"), true);
        return 1;
    }
}
