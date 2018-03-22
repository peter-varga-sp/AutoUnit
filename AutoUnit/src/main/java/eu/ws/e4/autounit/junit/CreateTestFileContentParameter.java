package eu.ws.e4.autounit.junit;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.CompilationUnitFacade;
import eu.ws.e4.autounit.junit.creator.mockito.TestRunnerDefinition;

public class CreateTestFileContentParameter {

	private final String nameOfClassUnderTest;

	private final String testClassName;

	private final CompilationUnitFacade cuFacade;

	private final GuiOptionsHolder guiOptionsHolder;

	public CreateTestFileContentParameter(String nameOfClassUnderTest, String testClassName, CompilationUnitFacade cuFacade,
			GuiOptionsHolder guiOptionsHolder) {
		this.nameOfClassUnderTest = nameOfClassUnderTest;
		this.testClassName = testClassName;
		this.cuFacade = cuFacade;
		this.guiOptionsHolder = guiOptionsHolder;
	}

	public String getPackegeDeclaration() {
		return cuFacade.getPackegeDeclaration();
	}

	public List<IMethod> getAllTestableMethods() {
		return cuFacade.getAllTestableMethods();
	}

	public List<SourceField> getMockableFields() {
		return cuFacade.getMockableFields();
	}

	public String getTestClassName() {
		return testClassName;
	}

	public TestRunnerDefinition getTestRunner() {
		return guiOptionsHolder.getTestRunnerDefinition();
	}

	public String getNameOfClassUnderTest() {
		return nameOfClassUnderTest;
	}

	public CompilationUnitFacade getCuFacade() {
		return cuFacade;
	}

	public GuiOptionsHolder getGuiOptionsHolder() {
		return guiOptionsHolder;
	}
}