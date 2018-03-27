package eu.ws.e4.autounit.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.SourceField;

public class CompilationUnitFacade {

	private final CompilationUnit javaClass;

	public CompilationUnitFacade(ICompilationUnit javaClass) {
		super();
		this.javaClass = (CompilationUnit) javaClass;
	}

	/**
	 * Returns all non private methods of the underlying java class
	 */
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

	public List<SourceField> getMockableFields() {
		List<SourceField> result = new ArrayList<>();
		try {
			IField[] fields = javaClass.findPrimaryType().getFields();
			for (int i = 0; i < fields.length; i++) {
				SourceField iField = (SourceField) fields[i];
				int flags = iField.getFlags();
				if (!Flags.isFinal(flags)) {
					result.add(iField);
				}
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getPackegeDeclaration() {
		try {
			IPackageDeclaration[] packageDeclarations = javaClass.getPackageDeclarations();

			return packageDeclarations[0].getElementName();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return "";
	}

	public String getClassName() {
		return new String(javaClass.getMainTypeName());
	}

	public String getSourceCode() {
		String sourceCode = "";
		try {
			sourceCode = javaClass.getSource();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourceCode;
	}

}
