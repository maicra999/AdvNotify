package cc.maicra999.advnotify;

import io.papermc.paper.advancement.AdvancementDisplay;
import java.nio.charset.StandardCharsets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvNotifyListener implements Listener {

    private static final String DEATH_MESSAGE_FORMAT = "%s␞%s␟%s␟%s";
    private static final String ADVANCEMENT_MESSAGE_FORMAT = "%s␞%s␟%s␟%s␟%s␟%s";

    private final AdvNotify plugin;

    public AdvNotifyListener(AdvNotify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        AdvNotifyConfig config = plugin.config();
        if (!config.announceDeaths) {
            return;
        }

        event.getPlayer()
                .sendPluginMessage(
                        plugin,
                        AdvNotify.CHANNEL_GENERIC,
                        String.format(
                                        DEATH_MESSAGE_FORMAT,
                                        "yep:death",
                                        event.getPlayer().getName(),
                                        plain(event.getPlayer().displayName()),
                                        event.deathMessage() != null ? plain(event.deathMessage()) : null)
                                .getBytes(StandardCharsets.UTF_8));
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        AdvNotifyConfig config = plugin.config();
        if (!config.announceAdvancements) {
            return;
        }

        AdvancementDisplay display = event.getAdvancement().getDisplay();
        if (display == null || event.message() == null) {
            return;
        }

        event.getPlayer()
                .sendPluginMessage(
                        plugin,
                        AdvNotify.CHANNEL_ADVANCEMENT,
                        String.format(
                                        ADVANCEMENT_MESSAGE_FORMAT,
                                        "yep:advancement",
                                        event.getPlayer().getName(),
                                        plain(event.getPlayer().displayName()),
                                        display.frame().name(),
                                        plain(display.title()),
                                        plain(display.description()))
                                .getBytes(StandardCharsets.UTF_8));
    }

    private static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
