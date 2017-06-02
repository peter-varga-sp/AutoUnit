package eu.ws.e4.autounit.helper;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.osgi.internal.framework.BundleContextImpl;
import org.junit.Test;
import org.osgi.framework.BundleContext;

public class CompilationUnitFacadeTest {

    @Test
    public void testGetAllTestableMethods() throws Exception {

	// ResourcesPlugin plugin = new ResourcesPlugin();
	// BundleContext context = new BundleContextImpl(bundle, container);
	// plugin.start(context );
	//
	// IWorkspace workspace = ResourcesPlugin.getWorkspace();
	// IPath path = Path.fromOSString("ClassUnderTest.java");
	// IFile file = workspace.getRoot().getFileForLocation(path);
	// ICompilationUnit compilationUnit = (ICompilationUnit) JavaCore.create(file);
	//
	// IType primaryType = compilationUnit.findPrimaryType();
	// assertNotNull(primaryType);

	// FileUtils.writeStringToFile(new File("qwert.txt"), "Test", true);
	//
	// File file = new File("ClassUnderTest.java");
	//
	// String sourceContent = FileUtils.readFileToString(file, "UTF-8");
	// ASTParser parser = ASTParser.newParser(AST.JLS3);
	// parser.setSource(sourceContent.toCharArray());
	// CompilationUnit testUnit = (CompilationUnit) parser.createAST(null);
	//
	// IJavaElement javaElement = testUnit.getJavaElement();
	//
	// assertNotNull(javaElement);

    }

}
