package it.unige.dibris.TExpRVJade.examples.ping_pong;

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

public class Ping_pong {
	
	public static void main(String[] args) throws StaleProxyException, DecentralizedPartitionNotFoundException, IOException {
		JPLInitializer.init();
		
		TraceExpression tExp = new TraceExpression("/Users/angeloferrando/Documents/workspace/rivertools_test/src-gen/ping_pong.pl");
		
		/* Initialize JADE environment */
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		AgentContainer container = runtime.createMainContainer(profile);	
		
		List<AgentController> agents = new ArrayList<>();
		
		PingAgent alice = new PingAgent();
		alice.setArguments(new String[] {
		});
		AgentController aliceC = container.acceptNewAgent("alice", alice);
		agents.add(aliceC);
		PongAgent bob = new PongAgent();
		bob.setArguments(new String[] {
		});
		AgentController bobC = container.acceptNewAgent("bob", bob);
		agents.add(bobC);
		/* Create and Set the partition */
		List<Condition<String>> constraints = new ArrayList<>();
		constraints.add(ConditionsFactory.createMustBeTogetherCondition("alice","bob"));
		Partition<String> partition = tExp.getRandomMonitoringSafePartition(constraints);
		
		/* Decentralized monitors */
		
		for(Monitor m : SnifferMonitorFactory.createDecentralizedMonitors(tExp, partition, agents)){
			container.acceptNewAgent(m.getMonitorName(), m).start();
		}
		
		Monitor.setErrorMessageGUIVisible(false);
		
		/* Channels creation */
		
		/* Run the agents */
		aliceC.start();
		bobC.start();
	}
}
