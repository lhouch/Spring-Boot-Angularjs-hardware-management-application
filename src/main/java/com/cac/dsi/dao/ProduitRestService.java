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

import com.cac.dsi.entites.Produit;
import com.cac.dsi.entites.Typemachine;

@RestController
public class ProduitRestService {

	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private DemandeRepository demandeRepository;

	@RequestMapping(value = "/produits/{id}", method = RequestMethod.GET)
	public Page<Produit> listProduit(@PathVariable("id") Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		return produitRepository
				.produitsByType(id, new PageRequest(page, size));
	}
	
	@RequestMapping(value = "/produits", method = RequestMethod.GET)
	public String listProduits() {

		List<Typemachine> types = typeRepository.findAll();
		String listr = "[";
		int index = 1;
		for (Typemachine type : types ){
			
			if (index > 1 && index <= types.size()) listr += ",";
			
			listr += "{\"nom\":\""+type.getLabel()+"\",\"total\":"+produitRepository.ProCount(type.getId())+" , \"libre\": "+produitRepository.CountLBT(type.getId())+"}";
			index++;
		}
		
		return listr+"]";
	}

	@RequestMapping(value = "/chercherProduits/{id}", method = RequestMethod.GET)
	public Page<Produit> chercher(@PathVariable("id") Long id, String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return produitRepository.chercherProduits(id, "%" + mc + "%",
				new PageRequest(page, size));
	}

	@RequestMapping(value = "/produit/{id}", method = RequestMethod.GET)
	public Produit getProduit(@PathVariable("id") Long id) {
		return produitRepository.findOne(id);
	}

	@RequestMapping(value = "/produitdlvr/{id}", method = RequestMethod.GET)
	public Page<Produit> produitdlvr(@PathVariable("id") long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return produitRepository.produitsDelvre(new PageRequest(page, size) , id);
	}

	@RequestMapping(value = "/produit/{id}", method = RequestMethod.POST)
	public Produit save(@RequestBody Produit p, @PathVariable("id") long id) {
		p.setType(typeRepository.getOne(id));
		return produitRepository.save(p);
	}

	@RequestMapping(value = "/produits/{id}/{idt}", method = RequestMethod.PUT)
	public Produit update(@RequestBody Produit p, @PathVariable Long id,
			@PathVariable Long idt) {
		p.setId(id);
		p.setType(typeRepository.getOne(idt));
		return produitRepository.saveAndFlush(p);
	}

	@RequestMapping(value = "/produits/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		demandeRepository.deleteDemandeByProd(id);
		produitRepository.delete(id);

	}
	
	
}
