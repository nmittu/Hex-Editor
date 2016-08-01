package com.nickapps.hexeditor.addon;

import java.awt.Component;
import java.util.ArrayList;

import com.nickapps.hexeditor.HexEditor;

public abstract class ToolBarAddOn implements AddOn {
	abstract public ArrayList<Component> getComponents();
	
	@Override
	public void add() {
		// TODO Auto-generated method stub
		HexEditor.frame.toolbar.addSeparator();
		ArrayList<Component> comps = getComponents();
		for(Component comp : comps){
			if(comp == null){
				HexEditor.frame.toolbar.addSeparator();
			}else{
				HexEditor.frame.toolbar.add(comp);
			}
		}
	}

}
