package com.solution.ntq.repository;

import com.solution.ntq.model.ClassMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassMemberRepository extends JpaRepository<ClassMember,Integer> {
    @Override
    <S extends ClassMember> S saveAndFlush(S s);

}
