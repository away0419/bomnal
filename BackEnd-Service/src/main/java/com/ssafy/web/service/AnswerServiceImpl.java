package com.ssafy.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.web.db.entity.questions.Answer;
import com.ssafy.web.db.repository.AnswerRepository;
import com.ssafy.web.dto.Answerlist;
import com.ssafy.web.request.AnswerRequest;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	AnswerRepository answerRepo;
	
	@Autowired
	WebClient webClient; 
	
	@Override
	public int registAnswer(AnswerRequest answerReq) {
		String childName = answerReq.getChild_name();
		String parentId = answerReq.getParent_id();
		System.out.println(childName+" "+parentId);
		String childId = webClient.get().uri("/child/"+parentId +"/"+childName).retrieve().bodyToMono(String.class)
				.block();
		log.debug("문진표 저장 아동 아이디 : "+childId);
		/*등록하지 않은 아동*/
		if(childId == null) return 0; 
		/*문진 기록 존재*/
		if(answerRepo.findAnswerByChildId(childId)!=null) return 0;
		
		Answer ans = new Answer();
		ans.setChildId(childId);
		
		//응답문항, 점수 
		List<Answerlist> ansLis = answerReq.getAnswer();
		String str="";
		int score1=0;
		int score2=0;
		int score3=0;
		for(int i=0; i<ansLis.size(); i++) {
			int score = ansLis.get(i).getScore();
			str+=ansLis.get(i).getOptions();
			str+=",";
			score1+=score;
			//13~20번 점수
			if(i>=12 && i<=19) score2+=score;
			if(i>=20 && i<=22) score3+=score;
		}
		ans.setAnswer(str);
		ans.setScore1(score1);
		ans.setScore2(score2);
		ans.setScore3(score3);
		
		answerRepo.save(ans);
		return 1;
	}

	
}