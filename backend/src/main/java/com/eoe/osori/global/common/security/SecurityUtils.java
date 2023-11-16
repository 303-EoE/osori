//package com.eoe.osori.global.common.security;
//
//import java.util.Objects;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class SecurityUtils {
//
//	private Authentication authentication;
//	private UserDetailsImpl userDetails;
//
//	private void getAuthentication(){
//		log.info("SecurityUtils getAuthentication");
//			Authentication authentication = Objects.requireNonNull(SecurityContextHolder
//				.getContext()
//				.getAuthentication());
//
//			if (authentication instanceof AnonymousAuthenticationToken) {
//				authentication = null;
//			}
//
//			this.authentication = authentication;
//			this.userDetails = (UserDetailsImpl)authentication.getPrincipal();
//	}
//
//	public Long getUserId() {
//		getAuthentication();
//		return userDetails.getUserId();
//	}
//}
