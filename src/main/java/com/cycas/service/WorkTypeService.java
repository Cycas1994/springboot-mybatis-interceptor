package com.cycas.service;

public interface WorkTypeService {

    int insert(Integer i);

    int batchSaveWorkTypes();

    int batchUpdateWorkTypes();

    int batchSaveRoleFunc();

    int insertForeach();

    int deleteRoleFunc();
}
