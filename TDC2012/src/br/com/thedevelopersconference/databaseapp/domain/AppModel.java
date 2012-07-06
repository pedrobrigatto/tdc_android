package br.com.thedevelopersconference.databaseapp.domain;

public class AppModel {
	
	public static String [] PROFESSIONAL_ROLES = new String [] {
		"Developer", "Architect"};
	
	private static AppModel model;
	
	private AppModel() {}
	
	public static AppModel getModel() {
		if (model == null) {
			model = new AppModel();
		}
		return model;
	}

}
