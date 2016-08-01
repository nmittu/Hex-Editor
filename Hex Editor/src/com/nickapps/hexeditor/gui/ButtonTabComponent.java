package com.nickapps.hexeditor.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import com.nickapps.hexeditor.HexEditor;

public class ButtonTabComponent extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6667581917441258448L;
	private JTabbedPane pane;
	JLabel label;

	public ButtonTabComponent(JTabbedPane pane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.pane = pane;
		setOpaque(false);

		// make JLabel read titles from JTabbedPane
		label = new JLabel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 177370292245466303L;

			public String getText() {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
		};

		add(label);
		// add more space between the label and the button
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		// tab button
		JButton button = new CloseButton();
		add(button);
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	private class CloseButton extends JButton implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1106065312272637439L;

		public CloseButton() {
			int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("close this tab");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(ButtonTabComponent.this);
			System.out.println(i);
			try {
				int dialogResult = JOptionPane.NO_OPTION;
				if (((TextAreaPanel) pane.getComponentAt(i)).file != null
						&& !((TextAreaPanel) pane.getComponentAt(i)).file
								.isSaved(((TextAreaPanel) pane
										.getComponentAt(i)).textBox.getText())) {
					dialogResult = JOptionPane
							.showConfirmDialog(
									HexEditor.frame,
									((TextAreaPanel) pane.getComponentAt(i)).file
											.getPath()
											+ " is not saved.\nWould you like to save it?",
									"Warning", JOptionPane.YES_NO_CANCEL_OPTION);
				}
				if (dialogResult == JOptionPane.YES_OPTION) {
					((TextAreaPanel) pane.getComponentAt(i)).file
							.write(((TextAreaPanel) pane.getComponentAt(i)).textBox
									.getText());
					dialogResult = JOptionPane.NO_OPTION;
				}
				if (i != -1 && dialogResult == JOptionPane.NO_OPTION) {
					((TextTabbedPane) pane).remove(i);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// we don't want to update UI for this button
		public void updateUI() {
		}

		// paint the cross
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
					- delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
					- delta - 1);
			g2.dispose();
		}
	}

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
}