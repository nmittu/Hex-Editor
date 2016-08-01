package com.nickapps.hexeditor.macro;

import java.io.IOException;
import java.util.Arrays;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.nickapps.hexeditor.HexEditor;
import com.nickapps.hexeditor.gui.TextAreaPanel;
import com.nickapps.hexeditor.util.fileIO.TxtFile;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class MacroBuilder {
	public static String name;
	private static TextAreaPanel panel;

	public static void newM(String name) {
		MacroBuilder.name = name;
		panel = new TextAreaPanel();
		panel.textBox
				.setText("import com.nickapps.hexeditor.macro.Macro;\n\npublic class "
						+ name
						+ " implements Macro {\n\tpublic void run() {\n\t\t\n\t}\n\n\tpublic String getName() {\n\t\treturn \""
						+ name + "\";\n\t}\n}");
		panel.name = "Macro: " + name;
		panel.textBox.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		HexEditor.frame.tabbedPane.add(panel);
		HexEditor.frame.tabbedPane.setSelectedComponent(panel);
	}

	public static void done() {
		TxtFile file = new TxtFile(System.getProperty("user.home")
				+ "/.HexEditor/.macros/" + name + ".java");
		file.deleteOnExit();
		file.write(panel.textBox.getText());

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		String[] compilationUnits = { file.getAbsolutePath() };
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				null, Arrays.asList("-classpath",
						System.getProperty("java.class.path")), null,
				fileManager.getJavaFileObjectsFromStrings(Arrays
						.asList(compilationUnits)));
		boolean success = task.call();
		try {
			fileManager.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success: " + success);
		panel = null;
	}
}
