package com.burakcan.aksoy.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import com.burakcan.aksoy.exception.BookNotFoundException;
import com.burakcan.aksoy.exception.ExceptionMessage;

import feign.Response;
import feign.codec.ErrorDecoder;

//  Feign'in varsayılan hata çözümleme davranışını ezmek (override etmek) üzere oluşturulmuştur. 
public class RetrieveMessageErrorDecoder implements ErrorDecoder {	
	
	private final ErrorDecoder errorDecoder = new Default();
	
	@Override
	public Exception decode(String methodKey, Response response) {
		ExceptionMessage message = null;
		try(InputStream body = response.body().asInputStream()){
			Collection<String> dateHeaders = response.headers().get("date");
			String date = (dateHeaders != null && !dateHeaders.isEmpty()) ? dateHeaders.iterator().next() : java.time.LocalDateTime.now().toString();
			
			message = new ExceptionMessage(date,
					response.status(),HttpStatus.resolve(response.status()).getReasonPhrase(),
					IOUtils.toString(body,StandardCharsets.UTF_8), // burada ilgili servisten gelen hata mesajı çözülür.
					response.request().url());
		}catch (IOException exception) {
			return new Exception(exception.getMessage());
		}
		
		switch (response.status()) {
		case 404:
			throw new BookNotFoundException(message);
			
		default:
			return errorDecoder.decode(methodKey, response);
		}
	}

	
}
