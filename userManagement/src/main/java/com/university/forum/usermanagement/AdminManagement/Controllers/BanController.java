package com.university.forum.usermanagement.AdminManagement.Controllers;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.BanRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.BanResponse;
import com.university.forum.usermanagement.MemberManagement.Services.BanService;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/bans")
public class BanController {

    private final BanService banService;
    private final JwtTokenUtil jwtTokenUtil;

    public BanController(BanService banService, JwtTokenUtil jwtTokenUtil) {
        this.banService = banService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/")
    public ResponseEntity<?> getBans() {

        List<BanResponse> banResponses=banService.getBans();
        return ResponseEntity.ok().body(Map.of("bans",banResponses));
    }

    @PostMapping("/")
    public ResponseEntity<?> addBan(@RequestHeader("Authorization") String authorizationHeader ,@RequestBody BanRequest banRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID adminId = jwtTokenUtil.getUserIdFromToken(token);
        Map<String,Object> response=new HashMap<>();
        if (adminId==null) {
            response.put("message", "Admin Id must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        System.out.println("Endate is : "+banRequest.getEndDate() );
        if(!banRequest.isEndDateValid()){
            response.put("message", "End date must be in the past or null");
        }
        if(!banRequest.isBanRequestValid()){
            return ResponseEntity.badRequest().body(Map.of("message","Ban request is invalid"));
        }
        banRequest.setBannedById(adminId);
        banService.addBan(banRequest);
        return ResponseEntity.ok().body(Map.of("message","User banned successfully"));

    }

    @PatchMapping("/{banId}")
    public ResponseEntity<?> updateBan(@PathVariable int banId, @RequestBody BanRequest banRequest){
        if(banId<=0){
            return ResponseEntity.badRequest().body(Map.of("message","Ban id is invalid"));
        }

        banService.updateBan(banId,banRequest);
        return ResponseEntity.ok().body(Map.of("message","Ban updated successfully"));
    }

      @GetMapping("/{banId}")
    public ResponseEntity<?> getBanById(@PathVariable int banId){
          if(banId<=0){
              return ResponseEntity.badRequest().body(Map.of("message","Ban id is invalid"));
          }
          BanResponse ban=banService.getBanById(banId);
          return ResponseEntity.ok().body(Map.of("ban",ban));
      }

        @GetMapping("/bans/user/{userId}")
        public ResponseEntity<?> getBansByMemberId(@PathVariable UUID userId){
            if(userId==null){
                return ResponseEntity.badRequest().body(Map.of("message","Member id is invalid"));
            }
            List<BanResponse> bans=banService.getBansByMemberId(userId);
            return ResponseEntity.ok().body(Map.of("bans",bans));
        }

        @GetMapping("/bans/admin/{adminId}")
        public ResponseEntity<?> getBansByAdminId(@PathVariable UUID adminId){
            if(adminId==null){
                return ResponseEntity.badRequest().body(Map.of("message","Admin id is invalid"));
            }

            List<BanResponse> bans=banService.getBansByAdminId(adminId);
            return ResponseEntity.ok().body(Map.of("bans",bans));
        }
}
