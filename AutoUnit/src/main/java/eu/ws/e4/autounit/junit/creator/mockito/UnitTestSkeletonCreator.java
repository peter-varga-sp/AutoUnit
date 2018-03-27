package eu.ws.e4.autounit.junit.creator.mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.ImportStatementExtractor;
import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

class UnitTestSkeletonCreator {

	private static final String[] IMPORTS = {
			"import static org.hamcrest.CoreMatchers.equalTo",
			"import static org.junit.Assert.assertThat",
			"import static org.mockito.Mockito.when",
			"import static org.mockito.Mockito.times",
			"import static org.mockito.Mockito.verify",

			"import org.exparity.hamcrest.date.LocalDateTimeMatchers",
			"import org.junit.Test",
			"import org.junit.Before",
			"import org.junit.runner.RunWith",
			"import org.mockito.InjectMocks",
			"import org.mockito.Mock",
			"import org.mockito.Mockito",
			"import org.mockito.junit.MockitoJUnitRunner"
	};

	private static final String[] TEST_CLASS_SKELETON = {
			"public class TEST_CLASS_NAME {",
			"//VARIABLES",
			"//INIT_DEPENDENCIES",
			"//TEST_METHODS",
			"}"
	};

	private final CreateTestFileContentParameter parameterObject;

	private final String testedInstanceName;

	UnitTestSkeletonCreator(CreateTestFileContentParameter parameterObject) {
		this.parameterObject = parameterObject;
		this.testedInstanceName = WordUtils.uncapitalize(parameterObject.getNameOfClassUnderTest());
	}

	public String create() {
		String result = getPackageDeclaration() + getImportStatements();
		result = result + "\n" + getUnitTestClassSkeleton();
		return result;
	}

	private String getUnitTestClassSkeleton() {
		String strRunnerDef = parameterObject.getTestRunner().getRunnerDefinition() + "\n";

		String skeleton = StringUtils.join(TEST_CLASS_SKELETON, "\n");
		skeleton = skeleton.replace("TEST_CLASS_NAME", parameterObject.getTestClassName());
		skeleton = skeleton.replace("//VARIABLES", declareFields());
		DependencyMockGenerator dependencyMockGenerator = new DependencyMockGenerator(parameterObject);

		skeleton = skeleton.replace("//INIT_DEPENDENCIES", dependencyMockGenerator.getInitMethodWithMockedCalls());

		return strRunnerDef + skeleton;
	}

	private String getPackageDeclaration() {
		return "package " + parameterObject.getPackegeDeclaration() + ";\n\n";
	}

	private String getImportStatements() {
		String sourceCode = parameterObject.getCuFacade().getSourceCode();
		ImportStatementExtractor importStatementExtractor = new ImportStatementExtractor();
		List<String> foundImportStatementList = importStatementExtractor.findImportStatements(sourceCode);

		return StringUtils.join(IMPORTS, ";\n") + ";\n\n" + StringUtils.join(foundImportStatementList, "\n") + "\n";
	}

	private String declareFields() {
		return instantiateFields() + "\n\n" + instantiateClassUnderTest();
	}

	private String instantiateFields() {
		List<String> mockableFieldDeclarations = new ArrayList<>();
		for (SourceField field : parameterObject.getMockableFields()) {
			mockableFieldDeclarations.add(instantiateMockableField(field));
		}
		return StringUtils.join(mockableFieldDeclarations, "\n");
	}

	private String instantiateMockableField(SourceField field) {
		String typeSignature;
		try {
			typeSignature = field.getTypeSignature();
		} catch (JavaModelException e) {
			return "";
		}
		String signatureSimpleName = Signature.getSignatureSimpleName(typeSignature);

		String result = "private " + signatureSimpleName + " " + field.getElementName() + ";";

		List<IAnnotation> annotations = getAnnotationsOf(field);
		if (hasIocAnnotation(annotations)) {
			result = "@Mock\n" + result;
		}

		return result;
	}

	private boolean hasIocAnnotation(List<IAnnotation> annotations) {
		if (CollectionUtils.isEmpty(annotations)) {
			return false;
		}

		for (IAnnotation iAnnotation : annotations) {
			String elementName = iAnnotation.getElementName();
			if (elementName.equals("Autowired")) {
				return true;
			}
		}

		return false;
	}

	private List<IAnnotation> getAnnotationsOf(SourceField field) {
		try {
			IAnnotation[] annotations = field.getAnnotations();
			return Arrays.asList(annotations);
		} catch (JavaModelException e) {
			return Collections.emptyList();
		}
	}

	private String instantiateClassUnderTest() {
		if (parameterObject.getTestRunner().isFieldMockingSupported()) {
			return "\t@InjectMocks\n" + "\tprivate " + parameterObject.getNameOfClassUnderTest() + " " + testedInstanceName + ";";
		} else {
			return "\tprivate final " + parameterObject.getNameOfClassUnderTest() + " " + testedInstanceName +
					" = new " + parameterObject.getNameOfClassUnderTest() + "();";
		}
	}

}
