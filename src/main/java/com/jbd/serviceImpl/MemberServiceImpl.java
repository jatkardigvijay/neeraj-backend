package com.jbd.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbd.dao.MemberDao;
import com.jbd.entity.Member;
import com.jbd.exception.JbdException;
import com.jbd.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberDao memberDao;

	@Override
	public List<Member> getMemberDetails() throws JbdException {

		logger.info("executing getMemberDetails() method ... ");
		return memberDao.getMembers();
	}

	@Override
	public Member getMemberByMemberId(int memberId) throws JbdException, SQLException {

		logger.info("executing getMemberDetails() method ... ");
		return memberDao.getMemberByMemberId(memberId);
	}

}
