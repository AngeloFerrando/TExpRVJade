package it.unige.dibris.TExpRVJade.examples.book_purchase;

import it.unige.dibris.TExpRVJade.Channel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Alice extends Agent{

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setSender(getAID());
				msg.addReceiver(new AID("barbara", AID.ISLOCALNAME));
				msg.setContent("buy_me_book");

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("[" + getLocalName() + "]: send " + "buy_me_book" + " to " + "barbara");
				
				Channel ch = Channel.getChannel("whatsApp");
				// supposing to send a whatsApp message here!
				//ch.sent(msg);
				send(msg);

				//Monitor m = Monitor.getMyMonitor(getName());
				//m.addPerception(PerceptionFactory.createSimpleAction(Sender.this, "action1"));

				ACLMessage msgR= blockingReceive();
				if(msgR != null)
					System.out.println("[" + getLocalName() + "]: receive " + msgR.getContent() + " from " + msgR.getSender().getLocalName());
			}
		});
	}

}
