package org.nlavee.skidmore.webapps.database.beans;

import java.util.Date;

public class Message {

	private String body;
	private Date time;
	
	public Message(String body, Date date) {
		this.body = body;
		this.time = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Message [body=" + body + ", time=" + time + "]";
	}
	
	
}
