package com.jbd.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbd.config.Response;
import com.jbd.entity.Member;
import com.jbd.exception.JbdException;
import com.jbd.service.MemberService;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/member/api")
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@GetMapping("/v1/members")
	public ResponseEntity<Response> getMemberDetailsList() throws JbdException {

		List<Member> memberList = memberService.getMemberDetails();

		if (memberList == null || memberList.isEmpty()) {
			logger.error("Exception occured with message no data found");
			throw new JbdException("No data found", HttpStatus.OK, memberList);
		}
		logger.info("Received the list of members with size : " + memberList.size());
		return new ResponseEntity<Response>(new Response("success", memberList, null), HttpStatus.OK);

	}

	@GetMapping("/v1/{memberId}")
	public ResponseEntity<Response> getMemberbyMemberId(
			@PathVariable(value = "memberId", required = false) @Min(value = 1, message = "memberId should not be less than 1") int memberId)
			throws JbdException, SQLException {

		Member member = memberService.getMemberByMemberId(memberId);

		if (member == null) {

			logger.error("Exeception occured with no member found with id : " + memberId);
			throw new JbdException("No data found", HttpStatus.OK, member);
		} else {

			logger.info("Received member with id : " + memberId);
			return new ResponseEntity<Response>(new Response("success", member, null), HttpStatus.OK);
		}

	}
	
}
