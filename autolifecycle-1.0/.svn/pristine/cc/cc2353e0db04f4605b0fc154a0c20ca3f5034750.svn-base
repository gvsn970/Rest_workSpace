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

import com.nexiilabs.autolifecycle.product.ProductListDTO;
import com.nexiilabs.autolifecycle.product.ProductService;

@RestController
@RequestMapping("product/csv")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ProductCSVfileExport {
	
	@Autowired
	private ProductService service;
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void  buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setHeader("Content-Disposition", "attachment; filename=\"product.csv\"");
	    List<ProductListDTO> products = service.allproducts();
	    String[] header = {"product_id","product_name","product_desc","product_line_name","product_line_desc"};
	    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
	            CsvPreference.STANDARD_PREFERENCE);
	    csvWriter.writeHeader(header);
	    for(ProductListDTO user : products){
	       csvWriter.write(user,header);
	    }
	    csvWriter.close();
	}

}
