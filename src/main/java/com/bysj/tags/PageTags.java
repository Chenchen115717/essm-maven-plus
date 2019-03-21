package com.bysj.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 分页
 */
public class PageTags extends SimpleTagSupport {

	private PageContext context;

	private int pageIndex = 0;
	private int pageSize = 10;
	private int itemTotal;
	private int showPageNum = 3;

	private String cssClass = "pagenav pagenav-02";
	private String pageContentId = "pagertable";

	private boolean onePageIsShow = false;

	public PageContext getContext() {
		return context;
	}

	public void setContext(PageContext context) {
		this.context = context;
	}

	public int getPageIndex() {
		if (pageIndex == 0)
			pageIndex = 1;
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(int itemTotal) {
		this.itemTotal = itemTotal;
	}

	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getPageContentId() {
		return pageContentId;
	}

	public void setPageContentId(String pageContentId) {
		this.pageContentId = pageContentId;
	}

	public boolean isOnePageIsShow() {
		return onePageIsShow;
	}

	public void setOnePageIsShow(boolean onePageIsShow) {
		this.onePageIsShow = onePageIsShow;
	}

	@Override
	public void doTag() throws JspException, IOException {
		context.getOut().print(getPageArea());
		super.doTag();
	}

	@Override
	public void setJspContext(JspContext pc) {
		setContext((PageContext) pc);
		super.setJspContext(pc);
	}

	private String getPageArea() {

		if (getItemTotal() > getPageSize()) {
			setOnePageIsShow(true);
		}
		if (!isOnePageIsShow()) {
			return "";
		}

		int pageTotal = getItemTotal() / getPageSize();
		int remainder = getItemTotal() % getPageSize();
		if (remainder > 0) {
			pageTotal++;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<div class = \"").append(getCssClass()).append("\">");

		int start = getPageIndex() - showPageNum;
		int leftPageNum = start < 1 ? 1 : start;
		int end = getPageIndex() + showPageNum;
		int rightPageNum = end > pageTotal ? pageTotal : end;

		if (getPageIndex() > 1) {
			builder.append("<a href=\"javascript:void(0);\" class=\"page-prev pageclick\" data-page = \"")
					.append(getPageIndex() - 1).append("\">上一页</a>");
		}
		if (leftPageNum > 1) { // ...
			builder.append("<a href=\"javascript:void(0);\" class=\"pageclick\" data-page = \"1\">1</a>");
			builder.append("<i class=\"iconfont\">...</i>");
		}
		for (int i = leftPageNum; i <= rightPageNum; i++) {

			if (getPageIndex() == i) {
				builder.append("<span class=\"active\">").append(i).append("</span>");
				continue;
			}
			builder.append("<a href=\"javascript:void(0);\" class=\"pageclick\" data-page = \"").append(i).append("\">")
					.append(i).append("</a>");
		}
		if (rightPageNum < pageTotal) { // ...
			builder.append("<i class=\"iconfont\">...</i>");
			builder.append("<a href=\"javascript:void(0);\" class=\"pageclick\" data-page = \"").append(pageTotal)
					.append("\">").append(pageTotal).append("</a>");
		}
		if (getPageIndex() < rightPageNum) {
			int next = getPageIndex() + 1;
			if (next > pageTotal)
				next = pageTotal;
			builder.append("<a href=\"javascript:void(0);\" class=\"page-prev pageclick\" data-page = \"").append(next)
					.append("\">下一页</a>");
		}
		builder.append("<span class=\"total\">总共 <strong class=\"total-font\">").append(getItemTotal())
				.append(" </strong>条记录");
		builder.append("</div>");
		return builder.toString();
	}
}
