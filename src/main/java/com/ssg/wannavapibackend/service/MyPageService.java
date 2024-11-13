package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.dto.MyPageUpdateDTO;
import com.ssg.wannavapibackend.dto.response.MyPageResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MyPageService {

    MyPageResponseDTO findMyPage(Long userId);

    void updateMyPage(Long userId, MyPageUpdateDTO myPageUpdateDTO);
}
