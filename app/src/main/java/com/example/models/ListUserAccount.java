package com.example.models;

import java.util.ArrayList;

public class ListUserAccount {
    public static ArrayList<UserAccount> getUserAccounts()
    {
        ArrayList<UserAccount>database=new ArrayList<>();
        database.add(new UserAccount("admin","123","admin","Trần Ngọc Bảo Vy",true));
        database.add(new UserAccount("user1","1234","employee","Choi Soobin",true));
        database.add(new UserAccount("user1","12345","employee","Choi Yeonjun",true));
        return database;
    }
    public static UserAccount login(String username,String password){
        //step1: query database
        ArrayList<UserAccount>database=getUserAccounts();
        //step2: compare to login
        for(UserAccount user:database){
            if(user.getUsername().equalsIgnoreCase(username)&&
                    user.getPassword().equals(password))
            {//login success
                return user;
            }
        }
        return null;//failed
    }
}
