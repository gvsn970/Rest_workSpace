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

import com.nexiilabs.autolifecycle.releases.ReleaseListModel;
import com.nexiilabs.autolifecycle.releases.ReleaseService;

@RestController
@RequestMapping("/csv")
public class ReleaseCSVfileExport {
	@Autowired
	ReleaseService releaseService;
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/exportReleases", method = RequestMethod.GET)
	public void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"Releases.csv\"");
			List<ReleaseListModel> releases = releaseService.getAllReleases();
			String[] header = { "releaseId", "productName","productLineName", "releaseName", "releaseDateInternal", "releaseDateExternal",
					"releaseDescription", "owner", "status" };
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			csvWriter.writeHeader(header);
			for (ReleaseListModel release : releases) {
				csvWriter.write(release, header);
			}
			csvWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
