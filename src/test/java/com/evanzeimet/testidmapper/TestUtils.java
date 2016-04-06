package com.evanzeimet.testidmapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Lf2SpacesIndenter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SuppressWarnings("deprecation")
public class TestUtils {

	private final ObjectMapper objectMapper;

	public TestUtils() {
		objectMapper = new ObjectMapper();
	}

	public String dosToUnix(String dos) {
		String result;

		if (dos == null) {
			result = null;
		} else {
			result = dos.replaceAll("\\r\\n", "\n");
		}

		return result;
	}

	protected ObjectWriter getObjectMapperWriter() {
		DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
		pp.indentArraysWith(new Lf2SpacesIndenter());
		return objectMapper.writer(pp);
	}

	public File getRelativeResource(String relativePath) {
		String packageName = getClass().getPackage().getName();
		String packagePath = packageName.replaceAll("\\.", "/");
		String wholePath = packagePath + "/" + relativePath;

		URL url = Thread.currentThread().getContextClassLoader().getResource(wholePath);

		if (url == null) {
			String message = String.format("File [%s] not found", wholePath);
			throw new RuntimeException(message);
		}

		return new File(url.getPath());
	}

	public String readFile(File file) {
		String result;

		try {
			result = FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public String readRelativeResource(String relativePath) {
		File file = getRelativeResource(relativePath);
		return readFile(file);
	}

	public <T> T readType(String json, Type type) throws JsonParseException,
			JsonMappingException,
			IOException {
		JavaType javaType = objectMapper.constructType(type);
		return objectMapper.readValue(json, javaType);
	}

	public String stringify(Object object) {
		String result = null;
		ObjectWriter writer = getObjectMapperWriter();
		Exception exception = null;

		try {
			result = writer.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			exception = e;
		} catch (JsonMappingException e) {
			exception = e;
		} catch (IOException e) {
			exception = e;
		}

		if (exception != null) {
			throw new RuntimeException("Could not stringify", exception);
		}

		result = dosToUnix(result);

		return result;
	}
}
