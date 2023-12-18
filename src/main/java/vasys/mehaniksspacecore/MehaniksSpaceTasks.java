package vasys.mehaniksspacecore;

import static org.bukkit.Bukkit.*;
import static vasys.mehaniksspacecore.MehaniksSpaceCore.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;

public class MehaniksSpaceTasks {
    public static Plugin plugin = MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class);

    public static void playerOxygenTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World w : getServer().getWorlds()) {
                for (Player player : w.getPlayers()) {
                    if (MehaniksSpaceWorldMap.containsKey(w.getName())) {
                        int gravity = Integer.parseInt(MehaniksSpaceWorldMap.get(player.getWorld().getName()).get(1));
                        if (!player.hasPotionEffect(PotionEffectType.JUMP) && gravity != 0) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
                        }

                        ItemFrame oxigenShieldItemFrame = MehaniksSpaceFunctions.inActiveOxygenShield(player.getLocation());
                        if (oxigenShieldItemFrame != null) {
                            String name = oxigenShieldItemFrame.getItem().getItemMeta().getDisplayName();
                            int oxygen = Integer.parseInt(name.split(" ")[3]) - 1;
                            int oil = Integer.parseInt(name.split(" ")[4]);
                            int maxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                            int oilMaxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);
                            oxigenShieldItemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
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
                                MehaniksSpaceFunctions.oxigenTankData(player, spaceSuitChestplateMeta, loreOld);

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
                            MehaniksSpaceFunctions.oxigenTankData(player, spaceSuitChestplateMeta, loreOld);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 5, 1, true, false));
                        }
                    }
                }
            }
        }, 20, 20);
    }

    public static void playerTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World w : getServer().getWorlds()) {
                for (Player player : w.getPlayers()) {
                    ItemFrame oxigenShieldItemFrame = MehaniksSpaceFunctions.inActiveOxygenShield(player.getLocation());
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

                            oxigenShieldItemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygenS, oil));
                        } else {
                            if (maxOxygen - oxygen >= autoOxigenSpeed) oxygen += autoOxigenSpeed;
                            else oxygen = maxOxygen;
                        }

                        int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2));
                        String tanksTypes = loreOld.get(2).split(" ")[2];

                        MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, oxygen, maxOxygen, tanks, tanksTypes);
                    }

