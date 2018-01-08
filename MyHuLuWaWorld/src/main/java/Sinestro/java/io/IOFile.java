package io;

import creature.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class IOFile {

    private static File writeFile;

    private static File readFile;
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;

    public static synchronized void writeFile(Vector<Creature> roles) throws IOException {
        if (writeFile == null) {
            File directory = new File("document");
            if(!directory.exists()) {
                directory.mkdir();
            }
            Date cur = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH-mm-ss");
            String str = "document" + File.separator + simpleDateFormat.format(cur) + ".txt";
            writeFile = new File(str);
        }
        try {
            FileWriter fileWriter = new FileWriter(writeFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            ArrayList<String> str = new ArrayList<String>();
            for (Creature c : roles) {
                if(c.isRightRole() && c.role!= Role.YEYE) {
                    str.add("HuLuWa " + c.getID() + " " + c.getX() + " " + c.getY() + " " + c.isAlive() + " " + c.isAttacking());
                } else {
                    str.add(c.role.toString() + " " + c.getID() + " " + c.getX() + " " + c.getY() + " " + c.isAlive() + " " + c.isAttacking());
                }
            }
            for(String s: str){
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static File getReadFile() {
        return readFile;
    }

    public static void setReadFile(File file) {
        readFile = file;
        try {
            fileReader = new FileReader(readFile);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static synchronized String getNextString(){
        if(readFile == null) {
            return null;
        }
        String tmp = null;
        try {
            tmp = bufferedReader.readLine();
            if (tmp == null) {
                bufferedReader.close();
                fileReader.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return tmp;
    }

}
