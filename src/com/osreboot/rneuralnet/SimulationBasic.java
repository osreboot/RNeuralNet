package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.HvlMath;

public class SimulationBasic implements Simulation{

	private Entity entitySubject;

	@Override
	public float getBoundarySize(){
		return 200;
	}
	
	@Override
	public void initialize(){
		entitySubject = new Entity(0, 0, 10, Color.orange);
		entitySubject.setNetwork(new NeuralNetwork(entitySubject, new NeuralNetwork.Neuron[][]{
				//***************SENSORS***************//
				{
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return (Integer.MAX_VALUE/2) + networkArg.getParent().getX();
						}
					},
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return (Integer.MAX_VALUE/2) + networkArg.getParent().getY();
						}
					},
				}, 
				//***************HIDDEN***************//
				//NeuralNetwork.generateBlankNeuralSet(4),
				//***************OUTPUTS***************//
				{
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setX(networkArg.getParent().getX() + (10 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setX(networkArg.getParent().getX() - (10 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() + (10 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() - (10 * delta));
						}
					},
				}, 
		}));
		entitySubject.getNetwork().setDrawing(true);
	}

	@Override
	public void update(float delta){
		HvlCoord goal = new HvlCoord(100, 100);
		entitySubject.getNetwork().setStimulation((float)Math.pow((HvlMath.distance(goal.x, goal.y, entitySubject.getX(), entitySubject.getY())), 1.6f)/5);
		hvlDrawLine(goal.x + 20, goal.y + 20, goal.x - 20, goal.y - 20, Color.blue);
		hvlDrawLine(goal.x + 20, goal.y - 20, goal.x - 20, goal.y + 20, Color.blue);
	}

}
