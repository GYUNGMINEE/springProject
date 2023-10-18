package kr.or.ddit.vo;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CardVO {
	//문자열이 null이 아니고 trim(공백제거)한 길이가 0보다 커야 함
	@NotBlank
	private String no;
	@Pattern(regexp="^[0-9]{6}$",message="연도4자리와 월2자리로 입력해주세요")
	private String validMonth;
}


