package it.unige.dibris.TExpRVJade.examples.book_purchase;

import java.util.Random;

import it.unige.dibris.TExpRVJade.Channel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Dave extends Agent{

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				ACLMessage msgR= blockingReceive();
				if(msgR != null && msgR.getContent().equals("is_available")){
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setSender(getAID());
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(new Random().nextBoolean()){
						msg.addReceiver(new AID("emily", AID.ISLOCALNAME));
						msg.setContent("send_me_book");
						System.out.println("[" + getLocalName() + "]: send " + "send_me_book" + " to " + "emily");
						//Channel ch = Channel.getChannel("whatsApp");
						//ch.sent(msg);
					} else{
						msg.addReceiver(new AID("frank", AID.ISLOCALNAME));
						msg.setContent("order_book");
						System.out.println("[" + getLocalName() + "]: send " + "order_book" + " to " + "frank");
						//Channel ch = Channel.getChannel("email");
						//ch.sent(msg);
					}
					send(msg);
				}
			}
		});
	}

}
