package com.osreboot.rneuralnet;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCamera.HvlCameraAlignment;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String args[]){
		new Main();
	}
	
	public Main(){
		super(120, 1280, 720, "RNeuralNet - Neural Network / AI Testing", new HvlDisplayModeResizable());
	}

	public static final int IDX_FONT = 0, IDX_ENTITY = 1;
	
	public static Simulation sim;
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");
		getTextureLoader().loadResource("Entity");
		
		HvlCamera.setAlignment(HvlCameraAlignment.CENTER);
		
		sim = new SimulationBasic();
		
		sim.initialize();
	}

	@Override
	public void update(float delta){
		sim.update(delta);
		Entity.updateEntities(delta);
	}

}
