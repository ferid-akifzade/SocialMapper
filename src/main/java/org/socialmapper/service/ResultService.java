package org.socialmapper.service;

import org.socialmapper.libs.Target;
import org.socialmapper.repos.TargetRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final TargetRepo targetRepo;
    private final MapperService mapperService;
    public ResultService(TargetRepo targetRepo, MapperService mapperService) {
        this.targetRepo = targetRepo;
        this.mapperService = mapperService;
    }

    public List<Target> getData(){
        return (List<Target>) targetRepo.findAll();
    }

    public String getError(){
        return mapperService.getError();
    }
}
