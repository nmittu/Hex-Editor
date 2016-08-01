package com.nickapps.hexeditor.addon;

public abstract class BackGroundAddOn extends Thread implements AddOn {

	@Override
	abstract public void run();

	@Override
	public void add() {
		// TODO Auto-generated method stub
		this.start();
	}

}
