package team.bool.case_receiving_platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserPageService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserPageServiceImpl implements UserPageService{

    @Autowired
    private UserDao userDao;
    
    @Override
    public void updatePDF() {
        // TODO Auto-generated method stub
        
//    1. 讀取 該帳號的所有資料(User格式)
//      將 '字串'轉成'UUID'
        UUID uuid01=UUID.fromString("bdcd914c-43ce-42d3-983c-00acd5694fc4");
//      用輸入的uuid作為搜尋條件，在資料庫裡搜尋匹配資料。
        Optional<User> res = userDao.findById(uuid01);
//      判斷 res是否為空值，'是'則返回
        if(!res.isPresent()) {
            return;
        }
//      將 res 由Optional轉成User型別的 res01
        User res01 = res.get();
        System.out.println(res01);
        
//    2. (從'本機路徑')讀取 輸入的PDF檔
//        ?
        
//    3. 寫入 讀取的PDF檔(並將其 存到(伺服器的)固定的資料夾)
//    4. 更新 PDF檔的路徑(固定的資料夾/'User的UUID'.pdf)
        
    }

}
