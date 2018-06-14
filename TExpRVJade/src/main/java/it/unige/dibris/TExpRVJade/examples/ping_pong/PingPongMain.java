package it.unige.dibris.TExpRVJade.examples.ping_pong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dibris.unige.TExpSWIPrologConnector.JPL.JPLInitializer;
import it.dibris.unige.TExpSWIPrologConnector.exceptions.DecentralizedPartitionNotFoundException;
import it.dibris.unige.TExpSWIPrologConnector.texp.TraceExpression;
import it.unige.dibris.TExpRVJade.Monitor;
import it.unige.dibris.TExpRVJade.SnifferMonitorFactory;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class PingPongMain {

	public static void main(String[] args) throws StaleProxyException, DecentralizedPartitionNotFoundException, IOException {
		JPLInitializer.init();

		TraceExpression tExp = new TraceExpression("/Users/angeloferrando/Documents/workspace/rivertools_test/src-gen/ping_pong.pl");

		/* Initialize JADE environment */
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		AgentContainer container = runtime.createMainContainer(profile);

		List<AgentController> agents = new ArrayList<>();

		//Agent alice = new PingAgent();
		//Agent alice = new ClientAgentMappedInPingAgent();
		MsgAgent alice = new MsgAgent();
		alice.setArguments(new Object[] {
				"bob", true
		});
		AgentController aliceC = container.acceptNewAgent("alice", alice);
		agents.add(aliceC);
		//Agent bob = new PongAgent();
		MsgAgent bob = new MsgAgent();
		bob.setArguments(new Object[] {
				"alice", false
		});
		AgentController bobC = container.acceptNewAgent("bob", bob);
		agents.add(bobC);
		/* Centralized monitor */

		SnifferMonitorFactory.createAndRunCentralizedMonitor(tExp, container, agents);

		Monitor.setErrorMessageGUIVisible(false);

		/* Channels creation */

		/* Run the agents */
		aliceC.start();
		bobC.start();
	}
}
