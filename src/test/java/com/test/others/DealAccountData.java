package com.test.others;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Hinsteny
 * @date 2016/8/30
 * @copyright: 2016 All rights reserved.
 */
public class DealAccountData {

    private static Map<String, String> preData = new HashMap<>();
    private static Map<String, Account> resData = new HashMap<>();

    private static int count = 0, all = 0;

    public static void main(String[] args){
        handerPreData("E:\\Account.txt", true);
        printResData("E:\\account_hander_.txt", resData);
    }

    public static void handlerData(String identity, String name){
        if (identity == null || identity.length() == 0) return;
        all ++;
        Account account = resData.get(identity);
        if (account == null){
            Account item = new Account(identity);
            item.addName(name);
            resData.put(identity, item);
        }else {
            account.addName(name);
        }
    }

    private static Map<String, String> handerPreData(String file, boolean reverse) {
        Pattern pattern = Pattern.compile("[1-9]+");
        Map<String, String> preData = new HashMap<>();
        FileReader fr = null;
        BufferedReader br = null;
        String[] strs = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String readoneline;
            int l;
            while((l = br.read()) != -1){
                readoneline = br.readLine();
                if (readoneline != null && readoneline.length() > 0)
                    System.out.println(readoneline + "$ " + pattern.matcher(readoneline).find());
                if (pattern.matcher(readoneline).find()){
                    strs = readoneline.split("\\|");
                    if (reverse){
                        preData.put(strs[0].trim(), strs[1].trim());
                        handlerData(strs[0].trim(), strs[1].trim());
                    } else{
                        preData.put(strs[1].trim(), strs[0].trim());
                        handlerData(strs[1].trim(), strs[0].trim());
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preData;
    }

    private static void printResData(String file, Map<String, Account> resData) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(compensateStr("identity_num", 20) +  " |" + compensateStr("count", 3) +  "| partner_user_id ");
            bw.newLine();
            bw.write("-------------------------------------------------------------------------------------------");
            bw.newLine();
            String readoneline;
            int l;
            Account item = null;
            for (Map.Entry<String, Account> entry : resData.entrySet()) {
                item = entry.getValue();
                count+=item.getName().size();
                bw.write(item.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        System.out.println("All : " + all +" count : "+count);
    }

    public static String compensateStr (String str, int length){
        return String.format("%-"+length+"s", str);
    }

    private static class Account {
        private String identity;
        private Set<String> name;

        public Account(String identity) {
            this.identity = identity;
            this.name = new TreeSet<String>();
        }

        public Account addName(String name){
            this.name.add(name);
            return this;
        }

        public String getIdentity() {
            return identity;
        }

        public Set<String> getName() {
            return name;
        }

        @Override
        public String toString() {
            return compensateStr(identity, 20) +  " | " + compensateStr(String.valueOf(name.size()), 3) +  " | " + name.toString();
        }
    }

}
