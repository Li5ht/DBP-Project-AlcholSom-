package controller.recommend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.service.UserManager;

//import controller.user.UserSessionUtils;


public class TestController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {

		/* 로그인 여부 확인 */
		if (!UserSessionUtils.hasLogined(request.getSession())) {
			/* 로그인 X */
            request.setAttribute("noLogin", true);
        } else {
        	/* 로그인 O */
        	request.setAttribute("hasLogin", true);
			request.setAttribute("userId", UserSessionUtils.getLoginUserId(request.getSession()));
        	request.setAttribute("nickname", UserSessionUtils.getLoginUserNickname(request.getSession()));
        }

		if (request.getMethod().equals("GET")) { // testForm 요청
			return "/recommend/testForm.jsp";
		}
		
		String testType = "";
		testType = "ENFP";
		
		
		// 사용자 DB testType에 추가
		if (UserSessionUtils.hasLogined(request.getSession())) {
			/* 로그인 O */
            long id = UserSessionUtils.getLoginUserPrimaryKey(request.getSession());
            UserManager userMan = UserManager.getInstance();
            userMan.updateUserTestType(testType, id);
        }
		request.setAttribute("testType", testType);
		
		return "/recommend/testResult.jsp";
	}
}
