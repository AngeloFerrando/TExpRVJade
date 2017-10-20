package it.unige.dibris.TExpRVJade.examples.web_site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dibris.unige.TExpSWIPrologConnector.exceptions.DecentralizedPartitionNotFoundException;
import it.dibris.unige.TExpSWIPrologConnector.JPL.JPLInitializer;
import it.dibris.unige.TExpSWIPrologConnector.texp.TraceExpression;
import it.dibris.unige.TExpSWIPrologConnector.decentralized.Partition;
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

public class Web_site {
	
	public static void main(String[] args) throws StaleProxyException, DecentralizedPartitionNotFoundException, IOException {
		JPLInitializer.init();
		
		TraceExpression tExp = new TraceExpression("/Users/angeloferrando/Documents/runtime-EclipseApplication/web_site/src-gen/web_site.pl");
		
		/* Initialize JADE environment */
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		AgentContainer container = runtime.createMainContainer(profile);	
		
		List<AgentController> agents = new ArrayList<>();
		
		ClientAgent client = new ClientAgent();
		client.setArguments(new String[] {
		});
		AgentController clientC = container.acceptNewAgent("client", client);
		agents.add(clientC);
		ServerAgent server = new ServerAgent();
		server.setArguments(new String[] {
		});
		AgentController serverC = container.acceptNewAgent("server", server);
		agents.add(serverC);
		/* Centralized monitor */
		
		SnifferMonitorFactory.createAndRunCentralizedMonitor(tExp, container, agents);
		
		Monitor.setErrorMessageGUIVisible(false);
		
		/* Channels creation */
		
		/* Run the agents */
		clientC.start();
		serverC.start();
	}
}
