package hu.csani.application.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import hu.csani.application.util.Hidden;

// Excludes any field (or class) that is tagged with an "@Hidden"
public class HiddenAnnotationExclusionStrategy implements ExclusionStrategy {
	public boolean shouldSkipClass(Class<?> clazz) {
		return clazz.getAnnotation(Hidden.class) != null;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(Hidden.class) != null;
	}
}