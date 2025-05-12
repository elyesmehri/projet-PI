package com.example.care4elders.Implementations;

import com.example.care4elders.model.Pillular;
import com.example.care4elders.repository.PillularRepository;
import com.example.care4elders.services.PillularService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PillularServiceImpl extends PillularService {

    private final PillularRepository PillularRepository;

    @Override
    public void save(Pillular Pillular) {
        PillularRepository.save(Pillular);
    }
}
