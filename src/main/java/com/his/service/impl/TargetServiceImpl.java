package com.his.service.impl;

import com.his.constant.ErrorMessage;
import com.his.entity.Target;
import com.his.exceptionhandler.ApiException;
import com.his.repository.TargetRepo;
import com.his.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    private final TargetRepo targetRepository;
    private final ModelMapper modelMapper;

    public Target create(Target target) {
        Target createdTarget = targetRepository.save(target);
        return modelMapper.map(createdTarget, Target.class);
    }

    public Target getById(Long id) throws ApiException {
        Optional<Target> targetOptional = targetRepository.findById(id);
        if (targetOptional.isPresent()) {
            return targetOptional.get();
        }
        throw new ApiException(ErrorMessage.TARGET_NOT_FOUND);
    }

    public Target update(Long id, Target target) throws ApiException {
        if (!targetRepository.existsById(id)) {
            throw new ApiException(ErrorMessage.TARGET_NOT_FOUND);
        }
        return targetRepository.save(target);
    }

    public void delete(Long id) throws ApiException {
        if (!targetRepository.existsById(id)) {
            throw new ApiException(ErrorMessage.TARGET_NOT_FOUND);
        }
        targetRepository.deleteById(id);
    }

    @Override
    public Object getAll() {
        return targetRepository.findAll();
    }
}
