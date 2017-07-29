package com.application.taglib.pagination;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.application.constants.AppConstant;

public class PaginateTag extends TagSupport {

	Integer total;
	String action;
	String controller;

	String prev;
	String next;

	Boolean omitPrev = false;
	Boolean omitNext = false;
	Boolean omitFirst = false;
	Boolean omitLast = false;

	Integer max = 10;
	Integer maxsteps = 10;
	Integer offset = 0;

	@Override
	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();

		if (total == null)
			throw new IllegalArgumentException("Total is mission.");

		System.out.println("total: " + total);
		System.out.println("offset: " + offset);
		System.out.println("max: " + max);

		Boolean steps = maxsteps > 0;

		Integer currentstep = ((offset / max)) + 1;
		Integer firststep = 1;
		Integer laststep = new Long(Math.round(Math.ceil(total / max))).intValue();

		// display previous link when not on firststep unless omitPrev is true
		if (currentstep > firststep && !omitPrev) {
			// writeLink(out, "Previous");
			Map<String, Object> pp = new HashMap<String, Object>();
			pp.put("offset", offset - max);
			pp.put("max", getMax());
			writeLink(out, new String("<a class=\'prevLink\' href=\'" + generateLink(pp) + "\'>Previous</a>"));
		}

		// display steps when steps are enabled and laststep is not firststep
		if (steps && laststep > firststep) {
			// linkTagAttrs.put('class', 'step')

			// determine begin and endstep paging variables
			Integer beginstep = currentstep - new Long(Math.round(maxsteps / 2.0d)).intValue() + (maxsteps % 2);
			Integer endstep = currentstep + new Long(Math.round(maxsteps / 2.0d)).intValue() - 1;
			// System.out.println("beginstep:> " + beginstep);
			// System.out.println("endstep:> " + endstep);
			if (beginstep < firststep) {
				beginstep = firststep;
				endstep = maxsteps;
			}
			if (endstep > laststep) {
				beginstep = laststep - maxsteps + 1;
				if (beginstep < firststep) {
					beginstep = firststep;
				}
				endstep = laststep;
			}
			// System.out.println("===============");
			// System.out.println("beginstep:> " + beginstep);
			// System.out.println("endstep:> " + endstep);
			// display firststep link when beginstep is not firststep
			if (beginstep > firststep && !omitFirst) {
				// linkParams.offset = 0
				// writer << callLink((Map)linkTagAttrs.clone()) {firststep.toString()}
				// writeLink(out, firststep.toString());

				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("offset", 0);
				pp.put("max", getMax());
				writeLink(out, new String("<a href=\'" + generateLink(pp) + "\'>" + firststep.toString() + "</a>"));
			}
			// show a gap if beginstep isn't immediately after firststep, and if were not
			// omitting first or rev
			if (beginstep > firststep + 1 && (!omitFirst || !omitPrev)) {
				// writer << '<span class="step gap">..</span>'
				writeLink(out, "<span class='step gap'>..</span>");
			}

			for (Integer i = beginstep; i <= endstep; i++) {
				if (currentstep == i) {
					// writer << "<span class=\"currentStep\">${i}</span>"
					// System.out.println("I:> " + i);
					writeLink(out, "<span class=\'currentStep\'>" + i + "</span>");

				} else {
					// setOffset(i - 1 * max);
					// System.out.println("II:> " + i);
					// writeLink(out, i.toString());

					Map<String, Object> pp = new HashMap<String, Object>();
					pp.put("offset", (i - 1) * max);
					pp.put("max", getMax());
					writeLink(out, new String("<a href=\'" + generateLink(pp) + "\'>" + i.toString() + "</a>"));
				}
			}

			// show a gap if beginstep isn't immediately before firststep, and if were not
			// omitting first or rev
			if (endstep + 1 < laststep && (!omitLast || !omitNext)) {
				// writer << '<span class="step gap">..</span>'
				writeLink(out, "<span class='step gap'>..</span>");
			}
			// display laststep link when endstep is not laststep
			if (endstep < laststep && !omitLast) {
				// linkParams.offset = (laststep - 1) * max
				// setOffset(laststep -1 * max);
				// writer << callLink((Map)linkTagAttrs.clone()) { laststep.toString() }
				// writeLink(out, laststep.toString());

				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("offset", laststep - 1 * max);
				pp.put("max", getMax());
				writeLink(out, new String("<a href=\'" + generateLink(pp) + "\'>" + laststep.toString() + "</a>"));
			}
		}

		// display next link when not on laststep unless omitNext is true
		if (currentstep < laststep && !omitNext) {
			// linkTagAttrs.put('class', 'nextLink')
			// linkParams.offset = offset + max
			// writer << callLink((Map)linkTagAttrs.clone()) {
			// (attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null,
			// messageSource.getMessage('default.paginate.next', null, 'Next', locale),
			// locale))
			// }
			// writeLink(out, "Next");

			Map<String, Object> pp = new HashMap<String, Object>();
			pp.put("offset", offset + max);
			pp.put("max", getMax());
			writeLink(out, new String("<a href=\'" + generateLink(pp) + "\'>Next</a>"));
		}

		return SKIP_BODY;

	}

	public void writeLink(JspWriter out, String link) {
		try {
			out.write(link);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String generateLink(Map<String, Object> map) {

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponentsBuilder.scheme(pageContext.getRequest().getScheme());
		uriComponentsBuilder.port(pageContext.getRequest().getServerPort());
		uriComponentsBuilder.host(pageContext.getRequest().getServerName());
		if (getController() == null && getAction() == null) {
			uriComponentsBuilder
					.path(pageContext.getRequest().getAttribute("javax.servlet.forward.request_uri").toString());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(pageContext.getRequest().getServletContext().getContextPath());
			if (getController() != null)
				sb.append("/").append(getController());
			if (getAction() != null)
				sb.append("/").append(getAction());

			uriComponentsBuilder.path(sb.toString());
		}
		for (String key : map.keySet()) {
			uriComponentsBuilder.queryParam(key, map.get(key));
		}
		return uriComponentsBuilder.build().encode().toUriString();

	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public Boolean getOmitPrev() {
		return omitPrev;
	}

	public void setOmitPrev(Boolean omitPrev) {
		this.omitPrev = omitPrev;
	}

	public Boolean getOmitNext() {
		return omitNext;
	}

	public void setOmitNext(Boolean omitNext) {
		this.omitNext = omitNext;
	}

	public Boolean getOmitFirst() {
		return omitFirst;
	}

	public void setOmitFirst(Boolean omitFirst) {
		this.omitFirst = omitFirst;
	}

	public Boolean getOmitLast() {
		return omitLast;
	}

	public void setOmitLast(Boolean omitLast) {
		this.omitLast = omitLast;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMaxsteps() {
		return maxsteps;
	}

	public void setMaxsteps(Integer maxsteps) {
		this.maxsteps = maxsteps;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
