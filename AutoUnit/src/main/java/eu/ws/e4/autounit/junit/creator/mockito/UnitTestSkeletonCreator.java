package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.StringUtils;

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
			"//TEST_METHODS",
			"}"
	};

	private final CreateTestFileContentParameter parameterObject;

	UnitTestSkeletonCreator(CreateTestFileContentParameter parameterObject) {
		super();
		this.parameterObject = parameterObject;
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

		return strRunnerDef + skeleton;
	}

	private String getImportStatements() {
		return StringUtils.join(IMPORTS, ";\n") + ";\n";
	}
}
