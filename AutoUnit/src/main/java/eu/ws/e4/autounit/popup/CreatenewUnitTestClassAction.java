package eu.ws.e4.autounit.popup;

import java.io.File;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.ws.e4.autounit.file.TestFilePathRetriever;
import eu.ws.e4.autounit.junit.JunitTestFileCreator;

public class CreatenewUnitTestClassAction implements IObjectActionDelegate {

	// Logger instance
	// private static final Logger log = LoggerFactory.getLogger(CreatenewUnitTestClassAction.class);

	private Shell shell;

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
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection.isEmpty()) {
			return;
		}

		System.out.println("Selection:" + selection);

		if (selection instanceof TreeSelection) {
			ICompilationUnit firstSelectedClass = getSelectedJavaClass(selection);

			System.out.println("Selected java file name:" + firstSelectedClass.getElementName());

			if (testFileAlreadyExists(firstSelectedClass)) {
				System.out.println("Test file exists, switch to it: " + firstSelectedClass.getPath());

			} else {
				System.out.println("Test file does not exists, creating it for: " + firstSelectedClass.getPath());

				JunitTestFileCreator testCreator = new JunitTestFileCreator(firstSelectedClass);
				testCreator.createTestClass();
			}
		}
	}

	private boolean testFileAlreadyExists(ICompilationUnit firstSelectedClass) {
		String testFilePath = TestFilePathRetriever.of(firstSelectedClass.getPath().toString()).getTestFilePath();
		return new File(testFilePath).exists();
	}

	private ICompilationUnit getSelectedJavaClass(ISelection selection) {
		TreeSelection treeSelection = (TreeSelection) selection;
		ICompilationUnit firstSelectedClass = (ICompilationUnit) treeSelection.getFirstElement();
		System.out.println("Selected element:" + firstSelectedClass);
		return firstSelectedClass;
	}

}
