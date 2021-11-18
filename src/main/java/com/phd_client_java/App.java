package com.phd_client_java;

import static spark.Spark.*;

public final class App {
    public static void main(String[] args) {
        PHD phd = new PHD();

        get("/get_tag/:tag", (req, res) -> {
            System.out.println("API request HELLO");
            phd.connect(); // Открываем соединение с PHD

            float[] data = null;

            try {
                data = phd.getDataTag(req.params(":tag")); // "1AC167_SAP.N.OUT"
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
            }

            phd.disconnect(); // Закрывам соединение с PHD

            if (data != null) {
                return data[0];
            } else {
                System.out.println("WARNING: NO DATA");
                return "NO DATA";
            }
        });
    }
}
