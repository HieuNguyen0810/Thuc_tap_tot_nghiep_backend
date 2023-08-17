package com.his.service;

import com.his.entity.Target;
import com.his.exceptionhandler.ApiException;

public interface TargetService {
    Target create(Target target);
    Target getById(Long id) throws ApiException;
    Target update(Long id, Target target) throws ApiException;
    void delete(Long id) throws ApiException;

    Object getAll();
}
