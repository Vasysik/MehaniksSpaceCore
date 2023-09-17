package vasys.mehaniksspacecore;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
                            int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2));
                            String tanksTypes = loreOld.get(2).split(" ")[2];

                            if (maxOxygen - oxygen >= Integer.parseInt(loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1])) {
                                tanks -= 1;
                                maxOxygen -= Integer.parseInt(loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1]);

                                if (player.getInventory().firstEmpty() != -1) {
                                    player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank("0", loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1]));
                                } else {
                                    player.getWorld().dropItem(player.getLocation(), MehaniksSpaceItems.getIronOxygenTank("0", loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1]));
                                }
                                tanksTypes = "";
                                String[] tanksList = loreOld.get(2).split(" ")[2].split("/");
                                for (int i = 0; i < tanksList.length - 1; i++) {
                                    tanksTypes += tanksList[i];
                                    if (i < tanksList.length - 2) {
                                        tanksTypes += "/";
                                    }
                                }
                            }

                            String oxygenBar = "";
                            int oxPercent = Math.round((float) (oxygen * 10) / maxOxygen);
                            oxygenBar += ChatColor.BLUE + "■".repeat(oxPercent);
                            int noOxPercent = 12 - oxygenBar.length();
                            oxygenBar += ChatColor.DARK_GRAY + "■".repeat(noOxPercent);

                            lore.add(ChatColor.WHITE + "" + tanks + "/" + loreOld.get(0).split(" ")[0].split("/")[1] + " oxygen tanks");
                            lore.add(ChatColor.WHITE + "[" + ChatColor.BLUE + "" + oxygenBar + "" + ChatColor.WHITE + "] " + oxygen + "/" + maxOxygen);
                            lore.add(ChatColor.DARK_GRAY + "Tanks types: " + tanksTypes);
                            spaceSuitChestplateMeta.setLore(lore);
                            player.getInventory().getChestplate().setItemMeta(spaceSuitChestplateMeta);
                        } else {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 63, true, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
                        }
                    } else {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 63, true, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
                    }
                }
            }
        }, 100, 100);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME && entity.getName().equals(ChatColor.GRAY + "Oxygen Generator")) {
                        entity.setRotation(0, 15);
                        world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1f, 1f);
                    }
                }
            };
        }, 200, 200);
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
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(1));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank("60", "60"));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenGenerator());
                return true;
            }
            if (args[0].equals("chestplate")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(Integer.parseInt(args[1])));
            }
            if (args[0].equals("tank")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(args[1], args[2]));
            }
            sender.sendMessage(ChatColor.RED + "ms !null");
            return false;
        }
        return false;
    }
}
