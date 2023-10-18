package kr.or.ddit.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.ProductService;
import kr.or.ddit.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;


//프링이가 "아, 이 클래스는 컨트롤러 클래스구나. 자바빈으로 등록해놓아야지" 라고 처리함
@Slf4j
@Controller
public class ProductController {
	//이미 만들어져있는 자바빈을 주입
	//DI, IoC
	@Autowired
	ProductService productService;
	
	//요청URL : /shopping/welcome
	//요청파라미터 : 없음
	//요청방식 : GET
	@RequestMapping(value="/shopping/welcome",method=RequestMethod.GET)
	public ModelAndView welcome(ModelAndView mav) {
		// 	/WEB-INF/views/shopping/welcome.jsp
		mav.setViewName("shopping/welcome");
		//forwarding
		return mav;
	}
	
	//요청URL : /shopping/addProduct
	//요청파라미터 : 없음
	//요청방식 : GET
	@RequestMapping(value="/shopping/addProduct",method=RequestMethod.GET)
	public ModelAndView addProduct(ModelAndView mav) {
		//forwarding
		mav.setViewName("shopping/addProduct");
		return mav;
	}
	
	//상품 등록
//	요청URL : /shopping/processAddProduct
//	요청방식 : post
//	요청파라미터(HTTP파라미터,QueryString) :
//		{productId=P1237,pname=에어팟3,unitPrice=200000,description=굿음질,
//			manufacturer=Apple,category=Table,unitsInStock=1000,condition=Old,
//			productImage=파일객체}
	@RequestMapping(value="/shopping/processAddProduct",method=RequestMethod.POST)
	public ModelAndView processAddProduct(ProductVO productVO,
			ModelAndView mav) {
		/*
		ProductVO [productId=P1237, pname=에어팟3, unitPrice=200000, description=<p>asdf</p>
		, manufacturer=Apple, category=소설, unitsInStock=1000, condition=Old, filename=null
		, productImage=org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@8b8812f
		, quantity=0]
		 */
		log.info("productVO : " + productVO);
		
		//파일 업로드 시작 ///////////////////////////////
		//1) 업로드 폴더 설정
		String uploadFolder 
			= "C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\images";
		log.info("uploadFolder : " + uploadFolder);
		//vo객체에서 이미지 파일 객체를 get함
		MultipartFile multipartFile = productVO.getProductImage();
		
		String filename = multipartFile.getOriginalFilename();
		log.info("-----------------------");
		log.info("파일명 : " + filename);
		log.info("파일 크기 : " +  multipartFile.getSize());
		
		//랜덤값 생성
		UUID uuid = UUID.randomUUID();//adsfasdfkfsdkjfdslk
		filename = uuid.toString() + "_" + filename;
		
		//File객체 설계(복사할 대상 경로, 파일명)
		File saveFile = new File(uploadFolder, filename);
		try {
			//파일 복사 실행
			multipartFile.transferTo(saveFile);
			
			//productVO객체의 filename 멤버변수에 업로드될 이미지의 명을 set함
			productVO.setFilename(filename);
			
			//PRODUCT 테이블에 INSERT 기능 구현
			int result = productService.processAddProduct(productVO);
			log.info("result : " + result);
			
			//redirect
			mav.setViewName("redirect:/shopping/products");
			
			return mav;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			//redirect
			mav.setViewName("redirect:/shopping/addProduct");
		}
		//파일 업로드 끝 ///////////////////////////////
		//redirect
		return mav;
	}
	
	//상품 목록
	//요청URI : /shopping/products
	//요청파라미터 : 없음
	//요청방식 : get
	@RequestMapping(value="/shopping/products",method=RequestMethod.GET)
	public ModelAndView products(ModelAndView mav) {
		
		List<ProductVO> attributeValue = this.productService.products();
		log.info("attributeValue: " + attributeValue);
		
		mav.addObject("listOfProducts", attributeValue);
		//forwarding
		mav.setViewName("shopping/products");
		
		return mav;
	}
	
