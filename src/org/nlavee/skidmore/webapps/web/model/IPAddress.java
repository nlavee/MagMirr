package org.nlavee.skidmore.webapps.web.model;

public class IPAddress {
	String ipAddress;

	public IPAddress(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "IPAddress [ipAddress=" + ipAddress + "]";
	}
	
	
}
