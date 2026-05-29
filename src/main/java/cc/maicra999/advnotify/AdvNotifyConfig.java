package cc.maicra999.advnotify;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class AdvNotifyConfig {

    public static final int CURRENT_CONFIG_VERSION = 1;

    public boolean announceAdvancements = true;
    public boolean announceDeaths = true;

    public int configVersion = -1;
}
