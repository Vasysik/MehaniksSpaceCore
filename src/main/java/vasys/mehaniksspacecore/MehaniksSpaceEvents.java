package vasys.mehaniksspacecore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.BatchUpdateException;
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
            if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getItemMeta().hasCustomModelData() || player.getInventory().getChestplate().getItemMeta().getCustomModelData() != 1001 || Integer.parseInt(player.getInventory().getChestplate().getItemMeta().getLore().get(2).toString().split(" ")[1].toString().split("/")[0]) <= 0) {
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
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        player.setGravity(true);
        player.lockFreezeTicks(false);
    }
}
