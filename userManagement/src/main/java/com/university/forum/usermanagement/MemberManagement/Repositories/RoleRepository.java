package com.university.forum.usermanagement.MemberManagement.Repositories;

import com.university.forum.usermanagement.MemberManagement.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByIdIn(List<Integer> rolesId);
}
