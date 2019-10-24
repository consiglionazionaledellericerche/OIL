/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package it.cnr.helpdesk.MailManagement.valueobjects;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

public class MailItem implements Serializable {
	private String sender;
	private Collection receivers;
	private String subject;
	private String content;
	private String instance;
	private byte[] attachment;
	private boolean hasAttachment = false;
	private String fileName;
	
	public Collection getReceivers() {
		return receivers;
	}
	public void setReceivers(Collection receivers) {
		this.receivers = receivers;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public MailItem (Collection receivers, String subject, String content, String instance) {
		super();
		setReceivers(receivers);
		setSubject(subject);
		setContent(content);
		setInstance(instance);
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
		this.hasAttachment = true;
	}
	public boolean hasAttachment() {
		return hasAttachment;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
