package com.ssafy.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.db.repository.QuestionRepository;
import com.ssafy.web.request.AnswerRequest;
import com.ssafy.web.service.AnswerService;
import com.ssafy.web.service.RedisService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
	@Autowired
	RedisService redisService;

	@Autowired
	AnswerService answerService;
	
	@Autowired
	QuestionRepository ques;

	//문진표 응답 저장
	@PostMapping
	public ResponseEntity<?> getAnswers(@RequestBody AnswerRequest answerReq){
		int res = answerService.registAnswer(answerReq);
		if(res==1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);			
		}
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
}
