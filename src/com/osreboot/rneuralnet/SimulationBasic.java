package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.action.HvlAction3;
import com.osreboot.rneuralnet.NeuralNetwork.Sensor;

public class SimulationBasic implements Simulation{

	private Entity entitySubject;
	
	@Override
	public void initialize(){
		entitySubject = new Entity(0, 0, 5, Color.orange);
		entitySubject.setNetwork(new NeuralNetwork(entitySubject, new NeuralNetwork.Sensor[]{
				new NeuralNetwork.Sensor(new HvlAction3<Float, Entity, NeuralNetwork.Sensor>(){
					@Override
					public void run(Float delta, Entity e, Sensor s){
						s.setSensoryValue((Integer.MAX_VALUE/2) + e.getX());
					}
				}),
				new NeuralNetwork.Sensor(new HvlAction3<Float, Entity, NeuralNetwork.Sensor>(){
					@Override
					public void run(Float delta, Entity e, Sensor s){
						s.setSensoryValue((Integer.MAX_VALUE/2) + e.getY());
					}
				}),
		}, new NeuralNetwork.Neuron[]{
				new NeuralNetwork.Neuron(new HvlAction2<Float, Entity>(){
					@Override
					public void run(Float delta, Entity e){
						e.setX(e.getX() + (delta * 10));
					}
				}),
				new NeuralNetwork.Neuron(new HvlAction2<Float, Entity>(){
					@Override
					public void run(Float delta, Entity e){
						e.setX(e.getX() - (delta * 10));
					}
				}),
				new NeuralNetwork.Neuron(new HvlAction2<Float, Entity>(){
					@Override
					public void run(Float delta, Entity e){
						e.setY(e.getY() + (delta * 10));
					}
				}),
				new NeuralNetwork.Neuron(new HvlAction2<Float, Entity>(){
					@Override
					public void run(Float delta, Entity e){
						e.setY(e.getY() - (delta * 10));
					}
				}),
		}));
	}

	@Override
	public void update(float delta){
		HvlCoord goal = new HvlCoord(200, 200);
		entitySubject.setStimulation((goal.x - entitySubject.getX()) + (goal.y - entitySubject.getX()));
		hvlDrawLine(goal.x + 20, goal.y + 20, goal.x - 20, goal.y - 20, Color.blue);
		hvlDrawLine(goal.x + 20, goal.y - 20, goal.x - 20, goal.y + 20, Color.blue);
	}

}
