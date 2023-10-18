package kr.or.ddit.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//자바빈 클래스
//Controller에서 먼저 골뱅이Validated 애너테이션을 지정하여 입력값 검증 기능을 활성화
@Data
public class MemberVO {
	//입력값 검증 규칙을 지정
	//NotBlank : 필수항목
	@NotBlank(message="아이디는 필수입니다.")
	private String userId;
	//Size(max..) : 최대 6글자까지 허용
	@NotBlank(message="이름은 필수입니다.")
	@Size(max=6)
	private String userName;
	private String password;
	private int    coin;
	//이메일 주소 형식인지 검사
	@Email
	private String email;
	//Future : 현재보다 미래일때 통과
	//Past : 현재보다 과거일때 통과
	@Past(message="생일은 과거날짜만 가능합니다.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date   dateOfBirth;
	//성별
	private String gender;
	//국적
	private String nationality;
	//보유자동차
	private String[] cars;
	//집
	private ArrayList<String> homeList;
	//취미
	private String[] hobbys;
	//개발자 여부
	private String developer;
	//외국인 여부
	private boolean foreigner;
	//주소
	//중첩된 자바빈
	//MemberVO : AddressVO = 1 : 1
	//중첩된 자바빈즈 입력값 검증 : 골뱅이Valid
	@Valid
	private AddressVO addressVO;
	//보유 카드들
	@Valid
	private List<CardVO> cardVOList;
	//자기소개
	private String introduction;
	
	//파일업로드
	private MultipartFile picture;
	
	//다중파일업로드
	private MultipartFile[] pictures;
}






