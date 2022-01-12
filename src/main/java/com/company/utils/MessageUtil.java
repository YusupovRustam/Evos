package com.company.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageUtil {

    public static SendMessage notFoundMessage(Long chatId){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText("Bu xato kalla");
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }
}
