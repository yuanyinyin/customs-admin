package com.nteport.admin.util;

public class AliMessageResult {
	//{"RequestId":"CA309717-6A79-5228-AD71-DBBDF8444107","Message":"OK","BizId":"280906043091714120^0","Code":"OK"}
	private String RequestId;
	private String Message;
	private String BizId;
	private String Code;
	public String getRequestId() {
		return RequestId;
	}
	public void setRequestId(String requestId) {
		RequestId = requestId;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getBizId() {
		return BizId;
	}
	public void setBizId(String bizId) {
		BizId = bizId;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	
}
