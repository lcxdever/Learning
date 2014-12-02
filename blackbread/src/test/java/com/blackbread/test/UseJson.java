package com.blackbread.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class UseJson {
	public void getByJson(@Json(param = "aa",requied=false) String aaa) {
		Method[] ms = UseJson.class.getMethods();
		for (Method m : ms) {
			Annotation[][] as = m.getParameterAnnotations();
			for (Annotation[] a : as) {
				for (Annotation ai : a) {
					if(ai instanceof Json){
						Json j=(Json)ai;
						System.out.println(j.dft());
						System.out.println(j.param());
						System.out.println(j.requied());
					}
					System.out.println(ai.toString());
				}
			}
		}
	}

	public static void main(String[] args) {
		UseJson u = new UseJson();
		u.getByJson("1223");
	}
}
