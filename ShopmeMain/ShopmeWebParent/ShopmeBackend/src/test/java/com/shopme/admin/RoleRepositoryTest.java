package com.shopme.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.repository.RoleRepository;
import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repo.save(roleAdmin);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateRestRole() {
		Role roleSales = new Role("SalesPerson", "manage product, customer, shipping, orde, and report");
		Role roleEditor = new Role("Editor", "Edit stuff");
		Role roleShip = new Role("Shipper", "View and update orde status");
		Role roleAss = new Role("Assistant", "Manage questions and reviews");
		
		repo.saveAll(List.of(roleSales, roleEditor, roleShip, roleAss));
	}

}
