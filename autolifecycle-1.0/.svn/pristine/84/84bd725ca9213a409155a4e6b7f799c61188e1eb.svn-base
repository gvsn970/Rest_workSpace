package com.nexiilabs.autolifecycle.resources;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.nexiilabs.autolifecycle.features.FeaturesService;
import com.nexiilabs.autolifecycle.features.FeatureslistModel;

@RestController
@RequestMapping("features/csv")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class FeaturesCSVfileExport {
	@Autowired
	private FeaturesService service;
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void  buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setHeader("Content-Disposition", "attachment; filename=\"features.csv\"");
	    List<FeatureslistModel> features = service.getAllFeatures();
	    String[] header = {"feature_id","feature_name","feature_description","story_points","feature_type","status","releaseName"};
	    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
	            CsvPreference.STANDARD_PREFERENCE);
	    csvWriter.writeHeader(header);
	    for(FeatureslistModel user : features){
	       csvWriter.write(user,header);
	    }
	    csvWriter.close();
	}
}
