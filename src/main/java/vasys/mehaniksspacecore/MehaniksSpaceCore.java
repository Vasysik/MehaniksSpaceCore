package vasys.mehaniksspacecore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.*;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
                for (Player player : getServer().getWorld(s).getPlayers()) {
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
                                    player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(0, Integer.parseInt(loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1])));
                                } else {
                                    player.getWorld().dropItem(player.getLocation(), MehaniksSpaceItems.getIronOxygenTank(0, Integer.parseInt(loreOld.get(2).split(" ")[2].split("/")[loreOld.get(2).split(" ")[2].split("/").length - 1])));
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
            for (World w : getServer().getWorlds()) {
                if (!MehaniksSpaceWorldList.contains(w.getName())) {
                    for (Player player : w.getPlayers()) {
                        if (player.getInventory().getHelmet() != null && player.getInventory().getChestplate() != null &&
                                player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
                                player.getInventory().getChestplate().getItemMeta().getCustomModelData() == 1001 &&
                                Integer.parseInt(player.getInventory().getChestplate().getItemMeta().getLore().get(1).split(" ")[1].split("/")[0]) <
                                        Integer.parseInt(player.getInventory().getChestplate().getItemMeta().getLore().get(1).split(" ")[1].split("/")[1]) &&
                                player.getInventory().getHelmet().getItemMeta().hasCustomModelData() &&
                                player.getInventory().getHelmet().getItemMeta().getCustomModelData() == 1001 &&
                                player.getInventory().getHelmet().getItemMeta().getLore().get(0).split(" ")[3] != "0") {
                            Integer autoOxigenSpeed = Integer.parseInt(player.getInventory().getHelmet().getItemMeta().getLore().get(0).split(" ")[3]);

                            ItemMeta spaceSuitChestplateMeta = player.getInventory().getChestplate().getItemMeta();
                            List<String> loreOld = spaceSuitChestplateMeta.getLore();
                            List<String> lore = new ArrayList<>();
                            int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]);
                            int maxOxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[1]);
                            if (maxOxygen - oxygen >= autoOxigenSpeed) oxygen += autoOxigenSpeed;
                            else oxygen = maxOxygen;
                            int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2));
                            String tanksTypes = loreOld.get(2).split(" ")[2];

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
                        }
                    }
                }
            }
        }, 100, 100);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Oxygen Generator") &&
                                itemFrame.getItem().getType() == Material.FIREWORK_STAR) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);

                            Container barrel = (Container) itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState();

                            if (barrel.getInventory().contains(Material.WATER_BUCKET) || barrel.getInventory().contains(Material.COPPER_INGOT)) {
                                String name = itemFrame.getItem().getItemMeta().getDisplayName();
                                int oxygen = Integer.parseInt(name.split(" ")[2]);
                                int copper = Integer.parseInt(name.split(" ")[3]);
                                for (ItemStack i : barrel.getInventory().getContents()) {
                                    if (i != null && i.getType() == Material.WATER_BUCKET) {
                                        oxygen += 600;
                                        i.setType(Material.BUCKET);
                                    }
                                    if (i != null && i.getType() == Material.COPPER_INGOT) {
                                        copper += 30 * i.getAmount();
                                        i.setType(Material.GRAY_DYE);
                                    }
                                }
                                itemFrame.setItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.BLUE, oxygen, copper));
                            } else if (barrel.getInventory().contains(Material.CARROT_ON_A_STICK)) {
                                for (ItemStack i : barrel.getInventory().getContents()) {
                                    if (i != null && i.getType() == Material.CARROT_ON_A_STICK &&
                                            i.getItemMeta().hasCustomModelData() &&
                                            i.getItemMeta().getCustomModelData() == 1001) {
                                        ItemMeta itemMeta = i.getItemMeta();
                                        String name = itemFrame.getItem().getItemMeta().getDisplayName();
                                        List<String> loreOld = itemMeta.getLore();
                                        List<String> lore = new ArrayList<>();
                                        int min = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
                                        int max = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);
                                        int oxygen = Integer.parseInt(name.split(" ")[2]) - 1;
                                        int copper = Integer.parseInt(name.split(" ")[3]) - 5;

                                        if (min < max && oxygen >= 0 && copper >= 0) {
                                            min += 1;
                                            lore.add(ChatColor.WHITE + "volume: " + min + "/" + max);
                                            itemMeta.setLore(lore);
                                            i.setItemMeta(itemMeta);
                                            itemFrame.setItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.BLUE, oxygen, copper));

                                            if (itemFrame.getRotation() == Rotation.NONE) itemFrame.setRotation(Rotation.CLOCKWISE_45);
                                            else if (itemFrame.getRotation() == Rotation.CLOCKWISE_45) itemFrame.setRotation(Rotation.CLOCKWISE);
                                            else if (itemFrame.getRotation() == Rotation.CLOCKWISE) itemFrame.setRotation(Rotation.CLOCKWISE_135);
                                            else if (itemFrame.getRotation() == Rotation.CLOCKWISE_135) itemFrame.setRotation(Rotation.FLIPPED);
                                            else if (itemFrame.getRotation() == Rotation.FLIPPED) itemFrame.setRotation(Rotation.FLIPPED_45);
                                            else if (itemFrame.getRotation() == Rotation.FLIPPED_45) itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE);
                                            else if (itemFrame.getRotation() == Rotation.COUNTER_CLOCKWISE) itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
                                            else itemFrame.setRotation(Rotation.NONE);

                                            world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_BREATH, 0.5f, 1f);

                                            if (oxygen == 0 || copper == 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
                                        }
                                    }
                                }
                            }
                        } else if (itemFrame.getName().equals("Oxygen Shield Generator") &&
                                itemFrame.getItem().getType() == Material.MAGMA_CREAM) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);

                            String name = itemFrame.getItem().getItemMeta().getDisplayName();
                            int oxygen = Integer.parseInt(name.split(" ")[2]);
                            int oil = Integer.parseInt(name.split(" ")[3]);
                            int maxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[1]);
                            int oilMaxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[1]);

                            List<Container> containers = new ArrayList<Container>();
                            int genX = itemFrame.getLocation().getBlockX();
                            int genY = itemFrame.getLocation().getBlockY()-1;
                            int genZ = itemFrame.getLocation().getBlockZ();


                            if (itemFrame.getWorld().getBlockAt(genX + 1, genY, genZ).getState() instanceof InventoryHolder) {
                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX + 1, genY, genZ).getState());
                            }
                            if (itemFrame.getWorld().getBlockAt(genX - 1, genY, genZ).getState() instanceof InventoryHolder) {
                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX - 1, genY, genZ).getState());
                            }
                            if (itemFrame.getWorld().getBlockAt(genX, genY, genZ + 1).getState() instanceof InventoryHolder) {
                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX, genY, genZ + 1).getState());
                            }
                            if (itemFrame.getWorld().getBlockAt(genX, genY, genZ - 1).getState() instanceof InventoryHolder) {
                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX, genY, genZ - 1).getState());
                            }

                            if (containers.size() != 0) {
                                for (Container container : containers) {
                                    if (container.getInventory().contains(Material.CARROT_ON_A_STICK) || container.getInventory().contains(Material.INK_SAC)) {
                                        for (ItemStack item : container.getInventory().getContents()) {

                                            if (item.getType().equals(Material.CARROT_ON_A_STICK) &&
                                                    item.getItemMeta().getCustomModelData() == 1001) {

                                                ItemMeta itemMeta = item.getItemMeta();
                                                List<String> loreOld = itemMeta.getLore();
                                                List<String> lore = new ArrayList<>();
                                                int min = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
                                                int max = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);

                                                if (min > 0) {
                                                    oxygen += min;
                                                    min = 0;
                                                    lore.add(ChatColor.WHITE + "volume: " + min + "/" + max);
                                                    itemMeta.setLore(lore);
                                                }
                                            } else if (item.getType().equals(Material.INK_SAC) &&
                                                    item.getItemMeta().getCustomModelData() == 1001) {
                                                oil += 120;
                                            }
                                            itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygen, oil, maxR, oilMaxR));
                                        }
                                    }
                                }
                            }

                            Location sphereL = itemFrame.getLocation();

                            double sphereRadius = maxR;
                            if(oil < oilMaxR) {
                                sphereRadius *= (double) oil /oilMaxR;
                            }

                            for(double phi=0; phi<=Math.PI; phi+=Math.PI/15) {
                                for(double theta=0; theta<=2*Math.PI; theta+=Math.PI/30) {
                                    double x = sphereRadius*Math.cos(theta)*Math.sin(phi);
                                    double y = sphereRadius*Math.cos(phi) + 1.5;
                                    double z = sphereRadius*Math.sin(theta)*Math.sin(phi);

                                    sphereL.add(x,y,z);
                                    itemFrame.getWorld().spawnParticle(Particle.SCULK_CHARGE_POP, sphereL, 1, 0F, 0F, 0F, 0.001);
                                    sphereL.subtract(x, y, z);
                                }
                            }

                            if (oil > 0) {
                                oil -= 1;
                                itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygen, oil, maxR, oilMaxR));
                            }

                        } else if (!itemFrame.isVisible() && (itemFrame.getName().equals("Oxygen Generator") ||  itemFrame.getName().equals("Oxygen Shield Generator"))) {
                            itemFrame.setCustomName("");
                            itemFrame.setVisible(true);
                        }
                    }
                }
            };
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
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(1));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(30, 30));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, 0, 0, 5, 360));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getOil());
                return true;
            }
            if (args[0].equals("chestplate")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(Integer.parseInt(args[1])));
            }
            if (args[0].equals("tank")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            }
            if (args[0].equals("helmet")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(Integer.parseInt(args[1])));
            }
            sender.sendMessage(ChatColor.RED + "ms !null");
            return false;
        }
        return false;
    }
}
