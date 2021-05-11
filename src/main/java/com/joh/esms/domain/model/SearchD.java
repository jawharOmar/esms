package com.joh.esms.domain.model;

public class SearchD {

	private Integer id;
	private String keyword;
	private String secondKeyword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSecondKeyword() {
		return secondKeyword;
	}

	public void setSecondKeyword(String secondKeyword) {
		this.secondKeyword = secondKeyword;
	}

	@Override
	public String toString() {
		return "SearchD [id=" + id + ", keyword=" + keyword + ", secondKeyword=" + secondKeyword + "]";
	}

}
