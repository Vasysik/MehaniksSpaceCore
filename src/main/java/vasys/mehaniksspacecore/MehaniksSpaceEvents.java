package vasys.mehaniksspacecore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        if(player.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SCRAP &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
                player.getInventory().getChestplate().getItemMeta().getCustomModelData() == 1001) {
            ItemMeta spaceSuitChestplateMeta = player.getInventory().getChestplate().getItemMeta();
            List<String> loreOld = spaceSuitChestplateMeta.getLore();
            if (Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[1]) > Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2))) {
                player.getInventory().remove(MehaniksSpaceItems.getIronOxygenTank());
                List<String> lore = new ArrayList<>();

                Integer tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2)) + 1;
                Integer oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]) + 60;
                Integer maxOxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[1]) + 60;
                String tanksTypes = "1";
                if (loreOld.get(2).split(" ").length > 2) {
                    tanksTypes = loreOld.get(2).split(" ")[2] + "1";
                }

                String oxygenBar = "";
                Integer oxPercent = (oxygen / maxOxygen) * 10;
                oxygenBar += "#".repeat(oxPercent);
                Integer noOxPercent = 10 - oxygenBar.length();
                oxygenBar += "#".repeat(noOxPercent);

                lore.add(ChatColor.WHITE + "" + tanks + "/" + loreOld.get(0).split(" ")[0].split("/")[1] + " oxygen tanks");
                lore.add(ChatColor.WHITE + "[" + ChatColor.BLUE + "" + oxygenBar + "" + ChatColor.WHITE + "] " + oxygen + "/" + maxOxygen);
                lore.add(ChatColor.DARK_GRAY + "Tanks types: " + tanksTypes);
                spaceSuitChestplateMeta.setLore(lore);
                player.getInventory().getChestplate().setItemMeta(spaceSuitChestplateMeta);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        player.setGravity(true);
        player.lockFreezeTicks(false);
    }
}
