import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    public static void main(String[] args) {
        try {
            // ---- YOUR API KEY HERE ----
            String apiKey = "9bae4cd83cd5496dc589163621222765";

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter city name: ");
            String city = br.readLine().trim();

            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" 
                                + city + "&units=metric&appid=" + apiKey;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();

            // Extract simple info (manual string parsing)
            String temp = extract(json, "\"temp\":", ",");
            String humidity = extract(json, "\"humidity\":", "}");
            String weatherDesc = extract(json, "\"description\":\"", "\"");

            System.out.println("\n--- Weather Report ---");
            System.out.println("City: " + city);
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Condition: " + weatherDesc);

        } catch (Exception e) {
            System.out.println("Error fetching weather data! Check city name or internet connection.");
        }
    }

    // Helper method to extract values from JSON text
    private static String extract(String json, String startKey, String endKey) {
        try {
            int start = json.indexOf(startKey) + startKey.length();
            int end = json.indexOf(endKey, start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "N/A";
        }
    }
}
