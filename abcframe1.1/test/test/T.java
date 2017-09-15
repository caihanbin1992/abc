package test;

import java.lang.reflect.Method;

import fun.Function;

public class T {

	public static void main(String[] args) {
		Method[] ms = Function.class.getDeclaredMethods();
		for (Method method : ms) {
			System.out.println(method.getName());
		}
	}
}
