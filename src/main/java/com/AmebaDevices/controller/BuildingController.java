package com.AmebaDevices.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AmebaDevices.dto.BuildingDTO;
import com.AmebaDevices.services.BuildingService;
import com.AmebaDevices.services.CustomerService;


@RestController
@CrossOrigin
@RequestMapping("/Building")
public class BuildingController {

	private BuildingService buildingService;
	private CustomerService customerService;

	@Autowired
	public BuildingController(BuildingService bs, CustomerService cs) {
		buildingService = bs;
		customerService = cs;
	}
	
	
	// INSERT -> TESTED
	@RequestMapping(value="/new", method = RequestMethod.POST)
	public BuildingDTO newBuilding(
			@RequestParam(value="indirizzo") String indirizzo,
			@RequestParam(value="interno") int interno,
			@RequestParam(value="city") String city,
			@RequestParam(value="username") String username,
			@RequestParam(value="cap") String cap ) {		
		
		
		BuildingDTO myNewBuilding = new BuildingDTO();
		myNewBuilding.setAddress(indirizzo);
		myNewBuilding.setInterno(interno);
		myNewBuilding.setCity(city);
		myNewBuilding.setCap(cap);
		myNewBuilding.setOwner(customerService.findByUsername(username));
		myNewBuilding = buildingService.create(myNewBuilding);
		return myNewBuilding;
	}
	
	
	
	// READ -> TESTED
	@RequestMapping(value="", method = RequestMethod.GET)
	public BuildingDTO getOne(@RequestParam(value="buildingId") long buildingId) {
		BuildingDTO bdto = buildingService.findByPrimaryKey(buildingId);
		return bdto;
		
	}
	
	// DELETE -> TESTED
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public boolean delete(@RequestParam(value="buildingId") long buildingId) {
		BuildingDTO bdto = buildingService.findByPrimaryKey(buildingId);
		if (buildingService.delete(bdto)) return true;
		return false;
		
	}
	
	
	// UPDATE -> TESTED
	@RequestMapping(value="/edit", method= RequestMethod.POST)
	public BuildingDTO updateBuilding (
			@RequestParam(value="buildingId") long buildingId,
			@RequestParam(value="indirizzo", required=false) String indirizzo,
			@RequestParam(value="interno", required=false) int interno,
			@RequestParam(value="city", required=false) String city,
			@RequestParam(value="cap", required=false) String cap ) {
		
		BuildingDTO bdto = buildingService.findByPrimaryKey(buildingId);
		
		if (indirizzo != null) {
		bdto.setAddress(indirizzo);
		}
		if (interno != 0) {
		bdto.setInterno(interno);
		}
		if (city != null) {
		bdto.setCity(city);
		}
		if (cap != null) {
		bdto.setCap(cap);
		}
		bdto = buildingService.update(bdto);
		return bdto;
	}
	
	@RequestMapping(value="/installer", method = RequestMethod.GET)
	public List<BuildingDTO> getBuildingsByInstaller(
			@RequestParam(value="installer") String installer
	) {
		List<BuildingDTO> buildings = buildingService.findByInstaller(installer);
		return buildings;
	}


	/*
	
	@RequestMapping(value = "/insertForm", method = RequestMethod.GET)
	public String insertForm(HttpServletRequest request) {
		return "InsertBuilding";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		String indirizzo = request.getParameter("indirizzo");
		String interno = request.getParameter("interno");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("cap");
		BuildingDTO mynewBuilding = new BuildingDTO();
		mynewBuilding.setAddress(indirizzo);
		mynewBuilding.setInterno(Long.parseLong(interno));
		mynewBuilding.setCity(citta);
		mynewBuilding.setCap(cap);
		System.out.println("Called create");
		this.buildingService.create(mynewBuilding, username);
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "CustomerHome";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request) {
		return "BuildingMenu";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "ReadBuilding";
	}

	@RequestMapping(value = "/updateForm", method = RequestMethod.GET)
	public String updateForm(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "UpdateBuilding";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		long buildingId = Long.parseLong(request.getParameter("idselected"));
		String newindirizzo = request.getParameter("newindirizzo");
		String newinterno = request.getParameter("newinterno");
		String newcitta = request.getParameter("newcitta");
		String newcap = request.getParameter("newcap");
		BuildingDTO newValues = new BuildingDTO();
		newValues.setId(buildingId);
		newValues.setAddress(newindirizzo);
		newValues.setInterno(Long.parseLong(newinterno));
		newValues.setCity(newcitta);
		newValues.setCap(newcap);
		this.buildingService.update(newValues, username);
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "CustomerHome";
	}

	@RequestMapping(value = "/deleteForm", method = RequestMethod.GET)
	public String deleteForm(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "DeleteBuilding";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		String username = request.getSession().getAttribute("username").toString();
		int toDelete = Integer.parseInt(request.getParameter("idselected"));
		BuildingDTO toDestroy = buildingService.findByPrimaryKey(toDelete);
		this.buildingService.delete(toDestroy);
		List<BuildingDTO> buildings = this.buildingService.getAll(username);
		request.setAttribute("buildings", buildings);
		return "CustomerHome";
	}
	
	
	
	@RequestMapping(value = "/goBack", method = RequestMethod.GET)
	public String goBack(HttpServletRequest request) {
		return "CustomerHome";
	}

	*/
}
