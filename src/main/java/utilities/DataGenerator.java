package utilities;

import net.datafaker.Faker;

public class DataGenerator {
	
	private static Faker faker = new Faker();
	
	public static String getEmailId() {
		return faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com";
	}
	
	public static String getFullName() {
		return faker.name().fullName();
	}
	
	public static String getNickName() {
		return faker.name().firstName();
	}
	
	public static String getPhoneNumber() {
		return faker.number().digits(10);
	}
	
	public static String getNumber(int digitCount) {
		return faker.number().digits(digitCount);
	}
	
	public static String getDate() {
		return faker.date().birthday("yyyy-MM-dd'T'HH:mm:ss'Z'");
	}
	
	public static String getGender() {
		return faker.gender().binaryTypes();
	}
	
	public static String getRelationship() {
		return faker.relationships().any();
	}
	
	public static String getContactAddressType() {
		return faker.house().room();
	}
	
	public static String getContactAddressLine1() {
		return faker.address().city();
	}
	
	public static String getContactAddressLine2() {
		return faker.address().citySuffix();
	}
	
	public static String getPincode() {
		return faker.address().postcode();
	}
	
	public static String getCountry() {
		return faker.address().country();
	}
	
	public static String getPrimaryCountryCode() {
		return faker.address().countryCode();
	}
	
	public static String getLanguage() {
		return faker.programmingLanguage().name();
	}
	
	public static String getRandonText() {
		return faker.text().text(7);
	}
	
	public static boolean getFlag() {
		return faker.random().nextBoolean();
	}

}
