package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PurInfoVO {
	private String username;
	private String bookId;
	private int purCnt;
	private Date purDt;
}
