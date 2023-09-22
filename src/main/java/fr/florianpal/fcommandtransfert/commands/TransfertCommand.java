package fr.florianpal.fcommandtransfert.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.florianpal.fcommandtransfert.FCommandTransfert;
import net.md_5.bungee.api.CommandSender;

@CommandAlias("ftransfert")
public class TransfertCommand extends BaseCommand {

    private final FCommandTransfert plugin;

    public TransfertCommand(FCommandTransfert plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("ftransfert.send")
    public void onSend(CommandSender sender, String command) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("command");
        out.writeUTF(command);
        plugin.getProxy().getServers().get("spawn").sendData("ftransfert:command", out.toByteArray());
    }
}
