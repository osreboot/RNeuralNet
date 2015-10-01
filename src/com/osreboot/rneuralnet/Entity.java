package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Entity {

	public static ArrayList<Entity> entities = new ArrayList<>();
	
	public static void updateEntities(float delta){
		for(Entity e : entities) e.update(delta);
	}
	
	private float x, y, xs, ys, radius;
	private Color color;
	private NeuralNetwork network;
	
	public Entity(float xArg, float yArg, float radiusArg, Color colorArg){
		x = xArg;
		y = yArg;
		xs = 0;
		ys = 0;
		radius = radiusArg;
		color = colorArg;
		entities.add(this);
	}
	
	public void update(float delta){
		if(network != null) network.update(delta);
		x += xs * delta;
		y += ys * delta;
		x = Math.max(Math.min(Main.sim.getBoundarySize(), x), -Main.sim.getBoundarySize());
		y = Math.max(Math.min(Main.sim.getBoundarySize(), y), -Main.sim.getBoundarySize());
		hvlDrawQuadc(x, y, radius * 2, radius * 2, HvlTemplateInteg2D.getTexture(Main.IDX_ENTITY), color);
	}

	public NeuralNetwork getNetwork(){
		return network;
	}

	public void setNetwork(NeuralNetwork network){
		this.network = network;
	}

	public Color getColor(){
		return color;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public float getX(){
		return x;
	}

	public void setX(float x){
		this.x = x;
	}

	public float getY(){
		return y;
	}

	public void setY(float y){
		this.y = y;
	}

	public float getXs(){
		return xs;
	}

	public void setXs(float xs){
		this.xs = xs;
	}

	public float getYs(){
		return ys;
	}

	public void setYs(float ys){
		this.ys = ys;
	}

	public float getRadius(){
		return radius;
	}
	
}
