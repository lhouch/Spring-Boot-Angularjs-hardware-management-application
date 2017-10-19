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

import com.cac.dsi.entites.Departement;

@RestController
public class DepartementRestService {
	@Autowired
	DepartmentRepository departementRepository;
	@Autowired
	DemandeurRepository demandeurRepository;

	@RequestMapping(value = "/departements", method = RequestMethod.GET)
	public Page<Departement> departements(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return departementRepository.findAll(new PageRequest(page, size));
	}

	@RequestMapping(value = "/alldepartements", method = RequestMethod.GET)
	public List<Departement> alldepartements() {
		return departementRepository.findAll();
	}

	@RequestMapping(value = "/departement/{id}", method = RequestMethod.GET)
	public Departement getType(@PathVariable("id") Long id) {
		return departementRepository.findOne(id);
	}

	@RequestMapping(value = "/chercherDepartement", method = RequestMethod.GET)
	public Page<Departement> chercher(String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		return departementRepository.chercherDeppartement("%" + mc + "%",
				new PageRequest(page, size));
	}

	@RequestMapping(value = "/departement", method = RequestMethod.POST)
	public Departement save(@RequestBody Departement d) {
		return departementRepository.save(d);
	}

	@RequestMapping(value = "/departement/{id}", method = RequestMethod.PUT)
	public Departement update(@RequestBody Departement d, @PathVariable Long id) {
		d.setId(id);
		return departementRepository.saveAndFlush(d);
	}

	@RequestMapping(value = "/departement/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {

		demandeurRepository.deleteDemandeurByDepartement(id);
		departementRepository.delete(id);

	}
}
