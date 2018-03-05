package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.StringUtils;

public class UnitTestSkeletonCreator {

	private static final String[] TEST_CLASS_SKELETON = {
			"public class TEST_CLASS_NAME {",
			"//TEST_METHODS",
			"}"
	};

	public String getUnitTestClassSkeleton(TestRunnerDefinition testRunnerDefinition, String testClassName) {

		String strRunnerDef = testRunnerDefinition.getRunnerDefinition() + "\n";

		String skeleton = StringUtils.join(TEST_CLASS_SKELETON, "\n");
		skeleton = skeleton.replace("TEST_CLASS_NAME", testClassName);

		return strRunnerDef + skeleton;
	}
}
