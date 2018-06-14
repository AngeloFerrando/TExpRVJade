package it.unige.dibris.TExpRVJade.examples.book_purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dibris.unige.TExpSWIPrologConnector.exceptions.DecentralizedPartitionNotFoundException;
import it.dibris.unige.TExpSWIPrologConnector.JPL.JPLInitializer;
import it.dibris.unige.TExpSWIPrologConnector.texp.TraceExpression;
import it.dibris.unige.TExpSWIPrologConnector.decentralized.Partition;
import it.dibris.unige.TExpSWIPrologConnector.decentralized.Condition;
import it.dibris.unige.TExpSWIPrologConnector.decentralized.ConditionsFactory;
import it.unige.dibris.TExpRVJade.Channel;
import it.unige.dibris.TExpRVJade.Monitor;
import it.unige.dibris.TExpRVJade.SimulatedChannel;
import it.unige.dibris.TExpRVJade.SnifferMonitorFactory;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Book_purchase {
	
	public static void main(String[] args) throws StaleProxyException, DecentralizedPartitionNotFoundException, IOException {
		JPLInitializer.init();
		
		TraceExpression tExp = new TraceExpression("/Users/angeloferrando/Documents/workspace/rivertools_test/src-gen/book_purchase.pl");
		
		/* Initialize JADE environment */
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		AgentContainer container = runtime.createMainContainer(profile);	
		
		List<AgentController> agents = new ArrayList<>();
		
		Alice alice = new Alice();
		alice.setArguments(new String[] {
		});
		AgentController aliceC = container.acceptNewAgent("alice", alice);
		agents.add(aliceC);
		Barbara barbara = new Barbara();
		barbara.setArguments(new String[] {
		});
		AgentController barbaraC = container.acceptNewAgent("barbara", barbara);
		agents.add(barbaraC);
		Carol carol = new Carol();
		carol.setArguments(new String[] {
		});
		AgentController carolC = container.acceptNewAgent("carol", carol);
		agents.add(carolC);
		Dave dave = new Dave();
		dave.setArguments(new String[] {
		});
		AgentController daveC = container.acceptNewAgent("dave", dave);
		agents.add(daveC);
		Emily emily = new Emily();
		emily.setArguments(new String[] {
		});
		AgentController emilyC = container.acceptNewAgent("emily", emily);
		agents.add(emilyC);
		Frank frank = new Frank();
		frank.setArguments(new String[] {
		});
		AgentController frankC = container.acceptNewAgent("frank", frank);
		agents.add(frankC);
		/* Create and Set the partition */
		List<Condition<String>> constraints = new ArrayList<>();
		constraints.add(ConditionsFactory.createNumberAgentsForConstraintCondition(1,3));				
		constraints.add(ConditionsFactory.createMustBeSplitCondition("alice","barbara"));
		constraints.add(ConditionsFactory.createMustBeTogetherCondition("carol","dave"));
		Partition<String> partition = tExp.getRandomMonitoringSafePartition(constraints);
		
		/* Decentralized monitors */
		
		for(Monitor m : SnifferMonitorFactory.createDecentralizedMonitors(tExp, partition, agents)){
			container.acceptNewAgent(m.getMonitorName(), m).start();
		}
		
		Monitor.setErrorMessageGUIVisible(false);
		
		/* Channels creation */
		
		/* Run the agents */
		aliceC.start();
		barbaraC.start();
		carolC.start();
		daveC.start();
		emilyC.start();
		frankC.start();
	}
}
