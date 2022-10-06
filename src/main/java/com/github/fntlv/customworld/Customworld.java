package com.github.fntlv.customworld;

import com.github.fntlv.customworld.command.CmdRegister;
import com.github.fntlv.customworld.data.MainConfig;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;

@Plugin(
        id = "customworld",
        name = "Customworld",
        description = "a world setting"
)
public class Customworld {
    private static Customworld instance;
    private MainConfig config;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer pluginContainer;

    @Inject
    @DefaultConfig(
            sharedRoot = false
    )
    public Path configDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        logger.info("§f自定义世界配置加载ing");
        config = new MainConfig();
        new CmdRegister(pluginContainer);
    }

    @Listener
    public void onPlayerPlaceBlock(ChangeBlockEvent.Place event, @First Player player){
        String name = player.getWorld().getName();
        for (Object world :getConfig().getConfigNode().getNode("protect","list").getChildrenMap().keySet()){
                String worldname = String.valueOf(world);
                if (worldname.equals(name) && !player.hasPermission("customworld.admin")){
                    if (Customworld.getInstance().getConfig().getConfigNode().getNode("protect","list",worldname,"enable").getBoolean()){
                        event.setCancelled(true);
                    }
                }
        }
    }

    @Listener
    public void onPlayerBreakBlock(ChangeBlockEvent.Break event, @First Player player){
        String name = player.getWorld().getName();
        for (Object world :getConfig().getConfigNode().getNode("protect","list").getChildrenMap().keySet()){
            String worldname = String.valueOf(world);
            if (worldname.contains(name) && !player.hasPermission("customworld.admin")){
                if (Customworld.getInstance().getConfig().getConfigNode().getNode("protect","list",worldname,"enable").getBoolean()){
                    event.setCancelled(true);
                }
            }
        }
    }

    @Listener
    public void onPlayerInteract(InteractBlockEvent.Primary event,@First Player player){
        String name = player.getWorld().getName();
        for (Object world :getConfig().getConfigNode().getNode("protect","list").getChildrenMap().keySet()){
            String worldname = String.valueOf(world);
            if (worldname.contains(name) && !player.hasPermission("customworld.admin")){
                if (Customworld.getInstance().getConfig().getConfigNode().getNode("protect","list",worldname,"enable").getBoolean()){
                    event.setCancelled(true);
                }
            }
        }
    }

    public static Customworld getInstance(){
        return instance;
    }

    public MainConfig getConfig(){
        return this.config;
    }
}
