package br.edu.ifba.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifba.frontend.model.GaleriaModel;
import br.edu.ifba.frontend.service.GaleriaService;


@Controller
@RequestMapping("/galeria")
public class GaleriaController {
	@Autowired
	private GaleriaService galeriaService;
	
	@GetMapping("/")
	public String index(Model model) {
		System.out.println("tarefa_lista - init");
		List<GaleriaModel> list = this.galeriaService.getGalerias();
		model.addAttribute("galeria", list);
		return "galeria/index";
	}

	@GetMapping("/adicionar_form")
	public String adicionar_form() {
		return "galeria/adicionar_form";
	}
	
	@PostMapping("/adicionar")
	public String adicionar(@ModelAttribute GaleriaModel galeriaModel, Model model) {
		System.out.println("insert: " + galeriaModel.getNome_Galeria());
		GaleriaModel tm = new GaleriaModel();
		tm.setNome_Galeria(galeriaModel.getNome_Galeria());
		tm.setDescricao_Galeria(galeriaModel.getDescricao_Galeria());
		galeriaService.insert(tm);
		return "redirect:/galeria/";
	}
	
	@RequestMapping(value="/deletar/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Integer id) {
		Boolean boo = this.galeriaService.deleteGaleria(id);
		return "redirect:/galeria/";
	}
	
	@GetMapping("/editar_form/{id}")
	public String editar_form(@PathVariable("id") Integer id, Model model) {
		GaleriaModel tm = this.galeriaService.getGaleria(id);
		model.addAttribute("id", tm.getId());
		model.addAttribute("nome_Galeria", tm.getNome_Galeria());
		model.addAttribute("descricao_Galeria", tm.getDescricao_Galeria());
		model.addAttribute("readonly", true);
		return "galeria/editar_form";
	}
	
	@PostMapping("/editar")
	public String editar(@ModelAttribute GaleriaModel galeriaModel, Model model) {
		GaleriaModel tm = this.galeriaService.getGaleria(galeriaModel.getId());
		tm.setNome_Galeria(galeriaModel.getNome_Galeria());
		tm.setDescricao_Galeria(galeriaModel.getDescricao_Galeria());
		galeriaService.update(tm);
		return "redirect:/galeria/";
	}
	
	
	
}