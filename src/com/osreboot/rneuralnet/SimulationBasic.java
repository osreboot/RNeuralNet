package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
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
		entitySubject = new Entity(-100, -100, 10, Color.orange);
		entitySubject.setNetwork(new NeuralNetwork(entitySubject, new NeuralNetwork.Neuron[][]{
				//***************SENSORS***************//
				{
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return networkArg.getParent().getX();
						}
					},
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return networkArg.getParent().getY();
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
							networkArg.getParent().setX(networkArg.getParent().getX() + (30 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setX(networkArg.getParent().getX() - (30 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() + (30 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() - (30 * delta));
						}
					},
				}, 
		}));
		entitySubject.getNetwork().setDrawing(true);
	}

	@Override
	public void update(float delta){
		HvlCoord goal = new HvlCoord(0, 0);
		entitySubject.getNetwork().setStimulation((float)Math.pow((HvlMath.distance(goal.x, goal.y, entitySubject.getX(), entitySubject.getY())), 1.0f)/10);
		hvlDrawLine(goal.x + 20, goal.y + 20, goal.x - 20, goal.y - 20, Color.blue);
		hvlDrawLine(goal.x + 20, goal.y - 20, goal.x - 20, goal.y + 20, Color.blue);
		Main.font.drawWord("distance " + Math.round(entitySubject.getNetwork().getStimulation()), -Display.getWidth()/2, (-Display.getHeight()/2) + 14.4f, 0.1f, Color.white);
	}

}
