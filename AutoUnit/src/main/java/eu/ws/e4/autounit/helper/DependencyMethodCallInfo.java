package eu.ws.e4.autounit.helper;

import java.util.ArrayList;
import java.util.List;

public class DependencyMethodCallInfo {

	private final String dependencyFieldName;

	private final List<MethodCallDetail> methodCallDetails = new ArrayList<>();

	public DependencyMethodCallInfo(String dependencyFieldName) {
		this.dependencyFieldName = dependencyFieldName;
	}

	public boolean methodCallFound() {
		return !methodCallDetails.isEmpty();
	}

	public void registerMethodCall(String methodName, List<String> parameters) {

		// TODO make it more sophisticated
		boolean present = methodCallDetails.stream()
				.filter(m -> m.getMethodName().equals(methodName) && m.getParameters().size() == parameters.size())
				.findFirst().isPresent();

		if (!present) {
			methodCallDetails.add(new MethodCallDetail(methodName, parameters));
		}
	}

	public class MethodCallDetail {
		private final String methodName;

		private final List<String> parameters;

		public MethodCallDetail(String methodName, List<String> parameters) {
			this.methodName = methodName;
			this.parameters = parameters;
		}

		public String getMethodName() {
			return methodName;
		}

		public List<String> getParameters() {
			return parameters;
		}
	}

	public String getDependencyFieldName() {
		return dependencyFieldName;
	}

	public List<MethodCallDetail> getMethodCallDetails() {
		return methodCallDetails;
	}

}
