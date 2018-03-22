package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.StringUtils;

public enum TestRunnerDefinition {
	Junit(""), Mockito("@RunWith(MockitoJUnitRunner.class)"), PowerMock("@RunWith(PowerMockRunner.class)"), Spring("@RunWith(SpringRunner.class)");

	private final String runnerDefinition;

	String getRunnerDefinition() {
		return runnerDefinition;
	}

	private TestRunnerDefinition(String runnerDefinition) {
		this.runnerDefinition = runnerDefinition;
	}

	public boolean isFieldMockingSupported() {
		return StringUtils.isNotBlank(runnerDefinition);
	}

}
