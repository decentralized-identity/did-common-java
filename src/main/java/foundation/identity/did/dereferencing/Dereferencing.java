package foundation.identity.did.dereferencing;

import foundation.identity.did.DIDDocument;
import foundation.identity.did.DIDURL;
import foundation.identity.did.Service;
import foundation.identity.did.VerificationMethod;
import foundation.identity.did.parser.ParserException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dereferencing {

    private Dereferencing() {

    }

    public static Map<Integer, Service> selectServices(DIDDocument didDocument, String selectServiceName, String selectServiceType) {

        int i = -1;
        Map<Integer, Service> selectedServices = new HashMap<Integer, Service>();
        if (didDocument.getServices() == null) return selectedServices;

        for (Service service : didDocument.getServices()) {

            i++;

            if (selectServiceName != null) {

                DIDURL serviceDidUrl;
                try { serviceDidUrl = DIDURL.fromUri(service.getId()); } catch (ParserException ex) { serviceDidUrl = null; }
                String serviceName = serviceDidUrl == null ? null : serviceDidUrl.getFragment();

                if (serviceName == null) continue;
                if (! serviceName.equals(selectServiceName)) continue;
            }

            if (selectServiceType != null) {

                if (service.getTypes() == null) continue;
                if (! Arrays.asList(service.getTypes()).contains(selectServiceType)) continue;
            }

            selectedServices.put(Integer.valueOf(i), service);
        }

        return selectedServices;
    }

    public Map<Integer, VerificationMethod> selectKeys(DIDDocument didDocument, String selectKeyName, String selectKeyType) {

        int i = -1;
        Map<Integer, VerificationMethod> selectedKeys = new HashMap<Integer, VerificationMethod> ();
        if (didDocument.getVerificationMethods() == null) return selectedKeys;

        for (VerificationMethod publicKey : didDocument.getVerificationMethods()) {

            i++;

            if (selectKeyName != null) {

                DIDURL publicKeyDidUrl;
                try { publicKeyDidUrl = DIDURL.fromUri(publicKey.getId()); } catch (ParserException ex) { publicKeyDidUrl = null; }
                String publicKeyName = publicKeyDidUrl == null ? null : publicKeyDidUrl.getFragment();

                if (publicKeyName == null) continue;
                if (! publicKeyName.equals(selectKeyName)) continue;
            }

            if (selectKeyType != null) {

                if (publicKey.getTypes() == null) continue;
                if (! Arrays.asList(publicKey.getTypes()).contains(selectKeyType)) continue;
            }

            selectedKeys.put(Integer.valueOf(i), publicKey);
        }

        return selectedKeys;
    }
}
