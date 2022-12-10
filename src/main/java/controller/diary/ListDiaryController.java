package controller.diary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.*;
import model.dao.*;
import model.service.DiaryManager;

public class ListDiaryController implements Controller {
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
		long id = -1; /* 사용자 primary key */
		
		/* 로그인 여부 확인 */
		if (!UserSessionUtils.hasLogined(request.getSession())) {
			request.setAttribute("noLogin", true);
			return "redirect:/user/login/form";
        } else {
        	request.setAttribute("hasLogin", true);
        	id = UserSessionUtils.getLoginUserPrimaryKey(request.getSession());
        	request.setAttribute("nickname", UserSessionUtils.getLoginUserNickname(request.getSession()));
        }
		
		DiaryManager dManager = DiaryManager.getInstance();
		List<Diary> diaryList = dManager.findDiaryListByMemberId(id);
		
		// diaryList 객체를 request에 저장하여 음주 기록 리스트 화면으로 이동(forwarding)
		request.setAttribute("diaryList", diaryList);				
		return "/diary/list.jsp";        
    }
}
