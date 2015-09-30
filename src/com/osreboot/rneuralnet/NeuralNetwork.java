package com.osreboot.rneuralnet;

import java.util.HashMap;

public class NeuralNetwork {

	private Entity parent;
	private float stimulation = 0;
	private HashMap<Neuron, Integer> layers = new HashMap<Neuron, Integer>();
	private int layerLength;
	
	public static Neuron[][] generateNeuralMap(int xArg, int yArg){
		Neuron[][] output = new Neuron[xArg][yArg];
		for(int y = 0; y < output.length; y++){
			for(int x = 0; x < output[y].length; x++){
				output[y][x] = new Neuron();
			}
		}
		return output;
	}
	
	public NeuralNetwork(Entity parentArg, Sensor[] sensorsArg, Neuron[][] neuronsArg){
		parent = parentArg;
		for(int y = 0; y < neuronsArg.length; y++){
			for(int x = 0; x < neuronsArg[y].length; x++){
				layers.put(neuronsArg[y][x], x);
			}
		}
		layerLength = neuronsArg.length;
		for(int y = 0; y < layerLength; y++){
			for(Neuron n : layers.keySet()){
				
			}
		}
	}
	
	public void update(float delta){
		
	}
	
	public Entity getParent(){
		return parent;
	}

	public float getStimulation(){
		return stimulation;
	}

	public void setStimulation(float stimulation){
		this.stimulation = stimulation;
	}

	public static abstract class Sensor{
		
		private float sensoryValue = 0;
		
		public Sensor(){}
		
		public void update(float delta, NeuralNetwork networkArg){
			sensoryValue = fetchSensoryValue();
		}
		
		public abstract float fetchSensoryValue();

		public float getSensoryValue(){
			return sensoryValue;
		}

		public void setSensoryValue(float sensoryValue){
			this.sensoryValue = sensoryValue;
		}
		
	}
	
	public static class Neuron{
		
		//private HashMap<>
		
		public Neuron(){}
		
		public void establishConnections(){
			//TODO
		}
		
		public void update(float delta, NeuralNetwork networkArg){
			
		}
		
	}
	
	public static abstract class Output extends Neuron{
		
		public Output(){}
		
		public abstract void performAction(float inputArg);
		
	}
	
}