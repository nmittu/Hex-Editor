package com.nickapps.hexeditor.gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.input.HexListener;
import com.nickapps.hexeditor.util.fileIO.TxtFile;

public class TextAreaPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1270000821317662398L;
	public RSyntaxTextArea textBox;
	public TxtFile file;
	public String name;
	
	public static final int YES = 1;
	public static final int NO = 0;
	public static final int CANCEL = -1;

	public TextAreaPanel() {
		name = "Untitled";
		File dic = new File(System.getProperty("user.home")
				+ "/.HexEditor/.dictmp");
		dic.deleteOnExit();

		try {
			FileUtils
					.copyInputStreamToFile(TextAreaPanel.class
							.getResourceAsStream("/dic/english_dic.zip"), dic);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			System.out.println("here");
			e2.printStackTrace();
		}

		SpellingParser parser;

		try {
			parser = SpellingParser.createEnglishSpellingParser(dic, true);
		} catch (IOException e21) {
			// TODO Auto-generated catch block
			parser = null;
			e21.printStackTrace();
		}

		textBox = new RSyntaxTextArea();
		textBox.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		textBox.setCodeFoldingEnabled(true);
		textBox.setAntiAliasingEnabled(true);
		textBox.addParser(parser);

		setLayout(new BorderLayout());

		RTextScrollPane sp = new RTextScrollPane(textBox);
		sp.setFoldIndicatorEnabled(true);
		add(sp, BorderLayout.CENTER);
		
		HexListener listener = new HexListener();
		
		textBox.addKeyListener(listener);
		textBox.addMouseListener(listener);
		textBox.addMouseMotionListener(listener);
	}

	public boolean save() {
		if (file == null) {
			return saveAs();
		}
		if (file.write(textBox.getText())) {
			HexEditor.frame.setTitle(file.getPath());
			name = file.getName();
			HexEditor.frame.tabbedPane.setTitleAt(HexEditor.frame.tabbedPane.indexOfComponent(this), name);
			return true;
		}
		return false;
	}

	public boolean saveAs() {
		FileDialog fc = new FileDialog(HexEditor.frame, "Save", FileDialog.SAVE);
		//fc.setFile(null);
		fc.setVisible(true);
		String filePath = fc.getDirectory()+fc.getFile();
		if (fc.getFile() != null) {
			file = new TxtFile(filePath);
			HexEditor.frame.setTitle(file.getPath());
			return save();
		}
		return false;
	}
}
