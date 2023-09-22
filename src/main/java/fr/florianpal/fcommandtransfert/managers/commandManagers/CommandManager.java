package fr.florianpal.fcommandtransfert.managers.commandManagers;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.MessageType;
import fr.florianpal.fcommandtransfert.FCommandTransfert;
import net.md_5.bungee.api.ChatColor;

import java.io.IOException;
import java.util.Locale;

public class CommandManager extends BungeeCommandManager {
    public CommandManager(FCommandTransfert plugin) {
        super(plugin);
        this.enableUnstableAPI("help");

        this.setFormat(MessageType.SYNTAX, ChatColor.YELLOW, ChatColor.GOLD);
        this.setFormat(MessageType.INFO, ChatColor.YELLOW, ChatColor.GOLD);
        this.setFormat(MessageType.HELP, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED);
        this.setFormat(MessageType.ERROR, ChatColor.RED, ChatColor.GOLD);
        try {
            this.getLocales().loadYamlLanguageFile("lang_fr.yml", Locale.FRENCH);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load ACF core language file");
            e.printStackTrace();
        }

        this.getLocales().setDefaultLocale(Locale.FRENCH);
    }

    public void reloadLang() {
        try {
            this.getLocales().loadYamlLanguageFile("lang_fr.yml", Locale.FRENCH);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load ACF core language file");
            e.printStackTrace();
        }
    }
}
