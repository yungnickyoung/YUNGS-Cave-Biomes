package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.command.DebugSpawnArmorTrimsCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.command.SandstormCommand;
import net.minecraft.server.commands.SpawnArmorTrimsCommand;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class CommandModule {
    @AutoRegister("sandstorm")
    public static final AutoRegisterCommand SANDSTORM_COMMAND = AutoRegisterCommand.of(SandstormCommand::register);

    @AutoRegister("ycb_armor_trims")
    public static final AutoRegisterCommand DEBUG_ARMOR_TRIM_COMMAND = AutoRegisterCommand.of(DebugSpawnArmorTrimsCommand::register);

    @AutoRegister("spawn_armor_trims")
    public static final AutoRegisterCommand DEBUG_ALL_ARMOR_TRIM_COMMAND = AutoRegisterCommand.of((dispatcher, ctx, handler) ->
            SpawnArmorTrimsCommand.register(dispatcher));
}
