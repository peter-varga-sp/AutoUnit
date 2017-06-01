package eu.ws.e4.autounit.handlers;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class AutoUnitPlugin extends AbstractUIPlugin {

    private static AutoUnitPlugin instance;

    private AutoUnitPlugin() {
    }

    public static AutoUnitPlugin getInstance() {
	if (null == instance) {
	    instance = new AutoUnitPlugin();
	}
	return instance;
    }

}
