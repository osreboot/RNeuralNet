package com.osreboot.rneuralnet;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Entity {

	public static ArrayList<Entity> entities = new ArrayList<>();
	
	public static void updateEntities(float delta){
		for(Entity e : entities) e.update(delta);
	}
	
	private float x, y, xs, ys, radius;
	private Color color;
	private HvlAction2<Float, Entity> actionUpdate;
	
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
		if(actionUpdate != null) actionUpdate.run(delta, this);
		x += xs * delta;
		y += ys * delta;
		x = Math.max(Math.min(200, x), -200);
		y = Math.max(Math.min(200, y), -200);
		hvlDrawQuadc(x, y, radius * 2, radius * 2, HvlTemplateInteg2D.getTexture(Main.IDX_ENTITY), color);
	}
	
	public HvlAction2<Float, Entity> getActionUpdate(){
		return actionUpdate;
	}

	public void setActionUpdate(HvlAction2<Float, Entity> actionUpdate){
		this.actionUpdate = actionUpdate;
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
