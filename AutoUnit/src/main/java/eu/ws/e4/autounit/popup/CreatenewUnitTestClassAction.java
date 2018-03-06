package eu.ws.e4.autounit.popup;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.ws.e4.autounit.file.TestFilePathRetriever;
import eu.ws.e4.autounit.junit.JunitTestFileCreator;

public class CreatenewUnitTestClassAction implements IObjectActionDelegate {

	// Logger instance
	// private static final Logger log = LoggerFactory.getLogger(CreatenewUnitTestClassAction.class);

	private ICompilationUnit selectedCompilationUnit;

	/**
	 * Constructor for Action1.
	 */
	public CreatenewUnitTestClassAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing for now
		// shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		System.out.println("Selection:" + selectedCompilationUnit);
		System.out.println("Selected java file name:" + selectedCompilationUnit.getElementName());

		if (testClassSelected(selectedCompilationUnit)) {
			return;
		}
		if (testFileAlreadyExists(selectedCompilationUnit)) {
			System.out.println("Test file exists, switch to it: " + selectedCompilationUnit.getPath());

		} else {
			System.out.println("Test file does not exists, creating it for: " + selectedCompilationUnit.getPath());
			String testFilePath = testFilePath(selectedCompilationUnit);

			JunitTestFileCreator testCreator = new JunitTestFileCreator(selectedCompilationUnit, testFilePath);
			testCreator.createTestClass();
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		selectedCompilationUnit = (ICompilationUnit) structuredSelection.getFirstElement();
	}

	private boolean testClassSelected(ICompilationUnit firstSelectedClass) {
		// FIXME: define more sophisticated rule for it finding test class. Path or search for annotations.
		String elementName = firstSelectedClass.getElementName();
		return elementName.endsWith("Test.java");
	}

	private boolean testFileAlreadyExists(ICompilationUnit firstSelectedClass) {
		String testFilePath = testFilePath(firstSelectedClass);
		return new File(testFilePath).exists();
	}

	private String testFilePath(ICompilationUnit firstSelectedClass) {
		String classFilePath = getClassFilePath(firstSelectedClass);
		String testFilePath = TestFilePathRetriever.of(classFilePath).getTestFilePath();
		return testFilePath;
	}

	private String getClassFilePath(ICompilationUnit firstSelectedClass) {
		try {
			IResource underlyingResource = firstSelectedClass.getUnderlyingResource();
			if (underlyingResource.getType() == IResource.FILE) {
				IFile ifile = (IFile) underlyingResource;
				return ifile.getRawLocation().toString();
			}
		} catch (JavaModelException e) {

		}
		return null;

	}

}
