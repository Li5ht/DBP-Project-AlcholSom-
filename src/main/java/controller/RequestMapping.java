package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.diary.*;
import controller.recommend.*;
import controller.review.*;
import controller.user.*;



public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/home/home", new HomeController());
        
        mappings.put("/user/login/form", new ForwardController("/user/loginForm.jsp"));
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/view", new ViewUserController());

        // 회원가입
        mappings.put("/user/register", new RegisterUserController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/delete", new UpdateUserController());

        
       // 추천 관련
       mappings.put("/recommend/list", new RecommendController());
       mappings.put("/recommend/test", new TestController());
        
       // 시뮬레이터
       mappings.put("/simulate", new SimulateController());
       mappings.put("/simulate/result", new SimulateController());
       
       
       // 음주 기록
       mappings.put("/diary/list", new ListDiaryController());
       mappings.put("/diary/create", new ListDiaryController());
       mappings.put("/diary/update", new ListDiaryController());
       mappings.put("/diary/delete", new ListDiaryController());
       
       // 술 정보
       mappings.put("/product/info", new ViewAlcoholController());
       mappings.put("/review/create", new ViewAlcoholController());
       mappings.put("/review/update", new ViewAlcoholController());
       mappings.put("/review/delete", new ViewAlcoholController());
       mappings.put("/product/search", new ViewAlcoholController());
       
       logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}