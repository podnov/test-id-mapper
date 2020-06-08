package com.evanzeimet.testidmapper;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
		pp.indentArraysWith(SYSTEM_LINEFEED_INSTANCE);
		return objectMapper.writer(pp);
	}

	public File getRelativeResource(Class<?> clazz, String relativePath) {
		String packageName = clazz.getPackage().getName();
		String packagePath = packageName.replaceAll("\\.", "/");
		String wholePath = packagePath + "/" + relativePath;

		URL url = Thread.currentThread().getContextClassLoader().getResource(wholePath);

		if (url == null) {
			String message = String.format("File [%s] not found", wholePath);
			throw new RuntimeException(message);
		}

		return new File(url.getPath());
	}

	public <T> T objectify(String json, Type type) throws JsonParseException,
			JsonMappingException,
			IOException {
		JavaType javaType = objectMapper.constructType(type);
		return objectMapper.readValue(json, javaType);
	}

	public String readFile(File file) {
		String result;

		try {
			result = FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		result = dosToUnix(result);

		return result;
	}

	public String readRelativeResource(Class<?> clazz, String relativePath) {
		File file = getRelativeResource(clazz, relativePath);
		return readFile(file);
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
