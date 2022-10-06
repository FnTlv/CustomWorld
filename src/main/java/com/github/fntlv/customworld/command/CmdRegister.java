package com.github.fntlv.customworld.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public class CmdRegister {
    public CmdRegister(PluginContainer pluginContainer){
        CommandSpec commandSpec = CommandSpec.builder()
                .executor(new MainCommand())
                .arguments(
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("arg1"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("arg2"))
                        ),GenericArguments.optional(
                                GenericArguments.string(Text.of("arg3"))
                        )
                ).build();
        Sponge.getCommandManager().register(pluginContainer,commandSpec,"customworld");
    }
}
