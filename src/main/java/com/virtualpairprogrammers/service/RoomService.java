package com.virtualpairprogrammers.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.virtualpairprogrammers.dao.RoomDAO;
import com.virtualpairprogrammers.model.Room;

public class RoomService {
	
private RoomDAO roomdao;

	public RoomService() {
		this.roomdao = new RoomDAO();

	}

	public List <Room> getAllByRoom (){
		return roomdao.getAllByRoom();

	}

	public void delete(int id) {
		roomdao.delete(id);

	}
	
	public Room searchRoom(int id) {
		return roomdao.searchRoom(id);
	}

	public void update (Room f) {
		roomdao.update(f);

	}

	

	public boolean insertRoom(Room f) {
		// TODO Auto-generated method stub
		return this.roomdao.insertRoom(f);
	}
	

}


