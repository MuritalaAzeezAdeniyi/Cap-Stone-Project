package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.dto.request.RegisterUserRequest;
import africa.semicolon.koonnkt.dto.request.UpdateUserDetailRequest;
import africa.semicolon.koonnkt.dto.request.UserLoginRequest;
import africa.semicolon.koonnkt.dto.response.BlockUserResponse;
import africa.semicolon.koonnkt.dto.response.RegisterUserResponse;
import africa.semicolon.koonnkt.dto.response.UpdateUserDetailResponse;
import africa.semicolon.koonnkt.dto.response.UserLoginResponse;
import africa.semicolon.koonnkt.execption.InvalidCredentialException;
import africa.semicolon.koonnkt.execption.UserExistException;

public interface UsersService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) throws UserExistException, InvalidCredentialException;
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest) throws InvalidCredentialException;
    BlockUserResponse blockUser(Long userId) throws UserExistException;
    BlockUserResponse unblockUser(Long userId) throws UserExistException;
    UpdateUserDetailResponse updateUserDetail(Long userId, UpdateUserDetailRequest updateUserDetailRequest) throws InvalidCredentialException, UserExistException;
}
