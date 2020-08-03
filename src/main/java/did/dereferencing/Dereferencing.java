package did.dereferencing;

import did.DIDDocument;
import did.DIDURL;
import did.PublicKey;
import did.Service;
import did.parser.ParserException;

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
                try { serviceDidUrl = DIDURL.fromString(service.getId()); } catch (ParserException ex) { serviceDidUrl = null; }
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

    public Map<Integer, PublicKey> selectKeys(DIDDocument didDocument, String selectKeyName, String selectKeyType) {

        int i = -1;
        Map<Integer, PublicKey> selectedKeys = new HashMap<Integer, PublicKey> ();
        if (didDocument.getPublicKeys() == null) return selectedKeys;

        for (PublicKey publicKey : didDocument.getPublicKeys()) {

            i++;

            if (selectKeyName != null) {

                DIDURL publicKeyDidUrl;
                try { publicKeyDidUrl = DIDURL.fromString(publicKey.getId()); } catch (ParserException ex) { publicKeyDidUrl = null; }
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
