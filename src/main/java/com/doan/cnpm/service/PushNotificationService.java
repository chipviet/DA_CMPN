package com.doan.cnpm.service;

import com.doan.cnpm.domain.Device;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;


@Service
public class PushNotificationService {

    public static final String API_KEY = "OGE4Yzg1MDEtYmUzNy00YjA0LWI0ZGMtZTA3Y2FkY2QxMGYw";
    public static final String APP_ID = "32b75367-f900-49b7-96d1-e942a4b4d542";
    /**
     * Default PushNotificationService constructor
     */
    public PushNotificationService() {}

    public static void sendMessageToAllUsers(String message) {
        try {

            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + API_KEY); //REST API
            con.setRequestMethod("POST");

            String strJsonBody = "{" +
                    "\"app_id\": \"" + APP_ID + "\"," +
                    "\"included_segments\": [\"All\"]," +
                    "\"data\": {\"foo\": \"bar\"}," +
                    "\"contents\": {\"en\": \"" + message + "\"}" +
                    "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK &&
                httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    public static void sendMessageToUser(String message, List<Device> devices) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + API_KEY);
            con.setRequestMethod("POST");


            List<String> deviceUuids = devices.stream()
                    .map(Device::getDevice_uuid)
                    .collect(Collectors.toList());

            // String[] array = deviceUuids.toArray(new String[0]);
            System.out.println("REST request to update device cc " + converToJsonStringify(deviceUuids));

            String strJsonBody = "{" +
                    "\"app_id\": \"" + APP_ID + "\"," +
                    "\"include_player_ids\":" + converToJsonStringify(deviceUuids) + "," +
                    "\"data\": {\"foo\": \"bar\"}," +
                    "\"contents\": {\"en\": \"" + message + "\"}" +
                    "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();

            jsonResponse = mountResponseRequest(con, httpResponse);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String converToJsonStringify(List<String> deviceUuids) {
        String result = "[";
        for (String s : deviceUuids) {
            result = result + "\"" + s + "\",";
        }
        result = result.substring(0, result.length() - 1);
        result = result + "]";
        return result;
    }
}

