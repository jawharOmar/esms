package com.joh.esms.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class MessageResourceComparator {
	public static void main(String[] args) throws IOException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		Properties arabic = new Properties();

		InputStream aInput = classLoader.getResourceAsStream("messages_ar.properties");
		arabic.load(new InputStreamReader(aInput, Charset.forName("UTF-8")));

		Properties kurdish = new Properties();

		InputStream kInput = classLoader.getResourceAsStream("messages_ar_SY.properties");
		kurdish.load(new InputStreamReader(kInput, Charset.forName("UTF-8")));

		@SuppressWarnings("unchecked")
		Enumeration<String> enums = (Enumeration<String>) arabic.propertyNames();

		@SuppressWarnings("unchecked")
		Enumeration<String> kurdishem = (Enumeration<String>) kurdish.propertyNames();

		List<String> arabicKeys = new ArrayList<>();
		List<String> kurdishKeys = new ArrayList<>();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			arabicKeys.add(key);
		}

		while (kurdishem.hasMoreElements()) {
			String key = kurdishem.nextElement();
			kurdishKeys.add(key);
		}

		for (String arb : arabicKeys) {
			Optional<String> findAny = kurdishKeys.stream().filter(e -> e.equals(arb)).findAny();
			if (!findAny.isPresent()) {
				System.out.println(arb + "=" + arabic.getProperty(arb));
			}
		}

	}
}
