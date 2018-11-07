package com.AmebaDevices.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmebaDevices.converter.BuildingConverter;
import com.AmebaDevices.converter.FloorConverter;
import com.AmebaDevices.converter.TreeFloorConverter;
import com.AmebaDevices.dao.BuildingDAO;
import com.AmebaDevices.dao.FloorDAO;
import com.AmebaDevices.dao.RoomDAO;
import com.AmebaDevices.dto.FloorDTO;
import com.AmebaDevices.dto.TreeFloorDTO;
import com.AmebaDevices.model.Building;
import com.AmebaDevices.model.Floor;
import com.AmebaDevices.model.Room;

@Service
public class FloorService {
	
	private FloorDAO floordao;
	private BuildingDAO buildingdao;
	private RoomDAO roomdao;
	private RoomService roomService;
	
	@Autowired
	public FloorService(FloorDAO floorDAO, BuildingDAO buildingdao, RoomDAO roomdao, RoomService _roomService) {
		this.floordao = floorDAO;
		this.buildingdao = buildingdao;
		this.roomdao = roomdao;
		this.roomService = _roomService;
	}
	
	
	public FloorDTO insertFloor(FloorDTO f, long buildingId) {
		Building b = buildingdao.findOne(buildingId);
		f.setBuilding(BuildingConverter.convertToDto(b));
		return FloorConverter.convertToDto(floordao.save(FloorConverter.convertToFloor(f)));
	}
	
	public List <FloorDTO> getAllByBuilding (long buildingid){
		Building b = buildingdao.findOne(buildingid);
		List <Floor> floors = (List<Floor>) floordao.findByBuilding(b);
		List<FloorDTO> toReturn= new ArrayList<>();
		floors.forEach(f->toReturn.add(FloorConverter.convertToDto(f)));
		return toReturn;
	}
	
	public List<TreeFloorDTO> getTreeByBuilding(long buildingid) {
		Building b = buildingdao.findOne(buildingid);
		List <Floor> floors = (List<Floor>) floordao.findByBuilding(b);
		List<TreeFloorDTO> toReturn = new ArrayList<>();
		floors.forEach(floor -> {
			TreeFloorDTO floorDTO = TreeFloorConverter.convertToDTO(floor);
			floorDTO.setRooms(roomService.getAllTreeByFloor(floorDTO));
			toReturn.add(floorDTO);
		});
		return toReturn;
	}
	
	public boolean delete(FloorDTO f) {	
		Floor floor = FloorConverter.convertToFloor(f);
		List <Room>  rooms = roomdao.findByFloor(floor);
		for (Room room : rooms) {
			room.setFloor(null);
			roomdao.save(room);
			roomdao.delete(room);
		}
		this.floordao.delete(FloorConverter.convertToFloor(f));
		return true;
	
	}
	
	
	public FloorDTO update (FloorDTO f) {
		return FloorConverter.convertToDto(this.floordao.save(FloorConverter.convertToFloor(f)));
	}
		
	public void update (FloorDTO f, long buildingId) {
		Building b = buildingdao.findOne(buildingId);
		f.setBuilding(BuildingConverter.convertToDto(b));
		if (floordao.findOne(f.getId()) != null)
			this.floordao.save(FloorConverter.convertToFloor(f));
	}
	
	public FloorDTO findByPrimaryKey(long id) {
		return FloorConverter.convertToDto(this.floordao.findOne(id));
	}


	public void deleteById(long id) {
		this.floordao.delete(id);
		
	}


	public FloorDTO insert(FloorDTO fdto) {
		return FloorConverter.convertToDto(floordao.save(FloorConverter.convertToFloor(fdto)));
	}
	
	
	
}
