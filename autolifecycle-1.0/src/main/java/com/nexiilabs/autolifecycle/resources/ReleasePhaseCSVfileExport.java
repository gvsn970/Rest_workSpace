package com.nexiilabs.autolifecycle.resources;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesService;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhaseslistModel;

@RestController
@RequestMapping("releasephase/csv")
public class ReleasePhaseCSVfileExport {
	
	@Autowired
	private ReleasePhasesService service;
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void  buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setHeader("Content-Disposition", "attachment; filename=\"releasephases.csv\"");
	    List<ReleasePhaseslistModel> releasephases = service.getAllReleasePhases();
	    String[] header = {"release_phase_id","release_phase_name","release_phase_description","fk_release_phase_type","is_default"};
	    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
	            CsvPreference.STANDARD_PREFERENCE);
	    csvWriter.writeHeader(header);
	    for(ReleasePhaseslistModel user : releasephases){
	       csvWriter.write(user,header);
	    }
	    csvWriter.close();
	}

}
