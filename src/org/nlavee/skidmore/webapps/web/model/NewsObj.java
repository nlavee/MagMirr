package org.nlavee.skidmore.webapps.web.model;

import java.util.Arrays;
import java.util.Date;

public class NewsObj {
	
	public String title;
	public String NewsAbstract;
	public String url;
	public Date publishedTime;
	public String urlMultimedia;
	public String section;
	public String[] tags;
	
	public NewsObj(String title, String newsAbstract, String url,
			Date publishedTime, String urlMultimedia, String section,
			String[] tags) {
		this.title = title;
		this.NewsAbstract = newsAbstract;
		this.url = url;
		this.publishedTime = publishedTime;
		this.urlMultimedia = urlMultimedia;
		this.section = section;
		this.tags = tags;
	}

	public NewsObj() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewsAbstract() {
		return NewsAbstract;
	}

	public void setNewsAbstract(String newsAbstract) {
		NewsAbstract = newsAbstract;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(Date publishedTime) {
		this.publishedTime = publishedTime;
	}

	public String getUrlMultimedia() {
		return urlMultimedia;
	}

	public void setUrlMultimedia(String urlMultimedia) {
		this.urlMultimedia = urlMultimedia;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", NewsAbstract=" + NewsAbstract
				+ ", url=" + url + ", publishedTime=" + publishedTime
				+ ", urlMultimedia=" + urlMultimedia + ", section=" + section
				+ ", tags=" + Arrays.toString(tags) + "]";
	}
	
	
	
	
	
}
