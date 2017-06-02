package eu.ws.e4.autounit.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

public class CompilationUnitFacade {

    private final ICompilationUnit javaClass;

    public CompilationUnitFacade(ICompilationUnit javaClass) {
	super();
	this.javaClass = javaClass;
    }

    public List<IMethod> getAllTestableMethods() {
	List<IMethod> result = new ArrayList<>();

	try {
	    IMethod[] methods = javaClass.findPrimaryType().getMethods();

	    for (int i = 0; i < methods.length; i++) {
		if (isMethodTestable(methods[i])) {
		    result.add(methods[i]);
		}
	    }

	} catch (JavaModelException e) {
	    e.printStackTrace();
	}
	return result;
    }

    private boolean isMethodTestable(IMethod iMethod) throws JavaModelException {
	int flags = iMethod.getFlags();

	if (Flags.isPublic(flags) || Flags.isPackageDefault(flags) || Flags.isProtected(flags)) {
	    return true;
	}

	return false;
    }

}
