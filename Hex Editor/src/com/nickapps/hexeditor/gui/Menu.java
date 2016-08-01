package com.nickapps.hexeditor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import org.apache.commons.io.FilenameUtils;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.ToolTipSupplier;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.autocomplete.AutoComplete;
import com.nickapps.hexeditor.autocomplete.CCellRenderer;
import com.nickapps.hexeditor.util.Dictionary3;
import com.nickapps.hexeditor.util.Tuple3;
import com.nickapps.hexeditor.util.fileIO.FileClassLoader;
import com.nickapps.hexeditor.gui.prefrences.PreferencesPanel;
import com.nickapps.hexeditor.macro.Macro;
import com.nickapps.hexeditor.macro.MacroBuilder;
import com.nickapps.hexeditor.macro.MacroListener;

public class Menu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 497771454289925094L;
	private static final JMenu[] menuTitles = new JMenu[6];
	public static final JMenuItem[] fileItems = new JMenuItem[5];
	public static final JMenuItem[] editItems = new JMenuItem[5];
	public static final JMenuItem[] prefItems = new JMenuItem[1];
	public static final JMenuItem[] langItems = { new JMenuItem("None"),
			new JMenuItem("Assembly"), new JMenuItem("Batch"),
			new JMenuItem("C"), new JMenuItem("C#"), new JMenuItem("C++"),
			new JMenuItem("CSS"), new JMenuItem("Fortran"),
			new JMenuItem("HTML"), new JMenuItem("Java"),
			new JMenuItem("JavaScript"), new JMenuItem("JSON"),
			new JMenuItem("JSP"), new JMenuItem("Lisp"), new JMenuItem("LUA"),
			new JMenuItem("Make File"), new JMenuItem("Perl"),
			new JMenuItem("PHP"), new JMenuItem("Properties File"),
			new JMenuItem("Python"), new JMenuItem("Ruby"),
			new JMenuItem("SAS"), new JMenuItem("SQL"),
			new JMenuItem("Unix Shell"), new JMenuItem("Visual Basic"),
			new JMenuItem("XML") };
	public static final Dictionary3<JMenuItem, MacroListener, Macro> macroItems = new Dictionary3<>();

	public Menu() {
		super();
		String key = "control";
		if(System.getProperty("os.name").toLowerCase().indexOf("mac") >=0){
			key = "meta";
		}
		
		menuTitles[0] = new JMenu("File");
		menuTitles[1] = new JMenu("Edit");
		menuTitles[2] = new JMenu("Language");
		menuTitles[3] = new JMenu("Prefrences");
		menuTitles[4] = new JMenu("Macros");
		menuTitles[5] = new JMenu("Help");

		fileItems[0] = new JMenuItem("New");
		fileItems[1] = new JMenuItem("Open");
		fileItems[2] = new JMenuItem("Save");
		fileItems[3] = new JMenuItem("Save As");
		fileItems[4] = new JMenuItem("Save All");

		fileItems[0].setAccelerator(KeyStroke.getKeyStroke(key+" N"));
		fileItems[1].setAccelerator(KeyStroke.getKeyStroke(key+" O"));
		fileItems[2].setAccelerator(KeyStroke.getKeyStroke(key+" S"));
		fileItems[3].setAccelerator(KeyStroke.getKeyStroke(key+" alt S"));
		fileItems[4].setAccelerator(KeyStroke.getKeyStroke(key+" shift S"));

		editItems[0] = new JMenuItem("Undo");
		editItems[1] = new JMenuItem("Redo");
		editItems[2] = new JMenuItem("Copy");
		editItems[3] = new JMenuItem("Cut");
		editItems[4] = new JMenuItem("Paste");

		editItems[0].setAccelerator(KeyStroke.getKeyStroke(key+" Z"));
		editItems[1].setAccelerator(KeyStroke.getKeyStroke(key+" Y"));
		editItems[2].setAccelerator(KeyStroke.getKeyStroke(key+" C"));
		editItems[3].setAccelerator(KeyStroke.getKeyStroke(key+" X"));
		editItems[4].setAccelerator(KeyStroke.getKeyStroke(key+" V"));

		prefItems[0] = new JMenuItem("Prefrences");
		new File(System.getProperty("user.home") + "/.HexEditor/.macros/")
				.mkdirs();
		File[] files = new File(System.getProperty("user.home")
				+ "/.HexEditor/.macros/").listFiles();
		for (int i = 0; i < files.length; i++) {
			String ext = FilenameUtils.getExtension(files[i].getPath());
			Tuple3<JMenuItem, MacroListener, Macro> tuple;
			if (ext.equals("class")) {
				Macro macroObj;
				try {
					macroObj = (Macro) new FileClassLoader()
							.createObjectFromFile(files[i].getPath());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					macroObj = null;
				}
				tuple = new Tuple3<>(new JMenuItem(macroObj.getName()),
						new MacroListener(i), macroObj);
				macroItems.add(tuple);

				macroItems.getKey(i).addActionListener(
						macroItems.getValues(i).get0());
			}
		}

		JMenuItem macroNew = new JMenuItem("New");
		JMenuItem macroDone = new JMenuItem("Done");

		macroDone.setEnabled(false);

		for (JMenuItem item : fileItems) {
			menuTitles[0].add(item);
		}

		for (JMenuItem item : editItems) {
			menuTitles[1].add(item);
		}

		for (JMenuItem item : langItems) {
			menuTitles[2].add(item);
		}

		for (JMenuItem item : prefItems) {
			menuTitles[3].add(item);
		}

		for (int i = 0; i < macroItems.size(); i++) {
			menuTitles[4].add(macroItems.getKey(i));
		}

		menuTitles[4].add(macroNew);
		menuTitles[4].add(macroDone);

		for (JMenu title : menuTitles) {
			this.add(title);
		}

		fileItems[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HexEditor.frame.tabbedPane.newF();
			}
		});

		fileItems[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HexEditor.frame.tabbedPane.open();
			}
		});

		fileItems[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HexEditor.frame.tabbedPane.getSelectedComponent().save();
			}
		});

		fileItems[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HexEditor.frame.tabbedPane.getSelectedComponent().saveAs();
			}
		});
		
		fileItems[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HexEditor.frame.tabbedPane.saveAll();
			}
		});

		editItems[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
					HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.undoLastAction();
					HexEditor.frame.bottomBar.update();
				}
			}
		});

		editItems[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
					HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.redoLastAction();
					HexEditor.frame.bottomBar.update();
				}
			}
		});

		editItems[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
					HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.copy();
				}
			}
		});

		editItems[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
					HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.cut();
					HexEditor.frame.bottomBar.update();
				}
			}
		});
		
		editItems[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (HexEditor.frame.tabbedPane.getSelectedComponent() != null) {
					HexEditor.frame.tabbedPane.getSelectedComponent().textBox
							.paste();
					HexEditor.frame.bottomBar.update();
				}
			}
		});

		prefItems[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						PreferencesPanel frame = new PreferencesPanel();
						frame.setSize(500, 500);
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setVisible(true);
					}
				});
			}
		});

		macroNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						String name = JOptionPane
								.showInputDialog("Enter the macro's name");

						MacroBuilder.newM(name);

						macroDone.setEnabled(true);
					}

				});
			}

		});

		macroDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MacroBuilder.done();

						macroDone.setEnabled(false);
					}

				});
			}

		});

		addLangList();

	}

	private static void addLangList() {
		langItems[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
					}
				});
			}
		});

		langItems[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
					}
				});
			}
		});

		langItems[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH);
					}
				});
			}
		});

		langItems[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getC();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSHARP);
					}
				});
			}
		});

		langItems[5].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
					}
				});
			}
		});

		langItems[6].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
					}
				});
			}
		});

		langItems[7].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_FORTRAN);
					}
				});
			}
		});

		langItems[8].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getHTML();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[9].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
					}
				});
			}
		});

		langItems[10].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
					}
				});
			}
		});

		langItems[11].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
					}
				});
			}
		});

		langItems[12].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSP);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getJSP();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[13].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
					}
				});
			}
		});

		langItems[14].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
					}
				});
			}
		});

		langItems[15].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
					}
				});
			}
		});

		langItems[16].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getPerl();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[17].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getPHP();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[18].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
					}
				});
			}
		});

		langItems[19].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
					}
				});
			}
		});

		langItems[20].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_RUBY);
					}
				});
			}
		});

		langItems[21].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SAS);
					}
				});
			}
		});

		langItems[22].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
					}
				});
			}
		});

		langItems[23].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);

						try {
							AutoComplete.ac.uninstall();
						} catch (Exception e) {
							AutoComplete.ac = null;
						}

						CompletionProvider provider = AutoComplete.getSH();

						AutoComplete.ac = new AutoCompletion(provider);

						AutoComplete.ac
								.setListCellRenderer(new CCellRenderer());
						AutoComplete.ac.setShowDescWindow(true);
						AutoComplete.ac.setParameterAssistanceEnabled(true);
						AutoComplete.ac.install(HexEditor.frame.tabbedPane
								.getSelectedComponent().textBox);

						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setToolTipSupplier((ToolTipSupplier) provider);
						ToolTipManager.sharedInstance().registerComponent(
								HexEditor.frame.tabbedPane
										.getSelectedComponent().textBox);
					}
				});
			}
		});

		langItems[24].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC);
					}
				});
			}
		});

		langItems[25].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						HexEditor.frame.tabbedPane.getSelectedComponent().textBox
								.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
					}
				});
			}
		});
	}
}
