package com.sist.dao;
// 오라클 (row,record)
// DTO 

import java.util.*;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BoardVO {
   private int no,hit;
   private String name,subject,content,pwd;// 기본 게시물
   private int group_id,group_step,group_tab;// 답변표시
   private int root,depth; // 삭제용
   private Date regdate;
}