package it.unige.dibris.TExpRVJade.examples.ping_pong;

import it.unige.dibris.TExpRVJade.Channel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class ClientAgentMappedInPingAgent extends Agent{

	@Override
	protected void setup() {
		super.setup();
		//String[] args = (String[]) getArguments();
		//String receiver = args[0];
		//String content = args[1];
		//int waitMilliseconds = Integer.valueOf(args[2]);

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				String receiver = "bob";
				String content = "ping";
				boolean end = false;
				do{
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setSender(getAID());
					msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
					msg.setContent(content);

					doWait(5000);

					System.out.println("[" + getLocalName() + "]: send " + content + " to " + receiver);

					//SimulatedChannel ch = new SimulatedChannel("email", 0.8);
					//Channel ch = Channel.getChannel("email");
					// supposing to send an email here!
					//ch.sent(msg);
					send(msg);

					//Monitor m = Monitor.getMyMonitor(getName());
					//m.addPerception(PerceptionFactory.createSimpleAction(Sender.this, "action1"));

					end = false;

					ACLMessage msgR= blockingReceive();
					if(msgR != null){
						System.out.println("[" + getLocalName() + "]: receive " + msgR.getContent() + " from " + receiver);
						if(msgR.getContent().equals("404")){
							end = true;
						}
					}
				} while(!end);
			}
		});
	}

}
