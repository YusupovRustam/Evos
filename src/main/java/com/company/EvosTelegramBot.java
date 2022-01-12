package com.company;

import com.company.repository.FileRepository;
import com.company.utils.InlineButtonUtil;
import com.company.utils.KeyButtonUtil;
import com.company.utils.MessageUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EvosTelegramBot extends TelegramLongPollingBot {
 LinkedList<Update>updateList=new LinkedList<>();
    private File file=new File("LastText.txt");
    MenuController menuController=new MenuController();

    @Override
    public void onUpdateReceived(Update update) {
        int count=0;
        if (update.hasMessage()) {
            EditMessageReplyMarkup replyMarkup=new EditMessageReplyMarkup();
            SendMessage sendMessage = null;
            Message message = update.getMessage();
            replyMarkup.setChatId(message.getChatId().toString());
            replyMarkup.setMessageId(message.getMessageId());
            if (message.hasContact()) {
                sendMessage = sendMessage("choose NO/YES", message.getChatId(), confirmMenu());
                sendMsg(sendMessage);
                return;
            }
            if (message.hasLocation()) {
                Location location = message.getLocation();
                sendMessage = sendMessage("check your adress\n" + location.toString() + "\nchoose NO/YES",
                        message.getChatId(), confirmMenu());
                sendMsg(sendMessage);
                return;
            }
            String text = message.getText();
            switch (text) {
                case "/start":
                    sendMessage = sendMessage("Welcom my bot", message.getChatId(), keyBoardMenu());
                    sendMsg(sendMessage);
                    return;
                case "Menu":
                    FileRepository.writeFile(file, text);
                    sendMessage = sendMessage("Send Location or your address", message.getChatId(), LocationMenu());
                    sendMsg(sendMessage);
                    return;
                case "YES":
              sendMessage = sendMessageInline("How many", message.getChatId(), HowManyMenu());
                    sendMsg(sendMessage);
                    return;
                case "yes":
                   sendMessage = sendMessage("Meal menu", message.getChatId(), menuController.mealMenu());
                    sendMsg(sendMessage);
                    return;
                case "Lavash":
                    FileRepository.writeFile(file, text);
                    try {
                        execute(sendImageFromUrl(continueMenu(), message.getChatId().toString(), "src/main/resources/pexels-anastasia-belousova-10267321.jpg"));
                    } catch (IOException | TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
                case "Donar":
                    FileRepository.writeFile(file, text);
                    try {
                        execute(sendImageFromUrl(continueMenu(), message.getChatId().toString(),
                                "src/main/resources/pexels-julia-filirovska-7140333.jpg"));
                    } catch (IOException | TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
                case"plus":
                    count++;
                    replyMarkup.setInlineMessageId(String.valueOf(count));
                    replyMarkup.setReplyMarkup(HowManyMenu());
                    try {
                        execute(replyMarkup);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
            }

            if ((text.equals("NO") || text.equals("Back")) && FileRepository.getLast(file).equals("Menu")) {
                sendMessage = sendMessage("*Main Menu*", message.getChatId(), keyBoardMenu());
                sendMsg(sendMessage);
                return;
            }
            if (text.equals("NO") && (FileRepository.getLast(file).equals("Lavash") ||
                    FileRepository.getLast(file).equals("Donar") || FileRepository.getLast(file).equals("Tuxum barak")
                       || FileRepository.getLast(file).equals("Gumma") || FileRepository.getLast(file).equals("Salqin pirashki") )     ){
                sendMessage = sendMessage("*Main Menu*", message.getChatId(), keyBoardMenu());
                sendMsg(sendMessage);
                return;
            }
            if (text.equals("Back") && FileRepository.getLast(file).equals("Lavash")) {
                sendMessage = sendMessage("All meals", message.getChatId(), menuController.mealMenu());
                sendMsg(sendMessage);
                return;
            }
                sendMsg(MessageUtil.notFoundMessage(message.getChatId()));
                return;

        }else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String id = callbackQuery.getFrom().getId().toString();

            if (data.equals("plus")) {
                count++;
                EditMessageText editMessageText=getEditMessageText("how many",callbackQuery.getMessage().getMessageId(),
                        id,count);
                editMsg(editMessageText);
                return;
            } else if (data.equals("minus")) {
                if (count == 0) {
                    return;
                }
                count--;
                EditMessageText editMessageText=getEditMessageText("how many",callbackQuery.getMessage().getMessageId(),
                        id,count);
                editMsg(editMessageText);
                return;
            } else if(data.equals("select")){
                return;
            }
        }



    }
    public void editMsg(EditMessageText editMessageText){
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public EditMessageText getEditMessageText(String text,Integer messageId,String chatId,int count){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(text);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(HowManyMenu(count));
        editMessageText.setParseMode("Markdown");
        return editMessageText;
    }
    private InlineKeyboardMarkup HowManyMenu(int i) {
        InlineKeyboardButton button1 = InlineButtonUtil.button("➕", "plus");
        InlineKeyboardButton button2 = InlineButtonUtil.button(String.valueOf(i),"select");
        InlineKeyboardButton button3 = InlineButtonUtil.button("➖", "minus");
        List<List<InlineKeyboardButton>> lists = InlineButtonUtil.rowList(InlineButtonUtil.row(button1, button2, button3));
        return InlineButtonUtil.keyBoard(lists);
    }


    private InlineKeyboardMarkup HowManyMenu() {
        InlineKeyboardButton button1= InlineButtonUtil.button("➕","plus");
        InlineKeyboardButton button2= InlineButtonUtil.buttonNotData("0");
        InlineKeyboardButton button3= InlineButtonUtil.button("➖","minus");
        List<List<InlineKeyboardButton>> lists = InlineButtonUtil.rowList(InlineButtonUtil.row(button1,button2,button3));
        return InlineButtonUtil.keyBoard(lists);
    }

    public SendPhoto sendImageFromUrl(ReplyKeyboardMarkup replyKeyboardMarkup, String chatId,String pathPhoto) throws IOException {
        SendPhoto sendPhoto=new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setParseMode("Markdown");
        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
        InputFile inputFile1=new InputFile(new File(pathPhoto));
        sendPhoto.setPhoto(inputFile1);
        sendPhoto.setCaption("15000 som");

        return sendPhoto;
    }
    public ReplyKeyboardMarkup keyBoardMenu(){
        KeyboardButton button1=KeyButtonUtil.keyButton("Menu");
        KeyboardButton button2=KeyButtonUtil.keyButton("My booking");
        KeyboardButton button3=KeyButtonUtil.keyButton("Share feedback");
        KeyboardButton button4=KeyButtonUtil.keyButton("Setting");
        KeyboardRow keyboardRow1=KeyButtonUtil.keyRow(button1);
        KeyboardRow keyboardRow2=KeyButtonUtil.keyRow(button2);
        KeyboardRow keyboardRow3=KeyButtonUtil.keyRow(button3,button4);
        List<KeyboardRow>keyboardRowList=KeyButtonUtil.keyboardRowList(keyboardRow1,keyboardRow2,keyboardRow3);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }

    public ReplyKeyboardMarkup LocationMenu(){
        KeyboardButton button1=KeyButtonUtil.keyButton("Send lacation");
        button1.setRequestLocation(true);
        KeyboardButton button2=KeyButtonUtil.keyButton("Send phone");
        button2.setRequestContact(true);
        KeyboardButton button3=KeyButtonUtil.keyButton("Back");
        KeyboardRow keyboardRow1=KeyButtonUtil.keyRow(button1,button2);
        KeyboardRow keyboardRow2=KeyButtonUtil.keyRow(button3);
        List<KeyboardRow>keyboardRowList=KeyButtonUtil.keyboardRowList(keyboardRow1,keyboardRow2);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }
    public ReplyKeyboardMarkup continueMenu(){
        KeyboardButton button1=KeyButtonUtil.keyButton("NO");
        KeyboardButton button2=KeyButtonUtil.keyButton("YES");
        KeyboardButton button3=KeyButtonUtil.keyButton("Back");
        KeyboardRow keyboardRow1=KeyButtonUtil.keyRow(button1,button2);
        KeyboardRow keyboardRow2=KeyButtonUtil.keyRow(button3);
        List<KeyboardRow>keyboardRowList=KeyButtonUtil.keyboardRowList(keyboardRow1,keyboardRow2);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }
    public ReplyKeyboardMarkup confirmMenu(){
        KeyboardButton button1=KeyButtonUtil.keyButton("NO");
        KeyboardButton button2=KeyButtonUtil.keyButton("yes");
        KeyboardButton button3=KeyButtonUtil.keyButton("Back");
        KeyboardRow keyboardRow1=KeyButtonUtil.keyRow(button1,button2);
        KeyboardRow keyboardRow2=KeyButtonUtil.keyRow(button3);
        List<KeyboardRow>keyboardRowList=KeyButtonUtil.keyboardRowList(keyboardRow1,keyboardRow2);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }
    public void sendMsg(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public SendMessage sendMessageInline(String text,Long chatId,InlineKeyboardMarkup inlineKeyboardMarkup){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }
    public SendMessage sendMessage(String text,Long chatId,ReplyKeyboardMarkup replyKeyboardMarkup){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }


    @Override
    public String getBotUsername() {
        return "MyTest_One_bot";
    }

    @Override
    public String getBotToken() {
        return "5047973185:AAH9ZPhIB1a1bW_u9t6jVbCls3f-BudO-ic";
    }

}
