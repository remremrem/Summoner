package grokswell.summoner;

import static java.lang.System.out;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;



public class Summoner {
	String monsterType;
	int monsterAmount;
	Player player;
	NPCRegistry npcreg = CitizensAPI.getNPCRegistry();
	//final Sentry sentryplug;
	
	public void summon(String[] args, Player plyer) {
		monsterType = args[0];
		try {
			monsterAmount = Integer.parseInt(args[1]);
		} catch (Exception e) {
			player.sendMessage("You must say how many minions you are summoning "+ChatColor.RED+"/summon "+monsterType+" 2");
			return;
		}
		player = plyer;
		//sentryplug = (Sentry) Bukkit.getServer().getPluginManager().getPlugin("Sentry");
		
		//out.println(npc.getTrait(SentryTrait.class).getInstance().validTargets);

		int count = monsterAmount;
		while (count > 1) {
			spawnMinion();
			count = count-1;
		}
	}
	
	
	public boolean spawnMinion() {
		EntityType mtype;
		try {
			mtype = EntityType.valueOf(monsterType.toUpperCase());
		} catch (Exception e) {
			player.sendMessage(monsterType+" is not a valid minion type.");
			return false;
		}
		
		NPC npc = npcreg.createNPC(mtype, monsterType);
		npc.spawn(player.getLocation().add(player.getLocation().getDirection()).add(0,1,0));
		npc.getTrait(Owner.class).setOwner(player.getName());
		npc.addTrait(SentryTrait.class);
		npc.getTrait(SentryTrait.class).getInstance().validTargets.add("ENTITY:MONSTERS");
		npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(player.getName());
		return true;
	}
	
	public void cloneMinion(NPC n) {
		NPC npc = n.clone();
		npc.teleport(player.getLocation().add(1,1,1), TeleportCause.PLUGIN);
	}
}
