package kr.or.ddit.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//PoJo(Plain Old Java Object)에 위배되지만 편하므로 일단 씀
@Data
public class BookInfoVO {
	private int    rnum;
	private String author;
	private String bookId;
	private String category;
	private String condition;
	private String description;
	private String name;
	private String publisher;
	private String releaseDate;
	private int totalPages;
	private int unitPrice;
	private int unitsInStock;
	//<input type="file" name="bookImage"
	private MultipartFile bookImage;
	
	//BookInfoVO : AttachVO = 1 : N
	private List<AttachVO> attachVOList;
}





