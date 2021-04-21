import org.json.simple.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class APIFunction {
    URL url;
    Map<Object, Object> data;
    JSONObject json;
    String inline = "";

    private HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    //This method is depreciated
    /*void post() throws IOException, InterruptedException {
        HttpRequest r = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(getUrl().toString()))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> res = client.send(r, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());

    }*/

    void get() throws IOException{
        HttpURLConnection connection = ((HttpURLConnection) getUrl().openConnection());
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200){
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            System.out.println("\nConnected");
            Scanner s = new Scanner(getUrl().openStream());
            System.out.println("\nGetting data...");
            while (s.hasNext()){
                setInline(inline+=s.nextLine());
            }
            s.close();
        }
    }

    void put() throws IOException, InterruptedException {
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(getUrl().toString()))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        HttpResponse<String> res = client.send(r, HttpResponse.BodyHandlers.ofString());
    }

    void postJSON() throws IOException, InterruptedException {
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(getUrl().toString()))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        HttpResponse<String> res = client.send(r, HttpResponse.BodyHandlers.ofString());
    }

    void delete() throws IOException, InterruptedException {
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(getUrl().toString()))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> res = client.send(r, HttpResponse.BodyHandlers.ofString());
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getInline() {
        return inline;
    }

    public void setInline(String inline) {
        this.inline = inline;
    }
}
