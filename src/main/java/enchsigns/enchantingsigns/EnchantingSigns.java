package enchsigns.enchantingsigns;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class EnchantingSigns extends JavaPlugin {
    static Economy econ = null;

    static String pluginName = ChatColor.RED+"["+ChatColor.GRAY+"EnchantingSigns"+ChatColor.RED+"]"+ChatColor.WHITE+" ";


    @Override
    public void onEnable() {
        Startup();

    }

    @Override
    public void onDisable() {
        showDisable();
    }

    public void Startup(){
        Bukkit.getConsoleSender().sendMessage(pluginName+ChatColor.GRAY+"Cargando Enchanting-Signs por ImRama");

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new onSignClick(), this);
        Bukkit.getConsoleSender().sendMessage(pluginName+ChatColor.GRAY+"Registrando eventos...");
        setupEconomy();


    }
    public void showDisable(){
        Bukkit.getConsoleSender().sendMessage(pluginName+ChatColor.GRAY+"Desactivando Enchanting-Signs por ImRama");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        Bukkit.getConsoleSender().sendMessage(pluginName+ChatColor.GRAY+"Conectando con Vault...");
        return econ != null;
    }
}
