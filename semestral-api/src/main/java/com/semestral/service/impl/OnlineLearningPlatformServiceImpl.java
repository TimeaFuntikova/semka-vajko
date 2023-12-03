package com.semestral.service.impl;

import com.semestral.service.OnlineLearningPlatformService;
import org.springframework.stereotype.Service;

@Service
public class OnlineLearningPlatformServiceImpl implements OnlineLearningPlatformService {

    @Override
    public String getMessage() {
        return "Succesfully ran Spring. Injections were resolved.";
    }
}
