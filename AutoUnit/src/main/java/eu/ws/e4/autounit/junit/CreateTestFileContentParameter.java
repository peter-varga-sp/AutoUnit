package eu.ws.e4.autounit.junit;

import java.util.List;

import org.eclipse.jdt.core.IMethod;

import eu.ws.e4.autounit.junit.creator.mockito.TestRunnerDefinition;

public class CreateTestFileContentParameter {

	private final String nameOfClassUnderTest;

	private final String testClassName;

	private final TestRunnerDefinition testRunner;

	private final List<IMethod> allTestableMethods;

	public CreateTestFileContentParameter(String nameOfClassUnderTest, String testClassName, TestRunnerDefinition testRunner,
			List<IMethod> allTestableMethods) {
		this.nameOfClassUnderTest = nameOfClassUnderTest;
		this.testClassName = testClassName;
		this.testRunner = testRunner;
		this.allTestableMethods = allTestableMethods;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public TestRunnerDefinition getTestRunner() {
		return testRunner;
	}

	public List<IMethod> getAllTestableMethods() {
		return allTestableMethods;
	}

	public String getNameOfClassUnderTest() {
		return nameOfClassUnderTest;
	}
}