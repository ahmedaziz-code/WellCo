package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.User;
import com.springheroes.wellco.enumeration.CentreInter;
import com.springheroes.wellco.enumeration.Role;

public interface IUserService {
    User addUser(User u, Role role);
    void setCentreInterest(Long idUser, CentreInter centreInter);
}
