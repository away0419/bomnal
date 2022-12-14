package com.ssafy.web.request;

import java.util.List;

import com.ssafy.web.dto.Answerlist;

import lombok.Getter;

/**아이의 문진표 결과 저장  */
@Getter
public class AnswerRequest {

	//아동아이디
	String child_id;
	
	//응답 (질문번호 : 답 ) 리스트 
	List<Answerlist> answer;
	
}
