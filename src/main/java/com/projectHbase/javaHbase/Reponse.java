package com.projectHbase.javaHbase;

public class Reponse {
	private String id;
	private String text;
	private String votes;
	private String user_id;
	private String question_id;
	
	
	public Reponse(String id, String text, String votes, String user_id, String question_id) {
		super();
		this.id = id;
		this.text = text;
		this.votes = votes;
		this.user_id = user_id;
		this.question_id = question_id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getVotes() {
		return votes;
	}
	public void setVotes(String votes) {
		this.votes = votes;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	
	
	
	
}
