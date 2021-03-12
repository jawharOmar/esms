package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.AttachedFile;

public interface AttachedFileDAO extends CrudRepository<AttachedFile, Integer> {

}
