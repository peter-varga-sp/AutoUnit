package eu.ws.e4.autounit.helper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import eu.ws.e4.autounit.helper.DependencyMethodCallInfo.MethodCallDetail;

public class FieldUsageExtractorTest {

	private final FieldUsageExtractor fieldUsageExtractor = new FieldUsageExtractor();

	@Test
	public void testFindUsages() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		List<String> fieldNames = Arrays.asList("logicDao", "presenceSimulationSchedulerService");
		fieldUsageExtractor.findUsages(sourceCode, fieldNames);
	}

	@Test
	public void testFindUsages_singleField_noMethodParameter() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		DependencyMethodCallInfo usagesService = fieldUsageExtractor.findUsages(sourceCode, "presenceSimulationSchedulerService");
		assertEquals(usagesService.getDependencyFieldName(), "presenceSimulationSchedulerService");
		assertEquals(2, usagesService.getMethodCallDetails().size());

		MethodCallDetail methodCallDetailWithoutParameter = usagesService.getMethodCallDetails().get(0);
		assertThat(methodCallDetailWithoutParameter.getMethodName(), equalTo("refresh"));
		assertThat(methodCallDetailWithoutParameter.getParameters().size(), equalTo(0));
	}

	@Test
	public void testFindUsages_singleField_singleMethodParameter() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		DependencyMethodCallInfo usagesLogicDao = fieldUsageExtractor.findUsages(sourceCode, "logicDao");
		assertEquals(usagesLogicDao.getDependencyFieldName(), "logicDao");
		assertEquals(3, usagesLogicDao.getMethodCallDetails().size());

		MethodCallDetail methodCallDetailWithSingleParameter = usagesLogicDao.getMethodCallDetails().get(0);
		assertThat(methodCallDetailWithSingleParameter.getMethodName(), equalTo("findOne"));
		assertThat(methodCallDetailWithSingleParameter.getParameters().size(), equalTo(1));
	}

	@Test
	public void testFindUsages_singleField_multipleMethodParameters() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		DependencyMethodCallInfo usagesGatewayDao = fieldUsageExtractor.findUsages(sourceCode, "gatewayDao");
		assertEquals(usagesGatewayDao.getDependencyFieldName(), "gatewayDao");
		assertEquals(1, usagesGatewayDao.getMethodCallDetails().size());

		MethodCallDetail methodCallDetailWithMultipleParameters = usagesGatewayDao.getMethodCallDetails().get(0);
		assertThat(methodCallDetailWithMultipleParameters.getMethodName(), equalTo("registerResponse"));
		assertThat(methodCallDetailWithMultipleParameters.getParameters().size(), equalTo(2));
		assertThat(methodCallDetailWithMultipleParameters.getParameters().get(0), equalTo("response"));
		assertThat(methodCallDetailWithMultipleParameters.getParameters().get(1), equalTo("logic"));
	}
	
	@Test
	public void testFindUsages_singleField_furtherCallsInMethodParameter() throws IOException {
		String sourceCode = FileUtils.readFileToString(new File("./src/test/resources/eu/ws/e4/autounit/helper/FieldUsageExtractorTest.txt"),
				"UTF-8");

		DependencyMethodCallInfo usagesService = fieldUsageExtractor.findUsages(sourceCode, "presenceSimulationSchedulerService");
		assertEquals(usagesService.getDependencyFieldName(), "presenceSimulationSchedulerService");
		assertEquals(2, usagesService.getMethodCallDetails().size());

		MethodCallDetail methodCallDetailWithCallParameter = usagesService.getMethodCallDetails().get(1);
		assertThat(methodCallDetailWithCallParameter.getMethodName(), equalTo("schedule"));
		assertThat(methodCallDetailWithCallParameter.getParameters(), nullValue());
	}
}
