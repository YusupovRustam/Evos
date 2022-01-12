package com.company.utils;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyButtonUtil {
    public static KeyboardButton keyButton(String text){
        KeyboardButton keyboardButton=new KeyboardButton();
        keyboardButton.setText(text);
        return keyboardButton;
    }
    public static KeyboardButton keyButton(String text,String emoji){
        String emojiname= EmojiParser.parseToUnicode(emoji+" "+text);
        KeyboardButton keyboardButton=new KeyboardButton();
        keyboardButton.setText(emojiname);
        return keyboardButton;
    }

    public static KeyboardRow keyRow(KeyboardButton... keyboardButton){
        KeyboardRow keyboardRow=new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(keyboardButton));
        return keyboardRow;

    }
    public static List<KeyboardRow> keyboardRowList(KeyboardRow... row){
        return new LinkedList<>(Arrays.asList(row));
    }

    public static ReplyKeyboardMarkup replyKeyboardMarkup(List<KeyboardRow> keyboardRowList){
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);//buttonni razmerini to'g'irlaydi
        replyKeyboardMarkup.setSelective(true);// bottinga strelka qoshadi;
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

}
