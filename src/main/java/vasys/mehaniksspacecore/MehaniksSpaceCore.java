package vasys.mehaniksspacecore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MehaniksSpaceCore extends JavaPlugin {
    public static List<String> MehaniksSpaceList = new ArrayList<String>();

    public static HashMap<String, List<String>> MehaniksSpaceWorldMap = new HashMap<>();
    public static List<String> MehaniksSpaceWorldMapKeys = new ArrayList<>();
    
    public static HashMap<String, List<Object>> MeteorWorldMap = new HashMap<>();

    public static List<Material> fuelItems = new ArrayList<Material>();
    FileConfiguration config = this.getConfig();
    static public Boolean enabled = false;
    static public Boolean useMultiverseCore = false;

    static public String mainWorld = "world";
    static public int seed = 0;
    static public int defaultWorldBorderDistance = 1000;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MehaniksSpaceEvents(), this);
        enabled = config.getBoolean("enabled");
        useMultiverseCore = config.getBoolean("useMultiverseCore");
        mainWorld = config.getString("mainWorld");
        seed = config.getInt("seed");
        defaultWorldBorderDistance = config.getInt("defaultWorldBorderDistance");
        for (String string : config.getStringList("fuelItems")) fuelItems.add(Material.getMaterial(string));
        MehaniksSpaceItems.addRecipes();

        MehaniksSpaceTasks.playerOxygenTask();
        MehaniksSpaceTasks.playerTask();
        MehaniksSpaceTasks.entityTask();
        MehaniksSpaceTasks.oxygenShieldTask();
        MehaniksSpaceTasks.rocketTask();
        MehaniksSpaceTasks.oilGeneratorTask();
        MehaniksSpaceTasks.oxygenGeneratorTask();
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
                mainWorld = config.getString("mainWorld");
                seed = config.getInt("seed");
                defaultWorldBorderDistance = config.getInt("defaultWorldBorderDistance");
                for (String string : config.getStringList("fuelItems")) {
                    fuelItems.add(Material.getMaterial(string));
                }
                sender.sendMessage(ChatColor.GREEN + "MehaniksSpaceCore plugin successfully reloaded");
                return true;
            }
            Player player = getServer().getPlayer(sender.getName());
            if (player != null) {
                if (args[0].equals("getitems")) {
                    for (Map.Entry<String, ItemStack> entry : MehaniksSpaceItems.customItems.entrySet()) player.getInventory().addItem(entry.getValue());
                    return true;
                }
                if (args[0].equals("chestplate"))
                    player.getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(Integer.parseInt(args[1])));
                if (args[0].equals("tank"))
                    player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                if (args[0].equals("helmet"))
                    player.getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(Integer.parseInt(args[1])));
                if (args[0].equals("leggins"))
                    player.getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins(Integer.parseInt(args[1])));
                if (args[0].equals("shield")) {
                    int oxygen;
                    int oil;
                    if (args[3].equalsIgnoreCase("inf")) oxygen = Integer.MAX_VALUE;
                    else oxygen = Integer.parseInt(args[3]);
                    if (args[4].equalsIgnoreCase("inf")) oil = Integer.MAX_VALUE;
                    else oil = Integer.parseInt(args[4]);
                    player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, Integer.parseInt(args[1]), Integer.parseInt(args[2]), oxygen, oil));
                }
                if (args[0].equals("rocket"))
                    player.getInventory().addItem(MehaniksSpaceItems.getRocket(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5]));
                if (args[0].equals("battery"))
                    player.getInventory().addItem(MehaniksSpaceItems.getCopperBattery(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                if (args[0].equals("meteorite"))
                    player.getInventory().addItem(MehaniksSpaceItems.getMeteorite(args[1]));
                if (args[0].equals("meteor"))
                    MehaniksSpaceFunctions.summonMeteor(new Location(player.getWorld(), player.getX(), player.getWorld().getMaxHeight(), player.getZ()), args[1], true, Float.parseFloat(args[2]), Material.MAGMA_BLOCK);
                return true;
            }
            sender.sendMessage(ChatColor.RED + "ms !null");
        }
        return false;
    }
}
