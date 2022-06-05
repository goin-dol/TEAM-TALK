package com.goindol.teamtalk.client;

import com.goindol.teamtalk.client.service.noticeDAO;
import com.goindol.teamtalk.client.service.userDAO;
import com.goindol.teamtalk.client.service.VoteDAO;

public class Test {
    public static void main(String[] args) {
        VoteDAO voteDAO = new VoteDAO();
        userDAO userDAO = new userDAO();
        noticeDAO noticeDAO = new noticeDAO();
//        noticeDAO.createNotice("김대현",1,"공지","이것은 공지입니다.");
//        noticeDAO.checkNotice("sup",1);
//        noticeDAO.readNotice("sup",1);
//        int voteVar_id = voteDAO.ReadVoteVarByContent("cd");
//        System.out.println(voteDAO.checkOverlapVoteVar(1, 2, "김대현"));
//        System.out.println(voteDAO.checkOverLap(1));
//        voteDAO.choiceVote(1,voteVar_id,"cd","김대현");
//        List<String> ss = noticeDAO.readNoticeList(1);
//        for (String s : ss) {
//            System.out.println("s = " + s);
//        }
//        noticeDTO notice = noticeDAO.showNotice(1);
//        if(notice==null){
//            System.out.println("No");
//        }else{
//            System.out.println("notice = " + notice.getContent());
//        }
//        System.out.println("noticeDAO = " + noticeDAO.AllReadNotice(1));
//        System.out.println(noticeDAO.checkNotice(1));
    }

}
