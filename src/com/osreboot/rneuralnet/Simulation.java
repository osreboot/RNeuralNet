package com.osreboot.rneuralnet;

public interface Simulation {

	public void initialize();
	
	public void update(float delta);
	
	public float getBoundarySize();
	public float getBenchmarkLife();
	
}
