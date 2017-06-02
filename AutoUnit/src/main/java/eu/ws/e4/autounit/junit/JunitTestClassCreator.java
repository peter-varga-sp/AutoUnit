package eu.ws.e4.autounit.junit;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;

import eu.ws.e4.autounit.helper.CompilationUnitFacade;

public class JunitTestClassCreator {
    private final ICompilationUnit javaClass;

    public JunitTestClassCreator(ICompilationUnit javaClass) {
	super();
	this.javaClass = javaClass;
    }

    public void createTestClass() {
	CompilationUnitFacade cuFacade = new CompilationUnitFacade(javaClass);
	
	List<IMethod> allTestableMethods = cuFacade.getAllTestableMethods();

	System.out.println("Methods: " + allTestableMethods.size());

    }

    

}
