package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
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
	
	
	@RequestMapping("/addProduct.do")
	public String AddProduct(@ModelAttribute("product") Product product,HttpServletRequest request) throws Exception {
		
		System.out.println("/addProduct.do");
		
		productService.addProduct(product);
		
		Product provo = product;
		
		request.setAttribute("provo", provo);	
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String GetProduct( @RequestParam("prodNo") int prodNo,@RequestParam("menu") String menu, HttpServletResponse response, HttpServletRequest request ) throws Exception {

		System.out.println("/getProduct.do");
		//Business Logic
		Product provo = productService.getProduct(prodNo);
		
		System.out.println("ã�� ���δ�Ʈ : "+provo);
		
		request.setAttribute("provo", provo);
		
		String history=null;
		
		Integer no = new Integer(prodNo);
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
				
				}
			}
		}
		
		history+=","+no.toString();
		Cookie cookie = new Cookie("history",history);
		
		response.addCookie(cookie);
		
		if (menu.equals("manage")) {			
			return "forward:/product/updateProduct.jsp";
		}else {
			return "forward:/product/getProductDetail.jsp";
		}
		
	}
	
	@RequestMapping(value = "/listProduct.do", method=RequestMethod.GET)
	public String ListProductGet(@ModelAttribute("search") Search search,@RequestParam("menu") String menu,HttpServletRequest request) throws Exception {
		System.out.println("listProductAction ����");
		System.out.println("�Ѿ�� currentPage : "+request.getParameter("currentPage"));
		if(request.getParameter("currentPage") == null || request.getParameter("currentPage").equals("")) {
			search.setCurrentPage(1);
		}else {
			search.setCurrentPage((Integer.parseInt(request.getParameter("currentPage"))));
		}
		search.setPageSize(pageSize);
		
		Map<String,Object> map = productService.getProductList(search);
		Page resultPage = new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(),pageUnit,pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/listProduct.do")
	public String ListProduct(@ModelAttribute("search") Search search,@RequestParam("menu") String menu,HttpServletRequest request) throws Exception {
		System.out.println("listProductAction ����");
		if(request.getParameter("currentPage") == null || request.getParameter("currentPage").equals("")) {
			search.setCurrentPage(1);
		}else {
			search.setCurrentPage((Integer.parseInt(request.getParameter("currentPage"))));
		}
		search.setPageSize(pageSize);
		
		Map<String,Object> map = productService.getProductList(search);
		Page resultPage = new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(),pageUnit,pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/likeProduct.do")
	public String LikeAction(HttpSession session,@RequestParam("menu") String menu,@RequestParam("prod_no") String prodNo,@RequestParam("currentPage") int currentPage) throws Exception {
		System.out.println("likeProduct action ����");
		System.out.println("currentPage : "+currentPage);
		if(currentPage == 0 ){
			currentPage = 1;
		}
		User user = (User)session.getAttribute("user");
		boolean isLike = productService.chekLike(user.getUserId(), prodNo);
		if(isLike) {
			return "redirect:/listProduct.do?menu="+menu+"&currentPage="+currentPage+"&likefail=1";
		}else {
			productService.likeProduct(user.getUserId(),prodNo);
			return "redirect:/listProduct.do?menu="+menu+"&currentPage="+currentPage;
		}
	}
	
	@RequestMapping("/updateProduct.do")
	public String UpdateProduct(@ModelAttribute("product")Product product) throws Exception {
		productService.updateProduct(product);
		return "redirect:/getProduct.do?menu=ok&prodNo="+product.getProdNo();
	}
}