//package com.SecourityDemo.SecurityProject.service;
//
//import com.SecourityDemo.SecurityProject.model.UserDetails;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // Extract user information from oAuth2User.getAttributes()
//        String email = (String) oAuth2User.getAttributes().get("email");
//        String name = (String) oAuth2User.getAttributes().get("name");
//
////        // Create a custom user object (e.g., MyUserDetails)
//        UserDetails userDetails = new UserDetails(oAuth2User.getAttributes(), email, name);
//
//        return (OAuth2User) userDetails;
//    }
//}