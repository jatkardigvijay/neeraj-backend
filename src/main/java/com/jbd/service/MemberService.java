package com.jbd.service;

import java.sql.SQLException;
import java.util.List;

import com.jbd.entity.Member;
import com.jbd.exception.JbdException;

public interface MemberService {

	public List<Member> getMemberDetails() throws JbdException;

	public Member getMemberByMemberId(int memberId) throws JbdException, SQLException;
}
