package com.buah.cindodcindy.service;

import com.buah.cindodcindy.model.Buah;
import com.buah.cindodcindy.model.User;
import com.buah.cindodcindy.repository.BuahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuahService {
  @Autowired
  private BuahRepository buahRepository;

  public Buah save(Buah buah) {
    return buahRepository.save(buah);
  }

  public List<Buah> findByUser(User user) {
    return buahRepository.findByUserAndIsDeletedFalse(user);
  }

  public Optional<Buah> findById(Long id) {
    return buahRepository.findById(id);
  }

  public void softDelete(Buah buah) {
    buah.setIsDeleted(true);
    buahRepository.save(buah);
  }
}
