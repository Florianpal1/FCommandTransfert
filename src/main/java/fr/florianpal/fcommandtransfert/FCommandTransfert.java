package fr.florianpal.fcommandtransfert;

import fr.florianpal.fcommandtransfert.commands.TransfertCommand;
import fr.florianpal.fcommandtransfert.managers.commandManagers.CommandManager;
import net.md_5.bungee.api.plugin.Plugin;

public class FCommandTransfert extends Plugin {
    private CommandManager commandManager;

    @Override
    public void onEnable() {

        getProxy().registerChannel("ftransfert:command");
        commandManager = new CommandManager(this);
        commandManager.registerCommand(new TransfertCommand(this));
    }
}
