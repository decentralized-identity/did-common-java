package foundation.identity.did;

import com.fasterxml.jackson.databind.json.JsonMapper;
import foundation.identity.did.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValidationTest {

	private static final JsonMapper jsonMapper = JsonMapper.builder().build();

	private static final String EXAMPLE_DID_DOCUMENT_V1_0 =
			"""
                {
                  "@context": [ "https://www.w3.org/ns/did/v1", "https://w3id.org/security/suites/jws-2020/v1" ],
                  "assertionMethod": [
                    "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
                  ],
                  "authentication": [
                    "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
                  ],
                  "capabilityDelegation": [
                    "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
                  ],
                  "capabilityInvocation": [
                    "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
                  ],
                  "id": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
                  "verificationMethod": [
                    {
                      "controller": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
                      "id": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
                      "publicKeyJwk": {
                        "crv": "Bls12381G1",
                        "kty": "OKP",
                        "x": "lsfOFOAzlEpPIIKf-7vlvWiDYazg5M7VnAXblKuvB9GV66GeXw_UgoNhCZdixk_m"
                      },
                      "type": "JsonWebKey2020"
                    }
                  ]
                }
            """;

	private static final String EXAMPLE_DID_DOCUMENT_V1_1 =
		"""
			{
			  "@context": [ "https://www.w3.org/ns/did/v1.1", "https://w3id.org/security/suites/jws-2020/v1" ],
			  "assertionMethod": [
				"did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
			  ],
			  "authentication": [
				"did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
			  ],
			  "capabilityDelegation": [
				"did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
			  ],
			  "capabilityInvocation": [
				"did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs"
			  ],
			  "id": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
			  "verificationMethod": [
				{
				  "controller": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
				  "id": "did:key:z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs#z3tEFS9q2WkwvvVvr1BrYwNreqcudmcCQGGRSQ8r73recEqAUHGeLPWzwK6toBdKJgX3Fs",
				  "publicKeyJwk": {
					"crv": "Bls12381G1",
					"kty": "OKP",
					"x": "lsfOFOAzlEpPIIKf-7vlvWiDYazg5M7VnAXblKuvB9GV66GeXw_UgoNhCZdixk_m"
				  },
				  "type": "JsonWebKey2020"
				}
			  ]
			}
		""";

	@Test
	public void testValidation() throws Exception {

		DIDDocument didDocumentV1_0 = jsonMapper.readValue(EXAMPLE_DID_DOCUMENT_V1_0, DIDDocument.class);
		assertNotNull(didDocumentV1_0);
		Validation.validate(didDocumentV1_0);
		Validation.validate((DIDDocumentV1_0) didDocumentV1_0);

		DIDDocument didDocumentV1_1 = jsonMapper.readValue(EXAMPLE_DID_DOCUMENT_V1_1, DIDDocument.class);
		assertNotNull(didDocumentV1_1);
		Validation.validate(didDocumentV1_1);
		Validation.validate((DIDDocumentV1_1) didDocumentV1_1);
	}
}
