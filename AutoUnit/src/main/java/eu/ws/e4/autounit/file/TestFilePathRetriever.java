package eu.ws.e4.autounit.file;

import java.io.File;

import org.apache.commons.lang3.Validate;

/**
 * Responsible for calculating path of test file for a given java class.
 * 
 * Currently works only for maven projects. If needed, extract an interface out of it, and create implementations for different project structures.
 */
public class TestFilePathRetriever {
	private static final String SEPARATOR = File.separator;
	private static final String MAVEN_TEST_PATR_PART = SEPARATOR + "src" + SEPARATOR + "test" + SEPARATOR + "java" + SEPARATOR;
	private static final String MAVEN_JAVA_PATH_PART = SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "java" + SEPARATOR;

	private final String classFilePath;

	private TestFilePathRetriever(String classFilePath) {
		this.classFilePath = classFilePath;
	}

	public static TestFilePathRetriever of(String classFilePath) {
		Validate.notBlank(classFilePath);

		return new TestFilePathRetriever(classFilePath);
	}

	public String getTestFilePath() {
		return classFilePath.replace(MAVEN_JAVA_PATH_PART, MAVEN_TEST_PATR_PART);
	}

}
