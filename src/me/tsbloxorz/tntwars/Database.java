package me.tsbloxorz.tntwars;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class Database {
	private File file;
	private YamlConfiguration modifyFile;
	
	public void initiateFiles() throws IOException {
		/*file = new File(Bukkit.getServer().getPluginManager().getPlugin("TNTWars").getDataFolder(), "database.yml");
		if (!file.exists()) {
			file.createNewFile();
		} //NOTE: WHEN CALLING THIS METHOD MAKE SURE TO SURROUND THE METHOD IN A TRY CATCH LOOP
		
		modifyFile = YamlConfiguration.loadConfiguration(file); */
	} 
	public Database() {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("TNTWars").getDataFolder(), "database.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} //NOTE: WHEN CALLING THIS METHOD MAKE SURE TO SURROUND THE METHOD IN A TRY CATCH LOOP
		
		modifyFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getModifyFile() {
		return modifyFile;
	}
	public File getFile() {
		return file;
	}
	public void saveFile() {
		try {
			modifyFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


