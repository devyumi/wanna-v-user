package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.dto.request.MyPageUpdateDTO;
import com.ssg.wannavapibackend.dto.response.MyLikesResponseDTO;
import com.ssg.wannavapibackend.dto.response.MyPageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyPageService {

    MyPageResponseDTO findMyPage(Long userId);

    void updateMyPage(Long userId, MyPageUpdateDTO myPageUpdateDTO);

    List<MyLikesResponseDTO> findMyLikes(Long userId);
}
