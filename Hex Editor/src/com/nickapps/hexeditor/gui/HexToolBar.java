package com.nickapps.hexeditor.gui;

import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import org.apache.commons.io.IOUtils;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.input.DragAndDropListener;

public class HexToolBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5915166365356609948L;

	public HexToolBar(){
		setFloatable(false);
		
		InputStream newImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/new.png");
		JButton newB = new JButton();
		byte[] newImageData = null;
		try {
			newImageData = IOUtils.toByteArray(newImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newB.setIcon(new ImageIcon(newImageData));
		
		newB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.newF();
			}
			
		});
		
		InputStream openImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/open.png");
		JButton open = new JButton();
		byte[] openImageData = null;
		try {
			openImageData = IOUtils.toByteArray(openImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		open.setIcon(new ImageIcon(openImageData));
		
		open.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.open();
			}
			
		});
		
		InputStream saveImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/save.png");
		JButton save = new JButton();
		byte[] saveImageData = null;
		try {
			saveImageData = IOUtils.toByteArray(saveImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		save.setIcon(new ImageIcon(saveImageData));
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().save();
				
			}
			
		});
		
		InputStream copyImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/copy.png");
		JButton copy = new JButton();
		byte[] copyImageData = null;
		try {
			copyImageData = IOUtils.toByteArray(copyImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		copy.setIcon(new ImageIcon(copyImageData));
		
		copy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().textBox.copy();
				
			}
			
		});
		
		InputStream cutImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/cut.png");
		JButton cut = new JButton();
		byte[] cutImageData = null;
		try {
			cutImageData = IOUtils.toByteArray(cutImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cut.setIcon(new ImageIcon(cutImageData));
		
		cut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().textBox.cut();
				HexEditor.frame.bottomBar.update();
				
			}
			
		});
		
		InputStream pasteImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/paste.png");
		JButton paste = new JButton();
		byte[] pasteImageData = null;
		try {
			pasteImageData = IOUtils.toByteArray(pasteImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paste.setIcon(new ImageIcon(pasteImageData));
		
		paste.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().textBox.paste();
				HexEditor.frame.bottomBar.update();
				
			}
			
		});
		
		InputStream undoImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/undo.png");
		JButton undo = new JButton();
		byte[] undoImageData = null;
		try {
			undoImageData = IOUtils.toByteArray(undoImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		undo.setIcon(new ImageIcon(undoImageData));
		
		undo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().textBox.undoLastAction();
				HexEditor.frame.bottomBar.update();
				
			}
			
		});
		
		InputStream redoImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/redo.png");
		JButton redo = new JButton();
		byte[] redoImageData = null;
		try {
			redoImageData = IOUtils.toByteArray(redoImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redo.setIcon(new ImageIcon(redoImageData));
		
		redo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HexEditor.frame.tabbedPane.getSelectedComponent().textBox.redoLastAction();
				HexEditor.frame.bottomBar.update();
			}
			
		});
		
		add(newB);
		add(open);
		add(save);
		addSeparator();
		add(copy);
		add(cut);
		add(paste);
		addSeparator();
		add(undo);
		add(redo);
	}
	
	public void addDrag(){
		InputStream dragImg = HexToolBar.class.getResourceAsStream("/img/toolbarico/dragin.png");
		JLabel drag = new JLabel();
		byte[] dragImageData = null;
		try {
			dragImageData = IOUtils.toByteArray(dragImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drag.setIcon(new ImageIcon(dragImageData));
		
		DragAndDropListener ddl = new DragAndDropListener();
		new DropTarget(drag, ddl);
		
		add(Box.createHorizontalGlue());
		add(drag);
	}
}
