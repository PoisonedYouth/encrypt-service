package encrypt.service;

import encryption.data.Message;
import encryption.data.User;
import io.micronaut.runtime.server.EmbeddedServer;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class EncryptService {

	private final MongoService mongoService;
	private Datastore dataStore;

	public EncryptService(MongoService mongoService) {
		this.mongoService = mongoService;
		this.dataStore = mongoService.getDataStore();
	}

	public User addEncryptedMessage(String username, String text) {
		User user = findUserByName(username);

		String encrypted = new StringBuilder(text).reverse().toString();
		Message message = new Message(text, encrypted);
		dataStore.save(message);

		if (user == null) {
			User newUser = new User(username, Collections.singletonList(message));
			dataStore.save(newUser);
			return newUser;
		} else {
			user.getMessageList().add(message);
			return updateUser(user);
		}
	}

	public void deleteMessage(String username, String text) {
		User user = findUserByName(username);
		if (user == null) {
			return;
		}
		Optional<Message> existingMessage = user.getMessageList().stream().filter(m -> !m.getText().equals(text)).findFirst();
		existingMessage.ifPresent(message -> dataStore.delete(message));
	}

	private User findUserByName(String username) {
		Query<User> userQuery = dataStore.find(User.class).filter("username", username);
		return userQuery.get();
	}

	private User updateUser(User user) {
		UpdateOperations ops = dataStore.createUpdateOperations(User.class).set("messageList", user.getMessageList());
		dataStore.update(user, ops);
		return user;
	}

	public User getMessagesByUser(String username) {
		return findUserByName(username);
	}

	public User addUser(String username) {
		User user = findUserByName(username);
		if (user == null) {
			user = new User(username, new ArrayList<>());
			dataStore.save(user);
		}
		return user;
	}

	public List<User> getAllUser() {
		return dataStore.createQuery(User.class).asList();
	}
}
