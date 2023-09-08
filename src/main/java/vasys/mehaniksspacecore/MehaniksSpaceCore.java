package vasys.mehaniksspacecore;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class MehaniksSpaceCore extends JavaPlugin {
    public static List<String> MehaniksSpaceList = new ArrayList<String>();
    public static List<String> MehaniksSpaceWorldList = new ArrayList<String>();
    FileConfiguration config = this.getConfig();
    static public Boolean enabled = false;
    static public Boolean useMultiverseCore = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MehaniksSpaceEvents(), this);
        enabled = config.getBoolean("enabled");
        useMultiverseCore = config.getBoolean("useMultiverseCore");
        getLogger().log(Level.INFO, "Mars WorldGenerator was enabled successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ms")) {
            if (args[0].equals("list")) {
                String pluginList = "MSPlugins: ";
                for (int i = 0; i < MehaniksSpaceList.size(); i++) {
                    pluginList += MehaniksSpaceList.toArray()[i] + ", ";
                }
                sender.sendMessage(pluginList);
                return true;
            }
            if (args[0].equals("reload")) {
                reloadConfig();
                config = this.getConfig();
                enabled = config.getBoolean("enabled");
                useMultiverseCore = config.getBoolean("useMultiverseCore");
                sender.sendMessage(ChatColor.GREEN + "MehaniksSpaceCore plugin successfully reloaded");
                return true;
            }
            if (args[0].equals("getitems")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenHelmet());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "ms !null");
            return false;
        }
        return false;
    }

    static public void spaceMechanics(World world) {

    }
}
