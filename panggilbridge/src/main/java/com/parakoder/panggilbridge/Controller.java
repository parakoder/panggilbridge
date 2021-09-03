package com.parakoder.panggilbridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.parakoder.panggilbridge.dao.AntrianPanggil;
import com.parakoder.panggilbridge.model.GetPanggil;
@PropertySource({
    "file:src/main/resources/antrianbridge_application.properties" 
})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {
	@Autowired
    Environment env;
	@Autowired
	private AntrianPanggil antrianPanggil;
	
	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		logger.info("test");
		return "OK";
	}
	@RequestMapping(value = "/v1/api/antrian/call-offline", method = RequestMethod.GET)
	public GetPanggil getPanggilOffline(@RequestParam(name = "idPelayanan") Integer idPelayanan) {
		logger.info("get panggil");
		GetPanggil getPanggil = new GetPanggil();
		getPanggil.setStatus(200);
		getPanggil.setMessage("Success");
		getPanggil.setPanggil(false);
		getPanggil.setData(null);
		Integer status = antrianPanggil.cekStatus();
		if(status == null) {
			//ganti status
			antrianPanggil.update(idPelayanan, true);
			logger.info("panggil "+idPelayanan);
		}else {
			//tidak ganti status
			logger.info("not panggil");
		}
		return getPanggil;
	}
	@RequestMapping(value = "/v1/api/antrian/call", method = RequestMethod.GET)
	public GetPanggil getPanggil(@RequestParam(name = "idPelayanan") Integer idPelayanan) {
		logger.info("get panggil");
		GetPanggil getPanggil = new GetPanggil();
		getPanggil.setStatus(200);
		getPanggil.setMessage("Success");
		getPanggil.setPanggil(false);
		getPanggil.setData(null);
		Integer status = antrianPanggil.cekStatus();
		if(status ==null) {
			//ganti status
			antrianPanggil.update((idPelayanan+7), true);
			logger.info("panggil "+(idPelayanan+7));
		}else {
			//tidak ganti status
			logger.info("not panggil");
		}
		return getPanggil;
	}
	@RequestMapping(value = "/v1/api/antrian/call-display", method = RequestMethod.GET)
	public GetPanggil actPanggil() {
		Integer status = antrianPanggil.cekStatus();
		if(status != null) {
			//action panggil
			logger.info("act panggil "+status);
			antrianPanggil.update(status, false);
			if(status>7) {
				//online
				RestTemplate restTemplate = new RestTemplate();
				String fooResourceUrloffline = env.getProperty("API_DASHBOARD")+"/v1/api/antrian/call?idPelayanan="+(status-7);
				GetPanggil getPanggil = restTemplate.getForObject(fooResourceUrloffline, GetPanggil.class);
				return getPanggil;
			}else {
				//offline
				RestTemplate restTemplate = new RestTemplate();
				String fooResourceUrloffline = env.getProperty("API_DASHBOARD")+"/v1/api/antrian/call-offline?idPelayanan="+status;
				GetPanggil getPanggil = restTemplate.getForObject(fooResourceUrloffline, GetPanggil.class);
				return getPanggil;
			}
		}else {
			logger.info("not act panggil");
			GetPanggil getPanggil = new GetPanggil();
			getPanggil.setStatus(200);
			getPanggil.setMessage("Success");
			getPanggil.setPanggil(false);
			getPanggil.setData(null);
			
			return getPanggil;
		}
		
	}
}
