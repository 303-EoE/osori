//package com.eoe.osori.global.common.security;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//public class UserDetailsImpl implements UserDetails {
//
//	private Long id;
//	@Builder
//	public UserDetailsImpl(Long id){
//		this.id = id;
//	}
//
//	public Long getUserId(){
//		return id;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		return id.toString();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return false;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return false;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return false;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return false;
//	}
//}
