package com.ssafy.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.web.db.entity.Consult;
import com.ssafy.web.db.entity.ConsultPersonName;
import com.ssafy.web.db.repository.ConsultPersonNameRepository;
import com.ssafy.web.db.repository.ConsultRepository;
import com.ssafy.web.model.response.ConsultResponse;
import com.ssafy.web.model.response.ConsultTotalResponse;
import com.ssafy.web.request.ConsultRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl implements ConsultService{
	private final WebClient webClient;
	private final ConsultRepository conrepo;
	private final ConsultPersonNameRepository cpnamerepo;
	
	@Override
	public int createRoom(ConsultRequest conreq) {
		
		String childName = webClient.get().uri("/info/child/"+conreq.getChildId()).retrieve().
				bodyToMono(String.class).block();
		String theraName = webClient.get().uri("/info/thera/"+conreq.getTheraId()).retrieve().
				bodyToMono(String.class).block();
		String parentName = webClient.get().uri("/info/parent/"+conreq.getParentId()).retrieve().
				bodyToMono(String.class).block();
		
		Consult consult = conreq.toCreateEntity();
		ConsultPersonName cpname = new ConsultPersonName(consult, theraName, childName, parentName);
		conrepo.save(consult);
		cpnamerepo.save(cpname);
		
		return consult.getConsultNo();
	}

	@Override
	@Transactional
	public void updateMemo(ConsultRequest conreq) {
		Consult consult = conrepo.findByConsultNo(conreq.getConsultNo());
		consult.updateMemo(conreq.getMemo());
	}
	
	@Override
	@Transactional
	public void updateRecord(ConsultRequest conreq) {
		Consult consult = conrepo.findByConsultNo(conreq.getConsultNo());
		consult.updateRecord(conreq.getRecord());
	}

	@Override
	public ConsultResponse findByConsultNo(int ConsultNo) {
		Consult consult = conrepo.findByConsultNo(ConsultNo);
		ConsultResponse consultResponse = ConsultResponse.fromEntity(consult);
		return consultResponse;
	}

	@Override
	public List<ConsultTotalResponse> findByTheraIdAndChildId(String theraId, String childId, Pageable pageable) {
		List<ConsultResponse> list = conrepo.findByTheraIdAndChildIdjpql(theraId, childId, pageable);
		List<ConsultTotalResponse> ctrList = new ArrayList<ConsultTotalResponse>();
		for(int i=0; i<list.size(); i++) {
			ConsultResponse cr = list.get(i);
			ctrList.add(new ConsultTotalResponse(cr,cpnamerepo.findByConsult_ConsultNo(cr.getConsultNo())));
		}
		return ctrList;
	}

	@Override
	public List<ConsultTotalResponse> findByTheraId(String theraId,Pageable pageable) {
		List<ConsultResponse> list = conrepo.findByTheraIdjpql(theraId, pageable);
		List<ConsultTotalResponse> ctrList = new ArrayList<ConsultTotalResponse>();
		for(int i=0; i<list.size(); i++) {
			ConsultResponse cr = list.get(i);
			ctrList.add(new ConsultTotalResponse(cr,cpnamerepo.findByConsult_ConsultNo(cr.getConsultNo())));
		}
		return ctrList;
	}

	@Override
	public List<ConsultTotalResponse> findByParentIdAndChildId(String parentId, String childId, Pageable pageable) {
		List<ConsultResponse> list = conrepo.findByParentIdAndChildIdjpql(parentId, childId,pageable);
		List<ConsultTotalResponse> ctrList = new ArrayList<ConsultTotalResponse>();
		for(int i=0; i<list.size(); i++) {
			ConsultResponse cr = list.get(i);
			ctrList.add(new ConsultTotalResponse(cr,cpnamerepo.findByConsult_ConsultNo(cr.getConsultNo())));
		}
		return ctrList;
	}
	
	@Override
	public List<ConsultTotalResponse> findByParentId(String parentId, Pageable pageable) {
		List<ConsultResponse> list = conrepo.findByParentIdjpql(parentId, pageable);
		List<ConsultTotalResponse> ctrList = new ArrayList<ConsultTotalResponse>();
		for(int i=0; i<list.size(); i++) {
			ConsultResponse cr = list.get(i);
			ctrList.add(new ConsultTotalResponse(cr,cpnamerepo.findByConsult_ConsultNo(cr.getConsultNo())));
		}
		return ctrList;
	}



	@Override
	public int countByTheraIdBychildId(String theraId, String childId) {
		int res= conrepo.countAllByTheraIdAndChildId(theraId, childId);
		return res;
	}

	@Override
	public int countByParentIdBychildId(String parentId, String childId) {
		// TODO Auto-generated method stub
		int res= conrepo.countAllByParentIdAndChildId(parentId, childId);
		return res;
	}
	
	

}
