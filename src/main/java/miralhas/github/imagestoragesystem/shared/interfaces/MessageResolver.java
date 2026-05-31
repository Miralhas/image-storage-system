package miralhas.github.imagestoragesystem.shared.interfaces;

public interface MessageResolver {
	String get(String code);
	String get(String code, Object... args);
}
