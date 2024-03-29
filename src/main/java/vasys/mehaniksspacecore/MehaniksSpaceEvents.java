package vasys.mehaniksspacecore;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.plugin.java.JavaPlugin.*;
import static vasys.mehaniksspacecore.MehaniksSpaceCore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.Skull;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

public class MehaniksSpaceEvents implements Listener {
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (MehaniksSpaceWorldMap.containsKey(player.getWorld().getName())) {
            int gravity = Integer.parseInt(MehaniksSpaceWorldMap.get(player.getWorld().getName()).get(1));
            if (gravity != 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
            }
        } else if (MehaniksSpaceWorldMap.containsKey(event.getFrom().getName())) {
            player.lockFreezeTicks(false);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.removePotionEffect(PotionEffectType.WITHER);
            player.removePotionEffect(PotionEffectType.POISON);
            player.removePotionEffect(PotionEffectType.CONFUSION);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setAllowFlight(player.getInventory().getLeggings() != null &&
                player.getInventory().getLeggings().getItemMeta().hasCustomModelData() &&
                player.getInventory().getLeggings().getItemMeta().getCustomModelData() == 1001);
        if (MehaniksSpaceWorldMap.containsKey(player.getWorld().getName())) {
            int gravity = Integer.parseInt(MehaniksSpaceWorldMap.get(player.getWorld().getName()).get(1));
            if (gravity != 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, gravity - 1, true, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, (gravity / 3) - 1, true, false));
            }
        } else {
            player.setGravity(true);
            player.lockFreezeTicks(false);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.removePotionEffect(PotionEffectType.WITHER);
            player.removePotionEffect(PotionEffectType.POISON);
            player.removePotionEffect(PotionEffectType.CONFUSION);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.CARROT_ON_A_STICK &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1003 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1004 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1005) &&
                player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasCustomModelData() &&
                player.getInventory().getChestplate().getItemMeta().getCustomModelData() == 1001) {
            ItemMeta spaceSuitChestplateMeta = player.getInventory().getChestplate().getItemMeta();
            List<String> loreOld = spaceSuitChestplateMeta.getLore();

            ItemMeta tankMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> tankLore = tankMeta.getLore();

            int addOxygen = Integer.parseInt(tankLore.get(0).split(" ")[1].split("/")[0]);
            int addMaxOxygen = Integer.parseInt(tankLore.get(0).split(" ")[1].split("/")[1]);

            if (Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[1]) > Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2)) && addOxygen != 0) {
                String tanksTypes = "";
                if (loreOld.get(2).split(" ").length > 2) {
                    tanksTypes = loreOld.get(2).split(" ")[2] + "/";
                }
                tanksTypes += player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[1];
                player.getInventory().setItemInMainHand(null);
                
                int tanks = Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2)) + 1;
                int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]) + addOxygen;
                int maxOxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[1]) + addMaxOxygen;

                MehaniksSpaceFunctions.spaceSuitChestplateData(player, spaceSuitChestplateMeta, loreOld, oxygen, maxOxygen, tanks, tanksTypes);
                player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.5f, 1f);
            }
        } else if(player.getInventory().getItemInMainHand().getType() == Material.WARPED_FUNGUS_ON_A_STICK &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasCustomModelData() &&
                player.getInventory().getLeggings().getItemMeta().getCustomModelData() == 1001) {
            ItemMeta spaceSuitLegginsMeta = player.getInventory().getLeggings().getItemMeta();
            List<String> loreOld = spaceSuitLegginsMeta.getLore();
            ItemMeta batteryMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> batteryLore = batteryMeta.getLore();

            int fuel = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[0]);
            int maxFuel = Integer.parseInt(loreOld.get(0).split(" ")[1].split("/")[1]);
            int addFuel = Integer.parseInt(batteryLore.get(0).split(" ")[1].split("/")[0]);
            int nAddFuel = addFuel;
            int maxAddFuel = Integer.parseInt(batteryLore.get(0).split(" ")[1].split("/")[1]);
            if (maxFuel == fuel) nAddFuel = 0;
            else if (maxFuel - fuel < nAddFuel) nAddFuel = maxFuel - fuel;

            if (fuel < maxFuel && nAddFuel > 0) {
                player.getInventory().setItemInMainHand(MehaniksSpaceItems.getCopperBattery(addFuel - nAddFuel, maxAddFuel));
                MehaniksSpaceFunctions.spaceSuitLegginsData(player, spaceSuitLegginsMeta, loreOld, fuel + nAddFuel, maxFuel);
            }
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.IRON_CHESTPLATE &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001) {

            ItemMeta spaceSuitChestplateMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> loreOld = spaceSuitChestplateMeta.getLore();

            if (0 < Integer.parseInt(loreOld.get(0).split(" ")[0].split("/")[0].substring(2))) {
                List<String> lore = new ArrayList<>();
                String[] tanksList = loreOld.get(2).split(" ")[2].split("/");
                int oxygen = Integer.parseInt(loreOld.get(1).split(" ")[1].split("/")[0]);
                player.playSound(player, Sound.BLOCK_IRON_DOOR_OPEN, 0.5f, 1f);

                for (int i = 0; i <= tanksList.length - 1; i++) {
                    String maxOxygen;
                    if ( Integer.parseInt(tanksList[i]) <= oxygen) {
                        maxOxygen = tanksList[i];
                    } else {
                        maxOxygen = String.valueOf(oxygen);
                    }

                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(maxOxygen), Integer.parseInt(tanksList[i])));
                    } else {
                        player.getWorld().dropItem(player.getLocation(), MehaniksSpaceItems.getIronOxygenTank(Integer.parseInt(maxOxygen), Integer.parseInt(tanksList[i])));
                    }
                }

                lore.add(ChatColor.WHITE + "" + 0 + "/" + loreOld.get(0).split(" ")[0].split("/")[1] + " oxygen tanks");
                lore.add(ChatColor.WHITE + "[" + ChatColor.DARK_GRAY + "■".repeat(10) + ChatColor.WHITE + "] 0/0");
                lore.add(ChatColor.DARK_GRAY + "Tanks types: ");
                spaceSuitChestplateMeta.setLore(lore);
                player.getInventory().getItemInMainHand().setItemMeta(spaceSuitChestplateMeta);
            }
        } else if(player.getInventory().getItemInMainHand().getType() == Material.CARROT_ON_A_STICK &&
                player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1003 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1004 ||
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1005) &&
                !player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(" ")[1].split("/")[0].equals("0")) {

            ItemMeta oxygenTankMeta = player.getInventory().getItemInMainHand().getItemMeta();
            List<String> loreOld = oxygenTankMeta.getLore();
            List<String> lore = new ArrayList<>();
            player.playSound(player, Sound.ENTITY_PLAYER_BREATH, 0.5f, 1f);
            lore.add(ChatColor.WHITE + "volume: " + 0 + "/" + loreOld.get(0).split(" ")[1].split("/")[1]);
            oxygenTankMeta.setLore(lore);
            player.getInventory().getItemInMainHand().setItemMeta(oxygenTankMeta);
        }

    }

    @EventHandler
    public void FrameEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.GLOW_ITEM_FRAME) {
            ItemFrame itemFrame = (ItemFrame) event.getEntity();
            // if (itemFrame.getItem().getType() == Material.COPPER_INGOT &&
            //         itemFrame.getItem().getItemMeta().hasCustomModelData() &&
            //         itemFrame.getItem().getItemMeta().getCustomModelData() == 1001) {
            //     if (Integer.parseInt(itemFrame.getItem().getItemMeta().getDisplayName().split(" ")[2]) <= 0 &&
            //             Integer.parseInt(itemFrame.getItem().getItemMeta().getDisplayName().split(" ")[3]) <= 0) {
            //         itemFrame.setCustomName("");
            //         itemFrame.setVisible(true);
            //     } else {
            //         itemFrame.remove();
            //         itemFrame.getWorld().createExplosion(itemFrame, 2);
            //     }
            // } else 
            if (itemFrame.getItem().getItemMeta().hasCustomModelData()) {
                itemFrame.setCustomName("");
                itemFrame.setVisible(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            Player player = event.getPlayer();
            if (!player.getInventory().getItemInMainHand().isEmpty() &&
                    player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                if (player.getInventory().getItemInMainHand().getType() == Material.COPPER_INGOT &&
                        (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 ||
                                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002 ||
                                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1003 ||
                                player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1004) &&
                        player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.BARREL) {
                    player.playSound(itemFrame, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Oxygen Generator");

                } else if (player.getInventory().getItemInMainHand().getType() == Material.MAGMA_CREAM &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                        player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.SCULK_CATALYST) {
                    player.playSound(itemFrame, Sound.BLOCK_SCULK_CATALYST_BLOOM, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Oxygen Shield Generator");

                } else if (player.getInventory().getItemInMainHand().getType() == Material.HONEYCOMB &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                        player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.BARREL) {
                    player.playSound(itemFrame, Sound.ITEM_HONEY_BOTTLE_DRINK, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Oil Generator");

                } else if (player.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SCRAP &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                        player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.LODESTONE) {
                    player.playSound(itemFrame, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Rocket");
                } else if (player.getInventory().getItemInMainHand().getType() == Material.NAUTILUS_SHELL &&
                    player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                    player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.BARREL) {
                player.playSound(itemFrame, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 1f);
                itemFrame.setCustomNameVisible(true);
                itemFrame.setCustomName("Rocket Conrol Panel");

                } else if (player.getInventory().getItemInMainHand().getType() == Material.AMETHYST_SHARD &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001) {
                    player.playSound(itemFrame, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Flight Control Panel");

                } else if (player.getInventory().getItemInMainHand().getType() == Material.EMERALD &&
                        player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1001 &&
                        player.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getType() == Material.BARREL) {
                    player.playSound(itemFrame, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5f, 1f);
                    itemFrame.setCustomNameVisible(true);
                    itemFrame.setCustomName("Rocket Modification Panel");
                }
            } else if (itemFrame.getName().equals("Rocket") &&
                    player.isSneaking() &&
                    itemFrame.getItem().getType() == Material.NETHERITE_SCRAP &&
                    itemFrame.getWorld().getBlockAt(itemFrame.getLocation().getBlockX(), itemFrame.getLocation().getBlockY() - 1, itemFrame.getLocation().getBlockZ()).getState().getType() == Material.LODESTONE) {
                int maxOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
                int maxStorage = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(1).split(" ")[2]);
                int currentOil = Integer.parseInt(itemFrame.getItem().getItemMeta().getLore().get(2).split(" ")[2]);
                String endPoint = itemFrame.getItem().getItemMeta().getLore().get(4).split(" ")[2];
                int needOil = Math.round((float) MehaniksSpaceFunctions.getDistance(itemFrame, endPoint) /100);
                String worldName = MehaniksSpaceFunctions.getWorldName(itemFrame, endPoint);

                if (currentOil >= needOil && !worldName.equals("")) {
                    Random random = new Random();
                    World world = getPlugin(MehaniksSpaceCore.class).getServer().getWorld(worldName);

                    int rX = random.nextInt(defaultWorldBorderDistance+1) - defaultWorldBorderDistance/2;
                    int rZ = random.nextInt(defaultWorldBorderDistance+1) - defaultWorldBorderDistance/2;
                    getPlugin(MehaniksSpaceCore.class).getLogger().info(world.getName() + " " + rX + " " + rZ);
                    Location location = new Location(world, rX, 319, rZ);

                    StorageMinecart minecart = null;
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.MINECART_CHEST);

                    for (Entity nearbyEntity : player.getWorld().getNearbyEntities(player.getLocation(), 1, 1, 1)) {
                        if (nearbyEntity.getType().equals(EntityType.MINECART_CHEST)) {
                            minecart = (StorageMinecart) nearbyEntity;
                        }
                    }

                    if (minecart != null) {
                        minecart.getInventory().addItem(MehaniksSpaceItems.getRocket(maxOil, maxStorage, 0, 0, "none"));

                        Container barrel = null;
                        for (Entity nearbyE : itemFrame.getWorld().getNearbyEntities(itemFrame.getLocation(), 2, 2, 2)) {
                            if (nearbyE.getType() == EntityType.GLOW_ITEM_FRAME && nearbyE.getCustomName() != null) {
                                ItemFrame nearbyItemFrame = (ItemFrame) nearbyE;
                                if (nearbyItemFrame.getCustomName().equals("Rocket Conrol Panel")) {
                                    BlockState block = nearbyItemFrame.getWorld().getBlockAt(nearbyItemFrame.getLocation().getBlockX(), nearbyItemFrame.getLocation().getBlockY() - 1, nearbyItemFrame.getLocation().getBlockZ()).getState();
                                    if (block.getType() == Material.BARREL) barrel = (Container) block;
                                }
                            }
                        }

                        if (barrel != null && barrel.getInventory().getContents().length != 0) {
                            for (ItemStack itemStack : barrel.getInventory().getContents()) {
                                if (itemStack != null) {
                                    minecart.getInventory().addItem(itemStack);
                                    itemStack.setAmount(0);
                                }
                            }
                        }
                        minecart.teleport(location);
                    }

                    player.teleport(location);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1200, 1, true, false));
                    itemFrame.remove();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (MehaniksSpaceWorldMap.containsKey(block.getWorld().getName())) {
            int temperature = Integer.parseInt(MehaniksSpaceWorldMap.get(block.getWorld().getName()).get(2));
            if ((temperature <= -5 || temperature >= 5) && MehaniksSpaceFunctions.inActiveOxygenShield(block.getLocation()) == null) {
                if (block.getBlockData() instanceof Sapling) block.setType(Material.DEAD_BUSH);
                else if (block.getType().equals(Material.GRASS_BLOCK)) block.setType(Material.DIRT);
                else if (block.getType().equals(Material.DIRT_PATH)) block.setType(Material.DIRT);
                else if (block.getBlockData() instanceof Ageable) block.breakNaturally();
                else if (block.getType().equals(Material.BAMBOO_SAPLING)) block.breakNaturally();
                else if (block.getType().equals(Material.BIG_DRIPLEAF)) block.breakNaturally();
            }
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Block block = event.getBlock();
        if (MehaniksSpaceWorldMap.containsKey(block.getWorld().getName())) {
            int temperature = Integer.parseInt(MehaniksSpaceWorldMap.get(block.getWorld().getName()).get(2));
            int liquidWaterY = Integer.parseInt(MehaniksSpaceWorldMap.get(block.getWorld().getName()).get(4));
            if (event.getBucket().equals(Material.WATER_BUCKET) &&
                    (liquidWaterY <= block.getLocation().getY() && MehaniksSpaceFunctions.inActiveOxygenShield(block.getLocation()) == null)) {
                if (temperature < 0) block.setType(Material.BLUE_ICE);
                if (temperature > 0) block.setType(Material.AIR);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(Material.PLAYER_HEAD) &&
                ((Skull) block.getState()).getPlayerProfile() != null &&
                MehaniksSpaceItems.meteorites.containsKey(((Skull) block.getState()).getPlayerProfile().getName())) {
            Random random = new Random();
            List<Object> meteorite = MehaniksSpaceItems.meteorites.get(((Skull) block.getState()).getPlayerProfile().getName());
            ItemStack drop = (ItemStack) meteorite.get(0);
            drop.setAmount(random.nextInt(((int) meteorite.get(3) - (int) meteorite.get(2)) + 1) + (int) meteorite.get(2));
            event.setDropItems(false);
            block.setType(Material.AIR);
            block.getWorld().createExplosion(block.getLocation().toCenterLocation(), (Float) meteorite.get(4));
            block.getWorld().dropItem(block.getLocation().toCenterLocation(), drop);
        }
    }

    @EventHandler()
    public void EntityChangeBlockEvent (EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            Entity entity = event.getEntity();
            String[] prop = entity.getName().split(" ");
            if (prop.length == 3 && MehaniksSpaceItems.meteorites.containsKey(prop[0]) || prop[0].equals("none")) {
                if (Boolean.parseBoolean(prop[1])) {
                    if (Float.parseFloat(prop[2]) > 0) entity.getWorld().createExplosion(entity.getLocation(), Float.parseFloat(prop[2]), true);
                    if (!prop[0].equals("none")) MehaniksSpaceFunctions.summonMeteor(entity.getLocation(), prop[0], false, 0, Material.BARRIER);
                } else if (!prop[0].equals("none")) {
                    UUID uuid = UUID.randomUUID();
                    PlayerProfile profile = Bukkit.createProfile(uuid, prop[0]);
                    profile.setProperty(new ProfileProperty("textures", (String) MehaniksSpaceItems.meteorites.get(prop[0]).get(1)));
                    Block block = event.getBlock();
                    block.setType(Material.PLAYER_HEAD);
                    Skull skull = (Skull) block.getState();
                    skull.setPlayerProfile(profile);
                    skull.update();
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        player.setGravity(true);
        player.lockFreezeTicks(false);
    }
}
