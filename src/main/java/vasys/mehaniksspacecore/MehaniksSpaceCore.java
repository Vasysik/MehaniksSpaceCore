package vasys.mehaniksspacecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class MehaniksSpaceCore extends JavaPlugin {
    public static List<String> MehaniksSpaceList = new ArrayList<String>();
    public static List<String> MehaniksSpaceWorldList = new ArrayList<String>();
    public static List<Integer> MehaniksSpaceGravityList = new ArrayList<Integer>();
    public static List<Integer> MehaniksSpaceTemperatureList = new ArrayList<Integer>();
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
        MehaniksSpaceItems.addRecipes();
        getLogger().log(Level.INFO, "Mars WorldGenerator was enabled successfully.");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (String s : MehaniksSpaceWorldList) {
                for (int j = 0; j < getServer().getWorld(s).getPlayers().size(); j++) {
                    Player player = getServer().getWorld(s).getPlayers().get(j);
                    if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
                            player.getInventory().getChestplate().getItemMeta().getCustomModelData() == 1001) {
                        ItemMeta spaceSuitChestplateMeta = player.getInventory().getChestplate().getItemMeta();
                        List<String> loreOld = spaceSuitChestplateMeta.getLore();

                        if (Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]) > 0) {
                            List<String> lore = new ArrayList<>();
                            int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]) - 1;
                            int maxOxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[1]);

                            String oxygenBar = "";
                            int oxPercent = Math.round((float) (oxygen * 10) / maxOxygen);
                            oxygenBar += ChatColor.BLUE + "■".repeat(oxPercent);
                            int noOxPercent = 12 - oxygenBar.length();
                            oxygenBar += ChatColor.DARK_GRAY + "■".repeat(noOxPercent);

                            lore.add(loreOld.get(0));
                            lore.add(ChatColor.WHITE + "[" + ChatColor.BLUE + "" + oxygenBar + "" + ChatColor.WHITE + "] " + oxygen + "/" + maxOxygen);
                            lore.add(loreOld.get(2));
                            spaceSuitChestplateMeta.setLore(lore);
                            player.getInventory().getChestplate().setItemMeta(spaceSuitChestplateMeta);
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 63, true, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
                        }
                    }
                }
            }
        }, 100, 100);
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
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "ms !null");
            return false;
        }
        return false;
    }
}
