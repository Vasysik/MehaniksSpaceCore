package vasys.mehaniksspacecore;

import static vasys.mehaniksspacecore.MehaniksSpaceCore.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

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
    public static void spaceSuitChestplateData(Player player, ItemMeta spaceSuitChestplateMeta, List<String> loreOld, int oxygen, int maxOxygen, int tanks, String tanksTypes) {
        String oxygenBar = "";
        int oxPercent = Math.round((float) (oxygen * 10) / maxOxygen);
        oxygenBar += ChatColor.BLUE + "■".repeat(oxPercent);
        int noOxPercent = 12 - oxygenBar.length();
        oxygenBar += ChatColor.DARK_GRAY + "■".repeat(noOxPercent);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "" + tanks + "/" + loreOld.get(0).split(" ")[0].split("/")[1] + " oxygen tanks");
        lore.add(ChatColor.WHITE + "[" + ChatColor.BLUE + "" + oxygenBar + "" + ChatColor.WHITE + "] " + oxygen + "/" + maxOxygen);
        lore.add(ChatColor.DARK_GRAY + "Tanks types: " + tanksTypes);
        spaceSuitChestplateMeta.setLore(lore);
        player.getInventory().getChestplate().setItemMeta(spaceSuitChestplateMeta);
    }

    public static void spaceSuitLegginsData(Player player, ItemMeta spaceSuitLegginsMeta, List<String> loreOld, int fuel, int maxFuel) {
        String fuelBar = "";
        int fuelPercent = Math.round((float) (fuel * 10) / fuel);
        fuelBar += ChatColor.BLUE + "■".repeat(fuelPercent);
        int noFuelPercent = 12 - fuelBar.length();
        fuelBar += ChatColor.DARK_GRAY + "■".repeat(noFuelPercent);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "[" + ChatColor.GOLD + "" + fuelBar + "" + ChatColor.WHITE + "] " + fuel + "/" + maxFuel);
        spaceSuitLegginsMeta.setLore(lore);
        player.getInventory().getLeggings().setItemMeta(spaceSuitLegginsMeta);
    }

    public static ItemFrame inActiveOxygenShield(Location location) {
        ItemFrame oxigenShieldItemFrame = null;
        for (Entity entity : location.getWorld().getNearbyEntities(location, 100, 100, 100)) {
            if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                double distance = entity.getLocation().distance(location);
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

    public static int getDistance(ItemFrame itemFrame, String endPoint) {
        int distance = 0;
        if (!endPoint.equals("none") && (MehaniksSpaceWorldMapKeys.contains(itemFrame.getWorld().getName()) || itemFrame.getWorld().getName().equals(mainWorld))) {
            if (endPoint.equals(mainWorld)) {
                if (itemFrame.getWorld().getName().equals(mainWorld)) distance = 0;
                else distance = Integer.parseInt(MehaniksSpaceWorldMap.get(itemFrame.getWorld().getName()).get(3));
            } else for (String key : MehaniksSpaceWorldMapKeys) {
                if (MehaniksSpaceWorldMap.get(key).get(0).equals(endPoint)) {
                    distance = Integer.parseInt(MehaniksSpaceWorldMap.get(key).get(3));
                    if (key.equals(itemFrame.getName()))
                        distance = 0;
                }
            }
        }
        return distance;
    }

    public static String getWorldName(ItemFrame itemFrame, String endPoint) {
        String worldName = "";
        if (!endPoint.equals("none") && (MehaniksSpaceWorldMapKeys.contains(itemFrame.getWorld().getName()) || itemFrame.getWorld().getName().equals(mainWorld))) {
            if (endPoint.equals(mainWorld)) {
                worldName = mainWorld;
            } else for (String key : MehaniksSpaceWorldMapKeys) {
                if (MehaniksSpaceWorldMap.get(key).get(0).equals(endPoint)) {
                    worldName = key;
                }
            }
        }
        return worldName;
    }

    public static void oxigenTankData(Player player, ItemMeta spaceSuitChestplateMeta, List<String> loreOld) {
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

        MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, oxygen, maxOxygen, tanks, tanksTypes);
    }

    public static ItemStack createHead(URL url, String name){
        UUID uuid = UUID.randomUUID();
        PlayerProfile profile = Bukkit.createProfile(uuid, name);
        PlayerTextures texture = profile.getTextures();
        texture.setSkin(url);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setPlayerProfile(profile);
        return head;
    }
}
