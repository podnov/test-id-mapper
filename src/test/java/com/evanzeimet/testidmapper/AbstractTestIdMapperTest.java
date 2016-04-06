package com.evanzeimet.testidmapper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class AbstractTestIdMapperTest {

	private static final Type ORGANIZATION_ENTITIES_LIST_TYPE = new TypeReference<List<OrganizationEntity>>() {
	}.getType();

	private static final Type PEOPLE_LIST_TYPE = new TypeReference<List<DefaultPerson>>() {
	}.getType();

	private static final Type TEST_ORGANIZATION_LIST_TYPE = new TypeReference<List<OrganizationEntity>>() {
	}.getType();

	private PersonToOrganizationIdMapper idMapper;

	private TestUtils testUtils;

	@Before
	public void setUp() {
		idMapper = new PersonToOrganizationIdMapper();

		testUtils = new TestUtils();
	}

	@Test
	public void test() throws JsonParseException,
			JsonMappingException,
			IOException {
		String givenOrganizationEntitiesJson = testUtils.readRelativeResource("AbstractTestIdMapperTest_test_givenOrganizationEntities.json");
		String givenTestOrganizationsJson = testUtils.readRelativeResource("AbstractTestIdMapperTest_test_givenTestOrganizations.json");

		List<OrganizationEntity> givenOrganizationEntities = testUtils.readType(givenOrganizationEntitiesJson,
				ORGANIZATION_ENTITIES_LIST_TYPE);
		List<OrganizationEntity> givenTestOrganizations = testUtils.readType(givenTestOrganizationsJson,
				TEST_ORGANIZATION_LIST_TYPE);

		idMapper.mapReferenceActualPersistenceIdsToGivenTestIds(givenOrganizationEntities, givenTestOrganizations);

		String givenPeopleJson = testUtils.readRelativeResource("AbstractTestIdMapperTest_test_givenPeople.json");

		List<Person> people = testUtils.readType(givenPeopleJson,
				PEOPLE_LIST_TYPE);

		idMapper.setReferrerExpectedPersistenceIdsForGivenTestIds(people);

		String actualPeopleJson = testUtils.stringify(people);
		String expectedPeopleJson = testUtils.readRelativeResource("AbstractTestIdMapperTest_test_expectedPeople.json");

		assertEquals(expectedPeopleJson, actualPeopleJson);
	}

}
