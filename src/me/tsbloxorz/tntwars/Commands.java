package me.tsbloxorz.tntwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Database database = new Database();
    	Game game = new Game();
    	if (label.equalsIgnoreCase("tnt")) {
    		if (args.length == 0) {
    			sender.sendMessage("Do /tnt help for more info!"); //REMEMBER TO ADD THIS IN AFTER UR DONE WITH ALL THE OTHER COMMANDS
    		} else if (args[0].equalsIgnoreCase("join")) {
    			if ((boolean) database.getModifyFile().get("inProgress")) {
    				sender.sendMessage("Game in progress! Please wait until the game is over!");
    			} else {
	    			sender.sendMessage("Joining game...");
	    			List<Player> playerNames = new ArrayList<Player>();
	    			
	    			/*int numberOfPlayers = database.getModifyFile().getInt("CurrentPlayers") + 1;
	    			database.getModifyFile().set("CurrentPlayers", numberOfPlayers); */
	    			playerNames = (List<Player>) database.getModifyFile().getList("Players", playerNames); 
	    			System.out.println("Playernames from database:" + playerNames);
	    			Player player = (Player) sender;
	    			playerNames.add(player);
	    			database.getModifyFile().set("Players", playerNames); 
	    			database.saveFile();
	    			sender.sendMessage("Joined game!");
    			}
    		} else if (args[0].equalsIgnoreCase("start")) {
    			Player player = (Player) sender;
    			game.gameStart(player.getLocation());
    			sender.sendMessage("Game Starting!!");
    			
    		} else if (args[0].equalsIgnoreCase("stop")) {
    			if ((boolean) database.getModifyFile().get("inProgress")) {
    				game.gameEnd();
    			} else {
    				sender.sendMessage("No Games are currently happening!");
    			}
    		} else if (args[0].equalsIgnoreCase("leave")) {
    			Player player = (Player) sender;
    			List<Player> players = new ArrayList<Player>();
    			players = (List<Player>) database.getModifyFile().getList("Players", players);
    			if (players.contains(player)) {
    				game.gameEnd(player);
    			} else {
    				player.sendMessage("You are not in a game!");
    			}
    		} else if (args[0].equalsIgnoreCase("help")) {
    			sender.sendMessage("Welcome to TNT Wars! A plugin created by TSBloxorz!");
    			sender.sendMessage("Here are a list of the possible commands:");
    			sender.sendMessage("/tnt join - joins the game");
    			sender.sendMessage("/tnt leave - leaves the game");
    			sender.sendMessage("/tnt start - starts the game (must have permission)");
    			sender.sendMessage("/tnt stop - stops the game (must have permission)");
    		}
     	}
    	return true;
    }


}
