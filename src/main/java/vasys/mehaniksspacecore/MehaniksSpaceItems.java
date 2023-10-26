package vasys.mehaniksspacecore;

import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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

public class MehaniksSpaceItems {

    public static void addRecipes() {
        ShapedRecipe ironSpaceSuitHelmet = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet"), getIronSpaceSuitHelmet(0));
        ironSpaceSuitHelmet.shape("*%*","%A%","mTm");
        ironSpaceSuitHelmet.setIngredient('*', Material.COPPER_INGOT);
        ironSpaceSuitHelmet.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitHelmet.setIngredient('A', Material.IRON_HELMET);
        ironSpaceSuitHelmet.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet.setIngredient('T', Material.TINTED_GLASS);
        getServer().addRecipe(ironSpaceSuitHelmet);

        ShapedRecipe ironSpaceSuitHelmet2 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_2"), getIronSpaceSuitHelmet(2));
        ironSpaceSuitHelmet2.shape("ini","mTm","c@c");
        ironSpaceSuitHelmet2.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet2.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitHelmet2.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet2.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitHelmet2.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet2.setIngredient('@', getIronSpaceSuitHelmet(0));
        getServer().addRecipe(ironSpaceSuitHelmet2);

        ShapedRecipe ironSpaceSuitHelmet3 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_3"), getIronSpaceSuitHelmet(4));
        ironSpaceSuitHelmet3.shape("nin","mTm","c@c");
        ironSpaceSuitHelmet3.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet3.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitHelmet3.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet3.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitHelmet3.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet3.setIngredient('@', getIronSpaceSuitHelmet(2));
        getServer().addRecipe(ironSpaceSuitHelmet3);

        ShapedRecipe ironSpaceSuitHelmet4 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_4"), getIronSpaceSuitHelmet(6));
        ironSpaceSuitHelmet4.shape("mnm","nTn","c@c");
        ironSpaceSuitHelmet4.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet4.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet4.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitHelmet4.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet4.setIngredient('@', getIronSpaceSuitHelmet(4));
        getServer().addRecipe(ironSpaceSuitHelmet4);

        ShapedRecipe ironSpaceSuitHelmet5 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_5"), getIronSpaceSuitHelmet(8));
        ironSpaceSuitHelmet5.shape("mnm","nTn","c@c");
        ironSpaceSuitHelmet5.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet5.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet5.setIngredient( 'c', Material.NETHERITE_SCRAP);
        ironSpaceSuitHelmet5.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet5.setIngredient('@', getIronSpaceSuitHelmet(6));
        getServer().addRecipe(ironSpaceSuitHelmet5);



        ShapedRecipe ironSpaceSuitChestplate = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_chestplate"), getIronSpaceSuitChestplate(1));
        ironSpaceSuitChestplate.shape("m%m","*A*","%m%");
        ironSpaceSuitChestplate.setIngredient('*', Material.COPPER_INGOT);
        ironSpaceSuitChestplate.setIngredient('%', Material.IRON_BLOCK);
        ironSpaceSuitChestplate.setIngredient('A', Material.IRON_CHESTPLATE);
        ironSpaceSuitChestplate.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironSpaceSuitChestplate);

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

        ShapedRecipe ironOxygenGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_generator"), getIronOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
        ironOxygenGenerator.shape("b%b","%B%","imi");
        ironOxygenGenerator.setIngredient('b', Material.BLAZE_POWDER);
        ironOxygenGenerator.setIngredient('%', Material.TINTED_GLASS);
        ironOxygenGenerator.setIngredient('B', Material.BUCKET);
        ironOxygenGenerator.setIngredient('i', Material.LIGHTNING_ROD);
        ironOxygenGenerator.setIngredient('m', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(ironOxygenGenerator);

        ShapedRecipe ironOxygenShieldGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_shield_generator"), getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, 0, 0, 10, 120));
        ironOxygenShieldGenerator.shape("iei","eme","iei");
        ironOxygenShieldGenerator.setIngredient('i', Material.IRON_INGOT);
        ironOxygenShieldGenerator.setIngredient('m', Material.PHANTOM_MEMBRANE);
        ironOxygenShieldGenerator.setIngredient('e', Material.ECHO_SHARD);
        getServer().addRecipe(ironOxygenShieldGenerator);

        ShapedRecipe ironOilGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oil_generator"), getIronOilGenerator(ChatColor.DARK_GRAY, 0, 0));
        ironOilGenerator.shape("iei","eme","iei");
        ironOilGenerator.setIngredient('i', Material.IRON_INGOT);
        ironOilGenerator.setIngredient('m', Material.HONEYCOMB_BLOCK);
        ironOilGenerator.setIngredient('e', Material.QUARTZ);
        getServer().addRecipe(ironOilGenerator);

        ShapedRecipe rocket = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket"), getRocket(2000, 0, 60000));
        rocket.shape("#n#","#t#","#z#");
        rocket.setIngredient('n', getRocketNose());
        rocket.setIngredient('t', getRocketFuelTank());
        rocket.setIngredient('z', getRocketNozzle());
        getServer().addRecipe(rocket);

        // ShapedRecipe rocketNose = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket_nose"), getRocketNose());
        // rocketNose.shape("#n#","#t#","#z#");
        // rocketNose.setIngredient('n', getRocketNose());
        // rocketNose.setIngredient('t', getRocketFuelTank());
        // rocketNose.setIngredient('z', getRocketNozzle());
        // getServer().addRecipe(rocketNose);
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
        ItemStack oxygenGenerator = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta oxygenGeneratorMeta = oxygenGenerator.getItemMeta();
        oxygenGeneratorMeta.setCustomModelData(1001);
        oxygenGeneratorMeta.setDisplayName(color + "Oxygen Generator " + oxygen + " " + copper);
        oxygenGenerator.setItemMeta(oxygenGeneratorMeta);
        return oxygenGenerator;
    }

