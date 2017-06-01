package eu.ws.e4.autounit.file;

import static org.hamcrest.Matchers.containsString;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFilePathRetrieverTest {

    @Test(expected = IllegalArgumentException.class)
    public void testOf_exceptionIfBlankArgument() throws Exception {
	TestFilePathRetriever.of("");
    }

    @Test
    public void testGetTestFilePath() throws Exception {
	TestFilePathRetriever tfpr = TestFilePathRetriever
		.of("C:\\Dev\\AutoUnitEclipsePlugin\\src\\main\\java\\eu\\ws\\e4\\autounit\\file\\TestFilePathRetriever.java");
	assertThat(tfpr.getTestFilePath(), containsString("src\\test\\java\\eu\\ws"));
    }

}
