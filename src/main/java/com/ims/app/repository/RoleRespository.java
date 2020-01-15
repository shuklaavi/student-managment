package com.ims.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.app.entity.Role;



@Repository("roleRepository")
public interface RoleRespository extends JpaRepository<Role, Integer> {

	Role findByRole(String string);


}