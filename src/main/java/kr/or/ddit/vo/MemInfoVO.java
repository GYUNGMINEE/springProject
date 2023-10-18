package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MemInfoVO {
	private String username;
	private String password;
	private String memNm;
	private Date regDt;
}
