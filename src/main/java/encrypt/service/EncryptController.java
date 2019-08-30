package encrypt.service;

import encryption.data.Message;
import encryption.data.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/")
public class EncryptController {

	private final EncryptService encryptService;

	public EncryptController(EncryptService encryptService) {
		this.encryptService = encryptService;
	}

	@Post("/encrypt/{username}/{text}")
	public HttpResponse<User> addEncryptedMessage(String username, String text) {
		return HttpResponse.created(encryptService.addEncryptedMessage(username, text));
	}

	public HttpResponse<User> addUser(String username) {
		return HttpResponse.created(encryptService.addUser(username));
	}

	@Get("/encrypt/{username}")
	public HttpResponse<List<Message>> getMessagesByUser(String username) {
		User user = encryptService.getMessagesByUser(username);
		if (user == null) {
			return HttpResponse.notFound();
		}
		return HttpResponse.ok(user.getMessageList());
	}

	@Delete("/encrypt/{username}/{text}")
	public HttpResponse<Void> deleteMessage(String username, String text) {
		encryptService.deleteMessage(username, text);
		return HttpResponse.accepted();
	}

	@Get("/encrypt/all")
	HttpResponse<List<User>> getAllUser() {
		return HttpResponse.ok(encryptService.getAllUser());
	}
}
