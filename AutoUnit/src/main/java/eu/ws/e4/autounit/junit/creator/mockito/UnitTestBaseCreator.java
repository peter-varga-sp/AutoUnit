package eu.ws.e4.autounit.junit.creator.mockito;

import org.apache.commons.lang3.StringUtils;

public class UnitTestBaseCreator {

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

	private final TestRunnerDefinition testRunnerDefinition;
	private final String testClassName;

	public UnitTestBaseCreator(TestRunnerDefinition testRunnerDefinition, String testClassName) {
		this.testRunnerDefinition = testRunnerDefinition;
		this.testClassName = testClassName;
	}

	private String getImportStatements() {
		return StringUtils.join(IMPORTS, ";\n") + ";\n";
	}

	private String getUnitTestClassSkeleton() {
		return new UnitTestSkeletonCreator().getUnitTestClassSkeleton(testRunnerDefinition, testClassName);
	}

	public String create() {
		String result = getImportStatements();
		result = result + "\n" + getUnitTestClassSkeleton();

		return result;
	}

}
