package com.evanzeimet.testidmapper;


public class DefaultPerson implements Person {

	private Long organizationId;
	private String organizationTestId;

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

	public void setOrganizationTestId(String organizationTestId) {
		this.organizationTestId = organizationTestId;
	}
}
