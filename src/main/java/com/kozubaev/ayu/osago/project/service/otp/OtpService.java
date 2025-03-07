package com.kozubaev.ayu.osago.project.service.otp;

import com.kozubaev.ayu.osago.project.model.UserChat;
import com.kozubaev.ayu.osago.project.repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class OtpService {

    @Autowired
    private UserChatRepository userChatRepository;

    private final Map<String, String> otpStorage = new HashMap<>(); // Хранилище OTP
    private static final String BOT_TOKEN = "7070472707:AAGBIL2qPcc2ph6Tldq1wZ34l0lRfGsblx4"; // Замените на ваш токен


    // Генерация OTP
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Генерация 6-значного кода
        return String.valueOf(otp);
    }

    // Отправка OTP через Telegram
    public void sendOTP(String phoneNumber, String otpCode) {
        String chatId = getChatIdByPhoneNumber(phoneNumber); // Получаем chat_id по номеру телефона
        if (chatId != null) {
            sendTelegramMessage(chatId, "Ваш OTP код: " + otpCode);
        }
    }

    // Сохранение OTP в хранилище
    public void storeOTP(String phoneNumber, String otpCode) {
        otpStorage.put(phoneNumber, otpCode);
    }

    // Проверка OTP
    public boolean verifyOTP(String phoneNumber, String userEnteredOTP) {
        String storedOTP = otpStorage.get(phoneNumber);
        return userEnteredOTP.equals(storedOTP);
    }

    // Получение chat_id по номеру телефона
    private String getChatIdByPhoneNumber(String phoneNumber) {
        UserChat userChat = userChatRepository.findByPhoneNumber(phoneNumber);
        if (userChat != null) {
            return userChat.getChatId();
        }
        return null; // Если chat_id не найден
    }

    // Сохранение chat_id в базе данных
    public void saveChatId(String phoneNumber, String chatId) {
        UserChat userChat = new UserChat();
        userChat.setPhoneNumber(phoneNumber);
        userChat.setChatId(chatId);
        userChatRepository.save(userChat);
    }

    // Отправка сообщения через Telegram API
    private void sendTelegramMessage(String chatId, String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?chat_id=" + chatId + "&text=" + encodedMessage;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("OTP sent via Telegram: " + message);
            } else {
                System.err.println("Ошибка при отправке OTP. Код ответа: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}