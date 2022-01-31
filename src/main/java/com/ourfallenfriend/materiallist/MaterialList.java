package com.ourfallenfriend.materiallist;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.*;

public class MaterialList {
    private final Hashtable<Material, ArrayList<Location>> materials = new Hashtable<>();

    public Hashtable<Material, ArrayList<Location>> getMaterials() {
        return this.materials;
    }

    public void addBlock(Block block) {
        if(blockExists(block)) return;
        if(materialExists(block.getType())) {
            getBlockLocations(block.getType()).add(block.getLocation());
        }else {
            getMaterials().put(block.getType(), new ArrayList<>(List.of(block.getLocation())));
        }
    }

    public void removeBlock(Block block) {
        if(!(blockExists(block))) return;
        materials.get(block.getType()).remove(block.getLocation());
        if(getBlockLocations(block.getType()).size() < 1) {
            getMaterials().remove(block.getType());
        }
    }

    private boolean materialExists(Material material) {
        return getMaterials().containsKey(material);
    }

    private boolean blockExists(Block block) {
        if(materialExists(block.getType())) {
            return getMaterials().get(block.getType()).contains(block.getLocation());
        }
        return false;
    }

    public ArrayList<Location> getBlockLocations(Material material) {
        if(!(getMaterials().containsKey(material))) {
            return null;
        }
        return getMaterials().get(material);
    }

    public String toJson() {
        Hashtable<Material, Integer> jsonFriendlyMaterials = new Hashtable<>();

        for(Map.Entry<Material, ArrayList<Location>> entry : getMaterials().entrySet()) {
            int count = entry.getValue().size();
            jsonFriendlyMaterials.put(entry.getKey(), count);
        }

        Gson gson = new Gson();
        return gson.toJson(jsonFriendlyMaterials);
    }
}
