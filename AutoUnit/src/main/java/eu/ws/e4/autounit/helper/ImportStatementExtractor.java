package eu.ws.e4.autounit.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportStatementExtractor {

	private static final String REGEX_IMPORT_STATEMENT = "import .*;";

	public List<String> findImportStatements(String sourceCode) {
		List<String> result = new ArrayList<>();

		Pattern pattern = Pattern.compile(REGEX_IMPORT_STATEMENT);
		Matcher matcher = pattern.matcher(sourceCode);

		while (matcher.find()) {
			String importStetement = matcher.group();
			result.add(importStetement);
		}

		return result;
	}
}
