package eu.ws.e4.autounit.junit.creator.mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.core.LocalVariable;
import org.eclipse.jdt.internal.core.SourceMethod;
import org.eclipse.jdt.internal.core.SourceMethodElementInfo;

import eu.ws.e4.autounit.junit.CreateTestFileContentParameter;

class UnitTestMethodCreator {
	private final IMethod method;
	private final String testedInstanceName;
	private final CreateTestFileContentParameter parameterObject;

	UnitTestMethodCreator(IMethod method, CreateTestFileContentParameter parameterObject) {
		this.method = method;
		this.parameterObject = parameterObject;
		this.testedInstanceName = WordUtils.uncapitalize(parameterObject.getNameOfClassUnderTest());
	}

	String create() {
		StringBuilder methodBody = new StringBuilder();

		methodBody.append("\n@Test\n");
		methodBody.append("public void " + calculateTestMethodName() + "() {").append("\n");
		// methodBody.append(getBeanDeclatationLine()).append("\n");
		methodBody.append(getParameterDeclarationLines()).append("\n\n");
		methodBody.append(getMethodCallLine()).append("\n");
		methodBody.append(getCheckResultValueLine()).append("\n");
		methodBody.append("}\n");

		return methodBody.toString();
	}

	private String calculateTestMethodName() {
		return method.getElementName() + getParametersIfNeeded() + "Test";
	}

	// differentiate the test methods by using parameter types in method name, if there are multiple methods with the same name in the class under
	// test
	private String getParametersIfNeeded() {
		if (hasMultipleMethodsWithSameName()) {
			return getParameterTypesForMethodName();
		}
		return "";
	}

	private boolean hasMultipleMethodsWithSameName() {
		long methodsWithSameName = parameterObject.getAllTestableMethods().stream()
				.filter(m -> m.getElementName().equals(method.getElementName()))
				.count();
		return methodsWithSameName >= 2;
	}

	private String getParameterTypesForMethodName() {
		List<ILocalVariable> parameters = getMethodParameters();
		if (parameters.isEmpty()) {
			return "";
		}

		String joinedParameterTypeNames = parameters.stream()
				.map(p -> {
					return Signature.getSignatureSimpleName(p.getTypeSignature());
				})
				.collect(Collectors.joining());

		return joinedParameterTypeNames;
	}

	private List<ILocalVariable> getMethodParameters() {
		try {
			ILocalVariable[] parameters = method.getParameters();
			return Arrays.asList(parameters);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	// private String getBeanDeclatationLine() {
	// return "\t" + parameterObject.getNameOfClassUnderTest() + " " + testedInstanceName + " = new " + parameterObject.getNameOfClassUnderTest()
	// + "();";
	// }

	private String getParameterDeclarationLines() {
		ArrayList<String> parameterDeclarations = new ArrayList<>();

		try {
			for (int i = 0; i < method.getParameters().length; i++) {
				String parameterDeclaration = createParameterDeclarationLine(i);
				parameterDeclarations.add(parameterDeclaration);
			}
		} catch (JavaModelException e) {

		}
		return StringUtils.join(parameterDeclarations, "\n");
	}

	private String createParameterDeclarationLine(int i) throws JavaModelException {
		LocalVariable param = (LocalVariable) method.getParameters()[i];
		String typeSignature = param.getTypeSignature();
		String signatureSimpleName = Signature.getSignatureSimpleName(typeSignature);

		String constructorCall = new ConstructorCallCreator().getConstructorCall(param);

		return signatureSimpleName + " " + param.getElementName() + " = " + constructorCall + ";";
	}

	private String getMethodCallLine() {
		try {
			String returnType = getMethodCallReturnType();
			String resultValueDef = returnType == null ? "" : returnType + " result = ";

			return "\t" + resultValueDef + testedInstanceName + "." + method.getElementName() + "("
					+ getParameterNames(method.getParameterNames()) + ");\n";
		} catch (JavaModelException e) {
			return "";
		}
	}

	private String getCheckResultValueLine() {
		try {
			String returnType = getMethodCallReturnType();
			if (returnType == null) {
				return "";
			}
			return "\t" + "assertThat(result, equalTo(null));";
		} catch (JavaModelException e) {
			return "";
		}
	}

	private String getMethodCallReturnType() throws JavaModelException {
		SourceMethod sm = (SourceMethod) method;
		SourceMethodElementInfo info = (SourceMethodElementInfo) sm.getElementInfo();
		String returnType = new String(info.getReturnTypeName());

		if (returnType.equals("void")) {
			return null;
		}

		return returnType;
	}

	private String getParameterNames(String[] parameters) {
		String result = "";
		for (int i = 0; i < parameters.length; i++) {
			String param = parameters[i];
			if (i > 0) {
				result = result + ", ";
			}
			result = result + param;
		}
		return result;
	}

}
