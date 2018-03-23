package eu.ws.e4.autounit.junit.creator.mockito;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.core.SourceField;

import eu.ws.e4.autounit.helper.DependencyMethodCallInfo;
import eu.ws.e4.autounit.helper.DependencyMethodCallInfo.MethodCallDetail;
import eu.ws.e4.autounit.helper.FieldUsageExtractor;
import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

public class DependencyMockGenerator {

	private static final String[] INIT_METHOD_SKELETON = {
			"\t@Before",
			"\tpublic void init() {",
			"//INIT_DEPENDENCIES",
			"\t}"
	};

	private final CreateTestFileContentParameter parameterObject;

	public DependencyMockGenerator(CreateTestFileContentParameter parameterObject) {
		this.parameterObject = parameterObject;
	}

	public String getInitMethodWithMockedCalls() {
		List<String> mockedCalls = getMockedCallsOfdependencyFields();

		String strMockedCallLines = StringUtils.join(mockedCalls, "\n");
		String initMethodBody = StringUtils.join(INIT_METHOD_SKELETON, "\n");

		return initMethodBody.replace("//INIT_DEPENDENCIES", strMockedCallLines);
	}

	public List<String> getMockedCallsOfdependencyFields() {

		List<String> dependencyCallInitializers = new ArrayList<>();

		List<SourceField> mockableFields = parameterObject.getCuFacade().getMockableFields();
		String sourceCode = parameterObject.getCuFacade().getSourceCode();

		for (SourceField sourceField : mockableFields) {
			String dependencyFieldName = sourceField.getElementName();

			DependencyMethodCallInfo usagesOfField = new FieldUsageExtractor().findUsages(sourceCode, dependencyFieldName);
			if (!usagesOfField.methodCallFound()) {
				continue;
			}

			for (MethodCallDetail methodCallDetail : usagesOfField.getMethodCallDetails()) {
				// IMethod calledMethod = getMethodsOfDependencyField(sourceField, methodCallDetail);

				String methodCallMock = "when(" + dependencyFieldName + "." + methodCallDetail.getMethodName() + "("
						+ calculateParameters(methodCallDetail)
						+ ")).thenReturn(null);";
				dependencyCallInitializers.add(methodCallMock);
			}

		}

		return dependencyCallInitializers;
	}

	private String calculateParameters(MethodCallDetail methodCallDetail) {
		if (CollectionUtils.isEmpty(methodCallDetail.getParameters())) {
			return "";
		}

		String parameterDef = "Mockito.eq(null)";
		List<String> callParameters = new ArrayList<>();
		methodCallDetail.getParameters().forEach(p -> {
			callParameters.add(parameterDef);
		});

		return StringUtils.join(callParameters, ",");
	}

	// private IMethod getMethodsOfDependencyField(SourceField sourceField, MethodCallDetail methodCallDetail) {
	// IJavaModel javaModel = sourceField.getJavaModel();
	// ITypeRoot typeRoot = sourceField.getTypeRoot();
	// IType declaringTypeOfDependencyField = sourceField.getDeclaringType();
	//
	// ICompilationUnit compilationUnit = sourceField.getCompilationUnit();
	// IClassFile classFile = sourceField.getClassFile();
	//
	// try {
	// List<IMethod> methods = Arrays.asList(declaringTypeOfDependencyField.getMethods());
	// List<IMethod> methodsWithSameName = new ArrayList<>();
	// for (IMethod iMethod : methods) {
	// if (iMethod.getElementName().equals(methodCallDetail.getMethodName())) {
	// System.out.println("Method with same name found: " + iMethod);
	// methodsWithSameName.add(iMethod);
	// }
	// }
	//
	// if (methodsWithSameName.isEmpty()) {
	// // TODO check if it can happen
	// return null;
	// }
	//
	// if (methodsWithSameName.size() == 1) {
	// return methodsWithSameName.get(0);
	// }
	//
	// if (methodCallDetail.getParameters() == null) {
	// // return first method with parameter
	// return methodsWithSameName.stream().filter(m -> m.getNumberOfParameters() > 0).findFirst().get();
	// }
	//
	// // return first method with same number of parameters
	// // TODO refine the search
	// return methodsWithSameName.stream()
	// .filter(m -> m.getNumberOfParameters() == methodCallDetail.getParameters().size())
	// .findFirst().get();
	//
	// } catch (JavaModelException e1) {
	// return null;
	// }
	// }

}
