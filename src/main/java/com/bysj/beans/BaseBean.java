package com.bysj.beans;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

public class BaseBean {

	@TableField(exist = false)
	private Integer subprice;
	@TableField(exist = false)
	private String username;
	@TableField(exist = false)
	private String categoryname;
	@TableField(exist = false)
	private Integer number;
	@TableField(exist = false)
	private String title;
	@TableField(exist = false)
	private Integer price;
	@TableField(exist = false)
	private Integer saleAllNumber;
	@TableField(exist = false)
	private String imagepath;
	@TableField(exist = false)
	private String createdate;
	@TableField(exist = false)
	private String commentname;
	@TableField(exist = false)
	private String replayname;
	@TableField(exist = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Integer getSaleAllNumber() {
		return saleAllNumber;
	}

	public void setSaleAllNumber(Integer saleAllNumber) {
		this.saleAllNumber = saleAllNumber;
	}

	public String getReplayname() {
		return replayname;
	}

	public void setReplayname(String replayname) {
		this.replayname = replayname;
	}

	public String getCommentname() {
		return commentname;
	}

	public void setCommentname(String commentname) {
		this.commentname = commentname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Integer getSubprice() {
		return subprice;
	}

	public void setSubprice(Integer subprice) {
		this.subprice = subprice;
	}

}
