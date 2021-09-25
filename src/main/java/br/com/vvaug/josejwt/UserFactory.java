package br.com.vvaug.josejwt;

import br.com.vvaug.josejwt.bean.User;

public class UserFactory {

	public static User getUser() {
		return User.builder()
				.name("User")
				.email("user@user.com")
				.document("123.456.789-01")
				.build();
	}
}
