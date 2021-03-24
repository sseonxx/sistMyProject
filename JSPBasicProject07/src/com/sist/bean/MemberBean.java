package com.sist.bean;

import lombok.Getter;
import lombok.Setter;

/*
 * 네트워크 통신
 * 브라우저 (클라이언트) <==========> 톰캣(server) C/S (client Server)
 			결과값 => 데이터가 많은 경우에 클래스로 묶어서 한번에 전송
 			JSP : Bean
 				=변수 (private) , 읽기 / 쓰기 => getter/setter
 				
 			MyBatis : DTO 
 			Spring : VO
 			==================캡슐화 (데이터 보호:Secure Coding)
 */
@Getter
@Setter
public class MemberBean {
	private String id; // isAdmin() ,setAdmin()
	private String name;
	private String addr;
	private String tel;
	private String sex;
	private boolean admin;
	
}