	//상품 상세
	//요청URI : /shopping/product?productId=P1234
	//요청파라미터 : productId=P1234
	//요청방식 : GET
	@RequestMapping(value="/shopping/product",method=RequestMethod.GET)
	public ModelAndView product(@RequestParam String productId,
			ModelAndView mav) {
		log.info("productId : " + productId);
		
		ProductVO productVO = this.productService.product(productId);
		log.info("productVO : " + productVO);
		
		//데이터
		mav.addObject("product", productVO);
		//forwarding
		mav.setViewName("shopping/product");
		
		return mav;
	}
	
	//상품 주문
	//요청URI : /shopping/addCart?productId=P1234
	//요청파라미터 : productId=P1234
	//요청방식 : get
	@RequestMapping(value="/shopping/addCart",method=RequestMethod.POST)
	public ModelAndView addCart(@RequestParam String productId,
			ModelAndView mav, HttpServletRequest request) {
		log.info("productid : " + productId);
		
		HttpSession session = request.getSession();
		
		//from : product.jsp
		//to   : post방식으로 addCart.jsp?productId=P1234
		//요청방식 : post방식
		
		//trim() : 공백제거
		//addCart.jsp or addCart.jsp?productId=
		if(productId==null || productId.trim().equals("")){
			mav.setViewName("redirect:/shopping/products");
			return mav;
		}
		
		//기본키인 P1234 코드의 상품을 찾아보자
		//싱글톤 패턴으로 공유되어 있는 객체를 1회 생성
		
		//select * from Product where id = 'P1234'
		ProductVO product = this.productService.product(productId);
		
		//상품 결과가 없으면..
		if(product==null){
			//[상품이 없음]예외처리 페이지로 강제 이동
			mav.setViewName("redirect:/shopping/exceptionNoProductId");
			return mav;
		}
		
		//상품이 있으면 계속 실행
		//장바구니(세션) => 세션명 : cartlist
		ArrayList<ProductVO> list 
			= (ArrayList<ProductVO>)session.getAttribute("cartlist");
		
		//장바구니가 없으면 생성
		if(list==null){
			//list는 null이므로 여기서 리스트를 생성해줘야 함
			list = new ArrayList<ProductVO>();
			//sertlist라는 세션명으로 생성
			session.setAttribute("cartlist", list);
		}
		
		int cnt = 0;
		//장바구니가 있다면 다음을 실행
		//1) 장바구니에 P1234 상품이 이미 들어있는 경우
		//private int quantity;	//장바구니에 담은 개수
		//quantity를 1 증가
		for(int i=0;i<list.size();i++){
			//list는 P1234, P1235, P1236
			//list.get(0).getProductId().equals("P1234")
			if(list.get(i).getProductId().equals(productId)){
				cnt++;//장바구니에 넣을 상품을 찾았다면 1 증가
				//장바구니에 상품이 이미 들어있다면 장바구니에 담은 개수를 1 증가
				//list.get(i) : productVO
				list.get(i).setQuantity(list.get(i).getQuantity()+1);
			}
		}

		//2) 장바구니에 P1234 상품이 없는 경우
		if(cnt==0){
			// quantity를 1로 처리
			product.setQuantity(1);
			// 장바구니에 P1234 상품을 넣어주고
			list.add(product);
		}
		
		//장바구니 확인
		//ArrayList<ProductVO> : list
		for(ProductVO vo : list){
			log.info("vo : " + vo);
		}
		
		mav.setViewName("redirect:/shopping/product?productId="+productId);
		
		return mav;
	}
	
