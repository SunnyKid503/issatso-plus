package com.university.forum.notificationManagement.Services.Impl;


import com.university.forum.notificationManagement.Dtos.Messages.MemberMessage;
import com.university.forum.notificationManagement.Dtos.Messages.StudentMessage;
import com.university.forum.notificationManagement.Exceptions.ElementAlreadyExistsException;
import com.university.forum.notificationManagement.Models.Enums.MemberType;
import com.university.forum.notificationManagement.Models.Member;
import com.university.forum.notificationManagement.RabbitMQListeners.MemberManagementListener;
import com.university.forum.notificationManagement.Repositories.MemberRepository;
import com.university.forum.notificationManagement.Services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberManagementListener.class);
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void createMember(MemberMessage memberMessage) {

        if ((memberMessage.getId() == null ) ||
                ((memberMessage.getAddressEmail() == null || memberMessage.getAddressEmail().isBlank())
                )) {
            throw new BadCredentialsException("❌ Member must have the ID and Email");
        }


        validateMember(memberMessage);
        Member member=new Member();
        member.setMemberType(memberMessage.getMemberType());
        member.setAddressEmail(memberMessage.getAddressEmail());
        member.setId(memberMessage.getId());
        member.setName(memberMessage.getFirstName());
        member.setLastName(memberMessage.getLastName());
        member.setPhoneNumber(memberMessage.getPhoneNumber());
        memberRepository.save(member);
        logger.info("Member created with email : {}",memberMessage.getAddressEmail());

    }

    public void validateMember(MemberMessage memberMessage) {
        logger.info("Validating member with email: {} and ID: {}", memberMessage.getAddressEmail(), memberMessage.getId());
        Member oldMember = null;

        if (memberMessage.getId() != null) {
            oldMember = memberRepository.findByAddressEmailOrId(
                    memberMessage.getAddressEmail(), memberMessage.getId()).orElse(null);
        } else if (memberMessage.getAddressEmail()!=null && !memberMessage.getAddressEmail().isEmpty()) {
            oldMember = memberRepository.findByAddressEmail(memberMessage.getAddressEmail()).orElse(null);
        }
        else{
            throw new BadCredentialsException("Member "+memberMessage+" does not have email/id");
        }

        if (oldMember != null) {
            throw new ElementAlreadyExistsException("Member with email/Id : "
                    + oldMember.getAddressEmail() + " / " + memberMessage.getId() + " already exists");
        }
    }
}
