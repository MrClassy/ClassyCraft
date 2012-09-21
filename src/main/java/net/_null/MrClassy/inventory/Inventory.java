package main.java.net._null.MrClassy.inventory;

//import java.io.IOException;
//import java.util.logging.Logger;


//import org.bukkit.ChatColor;
//import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;


public class Inventory extends JavaPlugin
{
	private void postInit()
	{
		//TODO: Create object that holds all registered events.  Set classes up to register thier events with this object
		//Object will pass an array to a class in Utils that will set up this plugin in bukkit memory.
	}
	public void onEnable()
	{
		postInit();
	}
	
	public void onDisable()
	{
		
	}
}
