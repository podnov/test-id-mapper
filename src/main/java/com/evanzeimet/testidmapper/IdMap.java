package com.evanzeimet.testidmapper;

import java.util.HashMap;
import java.util.Map;

public class IdMap<ReferencePersistenceIdType> {

	private final Map<String /* testId */, ReferencePersistenceIdType> ids = new HashMap<>();

	public ReferencePersistenceIdType getPersistenceId(String testId) {
		return ids.get(testId);
	}

	public void putPersistenceId(String testId, ReferencePersistenceIdType persistenceId) {
		ids.put(testId, persistenceId);
	}

}
