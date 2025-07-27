package com.buah.cindodcindy.controller;

import com.buah.cindodcindy.model.Buah;
import com.buah.cindodcindy.repository.BuahRepository;
import com.buah.cindodcindy.service.BuahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/buah")
public class BuahViewController {
  @Autowired
  private BuahRepository buahRepository;

  @GetMapping
  public String listBuah(Model model) {
    model.addAttribute("buahList", buahRepository.findAll());
    return "buah"; // loads buah.html from templates
  }

  @PostMapping("/save")
  public String saveBuah(@RequestParam String nama) {
    Buah buah = new Buah();
    buah.setNama(nama);
    buahRepository.save(buah);
    return "redirect:/buah"; // refresh page after saving
  }

  @GetMapping("/edit/{id}")
  public String editBuah(@PathVariable Long id, Model model) {
    Buah buah = buahRepository.findById(id).orElseThrow(() -> new RuntimeException("Buah not found"));
    model.addAttribute("buah", buah);
    return "edit-buah"; // create another HTML for edit form
  }

  @GetMapping("/delete/{id}")
  public String deleteBuah(@PathVariable Long id) {
    buahRepository.deleteById(id);
    return "redirect:/buah";
  }
}
