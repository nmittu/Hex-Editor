package com.nickapps.hexeditor.macro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nickapps.hexeditor.HexEditor;

public class MacroListener implements ActionListener {
	private int num;
	
	public MacroListener(int num){
		this.num = num;
	}
	
	public void setNum(int num){
		this.num = num;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		HexEditor.frame.menu.macroItems.getValues(num).get1().run();

	}

}
