package cc.maicra999.advnotify;

import cc.maicra999.advnotify.util.Logs;
import java.io.IOException;
import java.nio.file.Path;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class AdvNotify extends JavaPlugin {

    public static final String CHANNEL_GENERIC = "yep:generic";

    private static final Logger LOGGER = Logs.logger();

    private final YamlConfigurationLoader configLoader;

    private AdvNotifyConfig config;

    public AdvNotify() {
        this.configLoader = YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .path(getConfigPath())
                .build();
    }

    @Override
    public void onEnable() {
        try {
            loadAndUpdateConfig();
        } catch (IOException e) {
            LOGGER.error("Failed to load or update config.yml");
            throw new RuntimeException(e);
        }

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, CHANNEL_GENERIC);

        getServer().getPluginManager().registerEvents(new AdvNotifyListener(this), this);
    }

    public void loadAndUpdateConfig() throws IOException {
        CommentedConfigurationNode root = configLoader.load();
        this.config = root.get(AdvNotifyConfig.class);

        if (config != null && config.configVersion < AdvNotifyConfig.CURRENT_CONFIG_VERSION) {
            // Save the config file with new fields
            config.configVersion = AdvNotifyConfig.CURRENT_CONFIG_VERSION;
            root.set(AdvNotifyConfig.class, config);
            configLoader.save(root);
        }
    }

    public AdvNotifyConfig config() {
        return config;
    }

    private Path getConfigPath() {
        return getDataFolder().toPath().resolve("config.yml");
    }
}
