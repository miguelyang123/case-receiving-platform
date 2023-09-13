package team.bool.case_receiving_platform.service.impl;

import java.util.Date;

import team.bool.case_receiving_platform.service.ifs.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService{

	@Override
	public String verifyUser(String email, String password) {
		 /*
	     0000 login success
	     0001 wrong email
	     0002 wrong useracct
	     0003 wrong passwd
	     0004 acct locked
	     */
//	    Optional<User> user=userRepo.findUserByEmail(email);
//	    String result="9999";
//	    if(user.isPresent()){
//	        if(user.get().getUserAccount().equals(userAcct)){
//	            if(user.get().getUserPassword().equals(userPasswd)){
//	            	//新增了以下這段：
//	            	//設定30min過期
//	                Date expireDate = new Date(System.currentTimeMillis()+ 30 * 60 * 1000);
//	                
//	                
//	                String jwtToken = Jwts.builder()
//	                .setSubject(email) //我以email當subject
//	                .setExpiration(expireDate)
//	                //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
//	                .signWith(SignatureAlgorithm.HS512,"MySecret")
//	                .compact();
//	                //直接將JWT傳回
//	                result=jwtToken;
//	            }else{
//	                result="0003";
//	            }
//	        }else{
//	            result="0002";       
//	        }
//	    }else{
//	        result="0001";
//	    }
//	    return result;   
		return null;
	}

}
