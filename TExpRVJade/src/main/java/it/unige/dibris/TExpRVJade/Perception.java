package it.unige.dibris.TExpRVJade;

import java.util.List;

import jade.core.Agent;

public interface Perception {
	
	public String toPrologRepresentation();
	
	public List<Agent> getAgentsInvolved();
	
}
