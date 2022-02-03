package enchsigns.enchantingsigns;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.awt.*;
import java.lang.annotation.Annotation;

import static enchsigns.enchantingsigns.EnchantingSigns.econ;
import static org.bukkit.Bukkit.getServer;

public class onSignClick implements Listener {

    @EventHandler
    public void SignClick(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        Player p = e.getPlayer();

            Block b = e.getClickedBlock();
            if(b.getType() == Material.BIRCH_SIGN || b.getType() == Material.BIRCH_WALL_SIGN){

                Sign sign = (Sign) b.getState();
                if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Encantar]")) {
                    if(!sign.getLine(1).equals("") && !sign.getLine(2).equals("")){

                        String enchName = ChatColor.stripColor(sign.getLine(1));
                        String enchCost = ChatColor.stripColor(sign.getLine(2));
                        enchCost = enchCost.replaceAll("[$]", "");
                        switch(enchName){
                            case "Eficiencia":
                                enchName = "DIG_SPEED";
                                break;
                            case "Soul speed":
                                enchName = "SOUL_SPEED";
                                break;
                            case "Caída de pluma":
                                enchName = "PROTECTION_FALL";
                                break;
                            case "Afin. acuática":
                                enchName = "WATER_WORKER";
                                break;
                            case "Irrompibilidad":
                                enchName = "DURABILITY";
                                break;
                            case "Espinas":
                                enchName = "THORNS";
                                break;
                            case "Prot. Fuego":
                                enchName = "PROTECTION_FIRE";
                                break;
                            case "Protección":
                                enchName = "PROTECTION_ENVIRONMENTAL";
                                break;
                            case "Fortuna":
                                enchName = "LOOT_BONUS_BLOCKS";
                                break;
                            case "Aspecto ígneo":
                                enchName = "FIRE_ASPECT";
                                break;
                            case "Filo":
                                enchName = "DAMAGE_ALL";
                                break;
                            case "Empuje":
                                enchName = "KNOCKBACK";
                                break;
                            case "Fuego":
                                enchName = "ARROW_FIRE";
                                break;
                            case "Poder":
                                enchName = "ARROW_DAMAGE";
                                break;
                            case "Infinidad":
                                enchName = "ARROW_INFINITE";
                                break;
                            default:
                                enchName = null;

                        }
                        Enchantment ench = Enchantment.getByName(enchName);
                        if(p.getInventory().getItemInMainHand().getType().isAir()){
                            String title = (ChatColor.RED+"No estás sosteniendo ningún item!");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(title));
                            return;
                        }else if(p.getInventory().getItemInMainHand().containsEnchantment(ench)){
                            String title = (ChatColor.RED+"Este item ya posee ese encantamiento.");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(title));
                            return;
                        }else if(ench == null){
                            String title = (ChatColor.RED+"Ese encantamiento no existe.");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(title));
                            return;
                        }

                        ItemStack PlayerItem = p.getInventory().getItemInMainHand();
                        if(ench.canEnchantItem(PlayerItem)) {
                            if(!econ.has(p, Double.parseDouble(enchCost))){
                                String title = (ChatColor.RED+"No tienes suficientes monedas.");
                                String subtitle = ChatColor.WHITE+"Necesitas "+ChatColor.RED+enchCost+"⛁";
                                p.sendTitle(title, subtitle);
                                return;
                            }
                            p.getInventory().getItemInMainHand().addEnchantment(ench, 1);
                            econ.withdrawPlayer(p, Double.parseDouble(enchCost));
                            String title = ChatColor.GOLD+"Has encantado este item.";
                            String subtitle = ChatColor.RED+enchCost+"⛁"+ChatColor.GOLD+" Han sido removidos de tu cuenta.";
                            p.sendTitle(title, subtitle);
                        }else{
                            String title = (ChatColor.RED+"Ese encantamiento no funciona con este item.");
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(title));
                        }

                    }

                }
            }
    }
}
