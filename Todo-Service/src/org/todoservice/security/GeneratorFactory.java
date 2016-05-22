package org.todoservice.security;

public class GeneratorFactory implements GeneratorFactoryMethod{

	private TokenGenerator generator;
	@Override
	public String createEncript(String method, String username, String scode, String coockie) {
		if(method.equals("MD5")){
			generator = new MD5Generator();
		}
		return generator.encript(username, scode, coockie);
	}

	@Override
	public String createEncript(String method, String username, String scode) {
		if(method.equals("MD5")){
			generator = new MD5Generator();
		}
		return generator.encript(username, scode);
	}

}
