package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public final class SIteFactory {
    public static void publish(List<?> positions){
        new Thread(() -> {
            try {
               /* // Monta o JSON final
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("positions", new ArrayList<>(positions).toString());

                // Envia para o servidor
                URL url = new URL("http://192.168.43.153:5000/receive");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonObject.toJSONString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                RobotLog.d("HTTP Response code: " + code);

                conn.disconnect();*/

            } catch (Exception e) {
                RobotLog.ee("RobotContainer", e, "Erro enviando JSON");
            }
        }).start();
    }
    }

