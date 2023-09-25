package team.bool.case_receiving_platform.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`case`")
public class Case {

	// 案子ID(自動產生)(PK)(AI)
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

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
	private Boolean onShelf = true;

	// 目前案子狀態
	@Column(name = "current_status")
	private String currentStatus = "Not Started";

	// 進度百分比
	@Column(name = "progress_percentage")
	private int progressPercentage;

	// 案子完成時間
	@Column(name = "accepted_date")
	private LocalDateTime acceptedDate;

	// 案子完成評價
	@Column(name = "case_rating")
	private int caseRating;

	// 建構方法
	public Case() {
		super();
	}

	public Case(Integer id, String caseName, int budget, String location, String content, LocalDateTime deadline,
			LocalDateTime createdDate, String caseClass, String initiator, Boolean onShelf, String currentStatus,
			int progressPercentage, LocalDateTime acceptedDate, int caseRating) {
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
		this.currentStatus = currentStatus;
		this.progressPercentage = progressPercentage;
		this.acceptedDate = acceptedDate;
		this.caseRating = caseRating;
	}

	// Getters and setters
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

	public Boolean getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(Boolean onShelf) {
		this.onShelf = onShelf;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public int getProgressPercentage() {
		return progressPercentage;
	}

	public void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}

	public LocalDateTime getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(LocalDateTime acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public int getCaseRating() {
		return caseRating;
	}

	public void setCaseRating(int caseRating) {
		this.caseRating = caseRating;
	}

}
