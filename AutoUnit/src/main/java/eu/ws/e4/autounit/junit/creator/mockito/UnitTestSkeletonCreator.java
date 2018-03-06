package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

class UnitTestSkeletonCreator {

	private static final String[] IMPORTS = {
			"import static org.hamcrest.CoreMatchers.equalTo",
			"import static org.junit.Assert.assertThat",
			"import static org.mockito.Mockito.when",
			"import org.exparity.hamcrest.date.LocalDateTimeMatchers",
			"import org.junit.Test",
			"import org.junit.runner.RunWith",
			"import org.mockito.InjectMocks",
			"import org.mockito.Mock",
			"import org.mockito.Mockito",
			"import org.mockito.junit.MockitoJUnitRunner"
	};

	private static final String[] TEST_CLASS_SKELETON = {
			"public class TEST_CLASS_NAME {",
			"//VARIABLES",
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
		String result = getImportStatements();
		result = result + "\n" + getUnitTestClassSkeleton();
		return result;
	}

	private String getUnitTestClassSkeleton() {
		String strRunnerDef = parameterObject.getTestRunner().getRunnerDefinition() + "\n";

		String skeleton = StringUtils.join(TEST_CLASS_SKELETON, "\n");
		skeleton = skeleton.replace("TEST_CLASS_NAME", parameterObject.getTestClassName());

		skeleton = skeleton.replace("//VARIABLES", instantiateClassUnderTest());

		return strRunnerDef + skeleton;
	}

	private String getImportStatements() {
		return StringUtils.join(IMPORTS, ";\n") + ";\n";
	}

	private String instantiateClassUnderTest() {
		return "\tprivate final " + parameterObject.getNameOfClassUnderTest() + " " + testedInstanceName +
				" = new " + parameterObject.getNameOfClassUnderTest() + "();";
	}
}
