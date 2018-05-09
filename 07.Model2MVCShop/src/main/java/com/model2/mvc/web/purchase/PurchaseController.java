package com.model2.mvc.web.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.sun.tracing.dtrace.Attributes;

@Controller
public class PurchaseController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView AddPurchase(@RequestParam("prodNo") int prodNo,@ModelAttribute("purchase") Purchase purchase,HttpSession session) throws Exception {
		
		User uv = (User)session.getAttribute("user");
		purchase.setTranCode("1");
		purchase.setBuyer(uv);
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchaseService.addPurchase(purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		modelAndView.addObject("pvo2", purchase);
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchaseView.do",method=RequestMethod.GET)
	public ModelAndView AddPurchaseView(HttpSession session,@RequestParam("prod_no") int prodNo,@RequestParam("order_same") String check,@ModelAttribute("purchase") Purchase purchase) throws Exception {
		User uv = (User)session.getAttribute("user");
		purchase.setBuyer(uv);
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		modelAndView.addObject("pvo", purchase);
		modelAndView.addObject("check", check);
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchaseView.do",method=RequestMethod.POST)
	public ModelAndView AddPurchase(HttpSession session,@RequestParam("prod_no") int prodNo,@ModelAttribute("purchase") Purchase purchase) throws Exception {
		User uv = (User)session.getAttribute("user");
		purchase.setBuyer(uv);
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		modelAndView.addObject("pvo", purchase);
		return modelAndView;
	}
	
	@RequestMapping("/pay.do")
	public ModelAndView Pay(HttpSession session,@RequestParam("prodNo") int prodNo,@ModelAttribute("purchase") Purchase purchase) throws Exception {
		User uv = (User)session.getAttribute("user");
		purchase.setBuyer(uv);
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setTranCode("1");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/payView.jsp");
		modelAndView.addObject("pvo", purchase);
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView ListPrurchase(HttpServletRequest request,@ModelAttribute("search") Search search,HttpSession session) throws Exception {
		System.out.println("listPurchase 진입");
		User uv = (User)session.getAttribute("user");
		search.setSearchKeyword(uv.getUserId());
		System.out.println("넘어온 currentPage : "+request.getParameter("currentPage"));
		if(request.getParameter("currentPage") == null || request.getParameter("currentPage").equals("")) {
			search.setCurrentPage(1);
		}else {
			search.setCurrentPage((Integer.parseInt(request.getParameter("currentPage"))));
		}
		search.setPageSize(pageSize);
		Map<String,Object> map = purchaseService.getSaleList(search);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("count")).intValue(), pageUnit, pageSize);
		
		List<Purchase> list = (List<Purchase>)map.get("list");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		modelAndView.addObject("list", list);
		modelAndView.addObject("resultPage",resultPage);
		modelAndView.addObject("searchVO1", search);
		System.out.println("listPurchase out");
		return modelAndView;
	}
	
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView GetPurchase(HttpSession session,@RequestParam("tranNo") int tranNo) throws Exception {
		User uv = (User)session.getAttribute("user");
		Purchase pvo = purchaseService.getPurchase(tranNo);
		pvo.setBuyer(uv);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/purchaseView.jsp");
		modelAndView.addObject("pvo3",pvo);
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView UpdatePurchaseView(HttpSession session,@RequestParam("tranNo") int tranNo) throws Exception {
		User uv = (User)session.getAttribute("user");
		Purchase pvo = purchaseService.getPurchase(tranNo);
		pvo.setBuyer(uv);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("pvo4",pvo);
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView UpdatePurchase(HttpSession session,@RequestParam("tranNo") int tranNo,@ModelAttribute("purchase") Purchase purchase) throws Exception {
		User uv = (User)session.getAttribute("user");
		purchase.setTranNo(tranNo);
		System.out.println("자동바인딩된 purchase의 정보 : "+purchase);
		purchaseService.updatePurchase(purchase);
		purchase = purchaseService.getPurchase(tranNo);
		purchase.setBuyer(uv);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/purchaseView1.jsp");
		modelAndView.addObject("pvo6",purchase);
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public String UpdateTranCodeByProd(HttpServletRequest request,@RequestParam("prodNo") int prodNo,@RequestParam("tranCode") String tranCode) throws Exception {
		Product pv = productService.getProduct(prodNo);
		Purchase pvo = purchaseService.getPurchase2(prodNo);
		pvo.setTranCode(tranCode);
		purchaseService.updateTranCode(pvo);
		pvo.setPurchaseProd(pv);
		System.out.println("얻은currentPage값 : "+request.getParameter("currentPage"));
		return "forward:/listProduct.do?menu=manage";
	}
	
	@RequestMapping("/updateTranCode.do")
	public String UpdateTranCode(HttpServletRequest request,@RequestParam("tranNo") int tranNo,@RequestParam("tranCode") String tranCode) throws Exception {
		Purchase pvo = purchaseService.getPurchase(tranNo);
		pvo.setTranCode(tranCode);
		purchaseService.updateTranCode(pvo);
		System.out.println("얻은currentPage값 : "+request.getParameter("currentPage"));
		return "redirect:/listPurchase.do?currentPage="+request.getParameter("currentPage");
	}

}
