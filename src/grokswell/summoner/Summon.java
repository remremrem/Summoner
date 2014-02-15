package grokswell.summoner;

import static java.lang.System.out;

import net.aufdemrand.sentry.Sentry;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;



public class Summon {
	String monsterType;
	int monsterAmount;
	Player player;
	NPCRegistry npcreg = CitizensAPI.getNPCRegistry();
	//final Sentry sentryplug;
	
	public Summon(String[] args, Player plyer) {
		monsterType = args[0];
		monsterAmount = Integer.parseInt(args[1]);
		player = plyer;
		//sentryplug = (Sentry) Bukkit.getServer().getPluginManager().getPlugin("Sentry");
		
		//out.println(npc.getTrait(SentryTrait.class).getInstance().validTargets);

		int count = monsterAmount;
		while (count > 1) {
			spawnMinion();
			count = count-1;
		}
	}
	
	public void spawnMinion() {
		NPC npc = npcreg.createNPC(EntityType.valueOf(monsterType), monsterType);
		npc.spawn(player.getLocation().add(player.getLocation().getDirection()));
		npc.getTrait(Owner.class).setOwner(player.getName());
		npc.addTrait(SentryTrait.class);
		npc.getTrait(SentryTrait.class).getInstance().validTargets.add("ENTITY:MONSTERS");
		npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(player.getName());
	}
	
	public void cloneMinion(NPC n) {
		NPC npc = n.clone();
		npc.teleport(player.getLocation().add(1,1,1), TeleportCause.PLUGIN);
	}
}
