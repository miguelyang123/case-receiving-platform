package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.LoginReq;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;

	@PostMapping("login")
	public AuthRes login(@RequestBody LoginReq req, HttpSession http) {
		
		// get DB User Info
		AuthRes res = userService.login(req.getEmail(), req.getPwd());
		
		// Is already login return SUCCESSFUL
		String email = (String) http.getAttribute("email");
		if (StringUtils.hasText(email)) {
			return userService.login(req.getEmail(), req.getPwd());
		}

		// account error
		if(!res.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return res;
		}

		// save to HttpSession
		http.setAttribute("email", req.getEmail());
		http.setAttribute("password", req.getPwd());

		// set time 1 hour
		http.setMaxInactiveInterval(60 * 60);

		return res;

	}

	// for check is login
	@GetMapping("get_balance")
	public AuthRes getBalance(HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not find Session id
		if (!StringUtils.hasText(email)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}

		return userService.getBalance(email, pwd);
	}
	
	@PostMapping("logout")
	public AuthRes logout(HttpSession httpSession) {
		// Invalid Session
		httpSession.invalidate();
		return new AuthRes(AuthRtnCode.SUCCESSFUL_LOGOUT.getCode(),
				AuthRtnCode.SUCCESSFUL_LOGOUT.getMessage());
	}
	
//	@Transactional
	@PostMapping("signup")
	public AuthRes signup(@RequestBody User user,HttpSession httpSession) {
		System.out.println(user.getEmail());
		return userService.addNewUser(user);
	}
    
    // Upload a file to place into an Amazon S3 bucket.
    @RequestMapping(value = "pdf_upload", method = RequestMethod.POST)
    @ResponseBody
    public void singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("uuid") String uuid) throws IOException {
        
        try {

            byte[] bytes = file.getBytes();
            String name =  file.getOriginalFilename() ;

            System.out.println(bytes);
            System.out.println(name);
            System.out.println(uuid);
            
            File fileNew = new File("pdfs/"+uuid+".pdf");
            try {
                FileOutputStream fout
                        = new FileOutputStream(fileNew);
                fout.write(bytes);
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
////      (測試)讀檔+存檔
//        Path path = Paths.get("pdfs/"+uuid+".pdf");
//        byte[] data = Files.readAllBytes(path);
//        File fileNew = new File("pdfs/"+uuid+"(1)"+".pdf");
//        try {
//            FileOutputStream fout
//                    = new FileOutputStream(fileNew);
//            fout.write(data);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        
//        return new ModelAndView(new RedirectView("http://localhost:5173/personal_info_upload"));
        
//        return null;
    
    }

    @PostMapping("pdf_download")
    public byte[] pdfDownload(@RequestParam("uuid") String uuid) throws IOException {
        
        Path path = Paths.get("pdfs/"+uuid+".pdf");
        byte[] data = Files.readAllBytes(path);

        return data;

    }
    
}
