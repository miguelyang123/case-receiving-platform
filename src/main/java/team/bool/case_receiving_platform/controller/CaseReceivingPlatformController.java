package team.bool.case_receiving_platform.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseReceivingPlatformController {

//	@Autowired
//	private Service service;
//
//	@PostMapping(value = "/")
//	public Res addStore(@RequestBody Req req) {
//
//		return Service.add(req);
//
//	}
	
    @PostMapping(value = "/user_page_upload")
    public void addStore() {
        
        System.out.println("user_page_upload!");
        
        return;

    }

}
