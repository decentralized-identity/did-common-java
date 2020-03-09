package did;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DIDDocumentTest {

    @DisplayName("DIDDocument.build() with context, documentId and parameters as JsonLd")
    @Test
    void given_areValidJsonLdParametersParameters_when_buildCalled_then_correctDIDDocumentReturned() {
        Map<String, Object> publicKeyJsonLdObject = createAnySimpleJsonLdObject("id", "anyKey");
        List<PublicKey> publicKeys = createPublicKeys(publicKeyJsonLdObject);

        Map<String, Object> authenticationJsonLdObject = createAnySimpleJsonLdObject("id", "anyId");
        List<Authentication> authentications = createAuthentications(authenticationJsonLdObject);

        Map<String, Object> serviceJsonLdObject = createAnySimpleJsonLdObject("type", "anyType");
        List<Service> services = createServices(serviceJsonLdObject);

        String documentId = "documentId";
        Object context = "anyContext";
        DIDDocument didDocument = DIDDocument.build(context, documentId, publicKeys, authentications, services);

        assertEquals(context, didDocument.getContexts().get(0));
        assertEquals(documentId, didDocument.getId());
        assertEquals(publicKeyJsonLdObject, didDocument.getPublicKeys().get(0).jsonLdObject);
        assertEquals(authenticationJsonLdObject, didDocument.getAuthentications().get(0).jsonLdObject);
        assertEquals(serviceJsonLdObject, didDocument.getServices().get(0).jsonLdObject);
    }

    private List<Service> createServices(Map<String, Object> serviceJsonLdObject) {
        Service service = Service.build(serviceJsonLdObject);
        List<Service> services = new ArrayList<>();
        services.add(service);
        return services;
    }

    private List<Authentication> createAuthentications(Map<String, Object> authenticationJsonLdObject) {
        Authentication authentication = Authentication.build(authenticationJsonLdObject);
        List<Authentication> authentications = new ArrayList<>();
        authentications.add(authentication);
        return authentications;
    }

    private List<PublicKey> createPublicKeys(Map<String, Object> publicKeyJsonLdObject) {
        PublicKey publicKey = PublicKey.build(publicKeyJsonLdObject);
        List<PublicKey> publicKeys = new ArrayList<>();
        publicKeys.add(publicKey);
        return publicKeys;
    }

    private Map<String, Object> createAnySimpleJsonLdObject(String id, String anyId) {
        Map<String, Object> publicKeyJsonLdObject = new LinkedHashMap<>();
        publicKeyJsonLdObject.put(id, anyId);
        return publicKeyJsonLdObject;
    }

    @DisplayName("Raw DIDDocument returns null for JsonLdProperties")
    @Test
    void given_rawDIDDocument_when_gettersAreCalled_then_nullReturned() {
        DIDDocument didDocument = DIDDocument.build();
        assertNull(didDocument.getContexts());
        assertNull(didDocument.getPublicKeys());
        assertNull(didDocument.getId());
        assertNull(didDocument.getAuthentications());
        assertNull(didDocument.getServices());
    }

    @DisplayName("Context URI allowed")
    @Test
    void given_URIContext_when_getContextsCalled_then_contextIsReturned() throws URISyntaxException {

        URI context = new URI("www.danubetech.com");
        final String documentId = "documentId";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
        assertEquals(context, didDocument.getContexts().get(0));
    }

    @DisplayName("Context List<?> allowed")
    @Test
    void given_AnyListContext_when_getContextsCalled_then_contextIsReturned() {
        Object context = new ArrayList<>();
        ((ArrayList<Integer>) context).add(123);
        ((ArrayList<String>) context).add("Danubetech");
        final String documentId = "documentId";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
        assertEquals(didDocument.getContexts().get(0), ((ArrayList<String>) context).get(0));
        assertEquals(didDocument.getContexts().get(1), ((ArrayList<String>) context).get(1));
    }

    @DisplayName("Context int return Null")
    @Test
    void given_intContext_when_getContextsCalled_then_returnNull() {
        int context = 111111 ;
        final String documentId = "documentId";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
        assertNull(didDocument.getContexts());
    }

    @DisplayName("Null DocumentId and verify the Exception")
    @Test
    void given_Null_documentId_and_verify_exception(){
        DIDDocument didDocument = DIDDocument.build(null,null,null,null,null);
        assertNull(didDocument.getId());
    }

    @DisplayName("Service type which is not a LinkedHashMap and verify")
    @Test
    void given_hashmap_for_services_and_return_emptyList(){
        String documentId = "documentId";
        Map<String, Object> serviceJsonLdObject = new HashMap<>();
        serviceJsonLdObject.put("id", "did:method:123456#openId");
        serviceJsonLdObject.put("type", "serviceType");
        List<Service> services = createServices(serviceJsonLdObject);
        DIDDocument didDocument = DIDDocument.build(documentId,null,null,services);
        assertEquals(new ArrayList<>() , didDocument.getServices());

    }

    @DisplayName("Public Key type which is not a LinkedHashMap and verify")
    @Test
    void given_hashmap_for_public_key_and_return_emptyList(){
        String documentId = "documentId";
        Map<String, Object> publicKeyJsonLdObject = new HashMap<>();
        publicKeyJsonLdObject.put("id", "anyKey");
        List<PublicKey> publicKeys = createPublicKeys(publicKeyJsonLdObject);
        DIDDocument didDocument = DIDDocument.build(documentId,publicKeys,null,null);
        assertEquals(new ArrayList<>(), didDocument.getPublicKeys());
    }

    @DisplayName("Authentication type which is not a LinkedHashMap and verify")
    @Test
    void given_hashmap_for_authentication_key_and_return_emptyList(){
        String documentId = "documentId";
        Map<String, Object> authenticationJsonLdObject = new HashMap<>();
        authenticationJsonLdObject.put("id", "anyKey");
        List<Authentication> authenticationJsonLD = createAuthentications(authenticationJsonLdObject);
        DIDDocument didDocument = DIDDocument.build(documentId,null,authenticationJsonLD,null);
        assertEquals(new ArrayList<>(), didDocument.getAuthentications());
    }

    private DIDDocument createDIDDocumentWithValidService() {
        Map<String, Object> serviceJsonLdObject = new LinkedHashMap<>();
        serviceJsonLdObject.put("id", "did:method:123456#openId");
        serviceJsonLdObject.put("type", "serviceType");
        List<Service> services = createServices(serviceJsonLdObject);
        return DIDDocument.build("documentId", null, null, services);
    }

    // Task DIDDocumentTest Parser method
    @DisplayName("selectServices both params empty String")
    @Test
    void given_validServiceEntry_when_selectServicesWithBothParamsEmptyString_then_emptyHashMapReturned(){
        DIDDocument didDocument = createDIDDocumentWithValidService();

        Map<Integer, Service> selectedServices = didDocument.selectServices("", "");

        Map<Integer, Object> expectedEmptyMap = new HashMap<>();
        assertEquals(expectedEmptyMap, selectedServices);
    }
}