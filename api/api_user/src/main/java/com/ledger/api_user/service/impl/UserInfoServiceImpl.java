package com.ledger.api_user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.response.Result;
import com.ledger.api_common.util.FileUtil;
import com.ledger.api_user.model.domain.SecurityUser;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.model.domain.UserPermissions;
import com.ledger.api_user.model.dto.UserInfoLogin;
import com.ledger.api_user.model.dto.UserInfoRegister;
import com.ledger.api_user.model.vo.UploadVo;
import com.ledger.api_user.model.vo.UserInfoVo;
import com.ledger.api_user.service.UserInfoService;
import com.ledger.api_user.mapper.UserInfoMapper;
import com.ledger.api_user.service.UserPermissionsService;
import com.ledger.api_user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import javax.imageio.ImageIO;
/**
 * @author 22866
 * @description 针对表【user_info(存储用户信息)】的数据库操作Service实现
 * @createDate 2023-09-30 17:32:12
 */
@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private UserPermissionsService userPermissionsService;
    @Value("${sys.profile}")
    private String profile;

    private static final BCryptPasswordEncoder bCryptPasswordEncoder;

    static {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Result<String> login(UserInfoLogin user, HttpServletResponse response) {
        String username = user.getUsername();
        UserInfo userByUsername = getUserByUsername(username);
        if (userByUsername == null) {
            return Result.fail("用户名不存在", 403);
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userByUsername.getPassword())) {
            return Result.fail("密码错误", 403);
        }
        List<String> auth = userPermissionsService.getAuthByUserId(userByUsername.getId());
        SecurityUser securityUser = new SecurityUser(userByUsername, auth);
        String token = JwtUtil.createJwt(securityUser, auth, secret);
        StringJoiner stringJoiner = new StringJoiner(",");
        auth.forEach(stringJoiner::add);
        response.setHeader("Role", stringJoiner.toString());
        response.setHeader("Authorization", tokenHead + " " + token);
        return Result.success("登录成功");

    }

    @Override
    public Result<String> register(UserInfoRegister user, HttpServletResponse response) {
        String username = user.getUsername();
        String password = user.getPassword();
        String password2 = user.getPassword2();
        if (!Objects.equals(password, password2)) {
            return Result.fail("两次密码输入不一致", 403);
        }
        if (getUserByUsername(username) != null) {
            return Result.fail("用户已经存在", 403);
        }
        //保存用户信息
        UserInfo userInfo = new UserInfo();
        String userId = UUID.randomUUID().toString();
        userInfo.setId(userId);
        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(bCryptPasswordEncoder.encode(password));
        userInfo.setInvitation_code(IdUtil.simpleUUID().substring(0, 10));
        userInfo.setSecretKey(IdUtil.simpleUUID());
        userInfo.setAccessKey(IdUtil.simpleUUID());
        userInfo.setAccount(new BigDecimal(5));
        //保存权限
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setId(UUID.randomUUID().toString());
        userPermissions.setUser_id(userId);
        userPermissions.setPermission_id("0");
        save(userInfo);
        userPermissionsService.save(userPermissions);
        return Result.success("注册成功");
    }

    @Override
    public Result<UploadVo> uploadAvatar(MultipartFile file) {
        UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String id = getUserByUsername(authentication.getUsername()).getId();

        String nameWithFileExtension = FileUtil.uploadFile(file, id, profile);

        UploadVo uploadVo = new UploadVo();

        String name = nameWithFileExtension.substring(0, nameWithFileExtension.lastIndexOf("."));
        String fileExtension = nameWithFileExtension.substring(nameWithFileExtension.lastIndexOf(".") + 1);

        uploadVo.setName(name);
        uploadVo.setFileExtension(fileExtension);
        uploadVo.setNameWithFileExtension(nameWithFileExtension);

        String username = authentication.getUsername();

        UserInfo userByUsername = getUserByUsername(username);
        userByUsername.setAvatar(nameWithFileExtension);
        saveOrUpdate(userByUsername);

        return Result.success(uploadVo);

    }

//    @Override
//    public Result<String> getAvatar(String fileName) {
//        byte[] bytes = FileUtil.downloadFile(fileName, profile);
//
//        String base = FileUtil.byteToBase64(bytes);
//
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        UserInfo userInfo = getUserByUsername(userDetails.getUsername());
//
//        userInfo.setAvatar(base);
//
//        saveOrUpdate(userInfo);
//
//        return Result.success(base);
//
//    }

    @Override
    public Result<UserInfoVo> getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo userInfo = getUserByUsername(userDetails.getUsername());

        UserInfoVo userInfoVo = BeanUtil.copyProperties(userInfo, UserInfoVo.class);

        return Result.success(userInfoVo);
    }

    @Override
    public Result<String> getAvatarCheck() {
        String token =
                JwtUtil.createTempJwt((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), null,secret);
        return Result.success(token);
    }

    @Override
    public void getAvatar(String fileName, String token, HttpServletResponse response) {
        boolean b = JwtUtil.validateJwt(token, secret);
        if(!b){
            throw new KnowException("token验证失败");
        }
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] bytes = FileUtil.downloadFile(fileName, profile);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                //关闭流
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Override
    public UserInfo getUserByUsername(String username) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUsername, username);
        return getOne(wrapper);
    }


}




