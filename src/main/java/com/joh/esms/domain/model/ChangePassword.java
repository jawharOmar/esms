package com.joh.esms.domain.model;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePassword {

	
	@NotBlank(message = "oldPassword is empty")
	private String oldPassword;

	@NotBlank(message = "newPassword is empty")
	private String newPassword;

	@NotBlank(message = "confirmPassword is empty")
	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePassword [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
