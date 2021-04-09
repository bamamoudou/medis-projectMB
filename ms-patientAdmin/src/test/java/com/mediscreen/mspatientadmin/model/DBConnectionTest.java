package com.mediscreen.mspatientadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionTest {
	private DBConnection dbConnection;

	@BeforeEach
	void init_test() {
		dbConnection = new DBConnection("host", 1111, "db", "user", "password");
	}

	@Tag("DBConnectionTest")
	@Test
	void get_test() {
		assertThat(dbConnection.getHost()).isEqualTo("host");
		assertThat(dbConnection.getPort()).isEqualTo(1111);
		assertThat(dbConnection.getDatabase()).isEqualTo("db");
		assertThat(dbConnection.getUser()).isEqualTo("user");
		assertThat(dbConnection.getPassword()).isEqualTo("password");
	}

	@Tag("DBConnectionTest")
	@Test
	void set_test() {
		assertThat(dbConnection.getHost()).isEqualTo("host");
		assertThat(dbConnection.getPort()).isEqualTo(1111);
		assertThat(dbConnection.getDatabase()).isEqualTo("db");
		assertThat(dbConnection.getUser()).isEqualTo("user");
		assertThat(dbConnection.getPassword()).isEqualTo("password");

		dbConnection.setHost("new_host");
		dbConnection.setPort(2222);
		dbConnection.setDatabase("new_db");
		dbConnection.setUser("new_user");
		dbConnection.setPassword("new_password");

		assertThat(dbConnection.getHost()).isEqualTo("new_host");
		assertThat(dbConnection.getPort()).isEqualTo(2222);
		assertThat(dbConnection.getDatabase()).isEqualTo("new_db");
		assertThat(dbConnection.getUser()).isEqualTo("new_user");
		assertThat(dbConnection.getPassword()).isEqualTo("new_password");
	}
}