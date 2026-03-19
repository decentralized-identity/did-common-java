package foundation.identity.did;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializeTest {

	private record ResolveResult (
		DIDDocument didDocument,
		Map<String, Object> didResolutionMetadata,
		Map<String, Object> didDocumentMetadata
	) { }

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

	private static final String EXAMPLE_RESOLVE_RESULT_V1_0 =
			"""
            {
              "didDocument":
            """ + EXAMPLE_DID_DOCUMENT_V1_0 + """
		  ,
		  "didResolutionMetadata": {
			"a": "b"
		  },
		  "didDocumentMetadata": {
			"c": "d"
		  }
		}
		""";

	private static final String EXAMPLE_RESOLVE_RESULT_V1_1 =
		"""
		{
		  "didDocument":
		""" + EXAMPLE_DID_DOCUMENT_V1_1 + """
		  ,
		  "didResolutionMetadata": {
			"a": "b"
		  },
		  "didDocumentMetadata": {
			"c": "d"
		  }
		}
		""";

	@Test
	public void testDeserialize() throws Exception {

		DIDDocument didDocumentV1_0 = jsonMapper.readValue(EXAMPLE_DID_DOCUMENT_V1_0, DIDDocument.class);
		assertNotNull(didDocumentV1_0);
        assertInstanceOf(DIDDocumentV1_0.class, didDocumentV1_0);
		didDocumentV1_0 = jsonMapper.convertValue(jsonMapper.convertValue(didDocumentV1_0, Map.class), DIDDocument.class);
		assertNotNull(didDocumentV1_0);
		assertInstanceOf(DIDDocumentV1_0.class, didDocumentV1_0);

		DIDDocument didDocumentV1_1 = jsonMapper.readValue(EXAMPLE_DID_DOCUMENT_V1_1, DIDDocument.class);
		assertNotNull(didDocumentV1_1);
		assertInstanceOf(DIDDocumentV1_1.class, didDocumentV1_1);
		didDocumentV1_1 = jsonMapper.convertValue(jsonMapper.convertValue(didDocumentV1_1, Map.class), DIDDocument.class);
		assertNotNull(didDocumentV1_1);
		assertInstanceOf(DIDDocumentV1_1.class, didDocumentV1_1);

		ResolveResult resolveResultV1_0 = jsonMapper.readValue(EXAMPLE_RESOLVE_RESULT_V1_0, ResolveResult.class);
		assertNotNull(resolveResultV1_0);
		assertInstanceOf(DIDDocumentV1_0.class, resolveResultV1_0.didDocument());
		resolveResultV1_0 = jsonMapper.convertValue(jsonMapper.convertValue(resolveResultV1_0, Map.class), ResolveResult.class);
		assertNotNull(resolveResultV1_0);
		assertInstanceOf(DIDDocumentV1_0.class, resolveResultV1_0.didDocument());

		ResolveResult resolveResultV1_1 = jsonMapper.readValue(EXAMPLE_RESOLVE_RESULT_V1_1, ResolveResult.class);
		assertNotNull(resolveResultV1_1);
		assertInstanceOf(DIDDocumentV1_1.class, resolveResultV1_1.didDocument());
		resolveResultV1_1 = jsonMapper.convertValue(jsonMapper.convertValue(resolveResultV1_1, Map.class), ResolveResult.class);
		assertNotNull(resolveResultV1_1);
		assertInstanceOf(DIDDocumentV1_1.class, resolveResultV1_1.didDocument());
	}
}
