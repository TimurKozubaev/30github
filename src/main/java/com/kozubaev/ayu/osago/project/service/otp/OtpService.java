package com.kozubaev.ayu.osago.project.service.otp;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class OtpService {

    private static final Map<String, String> otpStorage = new HashMap<>(); // Хранение OTP для каждого номера

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Генерация 6-значного числа
        return String.valueOf(otp);
    }
    /**
     * Генерирует OTP и отправляет его на номер телефона.
     *
     * @param phoneNumber Номер телефона
     */
    public void sendOtp(String phoneNumber) {
        String otpCode = generateOtp();
        otpStorage.put(phoneNumber, otpCode); // Сохраняем OTP для номера

        // Отправляем OTP через Telegram (или другой сервис)
        String chatId = getChatIdByPhoneNumber(phoneNumber); // Получаем chat_id по номеру телефона
        TelegramOTP.sendOTP(chatId, otpCode);

        System.out.println("OTP отправлен на номер: " + phoneNumber);
    }

    /**
     * Проверяет введенный OTP.
     *
     * @param phoneNumber Номер телефона
     * @param userOtp     OTP, введенный пользователем
     * @return true, если OTP верный, иначе false
     */
    public boolean verifyOtp(String phoneNumber, String userOtp) {
        String storedOtp = otpStorage.get(phoneNumber);
        return storedOtp != null && storedOtp.equals(userOtp);
    }

    /**
     * Получает chat_id по номеру телефона (заглушка).
     *
     * @param phoneNumber Номер телефона
     * @return chat_id
     */
    private String getChatIdByPhoneNumber(String phoneNumber) {
        // Здесь должна быть логика получения chat_id по номеру телефона
        return "user_chat_id"; // Заглушка
    }
}