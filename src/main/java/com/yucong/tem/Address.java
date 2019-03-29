package com.yucong.tem;

public class Address {

	private Integer id;
	private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "[" + id + "\t" + detail + "]";
	}

}
