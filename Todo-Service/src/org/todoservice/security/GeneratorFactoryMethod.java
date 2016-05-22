package org.todoservice.security;

public interface GeneratorFactoryMethod {
	String createEncript(String method, String username, String scode, String coockie);
	String createEncript(String method, String username, String scode);
}
