package com.fontdb.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fontdb.demo.model.Privilege;
import com.fontdb.demo.model.Role;
import com.fontdb.demo.model.User;
import com.fontdb.demo.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public MyUserDetailsService() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		try {
			final User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("No user found with username: " + email);
			}
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
					true, true, true, getAuthorities(user.getRole()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final Collection<? extends GrantedAuthority> getAuthorities(final Role role) {
		return getGrantedAuthorities(getPrivileges(role));
	}

	private final List<String> getPrivileges(final Role role) {
		final List<String> privileges = new ArrayList<String>();
		final List<Privilege> collection = new ArrayList<Privilege>();
		collection.addAll(role.getPrivileges());
		for (final Privilege item : collection) {
			privileges.add(item.getName());
		}

		return privileges;
	}

	private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
