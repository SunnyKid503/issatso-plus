package com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes;


import com.university.forum.usermanagement.MemberManagement.Models.Member;
import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("PERSONNEL")
public abstract class Personnel extends Member {



}
