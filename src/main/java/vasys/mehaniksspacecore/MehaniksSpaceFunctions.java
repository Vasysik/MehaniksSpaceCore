package vasys.mehaniksspacecore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MehaniksSpaceFunctions {
    public static void itemFrameRotate(ItemFrame itemFrame) {
        if (itemFrame.getRotation() == Rotation.NONE) itemFrame.setRotation(Rotation.CLOCKWISE_45);
        else if (itemFrame.getRotation() == Rotation.CLOCKWISE_45) itemFrame.setRotation(Rotation.CLOCKWISE);
        else if (itemFrame.getRotation() == Rotation.CLOCKWISE) itemFrame.setRotation(Rotation.CLOCKWISE_135);
        else if (itemFrame.getRotation() == Rotation.CLOCKWISE_135) itemFrame.setRotation(Rotation.FLIPPED);
        else if (itemFrame.getRotation() == Rotation.FLIPPED) itemFrame.setRotation(Rotation.FLIPPED_45);
        else if (itemFrame.getRotation() == Rotation.FLIPPED_45) itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE);
        else if (itemFrame.getRotation() == Rotation.COUNTER_CLOCKWISE) itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
        else itemFrame.setRotation(Rotation.NONE);
    }
    public static void spaceSuitChestplateData(Player player, ItemMeta spaceSuitChestplateMeta, List<String> loreOld, List<String> lore, int oxygen, int maxOxygen, int tanks, String tanksTypes) {
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

    public static ItemFrame inActiveOxygenShield(Player player) {
        ItemFrame oxigenShieldItemFrame = null;
        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 100, 100, 100)) {
            if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                double distance = entity.getLocation().distance(player.getLocation());
                ItemFrame itemFrame = (ItemFrame) entity;
                if (itemFrame.getItem().getType() == Material.MAGMA_CREAM &&
                        itemFrame.getItem().getItemMeta().hasCustomModelData() &&
                        itemFrame.getItem().getItemMeta().getCustomModelData() == 1001) {
                    String name = itemFrame.getItem().getItemMeta().getDisplayName();
                    int oxygen = Integer.parseInt(name.split(" ")[3]);
                    int oil = Integer.parseInt(name.split(" ")[4]);
                    int maxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                    int oilMaxR = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[4]);
                    double sphereRadius = maxR;
                    if (oil < oilMaxR) sphereRadius *= (double) oil / oilMaxR;
                    if (distance <= sphereRadius && oxygen > 0) {
                        oxigenShieldItemFrame = itemFrame;
                    }
                }
            }
        }
        return oxigenShieldItemFrame;
    }
}