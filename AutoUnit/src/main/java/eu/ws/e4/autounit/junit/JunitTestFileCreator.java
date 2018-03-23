package eu.ws.e4.autounit.junit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ICompilationUnit;

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
		File newTestFile = new File(testFilePath);

		// TODO get it from GUI
		GuiOptionsHolder guiOptionsHolder = new GuiOptionsHolder();
		guiOptionsHolder.setTestRunnerDefinition(TestRunnerDefinition.Mockito);

		CreateTestFileContentParameter parameterObject = new CreateTestFileContentParameter(javaClass, guiOptionsHolder);

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
