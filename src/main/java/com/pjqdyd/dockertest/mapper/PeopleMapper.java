package com.pjqdyd.dockertest.mapper;

import com.pjqdyd.dockertest.pojo.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleMapper extends JpaRepository<People, String> {
}
