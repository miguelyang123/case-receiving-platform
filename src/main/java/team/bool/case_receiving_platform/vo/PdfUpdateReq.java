package team.bool.case_receiving_platform.vo;

import java.io.File;
import java.util.UUID;

public class PdfUpdateReq {

    private UUID uuid;
    
    private File pdf;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public File getPdf() {
        return pdf;
    }

    public void setPdf(File pdf) {
        this.pdf = pdf;
    }
}
