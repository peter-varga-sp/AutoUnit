package eu.ws.e4.autounit.junit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.CompilationUnitFacade;
import eu.ws.e4.autounit.junit.creator.mockito.TestRunnerDefinition;
import eu.ws.e4.autounit.junit.creator.mockito.UnitTestCreator;

public class JunitTestFileCreator {
	private final ICompilationUnit javaClass;

	private final String testFilePath;

	public JunitTestFileCreator(ICompilationUnit javaClass, String testFilePath) {
		this.javaClass = javaClass;
		this.testFilePath = testFilePath;
	}

	public void createTestClass() {
		CompilationUnitFacade cuFacade = new CompilationUnitFacade(javaClass);

		List<IMethod> allTestableMethods = cuFacade.getAllTestableMethods();
		System.out.println("Methods: " + allTestableMethods.size());

		List<SourceField> mockableFields = cuFacade.getMockableFields();
		System.out.println("Fields: " + mockableFields.size());

		File newTestFile = new File(testFilePath);
		String testClassName = newTestFile.getName().replace(".java", "");
		String nameOfClassUnderTest = javaClass.getElementName().replace(".java", "");

		String packegeDeclaration = cuFacade.getPackegeDeclaration();

		CreateTestFileContentParameter parameterObject = new CreateTestFileContentParameter(nameOfClassUnderTest, testClassName,
				TestRunnerDefinition.Junit, allTestableMethods, packegeDeclaration);

		String testFileContent = createTestFileContent(parameterObject);

		try {
			System.out.println("About to create file: " + testFilePath);
			FileUtils.write(newTestFile, testFileContent, "UTF-8");
			System.out.println("File got created: " + testFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String createTestFileContent(CreateTestFileContentParameter parameterObject) {
		UnitTestCreator unitTestBaseCreator = new UnitTestCreator(parameterObject);
		return unitTestBaseCreator.create();
	}

}
