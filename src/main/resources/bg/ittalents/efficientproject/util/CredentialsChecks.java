package bg.ittalents.efficientproject.util;

import java.util.regex.Pattern;

public class CredentialsChecks {

	public static boolean isPaswordStrong(String pass) {
		boolean letter = false;
		boolean number = false;

		for (int i = 0; i < pass.length(); i++) {
			if (letter == false && pass.charAt(i) >= 'A' && pass.charAt(i) <= 'z') {
				letter = true;
			}
			if (number == false && pass.charAt(i) >= '0' && pass.charAt(i) <= '9') {
				number = true;
			}

			if (number && letter && pass.length() > 4) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMailValid(String mail) {

		final Pattern pat = Pattern.compile(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

		return pat.matcher(mail).matches();
	}

}
