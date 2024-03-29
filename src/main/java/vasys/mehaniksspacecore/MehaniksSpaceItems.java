package vasys.mehaniksspacecore;

import static org.bukkit.Bukkit.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class MehaniksSpaceItems { 
    public static List<Integer> itemModelDataList = new ArrayList<>();
    public static HashMap<String, ItemStack> customItems = new HashMap<>();
    public static HashMap<String, List<Object>> meteorites = new HashMap<>();
    public static List<Object> lithiumProperties = new ArrayList<>();
    public static List<Object> meteoricIronProperties = new ArrayList<>();
    public static List<Object> titaniumProperties = new ArrayList<>();
    public static List<Object> nasturanProperties = new ArrayList<>();

    public static void addCustomItems() {
        itemModelDataList.add(1001);
        itemModelDataList.add(1002);
        itemModelDataList.add(1003);
        itemModelDataList.add(1004);

        customItems.put("iron_space_suit_helmet", getIronSpaceSuitHelmet(1));
        customItems.put("iron_space_suit_chestplate", getIronSpaceSuitChestplate(1));
        customItems.put("iron_space_suit_leggings", getIronSpaceSuitLeggins(1));
        customItems.put("iron_space_suit_boots", getIronSpaceSuitBoots());        
        customItems.put("iron_oxygen_tank", getIronOxygenTank(0, 300));

        customItems.put("copper_oxygen_generator", getCopperOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
        customItems.put("iron_oxygen_shield_generator", getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, 10, 360, 0, 0));
        customItems.put("iron_oil_generator", getIronOilGenerator(ChatColor.DARK_GRAY, 0, 0));

        customItems.put("rocket", getRocket(2000, 0, 0, 0, "none"));
        customItems.put("rocket_nose", getRocketNose());
        customItems.put("rocket_fuel_tank", getRocketFuelTank());
        customItems.put("rocket_nozzle", getRocketNozzle());
        customItems.put("rocket_control_panel", getRocketConrolPanel());
        customItems.put("flight_control_panel", getFlightControlPanel());
        customItems.put("modification_control_panel", getRocketModificationPanel());

        customItems.put("oil", getOil());
        customItems.put("raw_lithium", getRawLithium());
        customItems.put("lithium", getLithium());
        customItems.put("raw_meteoric_iron", getRawMeteoricIron());
        customItems.put("meteoric_iron", getMeteoricIron());
        customItems.put("raw_titanium", getRawTitanium());
        customItems.put("titanium", getTitanium());
        customItems.put("raw_nasturan", getRawNasturan());
        customItems.put("nasturan", getNasturan());
        customItems.put("meteorite", getMeteorite("meteoric_iron"));        

        customItems.put("copper_battery", getCopperBattery(300, 300));

        lithiumProperties.add(getRawLithium());
        lithiumProperties.add("ewogICJ0aW1lc3RhbXAiIDogMTcwMjQ2NzQwMjUxMywKICAicHJvZmlsZUlkIiA6ICI3YzJhNDBmZjVhNTA0MjBkYjQzMWZlMDJjN2VlNDA1MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWYXN5cyIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84ZjJhZGY2MzdkN2ZmYjJjZmM5NDU5YTg2Yjg1OWRiODY1ZDAwMTQwMjNiNTIwYmRlNWQ5MzkwNjkyOWM2YWQ5IgogICAgfQogIH0KfQ==");
        lithiumProperties.add(3);
        lithiumProperties.add(8);
        lithiumProperties.add(0.5f);
        meteorites.put("lithium", lithiumProperties);
        meteoricIronProperties.add(getRawMeteoricIron());
        meteoricIronProperties.add("ewogICJ0aW1lc3RhbXAiIDogMTcwMjQ2NzgxNDY4MSwKICAicHJvZmlsZUlkIiA6ICI3YzJhNDBmZjVhNTA0MjBkYjQzMWZlMDJjN2VlNDA1MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWYXN5cyIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMmM5YWVkYWJmYTVhNDI3ZGM0MjJjMDA4ZjI3MDQ1NTE3ZDcxYzFmMmVkZjE1Mjg5ZmMzMGZiZmE0YTRhNzhmIgogICAgfQogIH0KfQ==");
        meteoricIronProperties.add(3);
        meteoricIronProperties.add(8);
        meteoricIronProperties.add(0.1f);
        meteorites.put("meteoric_iron", meteoricIronProperties);
        titaniumProperties.add(getRawTitanium());
        titaniumProperties.add("ewogICJ0aW1lc3RhbXAiIDogMTcwMjQ2NzkxOTMzOSwKICAicHJvZmlsZUlkIiA6ICI3YzJhNDBmZjVhNTA0MjBkYjQzMWZlMDJjN2VlNDA1MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWYXN5cyIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84OGUyZDJmNjUxYWE1MTk1YzBhMDA3MDczODk5NTY5OTFiM2VjZTY5Yjk5NTM1NWEyZTQxYjE0YmE5YTU2ZDEiCiAgICB9CiAgfQp9");
        titaniumProperties.add(3);
        titaniumProperties.add(8);
        titaniumProperties.add(0.1f);
        meteorites.put("titanium", titaniumProperties);
        nasturanProperties.add(getRawNasturan());
        nasturanProperties.add("ewogICJ0aW1lc3RhbXAiIDogMTcwMjQ2Nzg4ODQxNSwKICAicHJvZmlsZUlkIiA6ICI3YzJhNDBmZjVhNTA0MjBkYjQzMWZlMDJjN2VlNDA1MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWYXN5cyIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jN2FlYzA1ODhjMDI5ODFhMDZmOTMyOThmMGJiY2FjZjA4MmI2YzQ1MzQzNTM0MGZiNjA1MDEyYzEwM2UzYTNhIgogICAgfQogIH0KfQ==");
        nasturanProperties.add(3);
        nasturanProperties.add(8);
        nasturanProperties.add(1f);
        meteorites.put("nasturan", nasturanProperties);
    }

    public static void addRecipes() {
        FurnaceRecipe lithium = new FurnaceRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "lithium"), getLithium(), new RecipeChoice.ExactChoice(getRawLithium()), 1.5f, 250);
        getServer().addRecipe(lithium);

        FurnaceRecipe meteoric_iron = new FurnaceRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "meteoric_iron"), getMeteoricIron(), new RecipeChoice.ExactChoice(getRawMeteoricIron()), 1, 250);
        getServer().addRecipe(meteoric_iron);

        FurnaceRecipe titanium = new FurnaceRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "titanium"), getTitanium(), new RecipeChoice.ExactChoice(getRawTitanium()), 1.5f, 300);
        getServer().addRecipe(titanium);

        FurnaceRecipe nasturan = new FurnaceRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "nasturan"), getNasturan(), new RecipeChoice.ExactChoice(getRawNasturan()), 2, 400);
        getServer().addRecipe(nasturan);

        ShapedRecipe ironSpaceSuitHelmet = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet"), getIronSpaceSuitHelmet(1));
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

        ShapedRecipe ironSpaceSuitHelmet3 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_3"), getIronSpaceSuitHelmet(3));
        ironSpaceSuitHelmet3.shape("nin","mTm","c@c");
        ironSpaceSuitHelmet3.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet3.setIngredient('i', Material.IRON_INGOT);
        ironSpaceSuitHelmet3.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet3.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitHelmet3.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet3.setIngredient('@', getIronSpaceSuitHelmet(2));
        getServer().addRecipe(ironSpaceSuitHelmet3);

        ShapedRecipe ironSpaceSuitHelmet4 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_4"), getIronSpaceSuitHelmet(4));
        ironSpaceSuitHelmet4.shape("mnm","nTn","c@c");
        ironSpaceSuitHelmet4.setIngredient('n', Material.NETHERITE_INGOT);
        ironSpaceSuitHelmet4.setIngredient('T', Material.TINTED_GLASS);
        ironSpaceSuitHelmet4.setIngredient( 'c', Material.COPPER_INGOT);
        ironSpaceSuitHelmet4.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironSpaceSuitHelmet4.setIngredient('@', getIronSpaceSuitHelmet(4));
        getServer().addRecipe(ironSpaceSuitHelmet4);

        ShapedRecipe ironSpaceSuitHelmet5 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_helmet_5"), getIronSpaceSuitHelmet(5));
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
        ironSpaceSuitChestplate5.setIngredient('@', getIronSpaceSuitChestplate(8));
        getServer().addRecipe(ironSpaceSuitChestplate5);



        ShapedRecipe ironSpaceSuitLeggins = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_space_suit_leggings"), getIronSpaceSuitLeggins(1));
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

        ShapedRecipe ironOxygenTank = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank"), getIronOxygenTank(0, 300));
        ironOxygenTank.shape("i%i","%m%","*-*");
        ironOxygenTank.setIngredient( 'i', Material.IRON_INGOT);
        ironOxygenTank.setIngredient( '%', Material.TINTED_GLASS);
        ironOxygenTank.setIngredient( 'm', Material.PHANTOM_MEMBRANE);
        ironOxygenTank.setIngredient( '*', Material.COPPER_INGOT);
        ironOxygenTank.setIngredient( '-', Material.IRON_TRAPDOOR);
        getServer().addRecipe(ironOxygenTank);

        ShapedRecipe ironOxygenTank2 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank_2"), getIronOxygenTank(0, 600));
        ironOxygenTank2.shape("c#c","#O#","cnc");
        ironOxygenTank2.setIngredient( '#', Material.TINTED_GLASS);
        ironOxygenTank2.setIngredient( 'c', Material.COPPER_INGOT);
        ironOxygenTank2.setIngredient( 'n', Material.NETHERITE_SCRAP);
        ironOxygenTank2.setIngredient( 'O', getIronOxygenTank(0, 300));
        getServer().addRecipe(ironOxygenTank2);

        ShapedRecipe ironOxygenTank3 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank_3"), getIronOxygenTank(0, 900));
        ironOxygenTank3.shape("c#c","nOn","c#c");
        ironOxygenTank3.setIngredient( '#', Material.TINTED_GLASS);
        ironOxygenTank3.setIngredient( 'c', Material.COPPER_INGOT);
        ironOxygenTank3.setIngredient( 'n', Material.NETHERITE_SCRAP);
        ironOxygenTank3.setIngredient( 'O', getIronOxygenTank(0, 600));
        getServer().addRecipe(ironOxygenTank3);

        ShapedRecipe ironOxygenTank4 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank_4"), getIronOxygenTank(0, 1200));
        ironOxygenTank4.shape("c#c","nOn","cnc");
        ironOxygenTank4.setIngredient( '#', Material.TINTED_GLASS);
        ironOxygenTank4.setIngredient( 'c', Material.COPPER_INGOT);
        ironOxygenTank4.setIngredient( 'n', Material.NETHERITE_SCRAP);
        ironOxygenTank4.setIngredient( 'O', getIronOxygenTank(0, 900));
        getServer().addRecipe(ironOxygenTank4);

        ShapedRecipe ironOxygenTank5 = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_tank_5"), getIronOxygenTank(0, 1500));
        ironOxygenTank5.shape("cnc","nOn","cnc");
        ironOxygenTank5.setIngredient( 'c', Material.COPPER_INGOT);
        ironOxygenTank5.setIngredient( 'n', Material.NETHERITE_SCRAP);
        ironOxygenTank5.setIngredient( 'O', getIronOxygenTank(0, 1200));
        getServer().addRecipe(ironOxygenTank5);

        ShapedRecipe copperOxygenGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "copper_oxygen_generator"), getCopperOxygenGenerator(ChatColor.DARK_GRAY, 0, 0));
        copperOxygenGenerator.shape("b%b","%B%","imi");
        copperOxygenGenerator.setIngredient('b', Material.BLAZE_POWDER);
        copperOxygenGenerator.setIngredient('%', Material.TINTED_GLASS);
        copperOxygenGenerator.setIngredient('B', Material.BUCKET);
        copperOxygenGenerator.setIngredient('i', Material.LIGHTNING_ROD);
        copperOxygenGenerator.setIngredient('m', Material.PHANTOM_MEMBRANE);
        getServer().addRecipe(copperOxygenGenerator);

        ShapedRecipe ironOxygenShieldGenerator = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "iron_oxygen_shield_generator"), getIronOxygenShieldGenerator(ChatColor.DARK_GRAY, 10, 360, 0, 0));
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

        ShapedRecipe rocket = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket"), getRocket(2000, 0, 0, 0, "none"));
        rocket.shape("#n#","#t#","#z#");
        rocket.setIngredient('n', getRocketNose());
        rocket.setIngredient('t', getRocketFuelTank());
        rocket.setIngredient('z', getRocketNozzle());
        getServer().addRecipe(rocket);

        ShapedRecipe rocketNose = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket_nose"), getRocketNose());
        rocketNose.shape("d@d","%c%","#-#");
        rocketNose.setIngredient('d', Material.DIAMOND);
        rocketNose.setIngredient('@', Material.TINTED_GLASS);
        rocketNose.setIngredient('%', Material.WAXED_COPPER_BLOCK);
        rocketNose.setIngredient('#', Material.IRON_BLOCK);
        rocketNose.setIngredient('-', Material.IRON_TRAPDOOR);
        rocketNose.setIngredient('c', Material.COMPASS);
        getServer().addRecipe(rocketNose);

        ShapedRecipe rocketFuelTank = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket_fuel_tank"), getRocketFuelTank());
        rocketFuelTank.shape("%@%","#@#","%@%");
        rocketFuelTank.setIngredient('@', Material.BARREL);
        rocketFuelTank.setIngredient('%', Material.WAXED_COPPER_BLOCK);
        rocketFuelTank.setIngredient('#', Material.IRON_BLOCK);
        getServer().addRecipe(rocketFuelTank);

        ShapedRecipe rocketNozzle = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket_nozzle"), getRocketNozzle());
        rocketNozzle.shape("%H%","#@#","nbn");
        rocketNozzle.setIngredient('n', Material.NETHERITE_SCRAP);
        rocketNozzle.setIngredient('@', Material.RESPAWN_ANCHOR);
        rocketNozzle.setIngredient('%', Material.WAXED_COPPER_BLOCK);
        rocketNozzle.setIngredient('#', Material.IRON_BLOCK);
        rocketNozzle.setIngredient('b', Material.BLAZE_POWDER);
        rocketNozzle.setIngredient('H', Material.HOPPER);
        getServer().addRecipe(rocketNozzle);

        ShapedRecipe rocketControlPanel = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "rocket_control_panel"), getRocketConrolPanel());
        rocketControlPanel.shape("i#i", "ivi", "zdz");
        rocketControlPanel.setIngredient('i', Material.IRON_INGOT);
        rocketControlPanel.setIngredient('#', Material.TINTED_GLASS);
        rocketControlPanel.setIngredient('v', Material.HOPPER);
        rocketControlPanel.setIngredient('z', Material.GOLD_INGOT);
        rocketControlPanel.setIngredient('d', Material.DIAMOND);
        getServer().addRecipe(rocketControlPanel);

        ShapedRecipe flightControlPanel = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "flight_control_panel"), getFlightControlPanel());
        flightControlPanel.shape("l#l", "isi", "aie");
        flightControlPanel.setIngredient('l', Material.LIGHTNING_ROD);
        flightControlPanel.setIngredient('i', Material.IRON_INGOT);
        flightControlPanel.setIngredient('#', Material.TINTED_GLASS);
        flightControlPanel.setIngredient('s', Material.ECHO_SHARD);
        flightControlPanel.setIngredient('a', Material.AMETHYST_SHARD);
        flightControlPanel.setIngredient('e', Material.EMERALD);
        getServer().addRecipe(flightControlPanel);

        ShapedRecipe modificationControlPanel = new ShapedRecipe(new NamespacedKey(MehaniksSpaceCore.getPlugin(MehaniksSpaceCore.class), "modification_control_panel"), getRocketModificationPanel());
        modificationControlPanel.shape("die", "iTi", "kak");
        modificationControlPanel.setIngredient('d', Material.DIAMOND);
        modificationControlPanel.setIngredient('i', Material.IRON_INGOT);
        modificationControlPanel.setIngredient('e', Material.EMERALD);
        modificationControlPanel.setIngredient('T', Material.SMITHING_TABLE);
        modificationControlPanel.setIngredient('k', Material.QUARTZ);
        modificationControlPanel.setIngredient('a', Material.AMETHYST_SHARD);
        getServer().addRecipe(modificationControlPanel);

    }

    public static ItemStack getIronSpaceSuitHelmet(Integer level) {
        Integer autoOxigenSpeed = (level - 1)*2;
        ItemStack spaceSuitHelmet = new ItemStack(Material.IRON_HELMET);
        ArmorMeta spaceSuitHelmetMeta = (ArmorMeta) spaceSuitHelmet.getItemMeta();

        if (level < 3) spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SILENCE));
        else if (level < 5) spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        else spaceSuitHelmetMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));

        spaceSuitHelmetMeta.setCustomModelData(1001);
        spaceSuitHelmetMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitHelmetMeta.setDisplayName(ChatColor.GRAY + "Space Suit Helmet");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "auto oxigen refill: " + autoOxigenSpeed);
        spaceSuitHelmetMeta.setLore(lore);
        spaceSuitHelmet.setItemMeta(spaceSuitHelmetMeta);
        return spaceSuitHelmet;
    }

    public static ItemStack getIronSpaceSuitChestplate(Integer level) {
        int max = 0;
        if (level == 1) max = 1;
        else max = (level - 1)*2;

        ItemStack spaceSuitChestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ArmorMeta spaceSuitChestplateMeta = (ArmorMeta) spaceSuitChestplate.getItemMeta();

        if (level < 2) spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SILENCE));
        else if (max < 4) spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        else spaceSuitChestplateMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));

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

    public static ItemStack getIronSpaceSuitLeggins(Integer level) {
        int maxEnergy = (level - 1)*50;
        ItemStack spaceSuitLeggins = new ItemStack(Material.IRON_LEGGINGS);
        ArmorMeta spaceSuitLegginsMeta = (ArmorMeta) spaceSuitLeggins.getItemMeta();

        if (level < 2) {
            spaceSuitLegginsMeta.setTrim(new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SILENCE));
        } else if (level < 4) {
            spaceSuitLegginsMeta.setTrim(new ArmorTrim(TrimMaterial.IRON, TrimPattern.SILENCE));
        } else {
            spaceSuitLegginsMeta.setTrim(new ArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SILENCE));
        }

        spaceSuitLegginsMeta.setCustomModelData(1001);
        spaceSuitLegginsMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        spaceSuitLegginsMeta.setDisplayName(ChatColor.GRAY + "Space Suit Leggins");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + "■".repeat(10) + ChatColor.WHITE + "] 0/" + maxEnergy);
        spaceSuitLegginsMeta.setLore(lore);
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

        if (max <= 300) oxygenTankMeta.setCustomModelData(1001);
        else if (max <= 600) oxygenTankMeta.setCustomModelData(1002);
        else if (max <= 900) oxygenTankMeta.setCustomModelData(1003);
        else if (max <= 1200) oxygenTankMeta.setCustomModelData(1004);
        else oxygenTankMeta.setCustomModelData(1005);

        oxygenTankMeta.setDisplayName(ChatColor.GRAY + "Oxygen Tank");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Volume: " + i + "/" + max);
        oxygenTankMeta.setLore(lore);
        oxygenTank.setItemMeta(oxygenTankMeta);
        return oxygenTank;
    }

    public static ItemStack getCopperOxygenGenerator(ChatColor color, Integer oxygen, Integer copper) {
        ItemStack oxygenGenerator = new ItemStack(Material.COPPER_INGOT);
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

    public static ItemStack getIronOxygenShieldGenerator(ChatColor color, Integer maxR, Integer oilMaxR, Integer oxygen, Integer oil) {
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

    public static ItemStack getRocket(Integer maxOil, Integer maxStorage, Integer currentOil, Integer currentStorage, String endPoint) {
        ItemStack rocket = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta rocketMeta = rocket.getItemMeta();

        rocketMeta.setCustomModelData(1001);

        rocketMeta.setDisplayName(ChatColor.DARK_GRAY + "Rocket");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Max oil: " + maxOil);
        lore.add(ChatColor.GRAY + "Max storage: " + maxStorage);
        lore.add(ChatColor.GRAY + "Current oil: " + currentOil);
        lore.add(ChatColor.GRAY + "Current storage: " + currentStorage);
        lore.add(ChatColor.GRAY + "End Point: " + endPoint);
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

    public static ItemStack getCopperBattery(Integer i, Integer max) {
        ItemStack copperBattery = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta copperBatteryMeta = copperBattery.getItemMeta();

        copperBatteryMeta.setCustomModelData(1001);

        copperBatteryMeta.setDisplayName(ChatColor.GOLD + "Copper Battery");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Charge: " + i + "/" + max);
        copperBatteryMeta.setLore(lore);
        copperBattery.setItemMeta(copperBatteryMeta);
        return copperBattery;
    }

    public static ItemStack getRawLithium() {
        ItemStack rawLithium = new ItemStack(Material.FLINT);
        ItemMeta rawLithiumMeta = rawLithium.getItemMeta();
        rawLithiumMeta.setCustomModelData(1001);
        rawLithiumMeta.setDisplayName("Raw Lithium");
        rawLithium.setItemMeta(rawLithiumMeta);
        return rawLithium;
    }

    public static ItemStack getLithium() {
        ItemStack lithium = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta lithiumMeta = lithium.getItemMeta();
        lithiumMeta.setCustomModelData(1002);
        lithiumMeta.setDisplayName("Lithium");
        lithium.setItemMeta(lithiumMeta);
        return lithium;
    }

    public static ItemStack getRawMeteoricIron() {
        ItemStack rawMeteoricIron = new ItemStack(Material.FLINT);
        ItemMeta rawMeteoricIronMeta = rawMeteoricIron.getItemMeta();
        rawMeteoricIronMeta.setCustomModelData(1002);
        rawMeteoricIronMeta.setDisplayName("Raw Meteoric Iron");
        rawMeteoricIron.setItemMeta(rawMeteoricIronMeta);
        return rawMeteoricIron;
    }

    public static ItemStack getMeteoricIron() {
        ItemStack meteoricIron = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meteoricIronMeta = meteoricIron.getItemMeta();
        meteoricIronMeta.setCustomModelData(1001);
        meteoricIronMeta.setDisplayName("Meteoric Iron");
        meteoricIron.setItemMeta(meteoricIronMeta);
        return meteoricIron;
    }

    public static ItemStack getRawTitanium() {
        ItemStack rawTitanium = new ItemStack(Material.FLINT);
        ItemMeta rawTitaniumMeta = rawTitanium.getItemMeta();
        rawTitaniumMeta.setCustomModelData(1003);
        rawTitaniumMeta.setDisplayName("Raw Titanium");
        rawTitanium.setItemMeta(rawTitaniumMeta);
        return rawTitanium;
    }
    
    public static ItemStack getTitanium() {
        ItemStack titanium = new ItemStack(Material.IRON_INGOT);
        ItemMeta titaniumMeta = titanium.getItemMeta();
        titaniumMeta.setCustomModelData(1001);
        titaniumMeta.setDisplayName("Titanium");
        titanium.setItemMeta(titaniumMeta);
        return titanium;
    }

    public static ItemStack getRawNasturan() {
        ItemStack rawNasturan = new ItemStack(Material.FLINT);
        ItemMeta rawNasturanMeta = rawNasturan.getItemMeta();
        rawNasturanMeta.setCustomModelData(1004);
        rawNasturanMeta.setDisplayName("Raw Nasturan");
        rawNasturan.setItemMeta(rawNasturanMeta);
        return rawNasturan;
    }

    public static ItemStack getNasturan() {
        ItemStack nasturan = new ItemStack(Material.ECHO_SHARD);
        ItemMeta nasturanMeta = nasturan.getItemMeta();
        nasturanMeta.setCustomModelData(1001);
        nasturanMeta.setDisplayName("Nasturan");
        nasturan.setItemMeta(nasturanMeta);
        return nasturan;
    }

    public static ItemStack getMeteorite(String ore) {
        if (!meteorites.containsKey(ore)) ore = "meteoric_iron";
        return MehaniksSpaceFunctions.createHead(ore, (String) meteorites.get(ore).get(1));
    }

}
