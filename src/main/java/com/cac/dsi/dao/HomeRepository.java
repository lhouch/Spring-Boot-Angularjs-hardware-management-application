package com.cac.dsi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cac.dsi.entites.Demande;

public interface HomeRepository extends JpaRepository<Demande, Long>{

}
