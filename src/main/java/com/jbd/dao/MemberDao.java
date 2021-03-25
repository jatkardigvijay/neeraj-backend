package com.jbd.dao;

import java.sql.SQLException;
import java.util.List;

import com.jbd.entity.Member;
import com.jbd.exception.JbdException;

public interface MemberDao {

	public List<Member> getMembers() throws JbdException;

	public Member getMemberByMemberId(int memberId) throws JbdException, SQLException;
}
