package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.jdt.core.IMethod;

import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

class UnitTestMethodCreator {
	private final IMethod method;

	private final String testedInstanceName;
	private final CreateTestFileContentParameter parameterObject;

	UnitTestMethodCreator(IMethod method, CreateTestFileContentParameter parameterObject) {
		this.method = method;
		this.parameterObject = parameterObject;
		this.testedInstanceName = WordUtils.uncapitalize(parameterObject.getNameOfClassUnderTest());
	}

	String create() {
		String methodBody = "@Test\n" +
				"public void " + calculateTestMethodName() + "() {\n" +
				calculateTestMethodCall() + "\n" +
				"}\n";

		return methodBody;
	}

	private String calculateTestMethodName() {
		return method.getElementName() + "Test";
	}

	private String calculateTestMethodCall() {
		return "\t" + testedInstanceName + "." + method.getElementName() + "();";
	}

}
