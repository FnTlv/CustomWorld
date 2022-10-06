package com.github.fntlv.customworld.data;

import com.github.fntlv.customworld.Customworld;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainConfig {
    private final Path configFile;
    private final ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode configNode;

    public MainConfig(){
        this.configFile = Paths.get(Customworld.getInstance().configDir.getParent()+ File.separator + "config.conf");
        this.configLoader = HoconConfigurationLoader.builder().setPath(this.configFile).build();
        this.setUp();
    }

    public void setUp() {
        if (!Files.exists(configFile)){
            try {
                Files.createFile(this.configFile);
                this.load();
                this.intdata();
                this.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            this.load();
        }
    }

    public void load(){
        try {
            this.configNode = this.configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            this.configLoader.save(this.configNode);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void intdata(){
        getConfigNode().getNode("protect","enable").setValue("true");
    }

    public CommentedConfigurationNode getConfigNode(){
        return this.configNode;
    }

}
