package kr.or.ddit.vo;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

//자바빈 클래스. 주소
@Data
public class AddressVO {
	//문자열이 null이 아니고 trim(공백제거)한 길이가 0보다 커야 함
	@NotBlank(message="우편번호는 필수입니다.")
	private String zonecode;//우편번호
	@NotBlank(message="주소는 필수입니다.")
	private String address;//주소
	private String buildingName;//상세주소
}
