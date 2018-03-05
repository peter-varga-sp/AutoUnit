package eu.ws.e4.autounit.junit.creator.mockito;

public enum TestRunnerDefinition {
	Junit(""), Mockito("@RunWith(MockitoJUnitRunner.class)"), PowerMock("@RunWith(PowerMockRunner.class)"), Spring("@RunWith(SpringRunner.class)");

	private final String runnerDefinition;

	String getRunnerDefinition() {
		return runnerDefinition;
	}

	private TestRunnerDefinition(String runnerDefinition) {
		this.runnerDefinition = runnerDefinition;
	}

}
