package com.nickapps.hexeditor.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import com.nickapps.hexeditor.HexEditor;

public class HexListener implements KeyListener, MouseListener, MouseMotionListener {


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HexEditor.frame.bottomBar.update();
				if (HexEditor.frame.tabbedPane.getSelectedComponent().file != null) {
					try {
						if (!HexEditor.frame.tabbedPane.getSelectedComponent().file
								.isSaved(HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox
										.getText())) {
							HexEditor.frame.setTitle("*"
									+ HexEditor.frame.tabbedPane
											.getSelectedComponent().file
											.getPath());
							HexEditor.frame.tabbedPane.getSelectedComponent().name = "*"
									+ HexEditor.frame.tabbedPane
											.getSelectedComponent().file
											.getName();
						} else {
							HexEditor.frame.setTitle(HexEditor.frame.tabbedPane
									.getSelectedComponent().file.getPath());
							HexEditor.frame.tabbedPane.getSelectedComponent().name = HexEditor.frame.tabbedPane
									.getSelectedComponent().file.getName();
						}
						HexEditor.frame.tabbedPane.setTitleAt(
								HexEditor.frame.tabbedPane.getSelectedIndex(),
								HexEditor.frame.tabbedPane
										.getSelectedComponent().name);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		thread.start();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		HexEditor.frame.bottomBar.update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		HexEditor.frame.bottomBar.update();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
