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

import com.cac.dsi.dao.TypeRepository;
import com.cac.dsi.entites.Typemachine;

@RestController
public class TypeRestService {

	@Autowired
	private TypeRepository typeRepository;

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public Page<Typemachine> ListType(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return typeRepository.findAll(new PageRequest(page, size));
	}

	@RequestMapping(value = "/AllTypes", method = RequestMethod.GET)
	public List<Typemachine> AllType() {
		return typeRepository.findAll();
	}

	@RequestMapping(value = "/type/{id}", method = RequestMethod.GET)
	public Typemachine getType(@PathVariable("id") Long id) {
		return typeRepository.findOne(id);
	}

	@RequestMapping(value = "/chercherType", method = RequestMethod.GET)
	public Page<Typemachine> chercher(String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		return typeRepository.chercherType("%" + mc + "%", new PageRequest(
				page, size));
	}

	@RequestMapping(value = "/types", method = RequestMethod.POST)
	public Typemachine save(@RequestBody Typemachine t) {
		return typeRepository.save(t);
	}

	@RequestMapping(value = "/types/{id}", method = RequestMethod.PUT)
	public Typemachine update(@RequestBody Typemachine t, @PathVariable Long id) {
		t.setId(id);
		return typeRepository.saveAndFlush(t);
	}

	@RequestMapping(value = "/types/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {

		typeRepository.delete(id);

	}

}
