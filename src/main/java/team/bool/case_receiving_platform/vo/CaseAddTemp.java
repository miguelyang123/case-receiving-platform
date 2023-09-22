package team.bool.case_receiving_platform.vo;

import java.time.LocalDateTime;

public class CaseAddTemp {

    // 案子ID(自動產生)(PK)(AI)
    private Integer id;

    // 案子名稱
    private String caseName;

    // 案子價格(固定)(最高預算)
    private int budget;

    // 案子地點(地點的流水號)
    private String location;

    // 案子內文(限定顯示字數)
    private String content;

    // 案子到期日
    private String deadline;

    // 案子的發案日(自動產生)
    private LocalDateTime createdDate;

    // 案子的類型
    private String caseClass;

    // 發案人(uid)
    private String initiator;

    // 上架/下架 (是否上架 0=下架)
    private Boolean onShelf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCaseClass() {
        return caseClass;
    }

    public void setCaseClass(String caseClass) {
        this.caseClass = caseClass;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Boolean getOnShelf() {
        return onShelf;
    }

    public void setOnShelf(Boolean onShelf) {
        this.onShelf = onShelf;
    }

    public CaseAddTemp() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
}
