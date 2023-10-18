package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

//자바빈 클래스
@Data
public class ItemAttachVO {
	private String fullname;
	private int itemId;
	private Date regdate;
}
