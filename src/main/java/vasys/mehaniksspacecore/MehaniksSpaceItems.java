package vasys.mehaniksspacecore;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class MehaniksSpaceItems {


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
