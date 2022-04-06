package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.Badge;

import java.util.List;


public interface IBadgeService {

    Badge addBadge(Badge b);

    Badge updateBadge(Badge b);

    void deleteBadge(int idBadge);

    List<Badge> ListOfBadge();

}
