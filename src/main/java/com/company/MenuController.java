package com.company;

import com.company.utils.KeyButtonUtil;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class MenuController {

    public ReplyKeyboardMarkup mealMenu(){
        KeyboardButton button1= KeyButtonUtil.keyButton("Lavash");
        KeyboardButton button2=KeyButtonUtil.keyButton("Donar");
        KeyboardButton button3=KeyButtonUtil.keyButton("Tuxum barak");
        KeyboardButton button4=KeyButtonUtil.keyButton("Gumma");
        KeyboardButton button5=KeyButtonUtil.keyButton("Salqin pirashki");
        KeyboardButton button6=KeyButtonUtil.keyButton("Issiq cola");
        KeyboardButton button7=KeyButtonUtil.keyButton("Back");
        KeyboardRow keyboardRow1=KeyButtonUtil.keyRow(button1,button2);
        KeyboardRow keyboardRow2=KeyButtonUtil.keyRow(button3,button4);
        KeyboardRow keyboardRow3=KeyButtonUtil.keyRow(button5,button6);
        KeyboardRow keyboardRow4=KeyButtonUtil.keyRow(button7);

        List<KeyboardRow> keyboardRowList=KeyButtonUtil.keyboardRowList(keyboardRow1,keyboardRow2,keyboardRow3,keyboardRow4);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }
}
