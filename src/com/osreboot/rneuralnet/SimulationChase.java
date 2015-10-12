package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.HvlMath;

public class SimulationChase implements Simulation{

	public static final float GOAL_SWAP_TIME = 8;
	
	private Entity entitySubject;
	private float goalElapsed = GOAL_SWAP_TIME, pain;
	private HvlCoord goal = new HvlCoord(0, 0);

	@Override
	public float getBoundarySize(){
		return 200;
	}
	
	@Override
	public float getBenchmarkLife(){
		return Integer.MAX_VALUE;
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
							return goal.x - networkArg.getParent().getX();
						}
					},
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return goal.y - networkArg.getParent().getY();
						}
					},
					new NeuralNetwork.Sensor(){
						@Override
						public float fetchValue(float deltaArg, NeuralNetwork networkArg) {
							return networkArg.getParent().getX() + getBoundarySize();
						}
					},
				}, 
				//***************HIDDEN***************//
				//NeuralNetwork.generateBlankNeuralSet(3),
				//***************OUTPUTS***************//
				{
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setX(networkArg.getParent().getX() + (60 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setX(networkArg.getParent().getX() - (60 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() + (60 * delta));
						}
					},
					new NeuralNetwork.Output(){
						@Override
						public void performAction(float delta, NeuralNetwork networkArg){
							networkArg.getParent().setY(networkArg.getParent().getY() - (60 * delta));
						}
					},
				}, 
		}));
		entitySubject.getNetwork().setDrawing(true);
	}

	@Override
	public void update(float delta){
		pain = Math.max(pain - (delta/1.6f), 0);
		goalElapsed += delta;
		if(goalElapsed > GOAL_SWAP_TIME){
			goalElapsed = 0;
			pain = (float)Math.pow((HvlMath.distance(goal.x, goal.y, entitySubject.getX(), entitySubject.getY())), 1.0f)/300;
			goal = new HvlCoord((float)Math.random() * (getBoundarySize() * 2) - getBoundarySize(), (float)Math.random() * (getBoundarySize() * 2) - getBoundarySize());
		}
		entitySubject.getNetwork().setStimulation(pain * 100);
		hvlDrawLine(goal.x + 20, goal.y + 20, goal.x - 20, goal.y - 20, Color.blue);
		hvlDrawLine(goal.x + 20, goal.y - 20, goal.x - 20, goal.y + 20, Color.blue);
		Main.font.drawWord("stimulation " + Math.round(entitySubject.getNetwork().getStimulation()), -Display.getWidth()/2, (-Display.getHeight()/2) + 14.4f, 0.1f, Color.white);
	}

}
