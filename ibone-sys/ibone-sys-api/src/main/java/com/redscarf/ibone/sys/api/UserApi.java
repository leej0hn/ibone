package com.redscarf.ibone.sys.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.redscarf.ibone.common.rpc.Result;
import com.redscarf.ibone.sys.api.dto.request.ChangePasswordRequestDTO;
import com.redscarf.ibone.sys.api.dto.request.GithubUserLoginRequestDTO;
import com.redscarf.ibone.sys.api.dto.response.UserBaseInfoResponseDTO;
import com.redscarf.ibone.sys.api.dto.response.UserInfoResponseDTO;
import com.redscarf.ibone.sys.api.dto.response.UserSecurityQuestionsResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/user")
public interface UserApi {

    @HystrixCommand(commandKey = "getUserByName")
    @RequestMapping(value = "/getUserByName")
    Result<UserBaseInfoResponseDTO> getUserByName(@RequestParam("username") String username);

    @HystrixCommand(commandKey = "getUserDetailByName")
    @RequestMapping(value = "/getUserDetail")
    Result<UserInfoResponseDTO> getUserDetailByName(@RequestParam("username") String username);

    @HystrixCommand(commandKey = "getUserDetailByNameAndServerName")
    @RequestMapping(value = "/getUserDetailByNameAndServerName")
    Result<UserInfoResponseDTO> getUserDetailByNameAndServerName(@RequestParam("username") String username, @RequestParam("serverName") String serverName);

    @HystrixCommand(commandKey = "getUserSecurityQuestions")
    @RequestMapping(value = "/getUserSecurityQuestions")
    Result<List<UserSecurityQuestionsResponseDTO>> getUserSecurityQuestions(@RequestParam("username") String username);

    @HystrixCommand(commandKey = "changePassword")
    @RequestMapping(value = "/changePassword")
    Result<Void> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO);

    @HystrixCommand(commandKey = "thirdPartyUserLogin")
    @RequestMapping(value = "/thirdPartyUserLogin")
    Result<Void> thirdPartyUserLogin(@RequestBody GithubUserLoginRequestDTO githubUserLoginRequestDTO);
}
