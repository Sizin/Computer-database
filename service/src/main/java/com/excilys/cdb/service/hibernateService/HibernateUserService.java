package com.excilys.cdb.service.hibernateService;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.User;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.cdb.persistence.hibernatePersistence.HibernateComputerDao;
import com.excilys.cdb.persistence.hibernatePersistence.HibernateUserDao;

@Service
public class HibernateUserService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger("HibernateUserService");

	@Autowired
	private HibernateUserDao huserDao;

	@Transactional(readOnly = true)
	public User getUser(String username) {
		return huserDao.getUserName(username);
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = huserDao.getUserName(username);
//		UserBuilder userBuilder = null;
//
//		if (user != null) {
//			userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
//			userBuilder.disabled(false);
//			userBuilder.password(user.getPassword());
//
////		      String[] authorities = user.getAuthorities()
////		                                  .stream()
////		                                  .map(a -> a.getAuthority().name())
////		                                  .toArray(String[]::new);
//
//			userBuilder.authorities(new String[] { "USER" });
//		} else {
//			throw new UsernameNotFoundException("User not found");
//		}
//
//		return userBuilder.build();
//	}
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		logger.info("loadUserByUsername username=" + username);

		if (!username.equals("admin")) {
			throw new UsernameNotFoundException(username + " not found");
		}

		// creating dummy user details, should do JDBC operations
		return new UserDetails() {

			private static final long serialVersionUID = 2059202961588104658L;

			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public String getPassword() {
				return "password";
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
				auths.add(new SimpleGrantedAuthority("admin"));
				return auths;
			}
		};
	}
	
	

}
