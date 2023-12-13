package vasys.mehaniksspacecore;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WaterMob;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;
import org.bukkit.util.Vector;

public final class MehaniksSpaceCore extends JavaPlugin {
    public static List<String> MehaniksSpaceList = new ArrayList<String>();

    public static HashMap<String, List<String>> MehaniksSpaceWorldMap = new HashMap<>();
    public static List<String> MehaniksSpaceWorldMapKeys = new ArrayList<>();

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
            if (args[0].equals("getitems")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.DARK_GRAY, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getOil());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketNose());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketFuelTank());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketNozzle());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocket(2000, 0, 0, 0, "none"));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketConrolPanel());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getFlightControlPanel());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketModificationPanel());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getLithium());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRawLithium());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getMeteoricIron());
                try { getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getMeteorite("meteoric_iron"));} catch (MalformedURLException e) {throw new RuntimeException(e);}
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getNasturan());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getTitanium());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRawTitanium());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRawMeteoricIron());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRawNasturan());
                return true;
            }
            if (args[0].equals("chestplate")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(Integer.parseInt(args[1])));
            if (args[0].equals("tank")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            if (args[0].equals("helmet")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(Integer.parseInt(args[1])));
            if (args[0].equals("leggins")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins(Integer.parseInt(args[1])));
            if (args[0].equals("shield")) {
                int oxygen;
                int oil;
                if (args[3].equalsIgnoreCase("inf")) oxygen = Integer.MAX_VALUE;
                else oxygen = Integer.parseInt(args[3]);
                if (args[4].equalsIgnoreCase("inf")) oil = Integer.MAX_VALUE;
                else oil = Integer.parseInt(args[4]);
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, Integer.parseInt(args[1]), Integer.parseInt(args[2]), oxygen, oil));
            }
            if (args[0].equals("rocket")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocket(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5]));
            if (args[0].equals("battery")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getCopperBattery(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            if (args[0].equals("meteorite")) try { getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getMeteorite(args[1]));} catch (MalformedURLException e) {throw new RuntimeException(e);}
            sender.sendMessage(ChatColor.RED + "ms !null");
            return true;
        }
        return false;
    }
}
