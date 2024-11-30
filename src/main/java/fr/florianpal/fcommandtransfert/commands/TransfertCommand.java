package fr.florianpal.fcommandtransfert.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import fr.florianpal.fcommandtransfert.FCommandTransfert;

import java.util.Optional;

@CommandAlias("ftransfert")
public class TransfertCommand extends BaseCommand {

    private final FCommandTransfert plugin;

    public TransfertCommand(FCommandTransfert plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("ftransfert.send")
    public void onSend(CommandSource sender, String player, String command) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("command");
        out.writeUTF(command);
        Optional<Player> optional = plugin.getServer().getPlayer(player);
        optional.ifPresent(r -> r.getCurrentServer().ifPresent(s -> s.sendPluginMessage(FCommandTransfert.BUNGEE_TRANSFERT, out.toByteArray())));
    }
}
