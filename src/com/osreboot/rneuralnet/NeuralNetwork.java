package com.osreboot.rneuralnet;

import com.osreboot.ridhvl.action.HvlAction2;

public class NeuralNetwork {

	private Sensor[] sensors;
	private Neuron[] neurons;
	
	public NeuralNetwork(Sensor sensorsArg[], Neuron neuronsArg[]){
		sensors = sensorsArg;
		neurons = neuronsArg;
	}
	
	public static class Sensor{
		
		private HvlAction2<Float, Sensor> getInput;
		private float sensoryValue = 0;
		
		public Sensor(HvlAction2<Float, Sensor> getInputArg){
			getInput = getInputArg;
		}
		
		public void update(float delta){
			getInput.run(delta, this);//TODO add more to this
		}
		
	}
	
	public static class Neuron{
		
	}
	
}