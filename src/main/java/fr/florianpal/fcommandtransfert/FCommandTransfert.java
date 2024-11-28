package fr.florianpal.fcommandtransfert;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import fr.florianpal.fcommandtransfert.commands.TransfertCommand;
import org.slf4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

@Plugin(id = "fcommandtransfert", name = "FCommandTransfert", version = "0.1.0-SNAPSHOT",
        url = "https://florianpal.fr", description = "FCommandTransfert", authors = {"Florianpal"})
public class FCommandTransfert {
    private fr.florianpal.fmessage.managers.commandManagers.CommandManager commandManager;

    private final ProxyServer server;
    private final Logger logger;

    private final Path dataDirectory;

    public static final MinecraftChannelIdentifier BUNGEE_TRANSFERT = MinecraftChannelIdentifier.from(("ftransfert:command"));

    @Inject
    public FCommandTransfert(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        File languageFile = new File(dataDirectory.toFile(), "lang_fr.yml");
        createDefaultConfiguration(languageFile, "lang_fr.yml");


        server.getChannelRegistrar().register(BUNGEE_TRANSFERT);
        commandManager = new fr.florianpal.fmessage.managers.commandManagers.CommandManager(getServer(), this);
        commandManager.registerCommand(new TransfertCommand(this));
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public void createDefaultConfiguration(File actual, String defaultName) {
        // Make parent directories
        File parent = actual.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (actual.exists()) {
            return;
        }

        InputStream input = null;
        try {
            JarFile file = new JarFile(new File(FCommandTransfert.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            ZipEntry copy = file.getEntry(defaultName);
            if (copy == null) throw new FileNotFoundException();
            input = file.getInputStream(copy);
        } catch (IOException e) {
            getLogger().error("Unable to read default configuration: " + defaultName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (input != null) {
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }

                getLogger().info("Default configuration file written: " + actual.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException ignored) {
                }

                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException ignored) {
                }
            }
        }
    }
}