//                    if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR) &&
//                            player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasCustomModelData() &&
//                            player.getInventory().getLeggings().getItemMeta().getCustomModelData() == 1001 &&
//                            Integer.parseInt(player.getInventory().getLeggings().getLore().get(0).split(" ")[1].split("/")[0]) > 0) {
//                        if (!player.getAllowFlight()) player.setAllowFlight(true);
//                        else {
//                            ItemStack leggins = player.getInventory().getLeggings();
//                            int fuel = Integer.parseInt(player.getInventory().getLeggings().getLore().get(0).split(" ")[1].split("/")[0]);
//                            int maxFuel = Integer.parseInt(player.getInventory().getLeggings().getLore().get(0).split(" ")[1].split("/")[1]);
//                            if (fuel > 0)
//                                MehaniksSpaceFunctions.spaceSuitLegginsData(player, leggins.getItemMeta(), leggins.getLore(), fuel - 10, maxFuel);
//                        }
//                    }
//                    else player.setAllowFlight(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR));
                }
            }
        }, 20, 20);
    }

    public static void entityTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType().isAlive() && MehaniksSpaceWorldMap.containsKey(world.getName())) {
                        LivingEntity livingEntity = (LivingEntity) entity;

                        int gravity = Integer.parseInt(MehaniksSpaceWorldMap.get(entity.getWorld().getName()).get(1));
                        if (!livingEntity.hasPotionEffect(PotionEffectType.JUMP) && gravity != 0) {
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
                        }

                        if (entity instanceof Animals || entity instanceof Ambient || entity instanceof WaterMob) {
                            ItemFrame oxigenShieldItemFrame = MehaniksSpaceFunctions.inActiveOxygenShield(entity.getLocation());
                            if (oxigenShieldItemFrame != null) {
                                String name = oxigenShieldItemFrame.getItem().getItemMeta().getDisplayName();
                                int oxygen = Integer.parseInt(name.split(" ")[3]) - 1;
                                int oil = Integer.parseInt(name.split(" ")[4]);
                                int maxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                                int oilMaxR = Integer.parseInt(oxigenShieldItemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);
                                oxigenShieldItemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
                                if (livingEntity.hasPotionEffect(PotionEffectType.POISON) && livingEntity.hasPotionEffect(PotionEffectType.CONFUSION)) {
                                    livingEntity.removePotionEffect(PotionEffectType.POISON);
                                    livingEntity.removePotionEffect(PotionEffectType.CONFUSION);
                                    livingEntity.removePotionEffect(PotionEffectType.BLINDNESS);
                                }
                            } else {
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 63, true, false));
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
                            }

                            if (oxigenShieldItemFrame == null){
                                int temperature = Integer.parseInt(MehaniksSpaceWorldMap.get(entity.getWorld().getName()).get(2));
                                if (temperature != 0) {
                                    if (temperature < 0 && !entity.isFreezeTickingLocked()) {
                                        entity.lockFreezeTicks(true);
                                        entity.setFreezeTicks(Math.abs(temperature) * 20);
                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, Math.abs(temperature) - 1, true, false));
                                    }
                                }
                            } else if (entity.isFreezeTickingLocked()) {
                                livingEntity.lockFreezeTicks(false);
                                livingEntity.removePotionEffect(PotionEffectType.WITHER);
                            }
                        }
                    }
                }
            }
        }, 100, 100);
    }

    public static void oilGeneratorTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Oil Generator") &&
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

                                if (oil >= 35) {
                                    oil -= 35;
                                    barrel.getInventory().addItem(MehaniksSpaceItems.getOil());

                                    if (oil == 0 || fuel == 0) itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.GRAY, oil, fuel));
                                    else itemFrame.setItem(MehaniksSpaceItems.getIronOilGenerator(ChatColor.BLACK, oil, fuel));
                                }
                            } else {
                                itemFrame.remove();
                                itemFrame.getWorld().createExplosion(itemFrame, 1);
                            }

                        }
                    }
                }
            }
        }, 40, 40);
    }

    public static void oxygenGeneratorTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Oxygen Generator") &&
                                itemFrame.getItem().getType() == Material.COPPER_INGOT) {
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
                                    if (oxygen <= 0 || copper <= 0) itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
                                    else itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.BLUE, oxygen, copper));
                                } else if (barrel.getInventory().contains(Material.CARROT_ON_A_STICK)) {
                                    for (ItemStack item : barrel.getInventory().getContents()) {
                                        if (item != null && item.getType() == Material.CARROT_ON_A_STICK &&
                                                item.getItemMeta().hasCustomModelData()) {
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

                                                if (oxygen <= 0 || copper <= 0) itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
                                                else itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.BLUE, oxygen, copper));
                                            }
                                        }
                                    }
                                }
                            } else {
                                itemFrame.remove();
                                itemFrame.getWorld().createExplosion(itemFrame, 2);
                            }
                        }
                    }
                }
            };
        }, 100, 100);
    }

    public static void oxygenShieldTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Oxygen Shield Generator") &&
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
                                                    item.getItemMeta().hasCustomModelData() &&
                                                    item.getItemMeta().getCustomModelData() / 1000 == 1) {
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
                                            if (oxygen <= 0 || oil <= 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, maxR, oilMaxR, oxygen, oil));
                                            else itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
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
                                if (oxygen <= 0 || oil <= 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, maxR, oilMaxR, oxygen, oil));
                                else itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
                            }
                        }
                    }
                }
            };
        }, 100, 100);
    }

    public static void rocketTask() {
        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        if (itemFrame.getName().equals("Rocket") &&
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

                                    currentStorage = 0;
                                    if (!barrel.getInventory().isEmpty()) {
                                        for (ItemStack itemStack : barrel.getInventory().getContents()) {
                                            if (itemStack != null) {
                                                if (itemStack.getType() == Material.INK_SAC &&
                                                        itemStack.getItemMeta().hasCustomModelData() &&
                                                        itemStack.getItemMeta().getCustomModelData() == 1001) {
                                                    if  (Math.round((float) MehaniksSpaceFunctions.getDistance(itemFrame, endPoint)/100) > currentOil) {
                                                        currentOil += itemStack.getAmount();
                                                        itemStack.setAmount(0);
                                                    }
                                                } else {
                                                    if (maxStorage < currentStorage + itemStack.getAmount() || Arrays.stream(barrel.getInventory().getContents()).filter(x -> x != null).toList().size() + 1 >= barrel.getInventory().getSize()) {
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
                                    int distance = MehaniksSpaceFunctions.getDistance(itemFrame, endPoint);

                                    sign.line(0, Component.text("End Point: " + endPoint));
                                    sign.line(2, Component.text("Dist:  " + "0".repeat(10 - String.valueOf(distance).length()) + distance));
                                    sign.line(3, Component.text("Oil:    " + "0".repeat(10 - String.valueOf(Math.round((float) distance/100)).length()) + Math.round((float) distance /100)));
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

                        }
                    }
                }
            };
        }, 100, 100);
    }

    public static void meteorWorldsTask() {
        for (World world : getServer().getWorlds()) {
            if (MeteorWorldMap.containsKey(world.getName())) {
                
            }
        }
    }

    public static void meteorSpawnTask(World world, Integer minExplosionPower, Integer maxExplosionPower, Integer minSpawnDelay, Integer maxSpawnDelay, List<String> types) {
        Random random = new Random();
        for (Chunk chunk : world.getLoadedChunks()) {
            
        }
        getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {meteorSpawnTask(world, minExplosionPower, maxExplosionPower, minSpawnDelay, maxSpawnDelay, types);}, random.nextInt((maxSpawnDelay - minSpawnDelay) + 1) + minSpawnDelay);
    }

