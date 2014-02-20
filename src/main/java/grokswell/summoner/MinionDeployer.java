package grokswell.summoner;

import java.util.ArrayList;

import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class MinionDeployer {
	SummonerPlugin plugin;
	ArrayList<deploymentOrder> minionQueue;
	DeploymentOrderExecutor deployer;
	double max_minions_per_second;
	int delay;
	boolean queueHalted;
	
	
	public MinionDeployer(SummonerPlugin plgn) {
		plugin = plgn;
		minionQueue = new ArrayList<deploymentOrder>();
		deployer = null;
		queueHalted = true;
		max_minions_per_second = 2;
		Double d = 20/max_minions_per_second;
		delay = d.intValue();
		if (delay == 0) delay = 1;
	}
	
	public void add(LivingEntity ownr, String minionType, int minionAmount){
		minionQueue.add(new deploymentOrder(ownr, minionType, minionAmount));
		if (queueHalted = true) {
			startQueue(new deploymentOrder(ownr, minionType, minionAmount));
		}
	}
	
	private void startQueue(deploymentOrder d){
		if (deployer == null){
			deployer = new DeploymentOrderExecutor();
		}
		queueHalted = false;
		deployer.runTaskLater(this.plugin, delay);
		
		
	}
	
	private void nextOrder(){
		deployer.runTaskLater(this.plugin, delay);
		
	}
	
	private void haltQueue(){
		queueHalted = true;
	}
	
	
	class DeploymentOrderExecutor extends BukkitRunnable {
		deploymentOrder d;
		public DeploymentOrderExecutor() {
        	d = minionQueue.get(0);
        }
        
        public void run() {
        	minionQueue.remove(0);
            if (minionQueue.isEmpty()) {
            	haltQueue();
            } else {
            	nextOrder();
            }
        }
    }
	
	class deploymentOrder {
		public deploymentOrder(LivingEntity ownr, String minionType, int minionAmount) {
			//new Summoner().summon(args, ownr)
		}
	}
}
