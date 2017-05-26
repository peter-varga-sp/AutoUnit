package autounit.popup.actions;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

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
	    TreeSelection treeSelection = (TreeSelection) selection;
	    ICompilationUnit firstSelectedClass = (ICompilationUnit) treeSelection.getFirstElement();
	    System.out.println("Selected element:" + firstSelectedClass);
	    
	    String javaFileName = firstSelectedClass.getElementName();
	    System.out.println("Selected element:" + firstSelectedClass);
	    
	}

    }

}
