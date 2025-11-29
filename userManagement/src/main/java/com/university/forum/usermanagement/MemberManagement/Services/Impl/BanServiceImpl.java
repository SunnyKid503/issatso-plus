package com.university.forum.usermanagement.MemberManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.MemberBannedException;
import com.university.forum.usermanagement.AdminManagement.Models.Admin;
import com.university.forum.usermanagement.AdminManagement.Repositories.AdminRepository;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.BanRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.BanResponse;
import com.university.forum.usermanagement.MemberManagement.Models.Ban;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import com.university.forum.usermanagement.MemberManagement.Repositories.BanRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.MemberRepository;
import com.university.forum.usermanagement.MemberManagement.Services.BanService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public BanServiceImpl(BanRepository banRepository, MemberRepository memberRepository, AdminRepository adminRepository) {
        this.banRepository = banRepository;
        this.memberRepository = memberRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<BanResponse> getBans() {

        return banRepository.findAll().stream().map(this::BanToResponse).toList();
    }

    @Override
    public void addBan(BanRequest banRequest) {

        Member member=memberRepository.findById(banRequest.getBannedUserId()).orElseThrow(
                ()-> new UsernameNotFoundException("Member not found with id " + banRequest.getBannedUserId())
        );
        if(banRepository.hasActiveBan(member.getId())){
            throw new MemberBannedException("Member already banned");
        }
        System.out.println("User banned ??  : "+ banRepository.hasActiveBan(member.getId()));
        Admin admin=adminRepository.findById(banRequest.getBannedById()).orElseThrow(
                ()-> new UsernameNotFoundException("Admin not found with id " + banRequest.getBannedById())
        );
        if(!banRequest.isEndDateValid()){
            throw  new IllegalArgumentException("EndDate should be in the past or null");
        }

        Ban ban=new Ban();
        ban.setBannedBy(admin);
        ban.setBannedUser(member);
        ban.setReason(banRequest.getReason()!=null? banRequest.getReason():"");
        ban.setEndDate(banRequest.getEndDate());
        ban.setStartDate(LocalDateTime.now());
        banRepository.save(ban);

    }

    @Override
    public void updateBan(int banId, BanRequest banRequest) {
        Ban ban=banRepository.findById(banId).orElseThrow(
                ()->new ElementNotFoundException("Ban not found")
        );
        System.out.println("Ban request : "+banRequest);
        if((banRequest.getEndDate()==null && ban.getEndDate()==null) && (ban.getReason()==null || ban.getReason().isBlank()) && banRequest.getActive()==null){
            throw  new IllegalArgumentException("Ban request invalid");
        }
        if( banRequest.getEndDate()!=ban.getEndDate()){
            if( banRequest.getEndDate()==null ||  banRequest.getEndDate().isAfter(LocalDateTime.now())  ){ban.setEndDate(banRequest.getEndDate());}
            else  {
                throw  new IllegalArgumentException("EndDate shouldn't be in the past");
            }
        }

        if(banRequest.getReason()!=null && !banRequest.getReason().isBlank() ){
            ban.setReason(banRequest.getReason());
        }
        if(banRequest.getActive()!=null){
            ban.setActive(banRequest.getActive());
        }
        banRepository.save(ban);
    }

    @Override
    public BanResponse getBanById(int banId) {
        Ban ban =banRepository.findById(banId).orElseThrow(()->new ElementNotFoundException("Ban not found"));
        return BanToResponse(ban);
    }

    @Override
    public List<BanResponse> getBansByMemberId(UUID userId) {
         return banRepository.findByBannedUserIdOrderByStartDateDesc(userId).stream().map(this::BanToResponse).toList();
    }

    @Override
    public List<BanResponse> getBansByAdminId(UUID adminId) {
        return banRepository.findByBannedByIdOrderByStartDateDesc(adminId).stream().map(this::BanToResponse).toList();
    }

    public BanResponse BanToResponse(Ban ban) {
        BanResponse banResponse = new BanResponse();
        banResponse.setId(ban.getId());
        banResponse.setReason(ban.getReason());
        banResponse.setEndDate(ban.getEndDate());
        banResponse.setStartDate(ban.getStartDate());
        banResponse.setBannedById(ban.getBannedBy().getId());
        banResponse.setBannedUserId(ban.getBannedUser().getId());
        banResponse.setBannedByFullName(ban.getBannedBy().getFullName());
        banResponse.setActive(ban.getActive());
        return banResponse;
    }
}
