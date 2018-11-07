package com.AmebaDevices.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmebaDevices.converter.ItemConverter;
import com.AmebaDevices.converter.RoomConverter;
import com.AmebaDevices.converter.TreeItemConverter;
import com.AmebaDevices.converter.TreeRoomConverter;
import com.AmebaDevices.dao.ItemDAO;
import com.AmebaDevices.dto.ItemDTO;
import com.AmebaDevices.dto.RoomDTO;
import com.AmebaDevices.dto.TreeItemDTO;
import com.AmebaDevices.dto.TreeRoomDTO;
import com.AmebaDevices.model.Item;
import com.AmebaDevices.model.Room;

@Service
public class ItemService {

	private ItemDAO itemEntityDao;

	@Autowired
	public ItemService(ItemDAO itemEntityDao) {
		this.itemEntityDao = itemEntityDao;
	}

	public ItemDTO insertItem(ItemDTO item) {
		return ItemConverter.convertToDto(this.itemEntityDao.save(ItemConverter.convertToItem(item)));
	}

	public boolean deleteItem(Long id) {
		Item item = itemEntityDao.findOne(id);
		 itemEntityDao.delete(item);
		 return true;
	}

	public ItemDTO updateItem(ItemDTO item) {
		return ItemConverter.convertToDto(this.itemEntityDao.save(ItemConverter.convertToItem(item)));
	}

	public ItemDTO searchItem(Long id) {
		return ItemConverter.convertToDto(this.itemEntityDao.findOne(id));
	}

	public List<ItemDTO> getAllByRoom(RoomDTO room) {
		List<Item> items = (List<Item>) itemEntityDao.findByRoom(RoomConverter.convertToRoom(room));
		List<ItemDTO> ItemPerRoom = new ArrayList<>();
		items.forEach(i -> ItemPerRoom.add(ItemConverter.convertToDto(i)));
		return ItemPerRoom;

	}
	
	public List<TreeItemDTO> getAllTreeItem(TreeRoomDTO room) {
		List<Item> items = (List<Item>) itemEntityDao.findByRoom(TreeRoomConverter.convertToRoom(room));
		List<TreeItemDTO> itemPerRoom = new ArrayList<>();
		items.forEach(item -> itemPerRoom.add(TreeItemConverter.convertToDto(item)));
		return itemPerRoom;

	}
}
