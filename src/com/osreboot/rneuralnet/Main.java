package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCamera.HvlCameraAlignment;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String args[]){
		new Main();
	}
	
	public Main(){
		super(120, 1280, 720, "RNeuralNet - Neural Network / AI Testing", new HvlDisplayModeResizable());
	}

	public static final int IDX_FONT = 0, IDX_ENTITY = 1, IDX_NODE = 2;
	
	public static Simulation sim;
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");
		getTextureLoader().loadResource("Entity");
		getTextureLoader().loadResource("Node");
		
		HvlCamera.setAlignment(HvlCameraAlignment.CENTER);
		
		sim = new SimulationBasic();
		
		sim.initialize();
	}

	@Override
	public void update(float delta){
		hvlDrawQuadc(0, 0, Display.getWidth(), Display.getWidth(), new Color(0.1f, 0.1f, 0.1f));
		sim.update(delta);
		hvlDrawLine(sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), Color.black, 2);//TODO temp
		hvlDrawLine(sim.getBoundarySize(), sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), Color.black, 2);
		hvlDrawLine(-sim.getBoundarySize(), -sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), Color.black, 2);
		hvlDrawLine(-sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), Color.black, 2);
		Entity.updateEntities(delta);
	}

}
