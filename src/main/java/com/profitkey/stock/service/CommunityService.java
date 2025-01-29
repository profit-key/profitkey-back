package com.profitkey.stock.service;

import com.profitkey.stock.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
	private final CommunityRepository communityRepository;

}
