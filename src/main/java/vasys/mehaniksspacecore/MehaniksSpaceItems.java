package vasys.mehaniksspacecore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import static org.bukkit.Bukkit.getServer;

public class MehaniksSpaceItems {

    public static void addRecipes() {
        ShapedRecipe ironOxygenHelmet = new ShapedRecipe(getIronOxygenHelmet());
        ironOxygenHelmet.shape("*%*","%A%","mTm");
        ironOxygenHelmet.setIngredient('*', Material.NETHERITE_INGOT);
        ironOxygenHelmet.setIngredient('%', Material.IRON_BLOCK);
        ironOxygenHelmet.setIngredient('A', Material.IRON_HELMET);
        ironOxygenHelmet.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironOxygenHelmet.setIngredient('T', Material.TINTED_GLASS);
        getServer().addRecipe(ironOxygenHelmet);

        ShapedRecipe ironSpaceSuitChestplate = new ShapedRecipe(getIronOxygenHelmet());
        ironSpaceSuitChestplate.shape("m%m","*A*","%m%");
        ironSpaceSuitChestplate.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitChestplate.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitChestplate.setIngredient('A', Material.IRON_CHESTPLATE);
        ironSpaceSuitChestplate.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitChestplate);

        ShapedRecipe ironSpaceSuitLeggins = new ShapedRecipe(getIronOxygenHelmet());
        ironSpaceSuitLeggins.shape("m%m","*A*","m%m");
        ironSpaceSuitLeggins.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitLeggins.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitLeggins.setIngredient('A', Material.IRON_LEGGINGS);
        ironSpaceSuitLeggins.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitLeggins);

        ShapedRecipe ironSpaceSuitBoots = new ShapedRecipe(getIronOxygenHelmet());
        ironSpaceSuitBoots.shape("m%m","*A*","i%i");
        ironSpaceSuitBoots.setIngredient('*', Material.NETHERITE_INGOT);
        ironSpaceSuitBoots.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitBoots.setIngredient('A', Material.IRON_BOOTS);
        ironSpaceSuitBoots.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitBoots.setIngredient( 'i', Material.IRON_INGOT);
        getServer().addRecipe(ironSpaceSuitBoots);
    }

    public static ItemStack getIronOxygenHelmet() {
        ItemStack oxigenHelmet = new ItemStack(Material.IRON_HELMET);
        ArmorMeta oxigenHelmetMeta = (ArmorMeta) oxigenHelmet.getItemMeta();
        oxigenHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        oxigenHelmetMeta.setCustomModelData(1001);
        oxigenHelmet.setItemMeta(oxigenHelmetMeta);
        return oxigenHelmet;
    }

    public static ItemStack getIronSpaceSuitChestplate() {
        ItemStack spaceSuitChestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ArmorMeta spaceSuitChestplateMeta = (ArmorMeta) spaceSuitChestplate.getItemMeta();
        spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitChestplateMeta.setCustomModelData(1001);
        spaceSuitChestplate.setItemMeta(spaceSuitChestplateMeta);
        return spaceSuitChestplate;
    }

    public static ItemStack getIronSpaceSuitLeggins() {
        ItemStack spaceSuitLeggins = new ItemStack(Material.IRON_LEGGINGS);
        ArmorMeta spaceSuitLegginsMeta = (ArmorMeta) spaceSuitLeggins.getItemMeta();
        spaceSuitLegginsMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitLegginsMeta.setCustomModelData(1001);
        spaceSuitLeggins.setItemMeta(spaceSuitLegginsMeta);
        return spaceSuitLeggins;
    }

    public static ItemStack getIronSpaceSuitBoots() {
        ItemStack spaceSuitBoots = new ItemStack(Material.IRON_BOOTS);
        ArmorMeta spaceSuitBootsMeta = (ArmorMeta) spaceSuitBoots.getItemMeta();
        spaceSuitBootsMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        spaceSuitBootsMeta.setCustomModelData(1001);
        spaceSuitBoots.setItemMeta(spaceSuitBootsMeta);
        return spaceSuitBoots;
    }

}
