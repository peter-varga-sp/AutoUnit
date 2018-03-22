package eu.ws.e4.autounit.junit;

import eu.ws.e4.autounit.junit.creator.mockito.TestRunnerDefinition;

/**
 * Data holder for generate options selected on GUI
 */
public class GuiOptionsHolder {
	private TestRunnerDefinition testRunnerDefinition = TestRunnerDefinition.Junit;

	public TestRunnerDefinition getTestRunnerDefinition() {
		return testRunnerDefinition;
	}

	public void setTestRunnerDefinition(TestRunnerDefinition testRunnerDefinition) {
		this.testRunnerDefinition = testRunnerDefinition;
	}
}
