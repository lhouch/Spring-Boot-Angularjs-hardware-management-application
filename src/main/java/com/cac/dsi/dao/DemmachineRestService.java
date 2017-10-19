package com.cac.dsi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cac.dsi.entites.Demandeur;
import com.cac.dsi.entites.Demmachine;

@RestController
public class DemmachineRestService {

	@Autowired
	DemandeurRepository demandeurRepository;
	@Autowired
	DemmachineRepository demmachineRepository;

	@RequestMapping(value = "/demmachine/{id}", method = RequestMethod.POST)
	public Demmachine save(@PathVariable("id") Long id,
			@RequestBody Demmachine dm) {

		Demandeur demandeur = demandeurRepository.findOne(id);
		dm.setDemandeur(demandeur);
		return demmachineRepository.save(dm);
	}

	@RequestMapping(value = "/newdemande/{id}", method = RequestMethod.GET)
	public Demmachine DemandeNew(@PathVariable("id") Long id) {
		Demmachine demmachine = demmachineRepository.findOne(id);
		demmachine.setVsible(true);
		demmachineRepository.saveAndFlush(demmachine);
		return demmachine;
	}

	@RequestMapping(value = "/Allnewdemande", method = RequestMethod.GET)
	public List<Demmachine> listDemandeNew() {
		return demmachineRepository.getAllv();
	}

	@RequestMapping(value = "/updatev", method = RequestMethod.GET)
	public String updatev() {
		List<Demmachine> listd = demmachineRepository.getAllv();
		for (Demmachine demm : listd) {
			demm.setVsible(true);
			demmachineRepository.saveAndFlush(demm);
		}
		return "libre";
	}

	@RequestMapping(value = "/demanded/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {

		demmachineRepository.delete(id);

	}

	@RequestMapping(value = "/Alldemandedem", method = RequestMethod.GET)
	public Page<Demmachine> listDemandeDem(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
			return demmachineRepository.findAll(new PageRequest(page, size));
	}
}
