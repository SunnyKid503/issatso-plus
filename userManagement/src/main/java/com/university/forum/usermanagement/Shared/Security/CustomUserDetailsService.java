//package com.university.forum.usermanagement.Shared.Security;
//
//import com.university.forum.usermanagement.MemberManagement.Models.Member;
//import com.university.forum.usermanagement.MemberManagement.Repositories.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String addressEmail) throws UsernameNotFoundException {
//
//        Member member = memberRepository.findByAddressEmail(addressEmail).orElseThrow(
//                () -> new UsernameNotFoundException(addressEmail + " not found")
//        );
//
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        member.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
//
//        return new org.springframework.security.core.userdetails.User(
//                member.getAddressEmail(),
//                member.getPassword(),
//                authorities
//        );
//    }
//}
