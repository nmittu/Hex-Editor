package com.nickapps.hexeditor.addon;

public abstract class MultiAddOn implements AddOn {

	abstract public BackGroundAddOn getBkgAddOn();

	abstract public MenuBarAddOn getMenuAddOn();

	abstract public ToolBarAddOn getToolBarAddOn();

	abstract public WindowAddOn getWindowAddOn();

	@Override
	public void add() {
		// TODO Auto-generated method stub
		BackGroundAddOn bkg = getBkgAddOn();

		if (bkg != null) {
			bkg.add();
		}

		MenuBarAddOn menu = getMenuAddOn();

		if (menu != null) {
			menu.add();
		}
		ToolBarAddOn toolB = getToolBarAddOn();

		if (toolB != null) {
			toolB.add();
		}
		WindowAddOn window = getWindowAddOn();

		if (window != null) {
			window.add();
		}

	}

}
