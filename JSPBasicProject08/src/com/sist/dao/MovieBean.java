package com.sist.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieBean {

	private int mno,cno,replyCount;
	private String poster,title,director,actor,regdate,grade,
				   genre,nation,time,showUser,
				   boxoffice,story,key;
	private double score;
}
