package com.university.forum.forummanagement.shared.abstracts;

import com.university.forum.forummanagement.membership.models.Role;
import com.university.forum.usermanagement.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.UUID;

public abstract class ControllerBase {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    protected UUID extractUserId(String authorizationHeader)
    {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtTokenUtil.getUserIdFromToken(token);
    }

    protected Set<String> extractRoles(String authorizationHeader)
    {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtTokenUtil.getRolesFromToken(token);
    }
}
