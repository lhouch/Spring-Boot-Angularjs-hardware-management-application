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

@RestController
public class DemandeurRestService {

	@Autowired
	private DemandeurRepository demandeurRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@Autowired
	private DemmachineRepository demmachineRepository;

	@RequestMapping(value = "/demandeurs", method = RequestMethod.GET)
	public Page<Demandeur> listDemandeur(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeurRepository.findAll(new PageRequest(page, size));
	}

	@RequestMapping(value = "/Alldemandeurs/{id}", method = RequestMethod.GET)
	public Page<Demandeur> listDemandeursByD(@PathVariable("id") Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeurRepository.getAll(id, new PageRequest(page, size));
	}

	@RequestMapping(value = "/Alldemandeur/{id}", method = RequestMethod.GET)
	public List<Demandeur> listDemandeursByDi(@PathVariable("id") Long id) {
		return demandeurRepository.getAlli(id);
	}

	@RequestMapping(value = "/demandeurs/{id}", method = RequestMethod.GET)
	public Page<Demandeur> listDemandeurByD(@PathVariable("id") Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeurRepository.demandeurByDepart(id, new PageRequest(page,
				size));
	}

	@RequestMapping(value = "/chercherDemandeur/{id}", method = RequestMethod.GET)
	public Page<Demandeur> chercher(@PathVariable("id") Long id, String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeurRepository.chercherDemandeur(id, "%" + mc + "%",
				new PageRequest(page, size));
	}

	@RequestMapping(value = "/chercherDemandeur", method = RequestMethod.GET)
	public Page<Demandeur> chercherAll(String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeurRepository.chercher("%" + mc + "%", new PageRequest(
				page, size));
	}

	@RequestMapping(value = "/demandeur/{id}", method = RequestMethod.GET)
	public Demandeur getDemandeur(@PathVariable("id") Long id) {
		return demandeurRepository.findOne(id);
	}
	
	@RequestMapping(value = "/demandeur/{id}", method = RequestMethod.POST)
	public Demandeur save(@RequestBody Demandeur d, @PathVariable("id") long id) {
		d.setDepartement(departmentRepository.getOne(id));

		return demandeurRepository.save(d);
	}
	
	@RequestMapping(value = "/demandeur/{id}/{idt}", method = RequestMethod.PUT)
	public Demandeur update(@RequestBody Demandeur d, @PathVariable Long id,
			@PathVariable Long idt) {
		d.setId(id);
		d.setDepartement(departmentRepository.getOne(idt));
		return demandeurRepository.saveAndFlush(d);
	}

	@RequestMapping(value = "/demandeur/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		demandeRepository.deleteDemandeByDemandeur(id);
		demmachineRepository.deleteDemmByDemandeur(id);
		demandeurRepository.delete(id);
	}
}