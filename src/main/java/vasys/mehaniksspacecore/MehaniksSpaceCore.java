package vasys.mehaniksspacecore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
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

    public static HashMap<String, List<String>> MehaniksSpaceWorldMap = new HashMap<>();
    public static List<String> MehaniksSpaceWorldMapKeys = new ArrayList<>();

    public static List<Material> fuelItems = new ArrayList<Material>();
    FileConfiguration config = this.getConfig();
    static public Boolean enabled = false;
    static public Boolean useMultiverseCore = false;

    static public String mainWorld = "world";
    static public int seed = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MehaniksSpaceEvents(), this);
        enabled = config.getBoolean("enabled");
        useMultiverseCore = config.getBoolean("useMultiverseCore");
        mainWorld = config.getString("mainWorld");
        seed = config.getInt("seed");
        for (String string : config.getStringList("fuelItems")) {
            fuelItems.add(Material.getMaterial(string));
        }
        MehaniksSpaceItems.addRecipes();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World w : getServer().getWorlds()) {
                for (Player player : w.getPlayers()) {
                    if (MehaniksSpaceWorldMap.containsKey(w.getName())) {
                        ItemFrame oxigenShieldItemFrame = MehaniksSpaceFunctions.inActiveOxygenShield(player);
                        if (oxigenShieldItemFrame != null) {
                            String name = oxigenShieldItemFrame.getItem().getItemMeta().getDisplayName();
                            int oxygen = Integer.parseInt(name.split(" ")[3]) - 1;
                            int oil = Integer.parseInt(name.split(" ")[4]);
                            int maxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                            int oilMaxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);
                            oxigenShieldItemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygen, oil, maxR, oilMaxR));
                            if (player.hasPotionEffect(PotionEffectType.POISON) && player.hasPotionEffect(PotionEffectType.CONFUSION)) {
                                player.removePotionEffect(PotionEffectType.POISON);
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                player.removePotionEffect(PotionEffectType.BLINDNESS);
                            }
                        } else if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
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

                                MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, lore, oxygen, maxOxygen, tanks, tanksTypes);

                                if (player.hasPotionEffect(PotionEffectType.POISON) && player.hasPotionEffect(PotionEffectType.CONFUSION)) {
                                    player.removePotionEffect(PotionEffectType.POISON);
                                    player.removePotionEffect(PotionEffectType.CONFUSION);
                                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                                }

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

                        if (oxigenShieldItemFrame == null &&
                                (player.getInventory().getHelmet() == null || !player.getInventory().getHelmet().getItemMeta().hasCustomModelData() || player.getInventory().getHelmet().getItemMeta().getCustomModelData() != 1001 ||
                                        player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getItemMeta().hasCustomModelData() || player.getInventory().getChestplate().getItemMeta().getCustomModelData() != 1001 ||
                                        player.getInventory().getLeggings() == null || !player.getInventory().getLeggings().getItemMeta().hasCustomModelData() || player.getInventory().getLeggings().getItemMeta().getCustomModelData() != 1001 ||
                                        player.getInventory().getBoots() == null || !player.getInventory().getBoots().getItemMeta().hasCustomModelData() || player.getInventory().getBoots().getItemMeta().getCustomModelData() != 1001)) {
                            int temperature = Integer.parseInt(MehaniksSpaceWorldMap.get(player.getWorld().getName()).get(2));
                            if (temperature != 0) {
                                if (temperature < 0 && !player.isFreezeTickingLocked()) {
                                    player.lockFreezeTicks(true);
                                    player.setFreezeTicks(Math.abs(temperature) * 20);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, Math.abs(temperature) - 1, true, false));
                                }
                            }
                        } else if (player.isFreezeTickingLocked()) {
                            player.lockFreezeTicks(false);
                            player.removePotionEffect(PotionEffectType.WITHER);
                        }
                    } else if (player.getWorld().getBlockAt(player.getLocation()).getType().equals(Material.WATER) &&
                            player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
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

                            MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, lore, oxygen, maxOxygen, tanks, tanksTypes);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 5, 1, true, false));
                        }
                    }
                }
            }
        }, 20, 20);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World w : getServer().getWorlds()) {
                for (Player player : w.getPlayers()) {
                    ItemFrame oxigenShieldItemFrame = MehaniksSpaceFunctions.inActiveOxygenShield(player);
                    if ((!MehaniksSpaceWorldMap.containsKey(w.getName()) || oxigenShieldItemFrame != null) &&
                            !player.getWorld().getBlockAt(player.getLocation()).getType().equals(Material.WATER) &&
                            player.getInventory().getHelmet() != null && player.getInventory().getChestplate() != null &&
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

                        if (oxigenShieldItemFrame != null) {
                            String name = oxigenShieldItemFrame.getItem().getItemMeta().getDisplayName();
                            int oxygenS = Integer.parseInt(name.split(" ")[3]);
                            int oil = Integer.parseInt(name.split(" ")[4]);
                            int maxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                            int oilMaxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);

                            if (oxygenS >= autoOxigenSpeed) {
                                oxygenS -= autoOxigenSpeed;
                                oxygen += autoOxigenSpeed;
                            } else {
                                oxygen += oxygenS;
                                oxygenS = 0;
                            }

                            oxigenShieldItemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygenS, oil, maxR, oilMaxR));
                        } else {
                            if (maxOxygen - oxygen >= autoOxigenSpeed) oxygen += autoOxigenSpeed;
                            else oxygen = maxOxygen;
                        }
                        
                        int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2));
                        String tanksTypes = loreOld.get(2).split(" ")[2];

                        MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, lore, oxygen, maxOxygen, tanks, tanksTypes);
                    }
                }
            }
        }, 20, 20);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Oxygen Generator") &&
                                itemFrame.getItem().getType() == Material.FIREWORK_STAR) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);
                            
                            BlockState block = itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState();
                            
                            if (block.getType() == Material.BARREL) {
                                Container barrel = (Container) block;

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
                                    for (ItemStack item : barrel.getInventory().getContents()) {
                                        if (item != null && item.getType() == Material.CARROT_ON_A_STICK &&
                                                item.getItemMeta().hasCustomModelData() &&
                                                item.getItemMeta().getCustomModelData() == 1001) {
                                            ItemMeta itemMeta = item.getItemMeta();
                                            String name = itemFrame.getItem().getItemMeta().getDisplayName();
                                            List<String> loreOld = itemMeta.getLore();
                                            List<String> lore = new ArrayList<>();
                                            int min = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
                                            int max = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);
                                            int oxygen = Integer.parseInt(name.split(" ")[2]) - 1;
                                            int copper = Integer.parseInt(name.split(" ")[3]) - 5;

                                            if (min < max && oxygen >= 0 && copper >= 0) {
                                                min += 5;
                                                lore.add(ChatColor.WHITE + "volume: " + min + "/" + max);
                                                itemMeta.setLore(lore);
                                                item.setItemMeta(itemMeta);

                                                MehaniksSpaceFunctions.itemFrameRotate(itemFrame);

                                                world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_BREATH, 0.5f, 1f);

                                                if (oxygen == 0 || copper == 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
                                                else itemFrame.setItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.BLUE, oxygen, copper));
                                            }
                                        }
                                    }
                                }
                            } else {
                                itemFrame.remove();
                                itemFrame.getWorld().createExplosion(itemFrame, 2);
                            }
                        } else if (itemFrame.getName().equals("Oxygen Shield Generator") &&
                                itemFrame.getItem().getType() == Material.MAGMA_CREAM) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);

                            String name = itemFrame.getItem().getItemMeta().getDisplayName();

                            int oxygen = Integer.parseInt(name.split(" ")[3]);
                            int oil = Integer.parseInt(name.split(" ")[4]);
                            int maxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                            int oilMaxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);

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
                                            if (item != null &&
                                                    item.getType().equals(Material.CARROT_ON_A_STICK) &&
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
                                                    item.setItemMeta(itemMeta);
                                                }
                                            } else if (item != null && item.getType().equals(Material.INK_SAC) &&
                                                    item.getItemMeta().hasCustomModelData() &&
                                                    item.getItemMeta().getCustomModelData() == 1001) {
                                                oil += 120 * item.getAmount();
                                                item.setAmount(0);
                                            }
                                            itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygen, oil, maxR, oilMaxR));
                                        }
                                    }
                                }
                            }
                            Location sphereL = itemFrame.getLocation();
                            double sphereRadius = maxR;
                            if(oil < oilMaxR) sphereRadius *= (double) oil/oilMaxR;

                            if (oil > 0) {
                                oil -= 1;
                                for(double phi=0; phi<=Math.PI; phi+=Math.PI/15) {
                                    for(double theta=0; theta<=2*Math.PI; theta+=Math.PI/30) {
                                        double x = sphereRadius*Math.cos(theta)*Math.sin(phi);
                                        double y = sphereRadius*Math.cos(phi);
                                        double z = sphereRadius*Math.sin(theta)*Math.sin(phi);

                                        sphereL.add(x,y,z);
                                        itemFrame.getWorld().spawnParticle(Particle.END_ROD, sphereL, 1, 0F, 0F, 0F, 0.001);
                                        sphereL.subtract(x, y, z);
                                    }
                                }
                                itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, oxygen, oil, maxR, oilMaxR));
                            }
                        } else if (itemFrame.getName().equals("Oil Generator") &&
                                itemFrame.getItem().getType() == Material.HONEYCOMB ) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);
                            String name = itemFrame.getItem().getItemMeta().getDisplayName();
                            int oil = Integer.parseInt(name.split(" ")[2]);
                            int fuel = Integer.parseInt(name.split(" ")[3]);

                            BlockState block = itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState();
                            
                            if (block.getType() == Material.BARREL) {
                                Container barrel = (Container) block;

                                for (ItemStack item : barrel.getInventory().getContents()) {
                                    if (item != null && (fuelItems.contains(item.getType()) || item.getType().isEdible())) {
                                        if (item.getType().isEdible()) fuel += item.getAmount() * 15;
                                        fuel += item.getAmount() * 5;
                                        item.setAmount(0);
                                        itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.BLACK, oil, fuel));
                                    }
                                }

                                if (fuel >= 5) {
                                    fuel -= 5;
                                    oil += 1;
                                    if (oil == 0 || fuel == 0) itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.GRAY, oil, fuel));
                                    else itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.BLACK, oil, fuel));

                                    MehaniksSpaceFunctions.itemFrameRotate(itemFrame);
                                }

                                if (oil >= 50) {
                                    oil -= 50;
                                    barrel.getInventory().addItem(MehaniksSpaceItems.getOil());

                                    if (oil == 0 || fuel == 0) itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.GRAY, oil, fuel));
                                    else itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.BLACK, oil, fuel));
                                }
                            } else {
                                itemFrame.remove();
                                itemFrame.getWorld().createExplosion(itemFrame, 1);
                            }

                        } else if (itemFrame.getName().equals("Rocket") &&
                                itemFrame.getItem().getType() == Material.NETHERITE_SCRAP &&
                                itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState().getType() == Material.LODESTONE) {
                            if (itemFrame.isVisible()) itemFrame.setVisible(false);

                            int maxOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                            int maxStorage = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[2]);
                            int currentOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(2).split(" ")[2]);
                            int currentStorage = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(3).split(" ")[2]);
                            String endPoint = itemFrame.getItem().getItemMeta().getLore().get(4).split(" ")[2];

                            ItemFrame rocketControlPanel = null;
                            ItemFrame flightControlPanel = null;
                            ItemFrame rocketModificationPanel = null;

                            for (Entity nearbyEntity : itemFrame.getWorld().getNearbyEntities(itemFrame.getLocation(), 2, 2, 2)) {
                                if (nearbyEntity.getType() == EntityType.GLOW_ITEM_FRAME && nearbyEntity.getCustomName() != null) {
                                    ItemFrame nearbyItemFrame = (ItemFrame) nearbyEntity;
                                    if (nearbyItemFrame.getCustomName().equals("Rocket Conrol Panel")) {
                                        rocketControlPanel = nearbyItemFrame;
                                    } else if (nearbyItemFrame.getCustomName().equals("Flight Control Panel")) {
                                        flightControlPanel = nearbyItemFrame;
                                    } else if (nearbyItemFrame.getCustomName().equals("Rocket Modification Panel")) {
                                        rocketModificationPanel = nearbyItemFrame;
                                    }
                                }
                            }

                            if (rocketControlPanel != null) {
                                if (rocketControlPanel.isVisible()) rocketControlPanel.setVisible(false);
                                BlockState signBlock = rocketControlPanel.getWorld().getBlockAt(rocketControlPanel.getLocation().getBlockX(), rocketControlPanel.getLocation().getBlockY(), rocketControlPanel.getLocation().getBlockZ()).getState();
                                BlockState block = rocketControlPanel.getWorld().getBlockAt(rocketControlPanel.getLocation().getBlockX(), rocketControlPanel.getLocation().getBlockY() - 1, rocketControlPanel.getLocation().getBlockZ()).getState();

                                if (block.getType() == Material.BARREL) {
                                    Container barrel = (Container) block;

                                    if (!barrel.getInventory().isEmpty()) {
                                        for (ItemStack itemStack : barrel.getInventory().getContents()) {
                                            if (itemStack != null) {
                                                currentStorage = 0;
                                                if (itemStack.getType() == Material.INK_SAC &&
                                                        itemStack.getItemMeta().hasCustomModelData() &&
                                                        itemStack.getItemMeta().getCustomModelData() == 1001) {

                                                    currentOil += itemStack.getAmount();
                                                    itemStack.setAmount(0);

                                                } else {
                                                    if (maxStorage <= currentStorage) {
                                                        rocketControlPanel.getWorld().dropItem(rocketControlPanel.getLocation(), itemStack);
                                                        itemStack.setAmount(0);
                                                    } else currentStorage += itemStack.getAmount();
                                                }
                                            }
                                        }
                                    }
                                }

                                if (signBlock instanceof Sign) {
                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
                                    sign.setGlowingText(true);
                                    sign.setColor(DyeColor.GREEN);

                                    sign.line(0, Component.text("Oil:    " + "0".repeat(10 - String.valueOf(currentOil).length()) + currentOil));
                                    sign.line(1, Component.text("Strg: " + "0".repeat(10 - String.valueOf(currentStorage).length()) + currentStorage));
                                    sign.line(2, Component.text("MaxO: " + "0".repeat(10 - String.valueOf(maxOil).length()) + maxOil));
                                    sign.line(3, Component.text("MaxS: " + "0".repeat(10 - String.valueOf(maxStorage).length()) + maxStorage));
                                    ((Sign) signBlock).update();
                                }
                            }

                            if (flightControlPanel != null) {
                                if (flightControlPanel.isVisible()) flightControlPanel.setVisible(false);
                                BlockState signBlock = flightControlPanel.getWorld().getBlockAt(flightControlPanel.getLocation().getBlockX(), flightControlPanel.getLocation().getBlockY(), flightControlPanel.getLocation().getBlockZ()).getState();

                                if (signBlock instanceof Sign) {
                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
                                    sign.setGlowingText(true);
                                    sign.setColor(DyeColor.GREEN);

                                    if (sign.getLine(0).split(" ").length == 3 &&
                                            !endPoint.equals(sign.getLine(0).split(" ")[2])) endPoint = sign.getLine(0).split(" ")[2];

                                    int distance = 0;
                                    for (String key : MehaniksSpaceWorldMapKeys) {
                                        if (sign.getLine(0).split(" ").length == 3 && Objects.equals(MehaniksSpaceWorldMap.get(key).get(0), sign.getLine(0).split(" ")[2])) {
                                            distance = Integer.parseInt(MehaniksSpaceWorldMap.get(key).get(3));
                                        }
                                    }

                                    sign.line(0, Component.text("End Point: " + endPoint));
                                    sign.line(2, Component.text("Dist:  " + "0".repeat(10 - String.valueOf(distance).length()) + distance));
                                    sign.line(3, Component.text("Oil:    " + "0".repeat(10 - String.valueOf(Math.round((float) distance /100)).length()) + Math.round((float) distance /100)));
                                    ((Sign) signBlock).update();
                                }
                            }

                            if (rocketModificationPanel != null) {
                                if (rocketModificationPanel.isVisible()) rocketModificationPanel.setVisible(false);
                                BlockState signBlock = rocketModificationPanel.getWorld().getBlockAt(rocketModificationPanel.getLocation().getBlockX(), rocketModificationPanel.getLocation().getBlockY(), rocketModificationPanel.getLocation().getBlockZ()).getState();

                                //
                                //
                                //

                                if (signBlock instanceof Sign) {
                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
                                    sign.setGlowingText(true);
                                    sign.setColor(DyeColor.GREEN);

                                    sign.line(0, Component.text("Modifications:"));
                                    sign.line(1, Component.text("-"));
                                    sign.line(2, Component.text("-"));
                                    sign.line(3, Component.text("-"));
                                    ((Sign) signBlock).update();
                                }
                            }

                            itemFrame.setItem(MehaniksSpaceItems.getRocket(maxOil, maxStorage, currentOil, currentStorage, endPoint));

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
                mainWorld = config.getString("mainWorld");
                seed = config.getInt("seed");
                for (String string : config.getStringList("fuelItems")) {
                    fuelItems.add(Material.getMaterial(string));
                }
                sender.sendMessage(ChatColor.GREEN + "MehaniksSpaceCore plugin successfully reloaded");
                return true;
            }
            if (args[0].equals("getitems")) {
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(1));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitLeggins());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitBoots());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(150, 150));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.DARK_GRAY, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, 10, 360, 0, 0));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getOil());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketNose());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketFuelTank());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketNozzle());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocket(2000, 0, 0, 0, null));
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketConrolPanel());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getFlightControlPanel());
                getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getRocketModificationPanel());
                return true;
            }
            if (args[0].equals("chestplate")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitChestplate(Integer.parseInt(args[1])));
            if (args[0].equals("tank")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            if (args[0].equals("helmet")) getServer().getPlayer(sender.getName()).getInventory().addItem(MehaniksSpaceItems.getIronSpaceSuitHelmet(Integer.parseInt(args[1])));
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
            sender.sendMessage(ChatColor.RED + "ms !null");
            return false;
        }
        return false;
    }
}
