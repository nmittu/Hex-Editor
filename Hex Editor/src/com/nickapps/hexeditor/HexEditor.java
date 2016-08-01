package com.nickapps.hexeditor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.nickapps.hexeditor.addon.AddOn;
import com.nickapps.hexeditor.addon.AddOnInfo;
import com.nickapps.hexeditor.gui.MainFrame;
import com.nickapps.hexeditor.prefrences.Preferences;

public class HexEditor {
	public static MainFrame frame;
	public static Preferences pref;
	public static void restart() throws URISyntaxException, IOException{
		String javaExe = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File jar = new File(HexEditor.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		if(!jar.getName().endsWith(".jar")){
			return;
		}
		ArrayList<String> command = new ArrayList<>();
		command.add(javaExe);
		command.add("-jar");
		command.add(jar.getPath());
		
		ProcessBuilder process = new ProcessBuilder(command);
		process.start();
		System.exit(0);
	}
	
	private static void addAddOns() throws ClassNotFoundException, InstantiationException, IllegalAccessException, URISyntaxException, IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		File addondir = new File(System.getProperty("user.home") + "/.HexEditor/.addons/");
		addondir.mkdirs();
		File[] files = addondir.listFiles();
		for(File file : files){
			if((!file.isDirectory()) && file.getName().endsWith(".jar")){
				URLClassLoader cl = new URLClassLoader(new URL[] {file.toURI().toURL()});
				Class infoC = cl.loadClass("Info");
				
				AddOnInfo info = (AddOnInfo) infoC.newInstance();
				AddOn addon = info.getAddOnObj();
				addon.add();
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				frame = new MainFrame("Hex Editor");
				
				InputStream hexpng = HexEditor.class.getResourceAsStream("/img/hexeditor.png");
				BufferedImage heximg = null;
				try {
					heximg = ImageIO.read(hexpng);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				frame.setIconImage(heximg);
				
				File f = new File(System.getProperty("user.home")+"/.HexEditor/");
				f.mkdir();
				f = new File(System.getProperty("user.home")+"/.HexEditor/.preferences");
				try {
					pref = Preferences.read(f);
				} catch (IOException e) {
					pref = new Preferences(8);
				}
				pref.initSet();

				frame.setVisible(true);
				
				try {
					addAddOns();
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				frame.toolbar.addDrag();
				
				//frame.addKeyListener(new HexKeyListener());
				
				frame.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosing(WindowEvent e) {
						pref.setExtendState(frame.getExtendedState());
			        	frame.setExtendedState(JFrame.NORMAL);
			        	
			        	try {
							Thread.sleep(5);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
			        	
			        	pref.setLoc(frame.getLocationOnScreen());
			        	pref.setSize(frame.getBounds());
			        	
			        	System.out.println(frame.getBounds());
			        	
			        	
			        	try {
							pref.write(new File(System.getProperty("user.home")+"/.HexEditor/.preferences"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
		});
	}
}
