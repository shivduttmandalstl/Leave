package com.employee.management.system.Leave.configuration;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@SuppressWarnings("serial")
public class UserInfoUserDetails implements UserDetails{
	
		private String email;

	    public UserInfoUserDetails(String userInfo) {
	        email=userInfo;	        
	    }

	    @Override
	    public String getPassword() {
	        return null;
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }


		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
		}


}
