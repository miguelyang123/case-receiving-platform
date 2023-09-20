package team.bool.case_receiving_platform.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.LoginReq;
import team.bool.case_receiving_platform.vo.PdfDownloadRes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Scanner;


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
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("uuid") String uuid) throws IOException {
        
        try {

            byte[] bytes = file.getBytes();
            String name =  file.getOriginalFilename() ;

//            System.out.println(bytes);
//            System.out.println(name);
//            System.out.println(uuid);
            
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
        
//        User user = new User();
//        UUID uuidNew = UUID.fromString(uuid);
//        Optional<User> res = userDao.findById(uuidNew);
////        if(!res.isPresent()) {
////            return new PdfDownloadRes(400, "error!", bytes);
////        }
//        user = res.get();
//        System.out.println("user: "+user);
//        
//        if(file==null)
//        {
//            user.setResumePdf(null);
//        }else {
//            user.setResumePdf(file.getBytes());
//        }
//        userDao.save(user);
        
        return new ModelAndView(new RedirectView("http://localhost:5173/personal_info_upload"));
    }

//    @PostMapping("pdf_download")@RequestParam("file")
//    public AuthRes pdfDownload(@RequestBody PdfDownloadReq req, HttpSession http) {
//        
//        System.out.println(req.getUuid());
//       
//        File fileNew = new File("pdfs/"+req.getUuid()+".pdf");
//        try {
//            FileInputStream fin
//                    = new FileInputStream(fileNew);
//            byte[] bytes = fin.readAllBytes();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        
//        return null;
//
//    }

    @PostMapping("pdf_download")
    public PdfDownloadRes pdfDownload(HttpServletResponse response, @RequestParam("uuid") String uuid, HttpSession http) throws IOException, SerialException, SQLException {
        
        System.out.println(uuid);
        String fileName = uuid+".pdf";
//        PDDocument in = PDDocument.load(new File("pdfs/"+uuid+".pdf"));
        byte[] pdfbytes = null;
        try {
            PDDocument in = PDDocument.load(new File("pdfs/"+uuid+".pdf"));
            pdfbytes = toByteArray(in);
            PDDocument out;
        } catch (Exception e) {
            System.out.println(e);
        }

        response.setContentType("application/x-download");
        if(System.getProperty("file.encoding").equals("GBK")) {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO-8859-1") + "\"");
        } else {
            response.setHeader("Content-Disposition", uuid);
        }
        
        OutputStream os = null;
        
        try {
            os = response.getOutputStream();
            os.write(pdfbytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                os.close();
            }
        }
        
        Scanner my_scanner = new Scanner(new BufferedReader(new FileReader("pdfs/"+uuid+".pdf"))); // you can put your file path here with file name
        Blob my_blob= null; // create a BLOB
        while(my_scanner.hasNext()) {
//           byte[] my_byte_array= my_scanner.nextLine().getBytes(); // convert your file to Byte Array
//           byte[] my_byte_array= my_scanner.nextLine().getBytes("GBK"); // convert your file to Byte Array
           byte[] my_byte_array= my_scanner.nextLine().getBytes(); // convert your file to Byte Array
           my_blob = new SerialBlob(my_byte_array); // convert Byte array to BLOB using SerialBlob() method
        }
//        byte[] pdfbytes01 = null;
//        pdfbytes01 = my_blob.getBytes(1l, (int)my_blob.length());
//        pdfbytes01 = my_blob.getBytes(0, 0);
//        File fileNew = new File("pdfs/"+uuid+"(1)"+".pdf");
//        try {
//            FileOutputStream fout
//                    = new FileOutputStream(fileNew);
//            fout.write(pdfbytes01);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        
        
        
//        File fileNew = new File("pdfs/"+uuid+".pdf");
//        byte[] bytes = null;
//        MultipartFile file = new 
//        bytes = fileNew.getBytes();
//        bytes = fileNew.
//        InputStream inputStream = fileNew.getInputStream();
//        try {
//            FileInputStream fin
//                    = new FileInputStream(fileNew);
////            bytes = fin.readAllBytes();
//            bytes = fin.readAllBytes();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        
//        User user = new User();
//        UUID uuidNew = UUID.fromString(uuid);
//        Optional<User> res = userDao.findById(uuidNew);
//        if(!res.isPresent()) {
//            return new PdfDownloadRes(400, "error!", bytes);
//        }
//        user = res.get();
//        System.out.println("user: "+user);
        

//        MultipartFile
//        File fileNew = new File("pdfs/"+uuid+".pdf");
//        byte[] bytes = fileNew.getBytes();
//        String name =  fileNew.getOriginalFilename() ;
//
//        try {
//            FileOutputStream fout
//                    = new FileOutputStream(fileNew);
//            fout.write(bytes);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        
//        byte[] testFile = new byte[1024];
//        InputStream inputStream = new ByteArrayInputStream(testFile);
//        MultipartFile file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
//        MultipartFile file = new MockMultipartFile
        
//        File file = new File(filePath+fileName);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        String[] fileNameSplit = fileName.split("\\.");
//        MultipartFile multipartFile = new MockMultipartFile(fileNameSplit[0], fileName, "application/vnd.ms-excel", fileInputStream);
        
//        return new PdfDownloadRes(200, "ya!", pdfbytes);
        return new PdfDownloadRes(200, "ya!", my_blob);

    }
    
    private static byte[] toByteArray(PDDocument pdDoc) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            pdDoc.save(out);
            pdDoc.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return out.toByteArray();
    }
    
}
