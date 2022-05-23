package com.itbaizhan.ORM;

import java.io.Serializable;
import java.util.Date;

/**
 * Leaveword generated by MyEclipse Persistence Tools
 */

public class Leaveword implements Serializable {

	// Fields

	private Integer id;

	private Member member;

	private Admin admin;

	private String title;

	private String content;

	private Date leaveDate;

	private String answerContent;

	private Date answerDate;

	// Constructors

	/** default constructor */
	public Leaveword() {
	}

	/** minimal constructor */
	public Leaveword(Member member) {
		this.member = member;
	}

	/** full constructor */
	public Leaveword(Member member, Admin admin, String title,
			String content, Date leaveDate, String answerContent,
			Date answerDate) {
		this.member = member;
		this.admin = admin;
		this.title = title;
		this.content = content;
		this.leaveDate = leaveDate;
		this.answerContent = answerContent;
		this.answerDate = answerDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getAnswerContent() {
		return this.answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Date getAnswerDate() {
		return this.answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

}