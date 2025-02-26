package com.yungnickyoung.minecraft.yungscavebiomes;

import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ConfigModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class YungsCaveBiomesCommon {
    public static final String MOD_ID = "yungscavebiomes";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ConfigModule CONFIG = new ConfigModule();

    public static boolean MARBLE_CAVES_ENABLED = false; // Rest in peace. Maybe one day...
    public static boolean DEBUG_LOG = false;
    public static boolean DEBUG_RENDERING = false;

    public static void init() {
        DEBUG_LOG = DEBUG_LOG && Services.PLATFORM.isDevelopmentEnvironment();
        DEBUG_RENDERING = DEBUG_RENDERING && Services.PLATFORM.isDevelopmentEnvironment();

        YungAutoRegister.scanPackageForAnnotations("com.yungnickyoung.minecraft.yungscavebiomes");
        Services.MODULES.loadModules();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
