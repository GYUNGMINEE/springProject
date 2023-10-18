package kr.or.ddit.vo;

import lombok.Data;

//자바빈 클래스
@Data
public class Item2VO {
	private int itemId;
	private String itemName;
	private int price;
	private String description;
	private String pictureUrl;
	private String pictureUrl2;
}
