package com.model2.mvc.web.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> ȸ������ Controller
@Controller
public class UserController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method ���� ����
		
	public UserController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/givecoupon.do")
	public ModelAndView GiveCoupon(@RequestParam("currentPage") String currentPage, @RequestParam("userId") String userId,@RequestParam("userName") String userName,@RequestParam("coupon") String couponNo) throws Exception {
		ModelAndView model = new ModelAndView();
		System.out.println("���� userId = "+userId);
		System.out.println("���� userName = "+userName);
		System.out.println("���� couponNo = "+couponNo);
		userService.giveCoupon(userId, couponNo);
		model.setViewName("redirect:/listUser.do?currentPage="+currentPage);
		return model;
	}
	
	@RequestMapping("/addUserView.do")
	public String addUserView() throws Exception {
		System.out.println("/addUserView.do");
		return "redirect:/user/addUserView.jsp";
	}
	
	@RequestMapping("/addUser.do")
	public String addUser( @ModelAttribute("user") User user ) throws Exception {

		System.out.println("/addUser.do");
		//Business Logic
		userService.addUser(user);
		
		return "redirect:/user/loginView.jsp";
	}
	
	@RequestMapping("/getUser.do")
	public String getUser( @RequestParam("userId") String userId , Model model ) throws Exception {
		
		System.out.println("/getUser.do");
		//Business Logic
		User user = userService.getUser(userId);
		// Model �� View ����
		model.addAttribute("user", user);
		
		return "forward:/user/getUser.jsp";
	}
	
	@RequestMapping("/updateUserView.do")
	public String updateUserView( @RequestParam("userId") String userId , Model model ) throws Exception{

		System.out.println("/updateUserView.do");
		//Business Logic
		User user = userService.getUser(userId);
		// Model �� View ����
		model.addAttribute("user", user);
		
		return "forward:/user/updateUser.jsp";
	}
	
	@RequestMapping("/updateUser.do")
	public String updateUser( @ModelAttribute("user") User user , Model model , HttpSession session) throws Exception{

		System.out.println("/updateUser.do");
		//Business Logic
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		return "redirect:/getUser.do?userId="+user.getUserId();
	}
	
	@RequestMapping("/loginView.do")
	public String loginView() throws Exception{
		
		System.out.println("/loginView.do");

		return "redirect:/user/loginView.jsp";
	}
	
	@RequestMapping("/login.do")
	public String login(@ModelAttribute("user") User user , HttpSession session ) throws Exception{
		
		System.out.println("/login.do");
		//Business Logic
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session ) throws Exception{
		
		System.out.println("/logout.do");
		
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/checkDuplication.do")
	public String checkDuplication( @RequestParam("userId") String userId , Model model ) throws Exception{
		
		System.out.println("/checkDuplication.do");
		//Business Logic
		boolean result=userService.checkDuplication(userId);
		// Model �� View ����
		model.addAttribute("result", new Boolean(result));
		model.addAttribute("userId", userId);

		return "forward:/user/checkDuplication.jsp";
	}
	
	@RequestMapping("/listUser.do")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listUser.do");
		System.out.println("CurrentPage : "+search.getCurrentPage());
		System.out.println("URL : "+request.getRequestURL());
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		// Coupon ó���� ���� �� ������
		if (request.getParameter("coupon") != null) {
			System.out.println("coupon user�� ���� ������ ����Ȯ��");
			System.out.println("���� userId = "+request.getParameter("userId"));
			System.out.println("���� userName = "+request.getParameter("userName"));
			List<String> list = userService.checkCoupon(request.getParameter("userId"));
			System.out.println("list ��� = "+list);
			model.addAttribute("currentPage", search.getCurrentPage());
			model.addAttribute("couponNo", list);
			if (list.size()==0) {
				System.out.println("list�� ������ 0");
				String url = "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1=0&couponNo2=0&couponNo3=0'";
				model.addAttribute("couponUrl", url);
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 1){
				System.out.println("list�� ������ 1");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2=0&couponNo3=0'");				
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 2){
				System.out.println("list�� ������ 2");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2="+list.get(1)+"&couponNo3=0'");				
				model.addAttribute("couponNo", "yes");
			}else if(list.size() == 3){
				System.out.println("list�� ������ 3");
				model.addAttribute("couponUrl", "'../user/coupon.jsp?userId="+request.getParameter("userId")+"&userName="+request.getParameter("userName")+"&currentPage="+request.getParameter("currentPage")+"&couponNo1="+list.get(0)+"&couponNo2="+list.get(1)+"&couponNo3="+list.get(2)+"'");				
				model.addAttribute("couponNo", "yes");
			}
		}
		
		return "forward:/user/listUser.jsp";
	}
	
}