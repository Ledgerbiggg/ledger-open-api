package com.ledger.api_user.service;

import com.ledger.api_common.model.domain.userInfo.UserInfo;
import com.ledger.api_common.response.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_user.model.dto.UserInfoLogin;
import com.ledger.api_user.model.dto.UserInfoRegister;
import com.ledger.api_user.model.vo.UploadVo;
import com.ledger.api_user.model.vo.UserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 22866
 * @description 针对表【user_info(存储用户信息)】的数据库操作Service
 * @createDate 2023-09-30 17:32:12
 */
public interface UserInfoService extends IService<UserInfo> {
    Result<String> login(UserInfoLogin user, HttpServletResponse response);

    UserInfo getUserByUsername(String username);



    Result<String> register(UserInfoRegister user, HttpServletResponse response);

    Result<UploadVo> uploadAvatar(MultipartFile file);

//    Result<String> getAvatar(String fileName);

    Result<UserInfoVo> getUser();


    void getAvatar(String fileName, String token, HttpServletResponse response);

    Result<String> getAvatarCheck();


}
