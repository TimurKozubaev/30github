package com.kozubaev.ayu.osago.project.service.otp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelegramOTP {

    // Токен вашего бота
    private static final String BOT_TOKEN = "7070472707:AAGBIL2qPcc2ph6Tldq1wZ34l0lRfGsblx4";

    /**
     * Отправляет OTP через Telegram.
     *
     * @param chatId  ID чата пользователя в Telegram
     * @param otpCode OTP код для отправки
     */
    public static void sendOTP(String chatId, String otpCode) {
        try {
            // Формируем сообщение
            String message = "Ваш OTP код: " + otpCode;

            // Кодируем сообщение для URL
            String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");

            // Формируем URL для запроса
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?chat_id=" + chatId + "&text=" + encodedMessage;

            // Создаем HTTP-соединение
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Читаем ответ от Telegram API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            // Выводим результат
            System.out.println("OTP sent via Telegram: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Пример использования
        String chatId = "user_chat_id"; // Замените на реальный chat_id пользователя
        String otpCode = "123456";     // Пример OTP кода
        sendOTP(chatId, otpCode);
    }
}