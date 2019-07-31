package me.tsbloxorz.tntwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Game {
	Database database = new Database();
	public Cuboid arena;
	public Cuboid wall;

	private void buildArena(Location loc) { //done
		//build floor
		Location loc1 = new Location(loc.getWorld(), loc.getX() + 20, loc.getY() + 20, loc.getZ() + 10);
		Location loc2 = new Location(loc.getWorld(), loc.getX() - 20, loc.getY() + 18, loc.getZ() - 10);
		arena = new Cuboid(loc1, loc2);
		System.out.println("Loc1: " + loc1);
		System.out.println("Loc2: " + loc2);
		int x1 = (int) Math.min(loc1.getX(), loc2.getX());
		int x2 = (int) Math.max(loc1.getX(), loc2.getX());
		int y1 = (int) Math.min(loc1.getY(), loc2.getY());
		int y2 = (int) Math.max(loc1.getY(), loc2.getY());
		int z1 = (int) Math.min(loc1.getZ(), loc2.getZ());
		int z2 = (int) Math.max(loc1.getZ(), loc2.getZ());
	
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					//System.out.println("inside buildArena loop!");
					Location locBlock = new Location(loc.getWorld(), x, y, z);
					locBlock.getBlock().setType(Material.COBBLESTONE); 
				}
			}
		}
		//build huge wall
		Location loc3 = new Location(loc.getWorld(), loc.getX() + 1, loc.getY() + 30, loc.getZ() + 10);
		Location loc4 = new Location(loc.getWorld(), loc.getX() - 1, loc.getY() + 18, loc.getZ() - 10);
		wall = new Cuboid(loc3, loc4);
		x1 = (int) Math.min(loc3.getX(), loc4.getX());
		x2 = (int) Math.max(loc3.getX(), loc4.getX());
		y1 = (int) Math.min(loc3.getY(), loc4.getY());
		y2 = (int) Math.max(loc3.getY(), loc4.getY());
		z1 = (int) Math.min(loc3.getZ(), loc4.getZ());
		z2 = (int) Math.max(loc3.getZ(), loc4.getZ());
	
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					//System.out.println("inside buildArena loop!");
					Location locBlock = new Location(loc.getWorld(), x, y, z);
					locBlock.getBlock().setType(Material.COBBLESTONE); 
				}
			}
		}
	}


	private void playerMats(Player player) { //give players the items, add more items later
		if (!player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE, 1));
		}
		if (!player.getInventory().contains(Material.TNT)) {
			player.getInventory().addItem(new ItemStack(Material.TNT, 64));
		}
		if (!player.getInventory().contains(Material.REDSTONE)) {
			player.getInventory().addItem(new ItemStack(Material.REDSTONE, 64));
		}
		if (!player.getInventory().contains(Material.COBBLESTONE)) {
			player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 64));
		}
		if (!player.getInventory().contains(Material.REDSTONE_COMPARATOR)) {
			player.getInventory().addItem(new ItemStack(Material.REDSTONE_COMPARATOR, 16));
		}
		if (!player.getInventory().contains(Material.REDSTONE_TORCH_ON)) {
			player.getInventory().addItem(new ItemStack(Material.REDSTONE_TORCH_ON, 16));
		}
		if (!player.getInventory().contains(Material.STONE_BUTTON)) {
			player.getInventory().addItem(new ItemStack(Material.STONE_BUTTON, 4));
		}
		
	}

	private void teleportPlayers(Location loc) {
		List<Player> players = new ArrayList<Player>();
		List<Player> team1 = new ArrayList<Player>();
		List<Player> team2 = new ArrayList<Player>();
		players = (List<Player>) database.getModifyFile().getList("Players", players);
		team1 = players.subList(0, players.size() / 2);
		team2 = players.subList(players.size() / 2, players.size());
		/*database.getModifyFile().createSection("Team1");
		database.getModifyFile().createSection("Team2");
		database.getModifyFile().set("Team1", team1);
		database.getModifyFile().set("Team2", team2); */
		
		Location loc1 = new Location(loc.getWorld(), loc.getX() + 10, loc.getY() + 20, loc.getZ() + 5);
		Location loc2 = new Location(loc.getWorld(), loc.getX() - 10, loc.getY() + 20, loc.getZ() - 5);
		for (int i = 0; i < team1.size(); i++) {
			team1.get(i).teleport(loc1);
		}
		for (int i = 0; i < team2.size(); i++) {
			team2.get(i).teleport(loc2);
		}
	}
	

	public void gameStart(Location loc) {
		buildArena(loc);
		teleportPlayers(loc);
		database.getModifyFile().set("OriginalLocation", loc);
		List<Player> players = new ArrayList<Player>();
		players = (List<Player>) database.getModifyFile().getList("Players", players);
		for (int i = 0; i < players.size(); i++) {
			players.get(i).getInventory().clear();
			players.get(i).setGameMode(GameMode.SURVIVAL);
		}
		database.getModifyFile().set("inProgress", true);
		database.saveFile();
		/*while loop causes server to lag, maybe include this in the playerMoveEvent or something
		//while (inProgress) {
			for (int i = 0; i < players.size(); i++) {
				playerMats(players.get(i));
				players.get(i).setFoodLevel(20);
				
			}
		} */
	}
	public void duringGame() {
		Database database = new Database();
		List<Player> players = new ArrayList<Player>();
		players = (List<Player>) database.getModifyFile().getList("Players", players);
		System.out.println("Players: " + players + " inProgress: " + database.getModifyFile().get("inProgress"));
		for (int i = 0; i < players.size(); i++) {
			playerMats(players.get(i));
			players.get(i).setFoodLevel(20);
			
		}
		
	}
	public void gameEnd(Player player) { //this is the ending of the game for 1 player
		List<Player> players = new ArrayList<Player>();
		players = (List<Player>) database.getModifyFile().getList("Players", players);
		if (players.contains(player)) {
			players.remove(player);
			database.getModifyFile().set("Players", players);
			database.saveFile();
			player.teleport((Location) database.getModifyFile().get("OriginalLocation"));
			player.sendMessage("You have left the game! Thanks for playing!");
		}
		if (players.size() == 0) {
			gameEnd();
		}
		
	}
	public void gameEnd() { //this ends the game for everyone
		List<Player> players = new ArrayList<Player>();
		players = (List<Player>) database.getModifyFile().getList("Players", players);
		for (int i = 0; i < players.size(); i++) {
			players.get(i).sendMessage("You have left the game! Thanks for playing!");
			players.get(i).teleport((Location) database.getModifyFile().get("OriginalLocation"));
		}
		List<Player> players1 = new ArrayList<Player>();
		database.getModifyFile().set("Players", players1);
		database.getModifyFile().set("TNT_Velocity", null);
		database.getModifyFile().set("TNT_Ticks", null);
		database.getModifyFile().set("Kits", null);
		database.getModifyFile().set("Team_Red", null);
		database.getModifyFile().set("Team_Blue", null);
		database.getModifyFile().set("Spectators", null);
		database.getModifyFile().set("inProgress", false);
		database.saveFile();
		Bukkit.broadcastMessage(ChatColor.GREEN + "The game has ended! Use /bl join to join the next game!");
		
	}




}
