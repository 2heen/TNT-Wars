package me.tsbloxorz.tntwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

	public void onEnable() {
		Database database = new Database();
		
		database.getModifyFile().createSection("Players");
		//database.getModifyFile().createSection("Team1");
		//database.getModifyFile().createSection("Team2");
		database.getModifyFile().set("inProgress", false);
		database.saveFile();
		getCommand("tnt").setExecutor(new me.tsbloxorz.tntwars.Commands());
		Bukkit.getPluginManager().registerEvents(new Listeners(), this);
		Game game = new Game();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Database database = new Database();
				System.out.println("inside Runnable! inProgress: " + database.getModifyFile().get("inProgress"));
				if ((boolean) database.getModifyFile().get("inProgress")) {
					game.duringGame();
				}
			}
		}, 5L, 100L);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "TNT WARS ENABLED!");


	}

	public void onDisable() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "TNT WARS DISABLED!");


	}

}
