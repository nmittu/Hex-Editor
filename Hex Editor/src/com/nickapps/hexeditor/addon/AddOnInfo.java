package com.nickapps.hexeditor.addon;

public interface AddOnInfo {
	public AddOn getAddOnObj();
	public AddOnTypes getAddOnType();
	public String getName();
	public String getVersion();
	public boolean needsRestartOnInstall();
}
