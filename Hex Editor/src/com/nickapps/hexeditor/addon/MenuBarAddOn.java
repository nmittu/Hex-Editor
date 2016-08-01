package com.nickapps.hexeditor.addon;

import java.util.ArrayList;

import javax.swing.JMenu;
import com.nickapps.hexeditor.HexEditor;

public abstract class MenuBarAddOn implements AddOn {
	abstract public ArrayList<JMenu> getMenu();
	
	@Override
	public void add() {
		// TODO Auto-generated method stub
		ArrayList<JMenu> menus = getMenu();
		for(JMenu menu : menus){
			HexEditor.frame.menu.add(menu);
		}
	}

}
