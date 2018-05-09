package com.model2.mvc.web.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 Controller
@Controller
@RequestMapping("/user/*")
public class UserController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	
	@RequestMapping(value="giveCoupon")
	public ModelAndView giveCoupon(@RequestParam("currentPage") String currentPage, @RequestParam("userId") String userId,@RequestParam("userName") String userName,@RequestParam("coupon") String couponNo) throws Exception {
		ModelAndView model = new ModelAndView();
		System.out.println("들어온 userId = "+userId);
		System.out.println("들어온 userName = "+userName);
		System.out.println("들어온 couponNo = "+couponNo);
		userService.giveCoupon(userId, couponNo);
		model.setViewName("redirect:/user/listUser?currentPage="+currentPage);
		return model;
	}
	
	@RequestMapping( value="addUserView", method=RequestMethod.GET )
	public String addUserView() throws Exception{
	
		System.out.println("/user/addUser : GET");
		
		return "redirect:/user/addUserView.jsp";
	}
	
	@RequestMapping( value="addUser", method=RequestMethod.POST )
	public String addUser( @ModelAttribute("user") User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		userService.addUser(user);
		
		return "redirect:/user/loginView.jsp";
	}
	
	@RequestMapping(value="getUser")
	public String getUser( @RequestParam("userId") String userId , Model model ) throws Exception {
		
		System.out.println("/user/getUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);
		
		return "forward:/user/getUser.jsp";
	}
	
	@RequestMapping(value="updateUserView")
	public String updateUserView( @RequestParam("userId") String userId , Model model ) throws Exception{

		System.out.println("/user/updateUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);
		
		return "forward:/user/updateUser.jsp";
	}
	
	@RequestMapping(value="updateUser")
	public String updateUser( @ModelAttribute("user") User user , Model model , HttpSession session) throws Exception{

		System.out.println("/user/updateUser : POST");
		//Business Logic
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}

		return "redirect:/user/getUser?userId="+user.getUserId();
	}
	
	@RequestMapping(value="loginView")
	public String loginView() throws Exception{
		
		System.out.println("/user/logon : GET");

		return "redirect:/user/loginView.jsp";
	}
	

	@RequestMapping(value="login")
	public String login(@ModelAttribute("user") User user , HttpSession session ) throws Exception{
		
		System.out.println("/user/login : POST");
		//Business Logic
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="logout")
	public String logout(HttpSession session) throws Exception{
		
		System.out.println("/user/logout : POST");
		
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
	
	
	@RequestMapping(value="checkDuplicaion")
	public String checkDuplication( @RequestParam("userId") String userId , Model model ) throws Exception{
		
		System.out.println("/user/checkDuplication : POST");
		//Business Logic
		boolean result=userService.checkDuplication(userId);
		// Model 과 View 연결
		model.addAttribute("result", new Boolean(result));
		model.addAttribute("userId", userId);

		return "forward:/user/checkDuplication.jsp";
	}
	
	@RequestMapping(value="listUser")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listUser");
		System.out.println("CurrentPage : "+search.getCurrentPage());
		System.out.println("URL : "+request.getRequestURL());
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		// Coupon 처리를 위한 값 보내기
		if (request.getParameter("coupon") != null) {
			System.out.println("coupon user값 저장 들어오는 유무확인");
			System.out.println("들어온 userId = "+request.getParameter("userId"));
			System.out.println("들어온 userName = "+request.getParameter("userName"));
			List<String> list = userService.checkCoupon(request.getParameter("userId"));
			System.out.println("list 목록 = "+list);
			model.addAttribute("currentPage", search.getCurrentPage());
			model.addAttribute("couponNo", list);
			if (list.size()==0) {
				System.out.println("list의 갯수가 0");
				String url = "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1=0&couponNo2=0&couponNo3=0'";
				model.addAttribute("couponUrl", url);
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 1){
				System.out.println("list의 갯수가 1");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2=0&couponNo3=0'");				
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 2){
				System.out.println("list의 갯수가 2");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2="+list.get(1)+"&couponNo3=0'");				
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 3){
				System.out.println("list의 갯수가 3");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2="+list.get(1)+"&couponNo3="+list.get(2)+"'");				
				model.addAttribute("couponNo", "yes");
			}
		}
		
		return "forward:/user/listUser.jsp";
	}
}