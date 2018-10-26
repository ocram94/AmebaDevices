package com.AmebaDevices.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.AmebaDevices.model.Building;
import com.AmebaDevices.model.Floor;
import com.AmebaDevices.model.Item;
import com.AmebaDevices.model.Room;
import com.AmebaDevices.services.ItemService;
import com.AmebaDevices.services.RoomService;
import com.AmebaDevices.utils.GestoreEccezioni;

@Controller
@RequestMapping("/Item")
public class ItemController  {
	private ItemService itemService;
	private RoomService roomService;

	@Autowired
	public ItemController(ItemService itemService, RoomService roomService) {
		this.itemService =itemService; 
		this.roomService=roomService;
	}

	@RequestMapping(value = "insertForm", method = RequestMethod.GET)
	public String insertForm(HttpServletRequest request) {
		String id = (String) request.getAttribute("roomId");
		return "CensimentoItemForm";
	}

	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(HttpServletRequest request) {
		String consumoEnergetico = request.getParameter("consumoEnergetico");
		String seriale = request.getParameter("seriale");
		String id = (String) request.getParameter("roomId");
		Room room= roomService.findByPrimaryKey(Long.parseLong(id));
		Item item = new Item();
		item.setConsumoEnergetico(consumoEnergetico);
		item.setSeriale(seriale);
		item.setRoom(room);
		itemService.insertItem(item);
		request.setAttribute("roomId", id);
		List <Item> listaPerRoom = new ArrayList<>();
		listaPerRoom = itemService.getAllByRoom(room);
		request.setAttribute("items", listaPerRoom);
		return "ItemMenu";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request) {
		long roomId=Long.parseLong(request.getParameter("roomId"));
		Room room= roomService.findByPrimaryKey(roomId);
		request.setAttribute("roomId", String.valueOf(roomId));
		List<Item> listaPerRoom= new ArrayList<>();
		listaPerRoom= itemService.getAllByRoom(room); 
		request.setAttribute("items", listaPerRoom);
		return "ItemMenu";
	}


	@RequestMapping(value = "/updateForm", method = RequestMethod.GET)
	public String updateForm(HttpServletRequest request) {
		long roomId=Long.parseLong(request.getParameter("roomId"));
		request.setAttribute("roomId", String.valueOf(roomId));
		return "ModificaItemForm";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request) {
		Long id = (long) Integer.parseInt(request.getParameter("id"));
		String consumoEnergetico = request.getParameter("consumoEnergetico");
		String seriale = request.getParameter("seriale");
		String roomdiD= request.getParameter("roomId");
		Room room= roomService.findByPrimaryKey(Long.parseLong(roomdiD));

		Item item = new Item();
		item.setId(id);
		item.setConsumoEnergetico(consumoEnergetico);
		item.setSeriale(seriale);
		item.setRoom(room);
		itemService.updateItem(item);
		List<Item> listaPerRoom= new ArrayList<>();
		listaPerRoom= itemService.getAllByRoom(room); 
		request.setAttribute("items", listaPerRoom);

		//ItemType updatedItem = itemTypeService.searchItemType(id);
		//request.setAttribute("item", updatedItem);
		return "ItemMenu";
	}

	@RequestMapping(value = "/deleteForm", method = RequestMethod.GET)
	public String deleteForm(HttpServletRequest request) {
		long roomId=Long.parseLong(request.getParameter("roomId"));
		request.setAttribute("roomId", String.valueOf(roomId));
		return "EliminaItemForm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		String deleteId = request.getParameter("id");
		long roomId = Long.parseLong(request.getParameter("roomId"));
		Room room = roomService.findByPrimaryKey(roomId);
		
		itemService.deleteItem((long) Integer.parseInt(deleteId));
		List<Item> listaPerRoom= new ArrayList<>();
		listaPerRoom= itemService.getAllByRoom(room); 
		request.setAttribute("items", listaPerRoom);
		return "ItemMenu";
	}

	@RequestMapping(value = "/goBack", method = RequestMethod.GET)
	public String goBack(HttpServletRequest request) {
		return "ItemMenu";
	}
}