    public static ItemStack getIronOilGenerator(ChatColor color, Integer oil, Integer fuel) {
        ItemStack oilGenerator = new ItemStack(Material.HONEYCOMB);
        ItemMeta oilGeneratorMeta = oilGenerator.getItemMeta();
        oilGeneratorMeta.setCustomModelData(1001);
        oilGeneratorMeta.setDisplayName(color + "Oil Generator " + oil + " " + fuel);
        oilGenerator.setItemMeta(oilGeneratorMeta);
        return oilGenerator;
    }

    public static ItemStack getIronOxygenShieldGenerator(ChatColor color, Integer oxygen, Integer oil, Integer maxR, Integer oilMaxR) {
        ItemStack oxygenShieldGenerator = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta oxygenShieldGeneratorMeta = oxygenShieldGenerator.getItemMeta();
        oxygenShieldGeneratorMeta.setCustomModelData(1001);
        oxygenShieldGeneratorMeta.setDisplayName(color + "Oxygen Shield Generator " + oxygen + " " + oil);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Max radius: " + maxR);
        lore.add(ChatColor.GRAY + "Oil for max radius: " + oilMaxR);
        oxygenShieldGeneratorMeta.setLore(lore);
        oxygenShieldGenerator.setItemMeta(oxygenShieldGeneratorMeta);
        return oxygenShieldGenerator;
    }

    public static ItemStack getOil() {
        ItemStack oil = new ItemStack(Material.INK_SAC);
        ItemMeta oilMeta = oil.getItemMeta();
        oilMeta.setCustomModelData(1001);
        oilMeta.setDisplayName(ChatColor.BLACK + "Oil");
        oil.setItemMeta(oilMeta);
        return oil;
    }

    public static ItemStack getRocket(Integer maxOil, Integer maxStorage, Integer maxDistance) {
        ItemStack rocket = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta rocketMeta = rocket.getItemMeta();

        rocketMeta.setCustomModelData(1001);

        rocketMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Max oil: " + maxOil);
        lore.add(ChatColor.GRAY + "Max storage: " + maxStorage);
        lore.add(ChatColor.GRAY + "Max distance: " + maxDistance);
        rocketMeta.setLore(lore);
        rocket.setItemMeta(rocketMeta);
        return rocket;
    }

    public static ItemStack getRocketNose() {
        ItemStack rocketNose = new ItemStack(Material.DIAMOND);
        ItemMeta rocketNoseMeta = rocketNose.getItemMeta();
        rocketNoseMeta.setCustomModelData(1001);
        rocketNoseMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket Nose");
        rocketNose.setItemMeta(rocketNoseMeta);
        return rocketNose;
    }

    public static ItemStack getRocketFuelTank() {
        ItemStack rocketFuelTank = new ItemStack(Material.LAPIS_LAZULI);
        ItemMeta rocketFuelTankMeta = rocketFuelTank.getItemMeta();
        rocketFuelTankMeta.setCustomModelData(1001);
        rocketFuelTankMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket Fuel Tank");
        rocketFuelTank.setItemMeta(rocketFuelTankMeta);
        return rocketFuelTank;
    }

    public static ItemStack getRocketNozzle() {
        ItemStack rocketNozzle = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta rocketNozzleMeta = rocketNozzle.getItemMeta();
        rocketNozzleMeta.setCustomModelData(1001);
        rocketNozzleMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket Nozzle");
        rocketNozzle.setItemMeta(rocketNozzleMeta);
        return rocketNozzle;
    }

    public static ItemStack getRocketConrolPanel() {
        ItemStack rocketControlPanel = new ItemStack(Material.NAUTILUS_SHELL);
        ItemMeta rocketControlPanelMeta = rocketControlPanel.getItemMeta();
        rocketControlPanelMeta.setCustomModelData(1001);
        rocketControlPanelMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket Control Panel");
        rocketControlPanel.setItemMeta(rocketControlPanelMeta);
        return rocketControlPanel;
    }

    public static ItemStack getFlightControlPanel() {
        ItemStack flightControlPanel = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta flightControlPanelMeta = flightControlPanel.getItemMeta();
        flightControlPanelMeta.setCustomModelData(1001);
        flightControlPanelMeta.setDisplayName(ChatColor.DARK_GRAY + "Flight Control Panel");
        flightControlPanel.setItemMeta(flightControlPanelMeta);
        return flightControlPanel;
    }

    public static ItemStack getRocketModificationPanel() {
        ItemStack rocketModificationPanel = new ItemStack(Material.EMERALD);
        ItemMeta rocketModificationPanelMeta = rocketModificationPanel.getItemMeta();
        rocketModificationPanelMeta.setCustomModelData(1001);
        rocketModificationPanelMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket Modification Panel");
        rocketModificationPanel.setItemMeta(rocketModificationPanelMeta);
        return rocketModificationPanel;
    }

}
