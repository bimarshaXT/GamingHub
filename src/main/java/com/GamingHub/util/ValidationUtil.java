package com.GamingHub.util;

import com.GamingHub.config.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

public class ValidationUtil {

	// 1. Validate if a field is null or empty
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	// 2. Validate if a string contains only letters
	public static boolean isAlphabetic(String value) {
		return value != null && value.matches("^[a-zA-Z]+$");
	}

//. Validate names: only alphabetic, no spaces, no digits
	public static boolean isValidName(String name) {
		return name != null && name.matches("^[A-Za-z]{2,}$");
	}

	// 3. Validate if a string starts with a letter and is composed of letters and
	// numbers
	public static boolean isAlphanumericStartingWithLetter(String value) {
		return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
	}

	// 4. Validate if a string is "male" or "female" (case insensitive)
	public static boolean isValidGender(String value) {
		return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
	}

	// 5. Validate if a string is a valid email address
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return email != null && Pattern.matches(emailRegex, email);
	}

	// 6. Validate if a number is of 10 digits and starts with 98
	public static boolean isValidPhoneNumber(String number) {
		return number != null && number.matches("^98\\d{8}$");
	}

	// 7. Validate if a password is composed of at least 1 capital letter, 1 number,
	// and 1 symbol
	public static boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		return password != null && password.matches(passwordRegex);
	}

	// 8. Validate if a Part's file extension matches with image extensions (jpg,
	// jpeg, png, gif)
	public static boolean isValidImageExtension(Part imagePart) {
		if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
			return false;
		}
		String fileName = imagePart.getSubmittedFileName().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
				|| fileName.endsWith(".gif") || fileName.endsWith(".webp");
	}

	// 10. Validate if the date of birth is at least 16 years before today
	public static boolean isAgeAtLeast16(LocalDate dob) {
		if (dob == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return Period.between(dob, today).getYears() >= 16;
	}

	// 11. Validate product name (text, max 100 chars)
	public static boolean isValidProductName(String name) {
		return name != null && name.length() <= 100;
	}

	// 12. Validate product description (text, max 255 chars)
	public static boolean isValidProductDescription(String description) {
		return description != null && description.length() <= 255;
	}

	// 13. Validate price (starts with $, float, e.g. $123.45)
	public static boolean isValidPrice(String price) {
		return price != null && price.matches("^\\d+(\\.\\d{1,2})?$");
	}

	// 14. Validate stock quantity (strictly numeric)
	public static boolean isValidStockQuantity(String quantity) {
		return quantity != null && quantity.matches("^\\d+$");
	}

	// 15. Validate brand (varchar, max 50 chars)
	public static boolean isValidBrand(String brand) {
		return brand != null && brand.length() <= 50;
	}

	// 16. Validate discount (e.g. 10%, 15.5%)
	public static boolean isValidDiscount(String discount) {
		return discount != null && discount.matches("^\\d+(\\.\\d{1,2})?$");
	}

	// 17. Validate category ID (dropdown, must not be null or empty)
	public static boolean isValidCategoryId(String categoryId) {
		return !isNullOrEmpty(categoryId) && categoryId.matches("^\\d+$");
	}
}
