package vasys.mehaniksspacecore;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.*;

public class MehaniksSpaceItems {

    public static void addRecipes() {
        ShapedRecipe ironOxygenHelmet = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet"), getIronSpaceSuitHelmet(0));
        ironOxygenHelmet.shape("*%*","%A%","mTm");
        ironOxygenHelmet.setIngredient('*', Material.COPPER_INGOT);
        ironOxygenHelmet.setIngredient('%', Material.IRON_BLOCK);
        ironOxygenHelmet.setIngredient('A', Material.IRON_HELMET);
        ironOxygenHelmet.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironOxygenHelmet.setIngredient('T', Material.TINTED_GLASS);
        getServer().addRecipe(ironOxygenHelmet);

        ShapedRecipe ironSpaceSuitChestplate = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate"), getIronSpaceSuitChestplate(1));
        ironSpaceSuitChestplate.shape("m%m","*A*","%m%");
        ironSpaceSuitChestplate.setIngredient('*', Material.COPPER_INGOT);
        ironSpaceSuitChestplate.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitChestplate.setIngredient('A', Material.IRON_CHESTPLATE);
        ironSpaceSuitChestplate.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitChestplate);

        ShapedRecipe ironSpaceSuitLeggins = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_leggings"), getIronSpaceSuitLeggins());
        ironSpaceSuitLeggins.shape("m%m","*A*","m%m");
        ironSpaceSuitLeggins.setIngredient('*', Material.COPPER_INGOT);
        ironSpaceSuitLeggins.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitLeggins.setIngredient('A', Material.IRON_LEGGINGS);
        ironSpaceSuitLeggins.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitLeggins);

        ShapedRecipe ironSpaceSuitBoots = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_boots"), getIronSpaceSuitBoots());
        ironSpaceSuitBoots.shape("m%m","*A*","i%i");
        ironSpaceSuitBoots.setIngredient('*', Material.COPPER_INGOT);
        ironSpaceSuitBoots.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitBoots.setIngredient('A', Material.IRON_BOOTS);
        ironSpaceSuitBoots.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitBoots.setIngredient( 'i', Material.IRON_INGOT);
        getServer().addRecipe(ironSpaceSuitBoots);

        ShapedRecipe ironOxygenTank = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank"), getIronOxygenTank(30, 30));
        ironOxygenTank.shape("i%i","%m%","*-*");
        ironOxygenTank.setIngredient( 'i', Material.IRON_INGOT);
        ironOxygenTank.setIngredient( '%', Material.TINTED_GLASS);
        ironOxygenTank.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironOxygenTank.setIngredient( '*', Material.COPPER_INGOT);
        ironOxygenTank.setIngredient( '-', Material.IRON_TRAPDOOR);
        getServer().addRecipe(ironOxygenTank);

        ShapedRecipe ironSpaceSuitChestplate2 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate_2"), getIronSpaceSuitChestplate(2));
        ironSpaceSuitChestplate2.shape("ini","T%T","c@c");
        ironSpaceSuitChestplate2.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate2.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitChestplate2.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitChestplate2.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitChestplate2.setIngredient( '%', Material.BARREL);
        ironSpaceSuitChestplate2.setIngredient('@', getIronSpaceSuitChestplate(1));
        getServer().addRecipe(ironSpaceSuitChestplate2);

        ShapedRecipe ironSpaceSuitChestplate3 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate_3"), getIronSpaceSuitChestplate(4));
        ironSpaceSuitChestplate3.shape("iTi","n%n","c@c");
        ironSpaceSuitChestplate3.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate3.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitChestplate3.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitChestplate3.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitChestplate3.setIngredient( '%', Material.BARREL);
        ironSpaceSuitChestplate3.setIngredient('@', getIronSpaceSuitChestplate(2));
        getServer().addRecipe(ironSpaceSuitChestplate3);

        ShapedRecipe ironSpaceSuitChestplate4 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate_4"), getIronSpaceSuitChestplate(6));
        ironSpaceSuitChestplate4.shape("ini","n%n","c@c");
        ironSpaceSuitChestplate4.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate4.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitChestplate4.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitChestplate4.setIngredient( '%', Material.BARREL);
        ironSpaceSuitChestplate4.setIngredient('@', getIronSpaceSuitChestplate(4));
        getServer().addRecipe(ironSpaceSuitChestplate4);

        ShapedRecipe ironSpaceSuitChestplate5 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate_5"), getIronSpaceSuitChestplate(8));
        ironSpaceSuitChestplate5.shape("ini","n%n","c@c");
        ironSpaceSuitChestplate5.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate5.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitChestplate5.setIngredient( 'c', Material.NETHERITE_SCRAP);
        ironSpaceSuitChestplate5.setIngredient( '%', Material.BARREL);
        ironSpaceSuitChestplate5.setIngredient('@', getIronSpaceSuitChestplate(6));
        getServer().addRecipe(ironSpaceSuitChestplate5);

        ShapedRecipe ironOxygenGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_generator"), getIronOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
        ironOxygenGenerator.shape("b%b","%B%","imi");
        ironOxygenGenerator.setIngredient('b', Material.BLAZE_POWDER);
        ironOxygenGenerator.setIngredient('%', Material.TINTED_GLASS);
        ironOxygenGenerator.setIngredient('B', Material.BUCKET);
        ironOxygenGenerator.setIngredient('i', Material.LIGHTNING_ROD);
        ironOxygenGenerator.setIngredient('m', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironOxygenGenerator);
    }

    public static ItemStack getIronSpaceSuitHelmet(Integer autoOxigenSpeed) {
        ItemStack spaceSuitHelmet = new ItemStack(Material.IRON_HELMET);
        ArmorMeta spaceSuitHelmetMeta = (ArmorMeta) spaceSuitHelmet.getItemMeta();

        if (autoOxigenSpeed < 1) {
            spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SILENCE));
        } else if (autoOxigenSpeed < 2) {
            spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        } else {
            spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));
        }

        spaceSuitHelmetMeta.setCustomModelData(1001);
        spaceSuitHelmetMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitHelmetMeta.setDisplayName(ChatColor.GRAY + "Space Suit Helmet");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "auto oxigen refill: " + autoOxigenSpeed);
        spaceSuitHelmetMeta.setLore(lore);
        spaceSuitHelmet.setItemMeta(spaceSuitHelmetMeta);
        return spaceSuitHelmet;
    }


    public static ItemStack getIronSpaceSuitChestplate(Integer max) {
        ItemStack spaceSuitChestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ArmorMeta spaceSuitChestplateMeta = (ArmorMeta) spaceSuitChestplate.getItemMeta();

        if (max < 4) {
            spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SILENCE));
        } else if (max < 8) {
            spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        } else {
            spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));
        }

        spaceSuitChestplateMeta.setCustomModelData(1001);
        spaceSuitChestplateMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitChestplateMeta.setDisplayName(ChatColor.GRAY + "Space Suit Chestplate");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "0/" + max + " oxygen tanks");
        lore.add(ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + "■".repeat(10) + ChatColor.WHITE + "] 0/0");
        lore.add(ChatColor.DARK_GRAY + "tanks types:");
        spaceSuitChestplateMeta.setLore(lore);
        spaceSuitChestplate.setItemMeta(spaceSuitChestplateMeta);
        return spaceSuitChestplate;
    }

    public static ItemStack getIronSpaceSuitLeggins() {
        ItemStack spaceSuitLeggins = new ItemStack(Material.IRON_LEGGINGS);
        ArmorMeta spaceSuitLegginsMeta = (ArmorMeta) spaceSuitLeggins.getItemMeta();
        spaceSuitLegginsMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitLegginsMeta.setCustomModelData(1001);
        spaceSuitLegginsMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitLegginsMeta.setDisplayName(ChatColor.GRAY + "Space Suit Leggins");
        spaceSuitLeggins.setItemMeta(spaceSuitLegginsMeta);
        return spaceSuitLeggins;
    }

    public static ItemStack getIronSpaceSuitBoots() {
        ItemStack spaceSuitBoots = new ItemStack(Material.IRON_BOOTS);
        ArmorMeta spaceSuitBootsMeta = (ArmorMeta) spaceSuitBoots.getItemMeta();
        spaceSuitBootsMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitBootsMeta.setCustomModelData(1001);
        spaceSuitBootsMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitBootsMeta.setDisplayName(ChatColor.GRAY + "Space Suit Boots");
        spaceSuitBoots.setItemMeta(spaceSuitBootsMeta);
        return spaceSuitBoots;
    }

    public static ItemStack getIronOxygenTank(Integer i, Integer max) {
        ItemStack oxygenTank = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta oxygenTankMeta = oxygenTank.getItemMeta();
        oxygenTankMeta.setCustomModelData(1001);
        oxygenTankMeta.setDisplayName(ChatColor.GRAY + "Oxygen Tank");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "volume: " + i + "/" + max);
        oxygenTankMeta.setLore(lore);
        oxygenTank.setItemMeta(oxygenTankMeta);
        return oxygenTank;
    }

    public static ItemStack getIronOxygenGenerator(ChatColor color, Integer oxygen, Integer copper) {
//        ItemStack oxygenGenerator = new ItemStack(Material.PLAYER_HEAD);
//        SkullMeta oxygenGeneratorMeta = (SkullMeta) oxygenGenerator.getItemMeta();
//
//        try {
//            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
//            PlayerTextures playerTextures = profile.getTextures();
//            playerTextures.setSkin(new URL("http://textures.minecraft.net/texture/0d61ab0136c69d7caddd566d2e9bcb35fdc5f9e3ca351e27362abf1374c4e58"));
//            profile.setTextures(playerTextures);
//            oxygenGeneratorMeta.setPlayerProfile(profile);
//        } catch (IllegalArgumentException | MalformedURLException e){getLogger().info("HEAD CAN'T DOWNLOAD");}
//
//        oxygenGeneratorMeta.setDisplayName(ChatColor.GRAY + "Oxygen Generator");
//        oxygenGenerator.setItemMeta(oxygenGeneratorMeta);

        ItemStack oxygenGenerator = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta oxygenGeneratorMeta = oxygenGenerator.getItemMeta();
        oxygenGeneratorMeta.setCustomModelData(1001);
        oxygenGeneratorMeta.setDisplayName(color + "Oxygen Generator " + oxygen + " " + copper);
        oxygenGenerator.setItemMeta(oxygenGeneratorMeta);
        return oxygenGenerator;
    }

}
