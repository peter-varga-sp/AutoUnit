package eu.ws.e4.autounit.junit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.CompilationUnitFacade;
import eu.ws.e4.autounit.junit.creator.mockito.TestRunnerDefinition;

public class CreateTestFileContentParameter {

	private final CompilationUnit javaClassToBeTested;

	private final CompilationUnitFacade cuFacade;

	private final GuiOptionsHolder guiOptionsHolder;
	
	private final List<String> importStatements = new ArrayList<>();

	public CreateTestFileContentParameter(ICompilationUnit javaClassToBeTested, GuiOptionsHolder guiOptionsHolder) {
		this.javaClassToBeTested = (CompilationUnit) javaClassToBeTested;
		this.cuFacade = new CompilationUnitFacade(javaClassToBeTested);
		this.guiOptionsHolder = guiOptionsHolder;
	}

	public String getNameOfClassUnderTest() {
		return cuFacade.getClassName();
	}

	public String getTestClassName() {
		return getNameOfClassUnderTest() + "Test";
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

	public TestRunnerDefinition getTestRunner() {
		return guiOptionsHolder.getTestRunnerDefinition();
	}

	public CompilationUnitFacade getCuFacade() {
		return cuFacade;
	}

	public GuiOptionsHolder getGuiOptionsHolder() {
		return guiOptionsHolder;
	}

	public ICompilationUnit getJavaClassToBeTested() {
		return javaClassToBeTested;
	}

	public List<String> getImportStatements() {
		return importStatements;
	}

	public boolean addImportStatement(String importStatement) {
		return importStatements.add(importStatement);
	}
}