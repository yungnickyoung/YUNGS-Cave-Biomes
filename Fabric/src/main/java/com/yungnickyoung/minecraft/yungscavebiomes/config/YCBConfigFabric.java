package com.yungnickyoung.minecraft.yungscavebiomes.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="yungscavebiomes-fabric-1_18_2")
public class YCBConfigFabric implements ConfigData {
    @ConfigEntry.Category("YUNG's Cave Biomes")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigGeneralFabric general = new ConfigGeneralFabric();
}
