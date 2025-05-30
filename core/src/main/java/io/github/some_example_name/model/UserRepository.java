package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepository {
    private static final HashMap<String, User> users = new HashMap<>();
    private static final String USERS_FILE = "users.json";

    public static boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) return false;
        users.put(user.getUsername(), user);
        return true;
    }

    public static boolean isUsernameTaken(String username) {
        return users.containsKey(username);
    }

    public static User findUserByUsername(String input) {
        for (String username : users.keySet()) {
            if (username.equalsIgnoreCase(input)) {
                return users.get(username);
            }
        }
        return null;
    }

    public static void deleteUser(User currentUser) {
        users.remove(currentUser.getUsername());
    }

    public static void saveUsers() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(false); // Prevent skipping fields due to prototype comparison

        //ArrayList<User> userList = new ArrayList<>(users.values());
        ArrayList<User> userList = new ArrayList<>();
        for (String username : users.keySet()) {
            if (users.get(username).isGuest)
                continue;
            userList.add(users.get(username));
        }

        FileHandle file = Gdx.files.local(USERS_FILE);
        file.writeString(json.prettyPrint(userList), false);
    }

    public static void loadUsers() {
        FileHandle file = Gdx.files.local(USERS_FILE);
        if (!file.exists()) return;

        Json json = new Json();
        ArrayList<User> userList = json.fromJson(ArrayList.class, User.class, file);
        users.clear();
        for (User user : userList) {
            users.put(user.getUsername(), user);
        }
    }

    public static ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