	/* JSON : JavaScript Object Notation(자바스크립트 객체 표기법)
		   - 텍스트에 기반을 둔 데이터 저장 및 교환을 위한 구문임
		   - 브라우저와 서버 간에 데이터를 교환할 때 데이터는 텍스트일 뿐임
		   - 모든 자바스크립트 객체를 JSON으로 변환하고 JSON을 서버로 보낼 수 있음
		   - 서버에서 받은 JSON을 자바스크립트 객체로 변환할 수 있음
	 */
	//장바구니 처리
	//요청URI : /shopping/cart
	//요청방식 : GET
	//골뱅이ResponseBody 리턴타입이 JSON으로 변환되어 리턴 
	@RequestMapping("/shopping/cart")
	public ModelAndView cart(HttpServletRequest request, ModelAndView mav) {
		//장바구니?세션
		HttpSession session = request.getSession();
		
		//세션의 고유 아이디(장바구니 번호)
		String cartId = session.getId();
		log.info("cartId : " + cartId);
		
		//session에 있는 세션 속성명인 cartlist를 통해
		// 상품 목록을 가져와보자
		List<ProductVO> cartList 
			= (List<ProductVO>)session.getAttribute("cartlist");
		
//		log.info("cartList : " + cartList);
		
		//Model
		mav.addObject("cartId", cartId);
		//jsp에서 달러{cartlist}라고 하면 gaeddongi는 무시됨
		//jsp에서 달러{gaeddongi}가 안찍히고 있음
		mav.addObject("cartList", cartList);
		//View
		//forwarding
		mav.setViewName("shopping/cart");
		
		//forwarding
		return mav;
	}
	
	//요청URI : /shopping/removeCart?productId=P1235
	//요청파라미터 : productId=P1235
	//요청방식 : GET
	//만약../shopping/removeCart?id=P1235 => RequestParam(value="id")
	@RequestMapping("/shopping/removeCart")
	public ModelAndView removeCart(@RequestParam String productId,
			ModelAndView mav, HttpServletRequest request) {
		log.info("productId : " + productId);
		
		//만약에 productId가 null(/removeCart)이거나 데이터가 없다면(/removeCart?productId=)
		// /shopping/products를 재요청
		if(productId==null||productId.trim().equals("")) {
			//redirect : URL재요청
			mav.setViewName("redirect:/shopping/products");
		}else {
			//select * from product where product_id = 'Z1234' => 결과는 null
			ProductVO productVO = this.productService.product(productId);
			
			log.info("productVO : " + productVO);
			
			if(productVO==null) {//파라미터 값에 해당되는 상품 자체가 없을 때..
				mav.setViewName("/shopping/exceptionNoProductId");
			}else {//상품 자체가 있으면
				//세션의 장바구니 목록(cartlist)에서 P1234가 있는지 체크 후
				HttpSession session = request.getSession();
				ArrayList<ProductVO> cartlist 
					= (ArrayList<ProductVO>)session.getAttribute("cartlist");
				
				//만약에 있다면 장바구니에서 제외처리
				for(int i=0;i<cartlist.size();i++) {
					//cartlist.get(0) => 첫번째 상품 productVO
					//cartlist.get(1) => 두번째 상품 productVO
					//cartlist.get(2).getProductId() => 세번째 상품 ProductVO객체의 상품아이디 데이터(P1235)
					if(cartlist.get(i).getProductId().equals(productId)) {
						//remove(Object)
						cartlist.remove(cartlist.get(i));
					}
				}
				//redirect
				mav.setViewName("redirect:/shopping/cart");
			}//end inner if
		}//end outer if
		return mav;
	}
	
	//장바구니 모두 삭제 요청 처리
	//요청URI : /shopping/deleteCart?cartId=ASDFKLDFSJ
	//요청파라미터 : cartId=ASDFKLDFSJ
	@RequestMapping("/shopping/deleteCart")
	public ModelAndView deleteCart(@RequestParam String cartId,
			ModelAndView mav, HttpServletRequest request) {
		log.info("cartId : " + cartId);
		//cartId가 없으면 /shopping/cart를 재요청(ModelAndView)
		if(cartId==null||cartId.trim().equals("")) {
			mav.setViewName("redirect:/shopping/cart");
		}else {//cartId가 정상적으로 있을 때
			//장바구니(세션=cartlist라는 속성명을 가진 세션)를 비울것임
			HttpSession session = request.getSession();
			session.removeAttribute("cartlist");
			
//			ArrayList<ProductVO> cartlist 
//				= (ArrayList<ProductVO>)session.getAttribute("cartlist");
			
			//장바구니 비우기
//			for(int i=0;i<cartlist.size();i++) {
//				cartlist.remove(cartlist.get(i));
//			}
			// /shopping/cart를 재요청
			mav.setViewName("redirect:/shopping/cart");
		}
		
		return mav;
	}
	
