package com.virtualpairprogrammers.model;


import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;

import com.virtualpairprogrammers.service.FloorService;
import com.virtualpairprogrammers.service.ItemService;



public class Building implements Buildable {
	
	private int buildingId;
	private String indirizzo;
	private String citta;
	private String cap;
	private String interno;
	
	// in futuro all'interno di questa classe si prevede di inserire la lista di item
	// associati al building stesso
	
	
	public int getBuildingId() {
		return buildingId;
	}
	
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public Building(int buildingId, String indirizzo, String citta, String cap,String interno) {
		this.buildingId = buildingId;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.cap = cap;
		this.interno=interno;
	}
	public Building(String indirizzo, String citta, String cap, String interno) {
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.cap = cap;
		this.interno=interno;
	}
	public Building() {
	}
	
	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getInterno() {
		// TODO Auto-generated method stub
		return this.interno;
	}

	@Override
	public String getId() {
		return String.valueOf(this.buildingId);
	}

	@Override
	public Element getElement() {
		Element toReturn =new Element(this.getClass().getSimpleName().toLowerCase());
		toReturn.setAttribute(new Attribute("id", this.getId()));
		toReturn.addContent(new Element("indirizzo").setText(this.getIndirizzo()));
		toReturn.addContent(new Element("interno").setText(this.getInterno()));
		toReturn.addContent(new Element("citta").setText(this.getCitta()));
		toReturn.addContent(new Element("cap").setText(this.getCap()));
		Element floorsFather = new Element("floors");
		Element floorsElement;
		FloorService fs = new FloorService();
		if (this.buildingId != 0) {
		List <Floor> floors = fs.getAllByBuilding(this.buildingId);
		for (int i=0 ; i < floors.size(); i++) {
			floorsElement = floors.get(i).getElement();
			floorsFather.addContent(floorsElement);
		} 
		
		toReturn.addContent(floorsFather);
		}
		return toReturn;
		
	}
	
	
}
