package it.unige.dibris.TExpRVJade.examples.book_purchase;

import it.unige.dibris.TExpRVJade.Channel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Barbara extends Agent{

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				ACLMessage msgR= blockingReceive();
				if(msgR != null && msgR.getContent().equals("buy_me_book")){
					System.out.println("[" + getLocalName() + "]: receive " + msgR.getContent() + " from " + msgR.getSender().getLocalName());
				
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setSender(getAID());
					msg.addReceiver(new AID("carol", AID.ISLOCALNAME));
					msg.setContent("reserve_me_book");
	
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("[" + getLocalName() + "]: send " + "reserve_me_book" + " to " + "carol");
	
					//Channel ch = Channel.getChannel("email");
					// supposing to send a email message here!
					//ch.sent(msg);
					send(msg);
					
					msgR= blockingReceive();
					if(msgR != null && msgR.getContent().equals("book_available")){
						
					}
				}
			}
		});
	}

}
