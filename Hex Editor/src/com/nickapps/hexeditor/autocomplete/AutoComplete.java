package com.nickapps.hexeditor.autocomplete;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;

public class AutoComplete {
	public static AutoCompletion ac;

	public static CompletionProvider getC() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			System.out.println(AutoComplete.class.getResource("/c.xml"));
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/c.xml"));

			DefaultCompletionProvider scp = new DefaultCompletionProvider();
			scp.addCompletion(new BasicCompletion(scp, "%c", "char",
					"Prints a character"));
			scp.addCompletion(new BasicCompletion(scp, "%i", "signed int",
					"Prints a signed integer"));
			scp.addCompletion(new BasicCompletion(scp, "%f", "float",
					"Prints a float"));
			scp.addCompletion(new BasicCompletion(scp, "%s", "string",
					"Prints a string"));
			scp.addCompletion(new BasicCompletion(scp, "%u", "unsigned int",
					"Prints an unsigned integer"));
			scp.addCompletion(new BasicCompletion(scp, "\\n", "Newline",
					"Prints a newline"));

			DefaultCompletionProvider ccp = new DefaultCompletionProvider();
			ccp.addCompletion(new BasicCompletion(ccp, "TODO:",
					"A to-do reminder"));
			ccp.addCompletion(new BasicCompletion(ccp, "FIXME:",
					"A bug that needs to be fixed"));

			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);

			provider.setStringCompletionProvider(scp);
			provider.setCommentCompletionProvider(ccp);

			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static CompletionProvider getHTML() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/html.xml"));
			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);
			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static CompletionProvider getJSP() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/jsp.xml"));
			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);
			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static CompletionProvider getPerl() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/perl5.xml"));
			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);
			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static CompletionProvider getPHP() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/php.xml"));
			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);
			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static CompletionProvider getSH() {

		DefaultCompletionProvider codeprovider = new DefaultCompletionProvider();
		try {
			codeprovider.loadFromXML(AutoComplete.class.getResourceAsStream(
					"/autocomplete/sh.xml"));
			LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
					codeprovider);
			return provider;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
