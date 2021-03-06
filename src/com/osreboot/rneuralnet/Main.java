package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlFontUtil;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.HvlCamera;
import com.osreboot.ridhvl.painter.HvlCamera.HvlCameraAlignment;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String args[]){
		new Main();
	}
	
	public Main(){
		super(120, 1280, 720, "RNeuralNet - Neural Network / AI Testing", new HvlDisplayModeDefault());
	}

	public static final int IDX_FONT = 0, IDX_ENTITY = 1, IDX_NODE = 2;
	public static HvlFontPainter2D font;
	public static Simulation sim;
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");
		getTextureLoader().loadResource("Entity");
		getTextureLoader().loadResource("Node");
		
		font = new HvlFontPainter2D(getTextureLoader().getResource(IDX_FONT), HvlFontUtil.DEFAULT, 2048, 2048, 112, 144, 18);
		
		HvlCamera.setAlignment(HvlCameraAlignment.CENTER);
		
		sim = new SimulationChase();
		
		sim.initialize();
	}

	@Override
	public void update(float delta){
		hvlDrawQuadc(0, 0, Display.getWidth(), Display.getWidth(), new Color(0.2f, 0.2f, 0.2f));
		sim.update(delta);
		drawBoundary();
		Entity.updateEntities(delta);
		font.drawWord("lifetime seconds " + Math.round(getTimer().getTotalTime()*1000)/1000, -Display.getWidth()/2, -Display.getHeight()/2, 0.1f, Color.white);
	}
	
	private void drawBoundary(){
		hvlDrawLine(sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), Color.black, 2);//TODO temp
		hvlDrawLine(sim.getBoundarySize(), sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), Color.black, 2);
		hvlDrawLine(-sim.getBoundarySize(), -sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), Color.black, 2);
		hvlDrawLine(-sim.getBoundarySize(), -sim.getBoundarySize(), sim.getBoundarySize(), -sim.getBoundarySize(), Color.black, 2);
	}

}
