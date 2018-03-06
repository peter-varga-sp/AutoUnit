package eu.ws.e4.autounit.junit.creator.mockito;

import org.eclipse.jdt.core.IMethod;

import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

public class UnitTestCreator {

	private final CreateTestFileContentParameter parameterObject;

	public UnitTestCreator(CreateTestFileContentParameter parameterObject) {
		this.parameterObject = parameterObject;
	}

	private String getUnitTestClassSkeleton() {
		return new UnitTestSkeletonCreator(parameterObject).create();
	}

	public String create() {
		String result = getUnitTestClassSkeleton();

		String testMethods = createTestMethods();
		result = result.replace("//TEST_METHODS", testMethods);
		
		return result;
	}

	private String createTestMethods() {
		String result = "";

		for (IMethod method : parameterObject.getAllTestableMethods()) {
			result = result + new UnitTestMethodCreator(method).create();
		}

		return result;
	}

}
