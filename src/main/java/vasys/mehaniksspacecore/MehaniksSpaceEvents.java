package vasys.mehaniksspacecore;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MehaniksSpaceEvents implements Listener {
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
//        event.getPlayer().sendMessage("from_world: " + event.getFrom().getName());
//        event.getPlayer().sendMessage("to_world: " + event.getPlayer().getWorld().getName());
//        event.getPlayer().sendMessage(MehaniksSpaceCore.MehaniksSpaceWorldList.toString());

        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, true, false));
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
        if (MehaniksSpaceCore.MehaniksSpaceWorldList.contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            if (player.getInventory().getHelmet() == null || !player.getInventory().getHelmet().getItemMeta().hasCustomModelData() || player.getInventory().getHelmet().getItemMeta().getCustomModelData() != 1001) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 63, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 31, true, false));
            } else {
                player.removePotionEffect(PotionEffectType.WITHER);
                player.removePotionEffect(PotionEffectType.CONFUSION);
            }

            if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().getItemMeta().hasCustomModelData() || player.getInventory().getChestplate().getItemMeta().getCustomModelData() != 1001 ||
                    player.getInventory().getLeggings() == null || !player.getInventory().getLeggings().getItemMeta().hasCustomModelData() || player.getInventory().getLeggings().getItemMeta().getCustomModelData() != 1001 ||
                    player.getInventory().getBoots() == null || !player.getInventory().getBoots().getItemMeta().hasCustomModelData() || player.getInventory().getBoots().getItemMeta().getCustomModelData() != 1001) {
                player.setFreezeTicks(10000);
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 31, true, false));
            } else {
                player.setFreezeTicks(0);
                player.removePotionEffect(PotionEffectType.WITHER);
            }
        }
    }
}
