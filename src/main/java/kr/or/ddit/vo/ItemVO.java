package kr.or.ddit.vo;

import lombok.Data;

//자바빈 클래스
@Data
public class ItemVO {
	private int itemId;
	private String itemName;
	private int price;
	private String description;//자료형(데이터타입)이 CLOB
	private String pictureUrl;
}
