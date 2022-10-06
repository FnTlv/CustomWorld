package com.github.fntlv.customworld.command;

import com.github.fntlv.customworld.Customworld;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainCommand implements CommandExecutor {
    private String arg1 = null;
    private String arg2 = null;
    private String arg3 = null;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = null;
        int commandKind = 0;
        if (src instanceof Player) {
            player = (Player) src;
        }

        if (src instanceof ConsoleSource) {
            Sponge.getServer().getConsole().sendMessage(Text.of("[自定义世界]§3检测你正在使用一个后台命令"));
            commandKind = 1;
        }

        Collection<String> arg1Collection = args.getAll(Text.of("arg1"));
        Collection<String> arg2Collection = args.getAll(Text.of("arg2"));
        Collection<String> arg3Collection = args.getAll(Text.of("arg3"));
        arg1 = this.getArg(arg1Collection);
        arg2 = this.getArg(arg2Collection);
        arg3 = this.getArg(arg3Collection);
        if (arg1 == null){
            if (commandKind ==0){
                player.sendMessage(Text.of("§7[§3世界管理§7] §c请输入正确的命令"));
            }
            return CommandResult.success();
        }

        if (arg1.equals("add")){
            if (commandKind==0){
                if (arg2 !=null){
                    Customworld.getInstance().getConfig().getConfigNode().getNode("protect","list",arg2,"enable").setValue(true);
                    Customworld.getInstance().getConfig().save();
                    player.sendMessage(Text.of("§7[§3世界管理§7] §f成功将世界§6"+arg2+"§f添加到不可破坏列表"));
                } else {
                    player.sendMessage(Text.of("§7[§3世界管理§7] §f通过指令 §a/customworld add 世界名字 §f来将世界添加到保护列表中"));
                }
            }
            return CommandResult.success();
        }

        if (arg1.equals("remove")){
            if (commandKind==0){
                if (arg2 != null){
                    Customworld.getInstance().getConfig().getConfigNode().getNode("protect","list",arg2,"enable").setValue(false);
                    Customworld.getInstance().getConfig().save();
                    player.sendMessage(Text.of("§7[§3世界管理§7] §f成功将世界§6"+arg2+"§f从到不可破坏列表中删除"));
                }else {
                    player.sendMessage(Text.of("§7[§3世界管理§7] §f通过指令 §a/customworld remove 世界名字 "));
                }
            }
            return CommandResult.success();
        }


        return CommandResult.success();
    }

    public String getArg(Collection<String> stringCollection) {
        List<String> args = new ArrayList<>(stringCollection);
        for (String arg : args) {
            return arg;
        }
        return null;
    }
}
