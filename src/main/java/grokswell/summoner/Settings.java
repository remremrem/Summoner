package grokswell.summoner;

//import static java.lang.System.out;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Settings {
	private static File dataFolder;
	private SummonerPlugin plugin;
	YamlConfiguration config;
	String ymlFilename = "config.yml";
	
	//settings
	private double max_spawn_per_second;
	private int max_spawn_per_player;
	private int max_spawn_per_npc;
	private boolean use_mob_permissions;
  
    public Settings(SummonerPlugin plgn) {
        plugin = plgn;
		dataFolder = plugin.getDataFolder();
		config = new YamlConfiguration();
		if ( !dataFolder.isDirectory() )  dataFolder.mkdir();
		loadConfigData();
    }
  
  
    public YamlConfiguration getConfigData() {
    	return this.config;
    }
  
    public void saveConfigData(String path, Object value) {
		File configFile = null;
		config.set(path, value);
		configFile = new File(dataFolder, ymlFilename);
		try {
				config.save(configFile);
		}
		catch(IOException ex) {
			plugin.getLogger().severe("Cannot save to "+ymlFilename);
		}
    }
  
    private void loadConfigData() {
		File configFile = null;
		configFile = new File(dataFolder, ymlFilename);
		try {
			if (configFile.exists()) {
				config.load(configFile);
				max_spawn_per_second = config.getDouble("max_spawn_per_second");
				max_spawn_per_player = config.getInt("max_spawn_per_player");
				max_spawn_per_npc = config.getInt("max_spawn_per_npc");
				use_mob_permissions = config.getBoolean("use_mob_permissions");
			}
		}
		catch (InvalidConfigurationException e) {
			plugin.getLogger().severe("Invalid "+ymlFilename+" file");
		} 
		catch(IOException ex) {
			plugin.getLogger().severe("Cannot load "+ymlFilename);
		}
    }


	public double getMax_spawn_per_second() {
		return max_spawn_per_second;
	}
	
	
	public void setMax_spawn_per_second(double max_spawn_per_second) {
		this.max_spawn_per_second = max_spawn_per_second;
	}
	
	
	public int getMax_spawn_per_player() {
		return max_spawn_per_player;
	}
	
	
	public void setMax_spawn_per_player(int max_spawn_per_player) {
		this.max_spawn_per_player = max_spawn_per_player;
	}
	
	
	public int getMax_spawn_per_npc() {
		return max_spawn_per_npc;
	}
	
	
	public void setMax_spawn_per_npc(int max_spawn_per_npc) {
		this.max_spawn_per_npc = max_spawn_per_npc;
	}
	
	
	public boolean isUse_mob_permissions() {
		return use_mob_permissions;
	}
	
	
	public void setUse_mob_permissions(boolean use_mob_permissions) {
		this.use_mob_permissions = use_mob_permissions;
	}
}

