package br.com.rhiemer.api.util.dto;

import javax.persistence.Query;

import br.com.rhiemer.api.util.annotations.entity.ToString;
import br.com.rhiemer.api.util.pojo.PojoAbstract;

/**
 * @author Raphael Jansen
 */
public class Pager extends PojoAbstract {

	private static final long serialVersionUID = 8391720976662130627L;

	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Pager DEFAULT_PAGER = new Pager(1);

	@ToString
	private Integer pageSize;

	@ToString
	private Integer currentPage;

	private Integer totalItens;

	public Pager() {
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	public Pager(final Integer page, final Integer pageSize) {
		super();
		this.currentPage = page;
		this.pageSize = pageSize;
		this.currentPage = new Integer(1);
	}

	public Pager(final Integer page) {
		this(page, DEFAULT_PAGE_SIZE);
	}

	public Pager(final Integer page, final Integer pageSize,
			final Integer totalItens) {
		this(page, pageSize);
		this.totalItens = totalItens;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
		// this.currentPage = 1;
	}

	public Integer getCurrentPage() {
		if (currentPage == null || currentPage.intValue() < 1)
			return 1;
		else if (getTotalPages() != null && currentPage > getTotalPages())
			return getTotalPages();
		else
			return this.currentPage;
	}

	public void setCurrentPage(final Integer currentPage) {
		/*
		 * if (currentPage < 1) { this.currentPage = 1; } else if
		 * (getTotalPages() != null && currentPage > getTotalPages()) {
		 * this.currentPage = getTotalPages(); } else { this.currentPage =
		 * currentPage; }
		 */
		this.currentPage = currentPage;
	}

	public Integer getTotalItens() {
		return this.totalItens;
	}

	public void setTotalItens(final Integer totalItens) {
		this.totalItens = totalItens;
	}

	public Integer getPageLastRow() {
		Integer lastPageRow = this.currentPage * this.pageSize;
		if (this.totalItens != null && this.totalItens < lastPageRow) {
			lastPageRow = this.totalItens;
		}
		return lastPageRow;
	}

	public Integer getPageFirstRow() {
		return this.currentPage * this.pageSize - this.pageSize;
	}

	public Integer getTotalPages() {
		Integer pages = null;
		if (this.totalItens != null && this.totalItens > 0) {
			pages = Math.abs(this.totalItens / this.pageSize);
			if (Math.abs(this.totalItens % this.pageSize) > 0) {
				pages++;
			}
		}
		return pages;

	}

	public void paginarQuery(Query query) {

		if (this.getPageFirstRow() != null
				&& this.getPageFirstRow().intValue() > 0) {
			query.setFirstResult(this.getPageFirstRow());
		}
		if (this.getPageSize() != null && this.getPageSize().intValue() > 0) {
			query.setMaxResults(this.getPageSize());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getName());
		builder.append("{").append("pageFirstRow=[").append(getPageFirstRow())
				.append("],");
		builder.append("pageLastRow=[").append(getPageLastRow()).append(']')
				.append('}');
		return builder.toString();
	}
}
