package ngm.hoang.provider;

public interface Hasher {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
