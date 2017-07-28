package com.application.taglib.pagination;

import org.springframework.stereotype.Component;

@Component
public class Paginator {

	Integer max = 10;
	Integer offset = 0;
	
	
	public Paginator() {}

	public Paginator(Integer max, Integer offset) {
		this.max = max;
		this.offset = offset;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
