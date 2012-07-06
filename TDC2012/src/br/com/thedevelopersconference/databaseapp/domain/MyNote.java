package br.com.thedevelopersconference.databaseapp.domain;

/**
 * A draft the user has entered about a specific speech.
 * 
 * @author pedrobrigatto
 */
public class MyNote {
	
	private Speech speech;
	private String body;
	
	public MyNote() {}
	
	public MyNote (String body) {
		this.body = body;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public Speech getSpeech() {
		return speech;
	}

	public void setSpeech(Speech speech) {
		this.speech = speech;
	}
}
