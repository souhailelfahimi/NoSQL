package com.projectHbase.javaHbase.bean;

public class Question {
	 private String id;
	 private String _id_user;
	 private String titre;
	 private String description;
	 
	 public String getDescription() {
		return description;
	}
	 public String getId() {
		return id;
	}
	 public String getTitre() {
		return titre;
	}
	 public void setDescription(String description) {
		this.description = description;
	}
	 public void setId(String id) {
		this.id = id;
	}
	 public void setTitre(String titre) {
		this.titre = titre;
	}
	public String get_id_user() {
		return _id_user;
	}
	 public void set_id_user(String _id_user) {
		this._id_user = _id_user;
	}
}
