package grokswell.summoner;



import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class SummonerPlugin extends JavaPlugin implements Listener {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		
		//REMOTEMENU 
		if (cmd.getName().equalsIgnoreCase("summon")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use the command "+ChatColor.RED+"/summon");
				return true;
			}
			
			Player player = (Player) sender;
			new Summoner().summon(args, player);
			return true;
		}
		return true;
	}
}