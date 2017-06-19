import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;


public class C20nDirectRouting {
    public static void main(String args[]) throws IOException, NoSuchAlgorithmException {
        HttpClientBuilder builder = HttpClientBuilder.create();

        SSLContext context = SSLContext.getDefault();

        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        builder.setSSLSocketFactory(sslConnectionFactory);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionFactory)
                .build();

        HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);

        builder.setConnectionManager(ccm);

        CloseableHttpClient httpClient = builder.build();

        HttpRequest httpRequest = new HttpGet("/test/service-discovery?app_name=zookeeper");

        HttpHost httpHost = new HttpHost("kjhpncoet5.execute-api.us-east-1.amazonaws.com", 443, "https");

        HttpResponse response = httpClient.execute(httpHost, httpRequest, (HttpContext) null);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println("Response follows:");
        System.out.println(result.toString());

    }
}
