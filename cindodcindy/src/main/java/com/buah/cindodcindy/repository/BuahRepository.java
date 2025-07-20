package com.buah.cindodcindy.repository;

import com.buah.cindodcindy.model.Buah;
import com.buah.cindodcindy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuahRepository extends JpaRepository<Buah, Long> {
  List<Buah> findByUserAndIsDeletedFalse(User user);
}
