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

	public String getImportStatements() {
		return StringUtils.join(IMPORTS, ";\n");
	}

}