	//요청URI : /shopping/shippingInfo?cartId=E21A05F16BBE2E6A62F1D7E5B78588A0
	//요청파라미터 : cartId=E21A05F16BBE2E6A62F1D7E5B78588A0
	//요청방식 : get
	@RequestMapping(value="/shopping/shippingInfo",method=RequestMethod.GET)
	public ModelAndView shippingInfo(String cartId, ModelAndView mav) {
		log.info("cartId : " + cartId);
		//Model
		mav.addObject("cartId", cartId);
		
		//View
		//forwarding
		//조립? 7) ViewResolver
		//설정? Servlet-context.xml
		mav.setViewName("shopping/shippingInfo");
		
		return mav;
	}
	
	//주문하기 요청 접수 및 기능 호출
	//요청URI : /shopping/processShippingInfo
	//요청파라미터 : {cartId=ASDFDSAFDS,name=개똥이,shippingDate=2023-07-25
	//		,country=대한민국,zipCode=12345,addressName=대전 중구 오류동12}
	//요청방식 : post
	@RequestMapping(value="/shopping/processShippingInfo",method=RequestMethod.POST)
	public ModelAndView processShippingInfo(@RequestParam Map<String,Object> param,
			ModelAndView mav) {
		log.info("param : " + param);
		//Model : 받은 map 그대로 orderconfirmation.jsp로 보내줌
		mav.addObject("map", param);
		
		//forwarding
		mav.setViewName("shopping/orderConfirmation");
		
		return mav;
	}
	
	//요청 완료
	//1) get
	//요청URI : /shopping/thankCustomer?shippingDate=2023-07-25&cartId=ASDFASDF
	//요청방식 : get
	//2) post
	//요청URL : /shopping/thankCustomer
	//요청파라미터 : {shippingDate=2023-07-25&cartId=ASDFASDF}
	//요청방식 : post
	@ResponseBody
	@RequestMapping(value="/shopping/thankCustomer",method=RequestMethod.POST)
	public String thankCustomer(ModelAndView mav, HttpSession session,
			@RequestParam Map<String,Object> param) {
		//shippingDate, cartId
		log.info("param : " + param);
		
		//session에 shippingDate와 cartId를 넣어주자
		Map<String,String> shippingDateMap = new HashMap<String, String>();
		shippingDateMap.put("shippingDate", param.get("shippingDate").toString());
		Map<String,String> cartIdMap = new HashMap<String, String>();
		cartIdMap.put("cartId", param.get("cartId").toString());
		
		session.setAttribute("shippingDateMap", shippingDateMap);
		session.setAttribute("cartIdMap", cartIdMap);
		
		//장바구니(세션=>세션속성명cartlist) 비우기
		session.removeAttribute("cartlist");
		
		//forwarding. ajax로 호출 시 mav는 안씀
//		mav.setViewName("shopping/thankCustomer");
		
		return "success";
	}
	
	//orderConfirmation.jsp에서 location.href="/shopping/thankCustomer"
	//동일한 요청URL이더라도 요청방식을 다르게 하여 메소드 매핑을 분기할 수 있음
	@RequestMapping(value="/shopping/thankCustomer",method=RequestMethod.GET)
	public ModelAndView thankCustomerGet(ModelAndView mav) {
		//forwarding
		mav.setViewName("shopping/thankCustomer");
		
		return mav;
	}
	
	//취소하기
	// 요청URL : /shopping/checkOutCancelled
	@RequestMapping(value="/shopping/checkOutCancelled",method=RequestMethod.GET)
	public ModelAndView checkOutCancelled(HttpSession session,
			ModelAndView mav) {
		session.removeAttribute("cartlist");
		
		//forwarding
		mav.setViewName("shopping/checkOutCancelled");
		
		return mav;
	}
}











