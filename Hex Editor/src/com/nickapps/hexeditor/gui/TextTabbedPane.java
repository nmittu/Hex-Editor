package com.nickapps.hexeditor.gui;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.util.fileIO.TxtFile;

public class TextTabbedPane extends JTabbedPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5728052393543023332L;

	private boolean dragging = false;
	private Image tabImage = null;
	private Point currentMouseLocation = null;
	private int draggedTabIndex = 0;

	public TextTabbedPane() {
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				try {
					HexEditor.frame.bottomBar.update();
				} catch (Exception e1) {

				}
				if (getSelectedComponent() != null
						&& getSelectedComponent().file != null) {
					try {
						if (!getSelectedComponent().file
								.isSaved(getSelectedComponent().textBox
										.getText())) {

							HexEditor.frame.setTitle("*"
									+ getSelectedComponent().file.getPath());
							getSelectedComponent().name = "*"
									+ getSelectedComponent().file.getName();

						} else {
							HexEditor.frame
									.setTitle(getSelectedComponent().file
											.getPath());
							getSelectedComponent().name = getSelectedComponent().file
									.getName();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						HexEditor.frame.setTitle("Hex Editor");
					} catch (Exception e1) {

					}
				}
				updateTitles();
			}

		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {

				if (!dragging) {
					// Gets the tab index based on the mouse position
					int tabNumber = getUI().tabForCoordinate(
							TextTabbedPane.this, e.getX(), e.getY());

					if (tabNumber >= 0) {
						draggedTabIndex = tabNumber;
						Rectangle bounds = getUI().getTabBounds(
								TextTabbedPane.this, tabNumber);

						// Paint the tabbed pane to a buffer
						Image totalImage = new BufferedImage(getWidth(),
								getHeight(), BufferedImage.TYPE_INT_ARGB);
						Graphics totalGraphics = totalImage.getGraphics();
						totalGraphics.setClip(bounds);
						// Don't be double buffered when painting to a static
						// image.
						setDoubleBuffered(false);
						paintComponent(totalGraphics);

						// Paint just the dragged tab to the buffer
						tabImage = new BufferedImage(bounds.width,
								bounds.height, BufferedImage.TYPE_INT_ARGB);
						Graphics graphics = tabImage.getGraphics();
						graphics.drawImage(totalImage, 0, 0, bounds.width,
								bounds.height, bounds.x, bounds.y, bounds.x
										+ bounds.width, bounds.y
										+ bounds.height, TextTabbedPane.this);

						dragging = true;
						repaint();
					}
				} else {
					currentMouseLocation = e.getPoint();

					// Need to repaint
					repaint();
				}

				super.mouseDragged(e);
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (dragging) {
					int tabNumber = getUI().tabForCoordinate(
							TextTabbedPane.this, e.getX(), 10);

					if (tabNumber >= 0) {
						Component comp = getComponentAt(draggedTabIndex);
						String title = getTitleAt(draggedTabIndex);
						removeTabAt(draggedTabIndex);
						insertTab(title, null, comp, null, tabNumber);
						setSelectedIndex(tabNumber);
						updateTitles();
					}
				}

				dragging = false;
				tabImage = null;
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Are we dragging?
		if (dragging && currentMouseLocation != null && tabImage != null) {
			// Draw the dragged tab
			g.drawImage(tabImage, currentMouseLocation.x,
					currentMouseLocation.y, this);
		}
	}

	public void add(TextAreaPanel panel) {
		add(panel.name, panel);
		setTabComponentAt(this.indexOfComponent(panel), new ButtonTabComponent(
				this));
	}

	@Override
	public TextAreaPanel getSelectedComponent() {
		// TODO Auto-generated method stub
		return (TextAreaPanel) super.getSelectedComponent();
	}

	public boolean open() {
		FileDialog fc = new FileDialog(HexEditor.frame, "Open", FileDialog.LOAD);
		fc.setFile(null);
		fc.setVisible(true);
		String filePath = fc.getDirectory() + fc.getFile();
		if (fc.getFile() != null) {
			newF();
			get(getTabCount() - 1).file = new TxtFile(filePath);
			try {
				get(getTabCount() - 1).textBox
						.setText(get(getTabCount() - 1).file.read());
				get(getTabCount() - 1).textBox.discardAllEdits();
				get(getTabCount() - 1).textBox.setCaretPosition(0);
				HexEditor.frame.setTitle(get(getTabCount() - 1).file.getPath());
				get(getTabCount() - 1).name = get(getTabCount() - 1).file
						.getName();
				this.setTitleAt(getTabCount() - 1, get(getTabCount() - 1).name);
				updateTitles();

				fireStateChanged();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
			return true;
		}

		return true;
	}

	public boolean open(String fName){
		try {
			TextAreaPanel panel = new TextAreaPanel();
			panel.file = new TxtFile(fName);
			panel.textBox.setText(panel.file.read());
			panel.textBox.discardAllEdits();
			panel.name = panel.file.getName();
			add(panel);
			setSelectedComponent(panel);

			return true;
		}catch (Exception e){
			return false;
		}
	}

	public void close(String fName){
		for (int i = 0; i < getTabCount(); i++){
			TextAreaPanel panel = get(i);
			try {
				if (panel.file.getAbsolutePath().equals(fName)) {
					int dialogResult = JOptionPane.NO_OPTION;
					if (panel.file != null
							&& !panel.file.isSaved(panel.textBox.getText())) {
						dialogResult = JOptionPane
								.showConfirmDialog(
										HexEditor.frame,
										panel.file.getPath()
												+ " is not saved.\nWould you like to save it?",
										"Warning", JOptionPane.YES_NO_CANCEL_OPTION);
					}
					if (dialogResult == JOptionPane.YES_OPTION) {
						panel.file.write(panel.textBox.getText());
						dialogResult = JOptionPane.NO_OPTION;
					}
					if (i != -1 && dialogResult == JOptionPane.NO_OPTION) {
						remove(i);
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void newF() {
		// TODO Auto-generated method stub
		TextAreaPanel panel = new TextAreaPanel();
		add("Untitled", panel);
		setSelectedComponent(panel);
		updateTitles();
	}

	private void updateTitles() {
		for (int i = 0; i < getTabCount(); i++) {
			try {
				this.setTabComponentAt(i, new ButtonTabComponent(this));

			} catch (Exception e) {

			}
		}
	}

	public TextAreaPanel get(int i) {
		if (getTabComponentAt(i) != null) {
			return (TextAreaPanel) this.getComponentAt(i);
		}
		return null;
	}

	public void saveAll() {
		for (int i = 0; i < getTabCount(); i++) {
			if (get(i) != null) {
				get(i).save();
			}
		}
	}
}