//    public static void other() {
//        getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
//            for (World world : getServer().getWorlds()) {
//                for (Entity entity : world.getEntities()) {
//                    if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
//                        ItemFrame itemFrame = (ItemFrame) entity;
//                        if (itemFrame.getName().equals("Oxygen Generator") &&
//                                itemFrame.getItem().getType() == Material.COPPER_INGOT) {
//                            if (itemFrame.isVisible()) itemFrame.setVisible(false);
//
//                            BlockState block = itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState();
//
//                            if (block.getType() == Material.BARREL) {
//                                Container barrel = (Container) block;
//
//                                if (barrel.getInventory().contains(Material.WATER_BUCKET) || barrel.getInventory().contains(Material.COPPER_INGOT)) {
//                                    String name = itemFrame.getItem().getItemMeta().getDisplayName();
//                                    int oxygen = Integer.parseInt(name.split(" ")[2]);
//                                    int copper = Integer.parseInt(name.split(" ")[3]);
//                                    for (ItemStack i : barrel.getInventory().getContents()) {
//                                        if (i != null && i.getType() == Material.WATER_BUCKET) {
//                                            oxygen += 600;
//                                            i.setType(Material.BUCKET);
//                                        }
//                                        if (i != null && i.getType() == Material.COPPER_INGOT) {
//                                            copper += 30 * i.getAmount();
//                                            i.setType(Material.GRAY_DYE);
//                                        }
//                                    }
//                                    if (oxygen <= 0 || copper <= 0) itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
//                                    else itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.BLUE, oxygen, copper));
//                                } else if (barrel.getInventory().contains(Material.CARROT_ON_A_STICK)) {
//                                    for (ItemStack item : barrel.getInventory().getContents()) {
//                                        if (item != null && item.getType() == Material.CARROT_ON_A_STICK &&
//                                                item.getItemMeta().hasCustomModelData()) {
//                                            ItemMeta itemMeta = item.getItemMeta();
//                                            String name = itemFrame.getItem().getItemMeta().getDisplayName();
//                                            List<String> loreOld = itemMeta.getLore();
//                                            List<String> lore = new ArrayList<>();
//                                            int min = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
//                                            int max = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);
//                                            int oxygen = Integer.parseInt(name.split(" ")[2]) - 1;
//                                            int copper = Integer.parseInt(name.split(" ")[3]) - 5;
//
//                                            if (min < max && oxygen >= 0 && copper >= 0) {
//                                                min += 5;
//                                                lore.add(ChatColor.WHITE + "volume: " + min + "/" + max);
//                                                itemMeta.setLore(lore);
//                                                item.setItemMeta(itemMeta);
//
//                                                MehaniksSpaceFunctions.itemFrameRotate(itemFrame);
//
//                                                world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_BREATH, 0.5f, 1f);
//
//                                                if (oxygen <= 0 || copper <= 0) itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.DARK_GRAY, oxygen, copper));
//                                                else itemFrame.setItem(MehaniksSpaceItems.getCopperOxygenGenerator(ChatColor.BLUE, oxygen, copper));
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                itemFrame.remove();
//                                itemFrame.getWorld().createExplosion(itemFrame, 2);
//                            }
//                        } else if (itemFrame.getName().equals("Oxygen Shield Generator") &&
//                                itemFrame.getItem().getType() == Material.MAGMA_CREAM) {
//                            if (itemFrame.isVisible()) itemFrame.setVisible(false);
//
//                            String name = itemFrame.getItem().getItemMeta().getDisplayName();
//
//                            int oxygen = Integer.parseInt(name.split(" ")[3]);
//                            int oil = Integer.parseInt(name.split(" ")[4]);
//                            int maxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
//                            int oilMaxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);
//
//                            List<Container> containers = new ArrayList<Container>();
//                            int genX = itemFrame.getLocation().getBlockX();
//                            int genY = itemFrame.getLocation().getBlockY()-1;
//                            int genZ = itemFrame.getLocation().getBlockZ();
//
//
//                            if (itemFrame.getWorld().getBlockAt(genX + 1, genY, genZ).getState() instanceof InventoryHolder) {
//                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX + 1, genY, genZ).getState());
//                            }
//                            if (itemFrame.getWorld().getBlockAt(genX - 1, genY, genZ).getState() instanceof InventoryHolder) {
//                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX - 1, genY, genZ).getState());
//                            }
//                            if (itemFrame.getWorld().getBlockAt(genX, genY, genZ + 1).getState() instanceof InventoryHolder) {
//                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX, genY, genZ + 1).getState());
//                            }
//                            if (itemFrame.getWorld().getBlockAt(genX, genY, genZ - 1).getState() instanceof InventoryHolder) {
//                                containers.add((Container) itemFrame.getWorld().getBlockAt(genX, genY, genZ - 1).getState());
//                            }
//
//                            if (containers.size() != 0) {
//                                for (Container container : containers) {
//                                    if (container.getInventory().contains(Material.CARROT_ON_A_STICK) || container.getInventory().contains(Material.INK_SAC)) {
//                                        for (ItemStack item : container.getInventory().getContents()) {
//                                            if (item != null &&
//                                                    item.getType().equals(Material.CARROT_ON_A_STICK) &&
//                                                    item.getItemMeta().hasCustomModelData() &&
//                                                    item.getItemMeta().getCustomModelData() / 1000 == 1) {
//                                                ItemMeta itemMeta = item.getItemMeta();
//                                                List<String> loreOld = itemMeta.getLore();
//                                                List<String> lore = new ArrayList<>();
//                                                int min = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
//                                                int max = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);
//
//                                                if (min > 0) {
//                                                    oxygen += min;
//                                                    min = 0;
//                                                    lore.add(ChatColor.WHITE + "volume: " + min + "/" + max);
//                                                    itemMeta.setLore(lore);
//                                                    item.setItemMeta(itemMeta);
//                                                }
//                                            } else if (item != null && item.getType().equals(Material.INK_SAC) &&
//                                                    item.getItemMeta().hasCustomModelData() &&
//                                                    item.getItemMeta().getCustomModelData() == 1001) {
//                                                oil += 120 * item.getAmount();
//                                                item.setAmount(0);
//                                            }
//                                            if (oxygen <= 0 || oil <= 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, maxR, oilMaxR, oxygen, oil));
//                                            else itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
//                                        }
//                                    }
//                                }
//                            }
//                            Location sphereL = itemFrame.getLocation();
//                            double sphereRadius = maxR;
//                            if(oil < oilMaxR) sphereRadius *= (double) oil/oilMaxR;
//
//                            if (oil > 0) {
//                                oil -= 1;
//                                for(double phi=0; phi<=Math.PI; phi+=Math.PI/15) {
//                                    for(double theta=0; theta<=2*Math.PI; theta+=Math.PI/30) {
//                                        double x = sphereRadius*Math.cos(theta)*Math.sin(phi);
//                                        double y = sphereRadius*Math.cos(phi);
//                                        double z = sphereRadius*Math.sin(theta)*Math.sin(phi);
//
//                                        sphereL.add(x,y,z);
//                                        itemFrame.getWorld().spawnParticle(Particle.END_ROD, sphereL, 1, 0F, 0F, 0F, 0.001);
//                                        sphereL.subtract(x, y, z);
//                                    }
//                                }
//                                if (oxygen <= 0 || oil <= 0) itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, maxR, oilMaxR, oxygen, oil));
//                                else itemFrame.setItem(MehaniksSpaceItems.getIronOxygenShieldGenerator(ChatColor.BLUE, maxR, oilMaxR, oxygen, oil));
//                            }
//                        } else if (itemFrame.getName().equals("Rocket") &&
//                                itemFrame.getItem().getType() == Material.NETHERITE_SCRAP &&
//                                itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState().getType() == Material.LODESTONE) {
//                            if (itemFrame.isVisible()) itemFrame.setVisible(false);
//
//                            int maxOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
//                            int maxStorage = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[2]);
//                            int currentOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(2).split(" ")[2]);
//                            int currentStorage = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(3).split(" ")[2]);
//                            String endPoint = itemFrame.getItem().getItemMeta().getLore().get(4).split(" ")[2];
//
//                            ItemFrame rocketControlPanel = null;
//                            ItemFrame flightControlPanel = null;
//                            ItemFrame rocketModificationPanel = null;
//
//                            for (Entity nearbyEntity : itemFrame.getWorld().getNearbyEntities(itemFrame.getLocation(), 2, 2, 2)) {
//                                if (nearbyEntity.getType() == EntityType.GLOW_ITEM_FRAME && nearbyEntity.getCustomName() != null) {
//                                    ItemFrame nearbyItemFrame = (ItemFrame) nearbyEntity;
//                                    if (nearbyItemFrame.getCustomName().equals("Rocket Conrol Panel")) {
//                                        rocketControlPanel = nearbyItemFrame;
//                                    } else if (nearbyItemFrame.getCustomName().equals("Flight Control Panel")) {
//                                        flightControlPanel = nearbyItemFrame;
//                                    } else if (nearbyItemFrame.getCustomName().equals("Rocket Modification Panel")) {
//                                        rocketModificationPanel = nearbyItemFrame;
//                                    }
//                                }
//                            }
//
//                            if (rocketControlPanel != null) {
//                                if (rocketControlPanel.isVisible()) rocketControlPanel.setVisible(false);
//                                BlockState signBlock = rocketControlPanel.getWorld().getBlockAt(rocketControlPanel.getLocation().getBlockX(), rocketControlPanel.getLocation().getBlockY(), rocketControlPanel.getLocation().getBlockZ()).getState();
//                                BlockState block = rocketControlPanel.getWorld().getBlockAt(rocketControlPanel.getLocation().getBlockX(), rocketControlPanel.getLocation().getBlockY() - 1, rocketControlPanel.getLocation().getBlockZ()).getState();
//
//                                if (block.getType() == Material.BARREL) {
//                                    Container barrel = (Container) block;
//
//                                    currentStorage = 0;
//                                    if (!barrel.getInventory().isEmpty()) {
//                                        for (ItemStack itemStack : barrel.getInventory().getContents()) {
//                                            if (itemStack != null) {
//                                                if (itemStack.getType() == Material.INK_SAC &&
//                                                        itemStack.getItemMeta().hasCustomModelData() &&
//                                                        itemStack.getItemMeta().getCustomModelData() == 1001) {
//                                                    if  (Math.round((float) MehaniksSpaceFunctions.getDistance(itemFrame, endPoint)/100) > currentOil) {
//                                                        currentOil += itemStack.getAmount();
//                                                        itemStack.setAmount(0);
//                                                    }
//                                                } else {
//                                                    if (maxStorage < currentStorage + itemStack.getAmount() || Arrays.stream(barrel.getInventory().getContents()).filter(x -> x != null).toList().size() + 1 >= barrel.getInventory().getSize()) {
//                                                        rocketControlPanel.getWorld().dropItem(rocketControlPanel.getLocation(), itemStack);
//                                                        itemStack.setAmount(0);
//                                                    } else currentStorage += itemStack.getAmount();
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//
//                                if (signBlock instanceof Sign) {
//                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
//                                    sign.setGlowingText(true);
//                                    sign.setColor(DyeColor.GREEN);
//
//                                    sign.line(0, Component.text("Oil:    " + "0".repeat(10 - String.valueOf(currentOil).length()) + currentOil));
//                                    sign.line(1, Component.text("Strg: " + "0".repeat(10 - String.valueOf(currentStorage).length()) + currentStorage));
//                                    sign.line(2, Component.text("MaxO: " + "0".repeat(10 - String.valueOf(maxOil).length()) + maxOil));
//                                    sign.line(3, Component.text("MaxS: " + "0".repeat(10 - String.valueOf(maxStorage).length()) + maxStorage));
//                                    ((Sign) signBlock).update();
//                                }
//                            }
//
//                            if (flightControlPanel != null) {
//                                if (flightControlPanel.isVisible()) flightControlPanel.setVisible(false);
//                                BlockState signBlock = flightControlPanel.getWorld().getBlockAt(flightControlPanel.getLocation().getBlockX(), flightControlPanel.getLocation().getBlockY(), flightControlPanel.getLocation().getBlockZ()).getState();
//
//                                if (signBlock instanceof Sign) {
//                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
//                                    sign.setGlowingText(true);
//                                    sign.setColor(DyeColor.GREEN);
//
//                                    if (sign.getLine(0).split(" ").length == 3 &&
//                                            !endPoint.equals(sign.getLine(0).split(" ")[2])) endPoint = sign.getLine(0).split(" ")[2];
//                                    int distance = MehaniksSpaceFunctions.getDistance(itemFrame, endPoint);
//
//                                    sign.line(0, Component.text("End Point: " + endPoint));
//                                    sign.line(2, Component.text("Dist:  " + "0".repeat(10 - String.valueOf(distance).length()) + distance));
//                                    sign.line(3, Component.text("Oil:    " + "0".repeat(10 - String.valueOf(Math.round((float) distance/100)).length()) + Math.round((float) distance /100)));
//                                    ((Sign) signBlock).update();
//                                }
//                            }
//
//                            if (rocketModificationPanel != null) {
//                                if (rocketModificationPanel.isVisible()) rocketModificationPanel.setVisible(false);
//                                BlockState signBlock = rocketModificationPanel.getWorld().getBlockAt(rocketModificationPanel.getLocation().getBlockX(), rocketModificationPanel.getLocation().getBlockY(), rocketModificationPanel.getLocation().getBlockZ()).getState();
//
//                                //
//                                //
//                                //
//
//                                if (signBlock instanceof Sign) {
//                                    SignSide sign = ((Sign) signBlock).getSide(Side.FRONT);
//                                    sign.setGlowingText(true);
//                                    sign.setColor(DyeColor.GREEN);
//
//                                    sign.line(0, Component.text("Modifications:"));
//                                    sign.line(1, Component.text("-"));
//                                    sign.line(2, Component.text("-"));
//                                    sign.line(3, Component.text("-"));
//                                    ((Sign) signBlock).update();
//                                }
//                            }
//
//                            itemFrame.setItem(MehaniksSpaceItems.getRocket(maxOil, maxStorage, currentOil, currentStorage, endPoint));
//
//                        } else if (!itemFrame.isVisible() && (itemFrame.getName().equals("Oxygen Generator") ||  itemFrame.getName().equals("Oxygen Shield Generator"))) {
//                            itemFrame.setCustomName("");
//                            itemFrame.setVisible(true);
//                        }
//                    }
//                }
//            };
//        }, 100, 100);
//    }
}
