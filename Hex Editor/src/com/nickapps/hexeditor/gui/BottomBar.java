package com.nickapps.hexeditor.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nickapps.hexeditor.HexEditor;

public class BottomBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6163244744903957396L;
	private JLabel lineCol;

	public BottomBar(Component c) {
		setPreferredSize(new Dimension(c.getWidth(), 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		lineCol = new JLabel();
		lineCol.setHorizontalAlignment(SwingConstants.LEFT);
		add(lineCol);
	}

	public void update() {
		if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
			int absPos = HexEditor.frame.tabbedPane.getSelectedComponent().textBox
					.getCaretPosition();
			int lineStart = HexEditor.frame.tabbedPane.getSelectedComponent().textBox
					.getLineStartOffsetOfCurrentLine();

			String lbltxt = ("Line:"
					+ (HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.getCaretLineNumber() + 1) + "    Col:" + (absPos - lineStart));

			String sel = HexEditor.frame.tabbedPane.getSelectedComponent().textBox
					.getSelectedText();

			if (sel != null) {
				int lineC = 1;
				for (int i = 0; i < sel.length(); i++) {
					if (sel.substring(i, i + 1).equals("\n")) {
						lineC++;
					}
				}
				lbltxt += "    Sel:" + lineC+":"+sel.length();
			}

			lbltxt += "    Lines:"
					+ HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.getLineCount()
					+ "    Length:"
					+ HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.getText().length();

			lineCol.setText(lbltxt);
		}
	}
}
