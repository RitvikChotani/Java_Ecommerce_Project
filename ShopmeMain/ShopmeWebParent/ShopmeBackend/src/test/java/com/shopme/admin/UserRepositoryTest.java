package com.shopme.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager test;
	
	/*
	 * @Test public void userCreateTestWith1Role() { Role role =
	 * test.find(Role.class, 1); User user = new User("ritvik@chotani.co," ,
	 * "ritvik" ,"Ritvik", "Chotani"); user.addRole(role); User saveUser =
	 * repo.save(user); assertThat(saveUser.getId()).isGreaterThan(0); }
	 */
	
	/*
	 * @Test public void userCreateTestWith2Role() { User user = new User("Ritvik" ,
	 * "Chotani" ,"ritvik", "ritvik@chotani.com"); //User user2 = new User("Sanya" ,
	 * "Chotani" ,"sanya", "sanya@chotani.com"); Role roleAss = new Role(5); Role
	 * roleEd = new Role(3); user.addRole(roleAss); user.addRole(roleEd); User
	 * saveUser = repo.save(user); assertThat(saveUser.getId()).isGreaterThan(0); }
	 */
	
	/*
	 * @Test public void listAllUserTest() { Iterable<User> listUser=
	 * repo.findAll();
	 * 
	 * Iterator<User> itr = listUser.iterator(); while(itr.hasNext()) {
	 * System.out.println(itr.next()); } }
	 */
	
	/*
	 * @Test public void testGetUserById() { User user = repo.findById(1).get();
	 * System.out.println(user); assertThat(user).isNotNull(); }
	 * 
	 * @Test public void testUpdateUser() { User user = repo.findById(1).get();
	 * user.setEnabled(true); user.setEmail("ritvik1@chotani.com");
	 * user.setFirstName("Ritvik"); user.setLastName("Chotani");
	 * 
	 * repo.save(user); }
	 */
	/**/
	
	/*
	 * @Test public void deleteUserTest() { Integer userId = 1; repo.deleteById(1);
	 * }
	 */
	
	/*
	 * @Test public void findUser() { String email="lol"; User user =
	 * repo.getUserByEmail(email);
	 * 
	 * assertThat(user).isNotNull(); }
	 */
	
	@Test
	public void countTest() {
		Integer id = 10;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void updateEnabled() {
		repo.updateEnableUserStatus(9, false);
	}
	
	@Test
	public void testLst() {
		int pageN=1;
		int pageS=4;
		Pageable pageable = PageRequest.of(pageN, pageS);
		Page<User> page = repo.findAll(pageable);
		List<User> listUser = page.getContent();
		
		listUser.forEach(user -> System.out.println(user));
		assertThat(listUser.size()).isEqualTo(pageS);
	}
	
	@Test
	public void searchUser() {
		String keyword = "bruce";
		
		int pageN=0;
		int pageS=4;
		Pageable pageable = PageRequest.of(pageN, pageS);
		Page<User> page = repo.findAll(keyword, pageable);
		List<User> listUser = page.getContent();
		
		listUser.forEach(user -> System.out.println(user));
		assertThat(listUser.size()).isGreaterThan(0);
	}
 }
