package ru.itis.other.project.repositories.interfaces;

import ru.itis.other.project.models.User;
import ru.itis.other.project.models.UserInfo;

public interface UserInfoRepository {

    UserInfo findByUser(User user);

    UserInfo save(UserInfo info);
}
