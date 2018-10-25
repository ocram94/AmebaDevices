package com.AmebaDevices.converter;

import com.AmebaDevices.dto.BuildingDTO;
import com.AmebaDevices.model.Building;

public class BuildingConverter {
	public static BuildingDTO convertToDto(Building b) {
		BuildingDTO building = new BuildingDTO();
		building.setId(b.getId());
		building.setCap(b.getCap());
		building.setCity(b.getCity());
		building.setAddress(b.getAddress());
		building.setInterno(b.getInterno());
		building.setOwner(CustomerConverter.convertToDto(b.getOwner()));
		return building;
	}
	public static Building convertToBuilding(BuildingDTO b) {
		Building building= new Building();
		building.setId(b.getId());
		building.setCap(b.getCap());
		building.setCity(b.getCity());
		building.setAddress(b.getAddress());
		building.setInterno(b.getInterno());
		building.setOwner(CustomerConverter.convertToCustomer(b.getOwner()));
		return building;
	}
}
