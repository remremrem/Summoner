package grokswell.summoner;

//import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import net.aufdemrand.sentry.SentryInstance;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.SimpleSpeechController;

public class MinionTrait extends Trait {
	static ArrayList<String> shoplist;
	ArrayList<String> target_list;
	ArrayList<String> rental_cooldown;
	final SummonerPlugin plugin;
	
	public DataKey trait_key;
	
	LivingEntity master;
	
	String farewellMsg;
	String welcomeMsg;
	String denialMsg;
	String closedMsg;
	String forHireMsg;
	String rentalMsg;
	double comission;
	boolean offduty;
	boolean forhire;
	boolean rental;
	boolean hired;
	boolean rented;
	String location;
	String shop_name;

	public MinionTrait(LivingEntity mstr, Location location) {
		super("summoner");
		plugin = (SummonerPlugin) Bukkit.getServer().getPluginManager().getPlugin("Summoner");
		
		master = mstr;
		
		hire_cooldown = new ArrayList<String>();
		rental_cooldown = new ArrayList<String>();

		farewellMsg = plugin.settings.FAREWELL;
		welcomeMsg = plugin.settings.WELCOME;
		denialMsg = plugin.settings.DENIAL;
		closedMsg = plugin.settings.CLOSED;
		forHireMsg = plugin.settings.FOR_HIRE_MSG;
		rentalMsg = plugin.settings.RENTAL_MSG;
		comission = plugin.settings.NPC_COMMISSION;
		offduty = plugin.settings.OFFDUTY;
		location = null;
		forhire = false;
		rental = false;
		hired = false;
		rented = false;
	}

	@Override
	public void load(DataKey key) {
		this.trait_key = key;
		this.shop_name = key.getString("shop_name");

		// Override defaults if they exist

		if (key.keyExists("welcome.default"))
			this.welcomeMsg = key.getString("welcome.default");
		if (key.keyExists("farewell.default"))
			this.farewellMsg = key.getString("farewell.default");
		if (key.keyExists("denial.default"))
			this.denialMsg = key.getString("denial.default");
		if (key.keyExists("closed.default"))
			this.closedMsg = key.getString("closed.default");
		if (key.keyExists("forHireMsg.default"))
			this.forHireMsg = key.getString("forHireMsg.default");
		if (key.keyExists("rentalMsg.default"))
			this.rentalMsg = key.getString("rentalMsg.default");
		if (key.keyExists("comission.default"))
			this.comission = key.getDouble("comission.default");
		if (key.keyExists("offduty.default"))
			this.offduty = key.getBoolean("offduty.default");
		if (key.keyExists("forhire.default"))
			this.forhire = key.getBoolean("forhire.default");
		if (key.keyExists("rental.default"))
			this.rental = key.getBoolean("rental.default");
		if (key.keyExists("hired.default"))
			this.hired = key.getBoolean("hired.default");
		if (key.keyExists("rented.default"))
			this.rented = key.getBoolean("rented.default");
		if (key.keyExists("location.default"))
			this.location = key.getString("location.default");

	}
	
  
  
	@EventHandler
	public void onRightClick(net.citizensnpcs.api.event.NPCRightClickEvent event) {
		if(this.npc!=event.getNPC()) return;
		
		Player player = event.getClicker();
	}
	
	@EventHandler
	public void onDeath(net.citizensnpcs.api.event.NPCDeathEvent event) {
		if(this.npc!=event.getNPC()) return;
		this.npc.destroy();
	}

//  //Code to add targets to sentry validTargets list
//	@EventHandler
//	public void onDamage(EntityDamageByEntityEvent event) {
//		//make sure this affects this minion or it's master
//		Entity entto = event.getEntity();
//		if (entto != this.npc.getEntity() && entto != (Entity) master) {
//			return;
//		}
//
//		Entity entfrom = event.getDamager();
//		if(	entfrom  instanceof org.bukkit.entity.Projectile){
//			entfrom = ((org.bukkit.entity.Projectile) entfrom).getShooter();
//		}
//		try {
//			if (!this.npc.getTrait(SentryTrait.class).getInstance().containsTarget("PLAYER:"+((Player) entfrom).getName().toUpperCase()) ) {
//				this.npc.getTrait(SentryTrait.class).getInstance().validTargets.add("PLAYER:"+((Player) entfrom).getName().toUpperCase());
//			}
//		} catch (Exception e) {
//			try {
//				if (!this.npc.getTrait(SentryTrait.class).getInstance().containsTarget("NPC:"+((LivingEntity) entfrom).getCustomName().toUpperCase()) ) {
//					this.npc.getTrait(SentryTrait.class).getInstance().validTargets.add("NPC:"+((LivingEntity) entfrom).getCustomName().toUpperCase());
//				}
//			} catch (Exception e2){
//				return;
//			}
//		}
//	}

	@Override
	public void save(DataKey key) {
		key.setString("shop_name", this.shop_name);
		key.setString("farewell.default", this.farewellMsg);
		key.setString("denial.default", this.denialMsg);
		key.setString("welcome.default", this.welcomeMsg);
		key.setString("closed.default", this.closedMsg);
		key.setString("forHireMsg.default", this.forHireMsg);
		key.setString("rentalMsg.default", this.rentalMsg);
		key.setDouble("comission.default", this.comission);
		key.setBoolean("offduty.default", this.offduty);
		key.setBoolean("forhire.default", this.forhire);
		key.setBoolean("rental.default", this.rental);
		key.setBoolean("hired.default", this.hired);
		key.setBoolean("rented.default", this.rented);
		key.setString("location.default", this.location);
		
	}
	
	@Override
	public void onAttach() {
		this.npc.spawn(master.getLocation().add(master.getLocation().getDirection()).add(0,1,0));

		this.npc.addTrait(SentryTrait.class);
		this.npc.getTrait(SentryTrait.class).getInstance().validTargets.add("ENTITY:MONSTERS");
		
		try {
			Player player = (Player) master;
			this.npc.getTrait(Owner.class).setOwner(player.getName());
			this.npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(player.getName());
		} catch (Exception e) {
			this.npc.getTrait(Owner.class).setOwner("Server");
			this.npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(master.getCustomName());
		}
		return;
	}

}