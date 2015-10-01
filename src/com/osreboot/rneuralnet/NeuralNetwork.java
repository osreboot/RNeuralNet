package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class NeuralNetwork {

	public static final float DRAWING_PAN = 0.35f,
			DRAWING_HSPACE = 30,
			DRAWING_VSPACE = 80,
			DRAWING_SIZE = 20,
			DRAWING_STIM_MAG = 0.1f,
			MODIFIER_LIMIT = 10;

	private boolean drawing = false;
	private Entity parent;
	private float stimulation = 0;
	private HashMap<Neuron, Integer> layers = new HashMap<Neuron, Integer>();
	private int layerLength;

	public static Neuron[] generateBlankNeuralSet(int lengthArg){
		Neuron[] output = new Neuron[lengthArg];
		for(int i = 0; i < output.length; i++){
			output[i] = new Hidden();
		}
		return output;
	}

	public NeuralNetwork(Entity parentArg, Neuron[][] neuronsArg){
		parent = parentArg;
		for(int y = 0; y < neuronsArg.length; y++){
			for(int x = 0; x < neuronsArg[y].length; x++){
				layers.put(neuronsArg[y][x], y);
			}
		}
		layerLength = neuronsArg.length;
		for(Neuron n : layers.keySet()){
			n.establishInputs(this);
		}
	}

	public void update(float delta){
		for(int y = 0; y < layerLength; y++){
			for(Neuron n : layers.keySet()){
				if(layers.get(n) == y){
					n.update(delta, this);
					if(drawing){
						float nx = (Display.getWidth()*DRAWING_PAN) + (-getNeurons(y).size()*DRAWING_HSPACE/2) + (getNeurons(y).indexOf(n)*DRAWING_HSPACE);
						float ny = (-layerLength*DRAWING_VSPACE/2) + (y*DRAWING_VSPACE);
						if(n instanceof Hidden){
							for(Neuron ni : getInputs(n)){
								float nix = (Display.getWidth()*DRAWING_PAN) + (-getNeurons(y - 1).size()*DRAWING_HSPACE/2) + (getNeurons(y - 1).indexOf(ni)*DRAWING_HSPACE);
								float niy = (-layerLength*DRAWING_VSPACE/2) + ((y - 1)*DRAWING_VSPACE);
								hvlForceRefresh();
								hvlDrawLine(nx, ny, nix, niy, new Color(((Hidden)n).getWeight(ni)*DRAWING_STIM_MAG, ((Hidden)n).getWeight(ni)*DRAWING_STIM_MAG, 0f));
							}
						}
						hvlDrawQuadc(nx, ny, DRAWING_SIZE, DRAWING_SIZE, HvlTemplateInteg2D.getTexture(Main.IDX_NODE),
								n instanceof Sensor ? new Color(0.1f, 1f, 0.1f) : n instanceof Output ? new Color(1f, ((Output)n).isTriggering() ? 0.5f : 0.1f, ((Output)n).isTriggering() ? 0.5f : 0.1f) : new Color(0.3f, 0.3f, 0.3f));
					}
				}
			}
		}
	}

	public boolean isDrawing(){
		return drawing;
	}

	public void setDrawing(boolean drawingArg){
		drawing = drawingArg;
	}

	public Entity getParent(){
		return parent;
	}

	public float getStimulation(){
		return stimulation;
	}

	public ArrayList<Neuron> getInputs(Neuron neuronArg){
		ArrayList<Neuron> inputs = new ArrayList<Neuron>();
		if(layers.containsKey(neuronArg)){
			for(Neuron n : layers.keySet()) if(layers.get(n) == layers.get(neuronArg) - 1) inputs.add(n);
			return inputs;
		}else return null;
	}

	public ArrayList<Neuron> getNeurons(int layerArg){
		ArrayList<Neuron> inputs = new ArrayList<Neuron>();
		for(Neuron n : layers.keySet()) if(layers.get(n) == layerArg) inputs.add(n);
		return inputs;
	}

	public void setStimulation(float stimulation){
		this.stimulation = stimulation;
	}

	public static abstract class Neuron{

		private float dataValue;

		public Neuron(){}

		public abstract void establishInputs(NeuralNetwork networkArg);

		public abstract float fetchValue(float delta, NeuralNetwork networkArg);

		public void update(float delta, NeuralNetwork networkArg){
			dataValue = fetchValue(delta, networkArg);
		}

		public float getDataValue(){
			return dataValue;
		}

	}

	public static abstract class Sensor extends Neuron{

		public Sensor(){}

		public void establishInputs(NeuralNetwork networkArg){}

	}

	public static class Hidden extends Neuron{

		private HashMap<Neuron, Float> inputs = new HashMap<>();

		public Hidden(){}

		public void establishInputs(NeuralNetwork networkArg){
			for(Neuron n : networkArg.getInputs(this)) inputs.put(n, 0f);
		}

		@Override
		public float fetchValue(float delta, NeuralNetwork networkArg){
			for(Neuron n : inputs.keySet()) inputs.put(n, (float)(Math.max(Math.min(inputs.get(n) + ((Math.random() - 0.5) * networkArg.getStimulation() * delta), MODIFIER_LIMIT), -MODIFIER_LIMIT)));
			float total = 0;
			for(Neuron n : inputs.keySet()) total += inputs.get(n);
			return total;
		}

		public float getWeight(Neuron neuronArg){
			return inputs.get(neuronArg);
		}

	}

	public static abstract class Output extends Hidden{

		public Output(){}

		@Override
		public void update(float delta, NeuralNetwork networkArg){
			super.update(delta, networkArg);
			if(isTriggering()) performAction(delta, networkArg);
		}

		public abstract void performAction(float delta, NeuralNetwork networkArg);//TODO add arguments for amplification[~PWM]

		public boolean isTriggering(){
			return getDataValue() > 0;
		}
		
	}

}