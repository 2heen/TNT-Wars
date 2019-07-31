package me.tsbloxorz.tntwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class Listeners implements Listener {


	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		Database database = new Database();
		e.getPlayer().sendMessage("yo");
		if ((boolean) database.getModifyFile().get("inProgress")) {
			e.getPlayer().sendMessage("playerDropItemEvent!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void toggleFlight(PlayerToggleFlightEvent e) {
		Database database = new Database();
		if ((boolean) database.getModifyFile().get("inProgress")) {
			e.getPlayer().sendMessage("playertoggleFlightEvent!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pickupItem(PlayerPickupItemEvent e) {
		Database database = new Database();
		if ((boolean) database.getModifyFile().get("inProgress")) {
			//e.getPlayer().sendMessage("playerPickupItemEvent!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void regainHealth(EntityRegainHealthEvent e) {
		Database database = new Database();
		if ((boolean) database.getModifyFile().get("inProgress")) {
			System.out.println("playerregainHealthEvent!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		Game game = new Game();
		Database database = new Database();
		if ((boolean) database.getModifyFile().get("inProgress")) {
			List<Player> players = new ArrayList<Player>();
			players = (List<Player>) database.getModifyFile().getList("Players", players);
			game.gameEnd(e.getPlayer());
		}
	}
	
	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		Game game = new Game();
		Database database = new Database();
		if ((boolean) database.getModifyFile().get("inProgress")) {
			game.gameEnd(e.getEntity());
		}
	}
	
	@EventHandler
	public void walkOffMap(PlayerMoveEvent e) { //fix this later, ask baba, wall is always null
		Game game = new Game();
		if (game.wall != null) {
			System.out.println("wall isn't null!");
			if (game.wall.containsLocation(e.getPlayer().getLocation())) {
				e.getPlayer().sendMessage("inside the wall!");
				e.setCancelled(true);
			}
		}
	} 
	
	
	

}
