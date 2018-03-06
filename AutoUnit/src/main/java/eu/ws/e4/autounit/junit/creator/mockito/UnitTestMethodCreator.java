package eu.ws.e4.autounit.junit.creator.mockito;

import org.eclipse.jdt.core.IMethod;

class UnitTestMethodCreator {
	private final IMethod method;

	UnitTestMethodCreator(IMethod method) {
		this.method = method;
	}

	String create() {
		String methodBody = "@Test\n" +
				"public void " + calculateTestMethodName() + "() {\n" +
				"\n" +
				"}\n";

		return methodBody;
	}

	private String calculateTestMethodName() {
		return method.getElementName() + "Test";
	}

}
