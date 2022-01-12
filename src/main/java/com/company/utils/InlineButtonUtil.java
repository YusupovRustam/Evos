package com.company.utils;

import com.vdurmont.emoji.EmojiParser;
import javassist.compiler.ast.ASTList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String name,String callBackData){
        InlineKeyboardButton button=new InlineKeyboardButton(name);
        button.setCallbackData(callBackData);
        return button;
    }
    public static InlineKeyboardButton buttonNotData(String name){
        return new InlineKeyboardButton(name);
    }
    public static InlineKeyboardButton button(String name, String callBackData,String emoji){
        String emojiname= EmojiParser.parseToUnicode(emoji+" "+name);
        InlineKeyboardButton button=new InlineKeyboardButton(emojiname);
        button.setCallbackData(callBackData);
        return button;
    }
    public static List<InlineKeyboardButton>row(InlineKeyboardButton ... inlineKeyboardButton){
        List<InlineKeyboardButton>row=new LinkedList<>(Arrays.asList(inlineKeyboardButton));
        return row;
    }
    public static  List<List<InlineKeyboardButton>>rowList(List<InlineKeyboardButton>... rows){
        return new LinkedList<>(Arrays.asList(rows));
    }
    public static InlineKeyboardMarkup keyBoard(List<List<InlineKeyboardButton>> rowList){
        return new InlineKeyboardMarkup(rowList);
    }
}
