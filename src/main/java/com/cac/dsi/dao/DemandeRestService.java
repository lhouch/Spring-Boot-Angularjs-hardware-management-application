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
import com.cac.dsi.entites.Demande;
import com.cac.dsi.entites.Demandeur;
import com.cac.dsi.entites.Produit;

@RestController
public class DemandeRestService {

	@Autowired
	DemandeRepository demandeRepository;
	@Autowired
	ProduitRepository produitRepository;
	@Autowired
	DemandeurRepository demandeurRepository;

	@RequestMapping(value = "/demandes", method = RequestMethod.GET)
	public Page<Demande> listDemande(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return demandeRepository.findAll(new PageRequest(page, size));
	}

	@RequestMapping(value = "/allDemandes", method = RequestMethod.GET)
	public List<Demande> allDemandes () {
		return demandeRepository.findAll();
	}
	
	@RequestMapping(value = "/demandebyid/{id}", method = RequestMethod.GET)
	public Demande DemandeByid(@PathVariable("id") Long id) {

		return demandeRepository.findOne(id);
	}

	@RequestMapping(value = "/demandebypro/{id}", method = RequestMethod.GET)
	public List<Demande> listDemandeBypro(@PathVariable("id") Long id) {
		return demandeRepository.demandeByPro(id);
	}

	@RequestMapping(value = "/demandebydemandeur/{id}", method = RequestMethod.GET)
	public List<Demande> listDemandeBydem(@PathVariable("id") Long id) {
		return demandeRepository.demandeByDem(id);
	}

	@RequestMapping(value = "/demande/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		demandeRepository.delete(id);
	}

	@RequestMapping(value = "/libreDemande/{id}", method = RequestMethod.GET)
	public void update(@PathVariable Long id) {
		Demande demande = demandeRepository.findOne(id);
		demande.setDelvre(false);
		demandeRepository.saveAndFlush(demande);
	}

	@RequestMapping(value = "/delivre/{idpro}/{iddema}/{numeros}", method = RequestMethod.POST)
	public Demande save(@PathVariable("idpro") long idpro,
			@PathVariable("iddema") long iddema,
			@PathVariable("numeros") String numeros,
			@RequestBody Demande demande) {
		Demandeur demandeur = demandeurRepository.findOne(iddema);
		Produit produit = produitRepository.findOne(idpro);
		produit.setNumeros(numeros);
		produitRepository.saveAndFlush(produit);
		List<Demande> demandes = demandeRepository.demandeByPro(idpro);
		for (Demande demande2 : demandes) {
			demande2.setDelvre(false);
			demandeRepository.saveAndFlush(demande2);
		}

		demande.setProd(produit);
		demande.setDemandeur(demandeur);
		demande.setDelvre(true);
		return demandeRepository.save(demande);
	}

}
