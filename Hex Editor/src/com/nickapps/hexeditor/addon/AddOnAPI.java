package com.nickapps.hexeditor.addon;

import java.io.IOException;
import java.net.URISyntaxException;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.gui.TextAreaPanel;

public class AddOnAPI {
	public static String getText(){
		return HexEditor.frame.tabbedPane.getSelectedComponent().textBox.getText();
	}
	
	public static void addTab(TextAreaPanel panel){
		HexEditor.frame.tabbedPane.add(panel);
	}
	
	public static TextAreaPanel getTab(){
		return HexEditor.frame.tabbedPane.getSelectedComponent();
	}
	
	public static void save(){
		HexEditor.frame.tabbedPane.getSelectedComponent().save();
	}
	
	public static void saveAll(){
		HexEditor.frame.tabbedPane.saveAll();
	}
	
	public static void restart() throws URISyntaxException, IOException{
		HexEditor.restart();
	}
}
