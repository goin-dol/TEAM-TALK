package com.goindol.teamtalk.client;

import com.goindol.teamtalk.client.service.userDAO;
import com.goindol.teamtalk.client.service.voteDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        voteDAO voteDAO = new voteDAO();
        userDAO userDAO = new userDAO();

//        userDAO.signUp("678","1234","손담비");

        ArrayList<String> content = new ArrayList<>();
        content.add("abc");
        content.add("cd");
        voteDAO.creatVote(1,"투표",false,false);
        int vote_id = voteDAO.Read_Vote_id(1);
        for(int i = 0; i < content.size(); i++) {
            voteDAO.createVoteVar(content.get(i), vote_id);
        }
//        System.out.println(voteDAO.Read_Vote_id(2));


    }
}
