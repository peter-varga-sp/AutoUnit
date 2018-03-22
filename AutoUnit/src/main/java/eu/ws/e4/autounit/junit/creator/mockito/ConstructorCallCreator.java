package eu.ws.e4.autounit.junit.creator.mockito;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.core.LocalVariable;

class ConstructorCallCreator {

	private static final Map<String, String> HARDCODED_CONSTRUCTORS = new HashMap<>();

	static {
		HARDCODED_CONSTRUCTORS.put("QString;", "\"\"");
		HARDCODED_CONSTRUCTORS.put("QInteger;", "0");
	}

	String getConstructorCall(LocalVariable param) {
		String typeSignature = param.getTypeSignature();

		if (HARDCODED_CONSTRUCTORS.containsKey(typeSignature)) {
			return HARDCODED_CONSTRUCTORS.get(typeSignature);
		}

		String signatureSimpleName = Signature.getSignatureSimpleName(typeSignature);
		return "new " + signatureSimpleName + "()";
	}
}
