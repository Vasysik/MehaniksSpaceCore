package vasys.mehaniksspacecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MehaniksSpaceEvents implements Listener {
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
//        event.getPlayer().sendMessage("from_world: " + event.getFrom().getName());
//        event.getPlayer().sendMessage("to_world: " + event.getPlayer().getWorld().getName());
//        event.getPlayer().sendMessage(MehaniksSpaceCore.MehaniksSpaceWorldList.toString());

        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            int gravity = MehaniksSpaceCore.MehaniksSpaceGravityList.get(MehaniksSpaceCore.MehaniksSpaceWorldList.indexOf(event.getPlayer().getWorld().getName()));
            if (gravity != 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
            }
        }

        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getFrom().getName())) {
            Player player = event.getPlayer();
            player.lockFreezeTicks(false);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.removePotionEffect(PotionEffectType.WITHER);
            player.removePotionEffect(PotionEffectType.CONFUSION);
        }

    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
//        event.getPlayer().sendMessage("world: " + event.getPlayer().getWorld().getName());
//        event.getPlayer().sendMessage(MehaniksSpaceCore.MehaniksSpaceWorldList.toString());
        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getItemMeta().hasCustomModelData() ||
                    player.getInventory().getChestplate().getItemMeta().getCustomModelData() != 1001 ||
                    Integer.parseInt(player.getInventory().getChestplate().getItemMeta().getLore().get(1).toString().split(" ")[1].toString().split("/")[0]) <= 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 63, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, true, false));
            } else if (player.hasPotionEffect(PotionEffectType.POISON) && player.hasPotionEffect(PotionEffectType.CONFUSION)) {
                player.removePotionEffect(PotionEffectType.POISON);
                player.removePotionEffect(PotionEffectType.CONFUSION);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
            }

            if (player.getInventory().getHelmet() == null || !player.getInventory().getHelmet().getItemMeta().hasCustomModelData() || player.getInventory().getHelmet().getItemMeta().getCustomModelData() != 1001 ||
                    player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getItemMeta().hasCustomModelData() || player.getInventory().getChestplate().getItemMeta().getCustomModelData() != 1001 ||
                    player.getInventory().getLeggings() == null || !player.getInventory().getLeggings().getItemMeta().hasCustomModelData() || player.getInventory().getLeggings().getItemMeta().getCustomModelData() != 1001 ||
                    player.getInventory().getBoots() == null || !player.getInventory().getBoots().getItemMeta().hasCustomModelData() || player.getInventory().getBoots().getItemMeta().getCustomModelData() != 1001) {
                int temperature = MehaniksSpaceCore.MehaniksSpaceTemperatureList.get(MehaniksSpaceCore.MehaniksSpaceWorldList.indexOf(event.getPlayer().getWorld().getName()));
                if (temperature != 0) {
                    if (temperature < 0 && !player.isFreezeTickingLocked()) {
                        player.lockFreezeTicks(true);
                        player.setFreezeTicks(Math.abs(temperature) * 20);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, Math.abs(temperature)-1, true, false));
                    }
                }
            } else if (player.isFreezeTickingLocked()) {
                player.lockFreezeTicks(false);
                player.removePotionEffect(PotionEffectType.WITHER);
            }

        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            int gravity = MehaniksSpaceCore.MehaniksSpaceGravityList.get(MehaniksSpaceCore.MehaniksSpaceWorldList.indexOf(event.getPlayer().getWorld().getName()));
            if (gravity != 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
            }
        } else {
            Player player = event.getPlayer();
            player.setGravity(true);
            player.lockFreezeTicks(false);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.removePotionEffect(PotionEffectType.WITHER);
            player.removePotionEffect(PotionEffectType.CONFUSION);
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.CARROT_ON_A_STICK &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
                player.getInventory().getChestplate().getItemMeta().getCustomModelData() == 1001) {
            ItemMeta spaceSuitChestplateMeta = player.getInventory().getChestplate().getItemMeta();
            List<String> loreOld = spaceSuitChestplateMeta.getLore();

            if (Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[1]) > Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2)) && !player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[0].equals("0")) {
                String tanksTypes = "";
                if (loreOld.get(2).split(" ").length > 2) {
                    tanksTypes = loreOld.get(2).split(" ")[2] + "/";
                }
                tanksTypes += player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[1];

                int addOxygen = Integer.parseInt(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[0]);
                int addMaxOxygen = Integer.parseInt(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[1]);
                player.getInventory().setItemInMainHand(null);
                List<String> lore = new ArrayList<>();
                int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2)) + 1;
                int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]) + addOxygen;
                int maxOxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[1]) + addMaxOxygen;

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

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.IRON_CHESTPLATE &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001) {

            ItemMeta spaceSuitChestplateMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> loreOld = spaceSuitChestplateMeta.getLore();

            if (0 < Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2))) {
                List<String> lore = new ArrayList<>();
                String[] tanksList = loreOld.get(2).split(" ")[2].split("/");
                int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]);

                for (int i = 0; i <= tanksList.length - 1; i++) {
                    String maxOxygen;
                    if ( Integer.parseInt(tanksList[i]) <= oxygen) {
                        maxOxygen = tanksList[i];
                    } else {
                        maxOxygen = String.valueOf(oxygen);
                    }

                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(maxOxygen, tanksList[i]));
                    } else {
                        player.getWorld().dropItem(player.getLocation(), MehaniksSpaceItems.getIronOxygenTank(maxOxygen, tanksList[i]));
                    }
                }

                lore.add(ChatColor.WHITE + "" + 0 + "/" + loreOld.get(0).split(" ")[0].split("/")[1] + " oxygen tanks");
                lore.add(ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + "■".repeat(10) + ChatColor.WHITE + "] 0/0");
                lore.add(ChatColor.DARK_GRAY + "Tanks types: ");
                spaceSuitChestplateMeta.setLore(lore);
                player.getInventory().getItemInMainHand().setItemMeta(spaceSuitChestplateMeta);
            }
        } else if(player.getInventory().getItemInMainHand().getType() == Material.CARROT_ON_A_STICK &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001) {

            ItemMeta oxygenTankMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> loreOld = oxygenTankMeta.getLore();
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.WHITE + "volume: " + 0 + "/" + loreOld.get(0).split(" ")[1].split("/")[1]);
            oxygenTankMeta.setLore(lore);
            player.getInventory().getItemInMainHand().setItemMeta(oxygenTankMeta);
        }

    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME &&
                event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIREWORK_STAR &&
                event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Oxygen Generator") &&
                event.getPlayer().getWorld().getBlockAt(event.getRightClicked().getLocation().getBlockX(), event.getRightClicked().getLocation().getBlockY()-1, event.getRightClicked().getLocation().getBlockZ()).getType() == Material.BARREL) {
            event.getRightClicked().setCustomNameVisible(true);
            event.getRightClicked().setCustomName(ChatColor.GRAY + "Oxygen Generator");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        player.setGravity(true);
        player.lockFreezeTicks(false);
    }
}
