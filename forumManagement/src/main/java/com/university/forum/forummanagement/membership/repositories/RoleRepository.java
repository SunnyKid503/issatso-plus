package com.university.forum.forummanagement.membership.repositories;

import com.university.forum.forummanagement.membership.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
    Set<Role> findAllByIdIn(List<Integer> ids);
}
