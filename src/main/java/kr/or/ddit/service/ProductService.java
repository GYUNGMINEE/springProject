package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.ProductVO;

public interface ProductService {

	//메소드 시그니처
	//상품 등록
	public int processAddProduct(ProductVO productVO);
	//모든 상품 검색
	public List<ProductVO> products();
	//상품 1행 검색
	public ProductVO product(String productId);
	
}
