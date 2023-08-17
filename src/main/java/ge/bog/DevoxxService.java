package ge.bog;

import com.google.gson.Gson;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class DevoxxService {
    private final Gson gson;

    public DevoxxService() {
        gson = new Gson();
    }

    public DevoxxResponse sendRequest() throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        Logger.info("Sending request to: https://reg.devoxx.be/api/v2/public/event/2023/ticket-categories");
        URL url = new URL("https://reg.devoxx.be/api/v2/public/event/2023/ticket-categories");
        URLConnection conn = url.openConnection();
        ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory());
        conn.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();
        String res = sb.toString();
//        Logger.info("Receiving result: " + res);
        return gson.fromJson(res, DevoxxResponse.class);
    }

    private static SSLSocketFactory sslSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        return sc.getSocketFactory();
    }
}
