package com.jbd.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.jbd.dao.MemberDao;
import com.jbd.entity.Member;
import com.jbd.exception.JbdException;
import com.jbd.utils.JbdConstants;

@Repository
public class MemberDaoImpl implements MemberDao {

	private Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);

	@Autowired
	private DataSource dataSource;

	public List<Member> getMembers() throws JbdException {

		List<Member> memberList = new ArrayList<>();

		PreparedStatement ps = null;

		try (Connection connection = dataSource.getConnection()) {

			ps = connection.prepareStatement(JbdConstants.StoredProcedure.GET_ALL_MEMBERS);

			logger.info("Executing query : " + JbdConstants.StoredProcedure.GET_ALL_MEMBERS);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Member member = new Member(rs.getInt("MemberId"), rs.getString("MemberFirstName"),
						rs.getString("MemberMiddleName"), rs.getString("MemberLastName"), rs.getString("MemberEmail"));

				memberList.add(member);
			}

		} catch (Exception e) {
			logger.error("Exception occured while executing query : " + JbdConstants.StoredProcedure.GET_ALL_MEMBERS);
			e.printStackTrace();
			throw new JbdException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage());
				e2.printStackTrace();
			}
		}

		return memberList;
	}

	@Override
	public Member getMemberByMemberId(int memberId) throws JbdException, SQLException {

		CallableStatement cb = null;

		try (Connection connection = dataSource.getConnection()) {

			cb = connection.prepareCall(JbdConstants.StoredProcedure.GET_MEMBER_BY_MEMBER_ID);

			cb.setInt(1, memberId);

			logger.info("Executing stored procedure : " + JbdConstants.StoredProcedure.GET_MEMBER_BY_MEMBER_ID);

			ResultSet rs = cb.executeQuery();

			if (rs.next()) {

				Member member = new Member(rs.getInt("MemberId"), rs.getString("MemberFirstName"),
						rs.getString("MemberMiddleName"), rs.getString("MemberLastName"), rs.getString("MemberEmail"));

				return member;
			} else {
				throw new JbdException("Member for given id=" + memberId + " does not exists.", HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Error occured while executing stored procedure : "
					+ JbdConstants.StoredProcedure.GET_MEMBER_BY_MEMBER_ID);
			e.printStackTrace();
			throw new JbdException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

		} finally {
			if (cb != null) {
				cb.close();
			}
		}

	}

}
