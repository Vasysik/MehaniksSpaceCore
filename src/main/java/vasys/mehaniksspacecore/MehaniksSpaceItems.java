package vasys.mehaniksspacecore;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class MehaniksSpaceItems {

    public static void addRecipes() {
        ShapedRecipe ironOxygenHelmet = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet"), getIronSpaceSuitHelmet());
        ironOxygenHelmet.shape("*%*","%A%","mTm");
        ironOxygenHelmet.setIngredient('*', Material.NETHERITE_INGOT);
        ironOxygenHelmet.setIngredient('%', Material.IRON_BLOCK);
        ironOxygenHelmet.setIngredient('A', Material.IRON_HELMET);
        ironOxygenHelmet.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironOxygenHelmet.setIngredient('T', Material.TINTED_GLASS);
        getServer().addRecipe(ironOxygenHelmet);

        ShapedRecipe ironSpaceSuitChestplate = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate"), getIronSpaceSuitChestplate());
        ironSpaceSuitChestplate.shape("m%m","*A*","%m%");
        ironSpaceSuitChestplate.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitChestplate.setIngredient('A', Material.IRON_CHESTPLATE);
        ironSpaceSuitChestplate.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitChestplate);

        ShapedRecipe ironSpaceSuitLeggins = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_leggings"), getIronSpaceSuitLeggins());
        ironSpaceSuitLeggins.shape("m%m","*A*","m%m");
        ironSpaceSuitLeggins.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitLeggins.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitLeggins.setIngredient('A', Material.IRON_LEGGINGS);
        ironSpaceSuitLeggins.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitLeggins);

        ShapedRecipe ironSpaceSuitBoots = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_boots"), getIronSpaceSuitBoots());
        ironSpaceSuitBoots.shape("m%m","*A*","i%i");
        ironSpaceSuitBoots.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitBoots.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitBoots.setIngredient('A', Material.IRON_BOOTS);
        ironSpaceSuitBoots.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitBoots.setIngredient( 'i', Material.IRON_INGOT);
        getServer().addRecipe(ironSpaceSuitBoots);
    }

    public static ItemStack getIronSpaceSuitHelmet() {
        ItemStack spaceSuitHelmet = new ItemStack(Material.IRON_HELMET);
        ArmorMeta spaceSuitHelmetMeta = (ArmorMeta) spaceSuitHelmet.getItemMeta();
        spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitHelmetMeta.setCustomModelData(1001);
        spaceSuitHelmetMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitHelmetMeta.setDisplayName(ChatColor.GRAY + "Space Suit Helmet");
        spaceSuitHelmet.setItemMeta(spaceSuitHelmetMeta);
        return spaceSuitHelmet;
    }

    public static ItemStack getIronSpaceSuitChestplate() {
        ItemStack spaceSuitChestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ArmorMeta spaceSuitChestplateMeta = (ArmorMeta) spaceSuitChestplate.getItemMeta();
        spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitChestplateMeta.setCustomModelData(1001);
        spaceSuitChestplateMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitChestplateMeta.setDisplayName(ChatColor.GRAY + "Space Suit Chestplate");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "0/2 oxygen tanks");
        lore.add(ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + "■".repeat(10) + ChatColor.WHITE + "] 0/0");
        lore.add(ChatColor.DARK_GRAY + "Tanks types:");
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

    public static ItemStack getIronOxygenTank() {
        ItemStack oxygenTank = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta oxygenTankMeta = oxygenTank.getItemMeta();
        oxygenTankMeta.setCustomModelData(1001);
        oxygenTankMeta.setDisplayName(ChatColor.GRAY + "Oxygen Tank");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "type: 1");
        lore.add(ChatColor.WHITE + "volume: 60");
        oxygenTankMeta.setLore(lore);
        oxygenTank.setItemMeta(oxygenTankMeta);
        return oxygenTank;
    }

}
