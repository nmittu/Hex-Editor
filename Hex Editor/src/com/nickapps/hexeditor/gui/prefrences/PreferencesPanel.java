package com.nickapps.hexeditor.gui.prefrences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.prefrences.Preferences;

public class PreferencesPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3489492748125852844L;
	private final SpinnerNumberModel tabSpinnerM = new SpinnerNumberModel(HexEditor.pref.getTabWidth(), 2, 12, 1);
	private final JSpinner tabSpinner = new JSpinner(tabSpinnerM); 
	private final JLabel tabL = new JLabel("tab width");
	private final JButton apply = new JButton("apply");
	public PreferencesPanel(){
		super("Prefrences");
		
		setLayout(new GridBagLayout());
		setResizable(false);
		setLocationRelativeTo(HexEditor.frame);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.00001;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		
		add(tabL, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(tabSpinner, gc);
		
		gc.weighty = 1.0;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(apply, gc);
		
		
		
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.pref = new Preferences((int) tabSpinner.getValue());
						HexEditor.pref.set();
					}
				});
			}
		});

	}
}
