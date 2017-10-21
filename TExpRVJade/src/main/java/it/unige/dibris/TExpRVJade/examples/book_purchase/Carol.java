package it.unige.dibris.TExpRVJade.examples.book_purchase;

import it.unige.dibris.TExpRVJade.Channel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Carol extends Agent{

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				ACLMessage msgR= blockingReceive();
				if(msgR != null && msgR.getContent().equals("reserve_me_book")){
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setSender(getAID());
					msg.addReceiver(new AID("dave", AID.ISLOCALNAME));
					msg.setContent("is_available");
	
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("[" + getLocalName() + "]: send " + "is_available" + " to " + "dave");
					
					//Channel ch = Channel.getChannel("whatsApp");
					// supposing to send a email message here!
					//ch.sent(msg);
					send(msg);
				}
			}
		});
	}

}
