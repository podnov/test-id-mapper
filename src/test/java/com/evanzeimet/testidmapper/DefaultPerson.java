package com.evanzeimet.testidmapper;


public class DefaultPerson implements Person {

	private String name;
	private Long organizationId;
	private String organizationTestId;

	public DefaultPerson() {

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getOrganizationId() {
		return organizationId;
	}

	@Override
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String getOrganizationTestId() {
		return organizationTestId;
	}

	@Override
	public void setOrganizationTestId(String organizationTestId) {
		this.organizationTestId = organizationTestId;
	}
}
