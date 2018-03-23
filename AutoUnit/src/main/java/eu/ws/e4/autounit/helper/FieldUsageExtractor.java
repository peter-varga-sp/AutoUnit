package eu.ws.e4.autounit.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Finds method calls on the defined fields in the given source code
 */
public class FieldUsageExtractor {

	private static final String REGEX_FIND_METHOD_CALL = "\\.(?<groupMethodName>.+?)\\((?<parameterList>.*)\\)";

	public List<DependencyMethodCallInfo> findUsages(String sourceCode, List<String> fieldNames) {
		List<DependencyMethodCallInfo> result = new ArrayList<>();

		for (String field : fieldNames) {
			result.add(findUsages(sourceCode, field));
		}

		return result;
	}

	public DependencyMethodCallInfo findUsages(String sourceCode, String fieldName) {
		DependencyMethodCallInfo result = new DependencyMethodCallInfo(fieldName);

		System.out.println("Searching for usages of field: " + fieldName);

		Pattern pattern = Pattern.compile(fieldName + REGEX_FIND_METHOD_CALL);
		Matcher matcher = pattern.matcher(sourceCode);

		while (matcher.find()) {
			String calledMethodName = matcher.group("groupMethodName");
			String parameterList = matcher.group("parameterList");
			System.out.println("Method name: " + calledMethodName);
			System.out.println("Parameter list: " + parameterList);

			List<String> parameters = exctactParameters(parameterList);

			result.registerMethodCall(calledMethodName, parameters);
		}

		return result;

	}

	private List<String> exctactParameters(String parameterList) {
		if (StringUtils.isBlank(parameterList)) {
			return Collections.emptyList();
		}

		if (!parameterList.contains(",")) {
			return Arrays.asList(parameterList);
		}
		if (parameterList.contains("(")) {
			// contains further method calls other constructors. Will be implemented later on.
			return null;
		}

		// simple parameters, separated by comma
		List<String> parameterNames = Arrays.asList(parameterList.split(","));
		return parameterNames.stream().map(p -> p.trim()).collect(Collectors.toList());
	}

}
