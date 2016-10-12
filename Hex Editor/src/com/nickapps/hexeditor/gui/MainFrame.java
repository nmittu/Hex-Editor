package com.nickapps.hexeditor.gui;


import java.awt.*;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4679735704222061577L;
	public final TextTabbedPane tabbedPane;
	public final BottomBar bottomBar;
	public final HexToolBar toolbar;
	public final Menu menu;
	
	public MainFrame(String title) {
		super(title);


		if(System.getProperty("os.name").toLowerCase().indexOf("mac") >=0){
			try{
				Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
				Class param[] = new Class[]{Window.class, Boolean.TYPE};
				Method method = util.getMethod("setWindowCanFullScreen", param);
				method.invoke(util, this, true);
			} catch (ClassNotFoundException e1){
			} catch (Exception e){
				e.printStackTrace();
			}
		}


		tabbedPane = new TextTabbedPane();
		bottomBar = new BottomBar(this);
		menu = new Menu();
		
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menu);

		toolbar = new HexToolBar();
		add(toolbar, BorderLayout.PAGE_START);
		
		tabbedPane.add(new TextAreaPanel());
		add(tabbedPane, BorderLayout.CENTER);
		
		bottomBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(bottomBar, BorderLayout.SOUTH);
	}
}
