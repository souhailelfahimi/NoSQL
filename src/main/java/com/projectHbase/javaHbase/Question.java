package com.projectHbase.javaHbase;

public class Question {
	 private String id;
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
}
