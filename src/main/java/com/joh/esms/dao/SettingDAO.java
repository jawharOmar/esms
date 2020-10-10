package com.joh.esms.dao;

import com.joh.esms.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingDAO extends JpaRepository<Setting, Integer> {

    Setting findByid(int id);

}
