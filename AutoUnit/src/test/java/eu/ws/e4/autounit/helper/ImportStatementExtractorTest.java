package eu.ws.e4.autounit.helper;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ImportStatementExtractorTest {

	private ImportStatementExtractor importStatementExtractor = new ImportStatementExtractor();

	@Test
	public void testFindUsages() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		List<String> statements = importStatementExtractor.findImportStatements(sourceCode);

		assertThat(statements.size(), equalTo(19));
	}
}
