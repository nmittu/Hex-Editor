package com.nickapps.hexeditor.addon;

import javax.swing.JFrame;

public abstract class WindowAddOn implements AddOn {
	abstract public JFrame getFrame();
	
	@Override
	public void add() {
		// TODO Auto-generated method stub
		JFrame frame = getFrame();
		frame.setVisible(true);
	}

}
