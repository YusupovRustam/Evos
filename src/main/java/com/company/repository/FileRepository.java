package com.company.repository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileRepository {

    public static void writeFile(File file,String s){
        try {
            PrintWriter printWriter=new PrintWriter(file);
            printWriter.println(s);
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static LinkedList<String> readFile(File file){
        LinkedList<String>list=new LinkedList<>();
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            String s;
            while ((s = bufferedReader.readLine())!=null){
                list.add(s);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getLast(File file){
        return readFile(file).getLast();
    }
}
