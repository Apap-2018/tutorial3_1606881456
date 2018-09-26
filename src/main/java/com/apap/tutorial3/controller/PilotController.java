package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					@RequestParam(value = "name", required = true) String name,
					@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping(value = {"/pilot/view/license-number/{licenseNumber}"})
    public String viewLicense(@PathVariable("licenseNumber") String licenseNumber, Model model) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        if (archive == null){
            return "error";
        } else {
            model.addAttribute("pilot", archive);
        }
        return "view-pilot";
    }
	
	@RequestMapping(value = {"/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
    public String updateFlyHours(@PathVariable("licenseNumber") String licenseNumber,
                              @PathVariable("flyHour") int flyHour, Model model) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        if (archive == null) {
            return "error";
        } else {
        	String sukses = "Halaman berhasil diubah";
            model.addAttribute("message", sukses);
            archive.setFlyHour(flyHour);
            model.addAttribute("pilot", archive);
        }
        return "view-pilot";
    }
	
	@RequestMapping(value = {"/pilot/delete/id/{id}"})
    public String deleteID(@PathVariable("id") String id, Model model) {
        PilotModel pilot = null;
        List<PilotModel> listpilot = pilotService.getPilotList();
        for (int i = 0; i < listpilot.size(); i++) {
            PilotModel databaru = listpilot.get(i);
            if (databaru.getId().equals(id)) {
                pilot = databaru;
                listpilot.remove(i);
                break;
            }
        }
        if (pilot== null) {
            return "error";
        } 
        else {
        	List<PilotModel> archive = pilotService.getPilotList();
    		model.addAttribute("listPilot", archive);
    		return "remove";
        }
    }
}
