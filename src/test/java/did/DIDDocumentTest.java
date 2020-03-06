package did;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void given_URIContext_when_getContextsCalled_then_contextIsReturned() {
        URI context = null;
        try {
             context = new URI("www.danubetech.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final String documentId = "documentId";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
        assertEquals(context,didDocument.getContexts());
    }

    @DisplayName("Context List<?> allowed")
    @Test
    void given_AnyListContext_when_getContextsCalled_then_contextIsReturned() {
        Object context = new ArrayList<>();
        ((ArrayList<Integer>) context).add(123);
        final String documentId = "documentId";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
    }

    @DisplayName("Context int not allowed")
    @Test
    void given_intContext_when_getContextsCalled_then_NullPointerExceptionThrown() {
        int context = 111111 ;
        final String documentId = "documentId";
        String exception_thrown = "java.lang.RuntimeException";
        DIDDocument didDocument = DIDDocument.build(context, documentId, null,null,null);
        RuntimeException exception =assertThrows( RuntimeException.class ,
                ()-> didDocument.toString()
        );
        assertTrue(exception.getMessage().contains(exception_thrown));
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

    @DisplayName("Map type which is not a LinkedHashMap and verify")
    @Test
    void give_hashmap_and_return_null(){
        String documentId = "documentId";
        Map<String, Object> serviceJsonLdObject = new HashMap<>();
        serviceJsonLdObject.put("id", "did:method:123456#openId");
        serviceJsonLdObject.put("type", "serviceType");
        List<Service> services = createServices(serviceJsonLdObject);
        DIDDocument didDocument = DIDDocument.build(documentId,null,null,services);
        didDocument.getServices();

    }

    @DisplayName("Map type which is not a LinkedHashMap and verify")
    @Test
    void give_hashmap_for_public_key_and_return_null(){
        String documentId = "documentId";
        Map<String, Object> serviceJsonLdObject = new HashMap<>();
        serviceJsonLdObject.put("id", "did:method:123456#openId");
        serviceJsonLdObject.put("type", "serviceType");
        List<Service> services = createServices(serviceJsonLdObject);
        DIDDocument didDocument = DIDDocument.build(documentId,null,null,services);
        didDocument.getServices();

    }

    private DIDDocument createDIDDocumentWithValidService() {
        Map<String, Object> serviceJsonLdObject = new LinkedHashMap<>();
        serviceJsonLdObject.put("id", "did:method:123456#openId");
        serviceJsonLdObject.put("type", "serviceType");
        List<Service> services = createServices(serviceJsonLdObject);
        return DIDDocument.build("documentId", null, null, services);
    }
}