package eu.ws.e4.autounit.junit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.CompilationUnitFacade;

public class JunitTestFileCreator {
	private final ICompilationUnit javaClass;

	public JunitTestFileCreator(ICompilationUnit javaClass) {
		super();
		this.javaClass = javaClass;
	}

	public String createTestClass() {
		CompilationUnitFacade cuFacade = new CompilationUnitFacade(javaClass);

		List<IMethod> allTestableMethods = cuFacade.getAllTestableMethods();
		System.out.println("Methods: " + allTestableMethods.size());

		List<SourceField> mockableFields = cuFacade.getMockableFields();
		System.out.println("Fields: " + mockableFields.size());

		createFileSkeletonFromTemplate();

		return "";

	}

	private void createFileSkeletonFromTemplate() {
		File templateFile = new File("/main/resources/templates/mockito/MockitoBasicTemplate.javatmpl");
		File newTestClassFile = new File("/main/resources/templates/mockito/MockitoBasicTemplate.java");
		try {
			FileUtils.copyFile(templateFile, newTestClassFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getClassStructure(Class clazz) {

		clazz.getEnclosingMethod();

	}

}
