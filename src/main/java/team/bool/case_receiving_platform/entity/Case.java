package team.bool.case_receiving_platform.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "case")
public class Case {
	
	// 案子ID(自動產生)(PK)(AI)
	@Id
	@Column(name = "id")
	private int id;

	// 案子名稱
	@Column(name = "case_name")
	private String caseName;

	// 案子價格(固定)(最高預算)
	@Column(name = "budget")
	private int budget;

	// 案子地點(地點的流水號)
	@Column(name = "location")
	private String location;

	// 案子內文(限定顯示字數)
	@Column(name = "content")
	private String content;

	// 案子到期日
	@Column(name = "deadline")
	private LocalDateTime deadline;

	// 案子的發案日(自動產生)
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 案子的類型
	@Column(name = "case_class")
	private String caseClass;

	// 發案人(uid)
	@Column(name = "initiator")
	private String initiator;

	// 上架/下架 (是否上架 0=下架)
	@Column(name = "on_shelf")
	private String onShelf;

	
	// 建構方法
	public Case() {
		super();
	}

	public Case(int id, String caseName, int budget, String location, String content, LocalDateTime deadline,
			LocalDateTime createdDate, String caseClass, String initiator, String onShelf) {
		super();
		this.id = id;
		this.caseName = caseName;
		this.budget = budget;
		this.location = location;
		this.content = content;
		this.deadline = deadline;
		this.createdDate = createdDate;
		this.caseClass = caseClass;
		this.initiator = initiator;
		this.onShelf = onShelf;
	}
	
	
	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
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

	public String getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(String onShelf) {
		this.onShelf = onShelf;
	}

}
