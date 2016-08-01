package com.nickapps.hexeditor.prefrences;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.nickapps.hexeditor.HexEditor;

public class Preferences implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9051334828988070021L;
	private int tabWidth;
	private Point loc;
	private Rectangle size;
	private int extendState;

	public Preferences(int tabW) {
		tabWidth = tabW;
	}

	public void set() {
		for(int i = 0; i<HexEditor.frame.tabbedPane.getTabCount();i++){
			HexEditor.frame.tabbedPane.get(i).textBox.setTabSize(tabWidth);
		}
	}

	public void initSet() {
		HexEditor.frame.setVisible(true);
		try {
			HexEditor.frame.setLocation(loc);
			HexEditor.frame.setSize(size.getSize());
			HexEditor.frame.setExtendedState(extendState);
		} catch (Exception e) {
		}
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}
	
	public void setSize(Rectangle size) {
		this.size = size;
	}
	
	public void setExtendState(int extendState){
		this.extendState = extendState;
	}

	public void write(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(this);

		oos.close();
		fos.close();
	}

	public static Preferences read(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Preferences ret = null;
		try {
			ret = (Preferences) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ois.close();
		fis.close();
		return ret;
	}

	public int getTabWidth() {
		return tabWidth;
	}
}
