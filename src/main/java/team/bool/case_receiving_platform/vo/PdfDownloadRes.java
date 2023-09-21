package team.bool.case_receiving_platform.vo;

import java.sql.Blob;

public class PdfDownloadRes {

    private int code;

    private String message;

//    private byte[] data;
    
    private Blob data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public PdfDownloadRes(int code, String message, Blob data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public PdfDownloadRes() {
        super();
        // TODO Auto-generated constructor stub
    }

}
