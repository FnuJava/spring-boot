package com.example.entity;

import java.io.Serializable;
import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

public class ForeverUserjoinDetail implements Serializable {

	private static final long serialVersionUID = 79079728869712703L;

	public static final String TABLE = "forever_userjoin_detail";

	public static final String PRIMARY_KEY = "id";

	public static final String[] columns = {"id", "user_id", "inverst_amount", "inverst_time", 
			"pre_interest_date", "real_interest_date", "update_time", "product_id", "mached_time", "loan_id", 
			"red_id", "red_amount", "interest_id", "interest_rate", "status", "type", 
			"quit_auto", "apply_quit_last", "apply_quit_time", "benjin_paid_time", "lixi_paid_time", "cur_period_no", 
			"start_period_no", "interest_months", "terminal"};

	public static final String INSERT_SQL = "insert into forever_userjoin_detail "
			+ "(`user_id`, `inverst_amount`, `inverst_time`, `pre_interest_date`, `real_interest_date`, "
			+ "`update_time`, `product_id`, `mached_time`, `loan_id`, `red_id`, "
			+ "`red_amount`, `interest_id`, `interest_rate`, `status`, `type`, "
			+ "`quit_auto`, `apply_quit_last`, `apply_quit_time`, `benjin_paid_time`, `lixi_paid_time`, "
			+ "`cur_period_no`, `start_period_no`, `interest_months`, `terminal`) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//fields
	private Integer id;
	private Integer userId;
	private Double inverstAmount;
	private Timestamp inverstTime;
	private Date preInterestDate;
	private Date realInterestDate;
	private Timestamp updateTime;
	private Integer productId;
	private Timestamp machedTime;
	private Integer loanId;
	private Integer redId;
	private Double redAmount;
	private Integer interestId;
	private Double interestRate;
	private String status;
	private String type;
	private Integer quitAuto;
	private Timestamp applyQuitLast;
	private Date applyQuitTime;
	private Timestamp benjinPaidTime;
	private Timestamp lixiPaidTime;
	private Integer curPeriodNo;
	private Integer startPeriodNo;
	private Integer interestMonths;
	private String terminal;

	//default constructor
	public ForeverUserjoinDetail() {
	}

	//getter
	public Integer getId() {
		return id;
	}
	public Integer getUserId() {
		return userId;
	}
	public Double getInverstAmount() {
		return inverstAmount;
	}
	public Timestamp getInverstTime() {
		return inverstTime;
	}
	public Date getPreInterestDate() {
		return preInterestDate;
	}
	public Date getRealInterestDate() {
		return realInterestDate;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public Integer getProductId() {
		return productId;
	}
	public Timestamp getMachedTime() {
		return machedTime;
	}
	public Integer getLoanId() {
		return loanId;
	}
	public Integer getRedId() {
		return redId;
	}
	public Double getRedAmount() {
		return redAmount;
	}
	public Integer getInterestId() {
		return interestId;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public String getStatus() {
		return status;
	}
	public String getType() {
		return type;
	}
	public Integer getQuitAuto() {
		return quitAuto;
	}
	public Timestamp getApplyQuitLast() {
		return applyQuitLast;
	}
	public Date getApplyQuitTime() {
		return applyQuitTime;
	}
	public Timestamp getBenjinPaidTime() {
		return benjinPaidTime;
	}
	public Timestamp getLixiPaidTime() {
		return lixiPaidTime;
	}
	public Integer getCurPeriodNo() {
		return curPeriodNo;
	}
	public Integer getStartPeriodNo() {
		return startPeriodNo;
	}
	public Integer getInterestMonths() {
		return interestMonths;
	}
	public String getTerminal() {
		return terminal;
	}

	//setter
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setInverstAmount(Double inverstAmount) {
		this.inverstAmount = inverstAmount;
	}
	public void setInverstTime(Timestamp inverstTime) {
		this.inverstTime = inverstTime;
	}
	public void setPreInterestDate(Date preInterestDate) {
		this.preInterestDate = preInterestDate;
	}
	public void setRealInterestDate(Date realInterestDate) {
		this.realInterestDate = realInterestDate;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void setMachedTime(Timestamp machedTime) {
		this.machedTime = machedTime;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public void setRedId(Integer redId) {
		this.redId = redId;
	}
	public void setRedAmount(Double redAmount) {
		this.redAmount = redAmount;
	}
	public void setInterestId(Integer interestId) {
		this.interestId = interestId;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setQuitAuto(Integer quitAuto) {
		this.quitAuto = quitAuto;
	}
	public void setApplyQuitLast(Timestamp applyQuitLast) {
		this.applyQuitLast = applyQuitLast;
	}
	public void setApplyQuitTime(Date applyQuitTime) {
		this.applyQuitTime = applyQuitTime;
	}
	public void setBenjinPaidTime(Timestamp benjinPaidTime) {
		this.benjinPaidTime = benjinPaidTime;
	}
	public void setLixiPaidTime(Timestamp lixiPaidTime) {
		this.lixiPaidTime = lixiPaidTime;
	}
	public void setCurPeriodNo(Integer curPeriodNo) {
		this.curPeriodNo = curPeriodNo;
	}
	public void setStartPeriodNo(Integer startPeriodNo) {
		this.startPeriodNo = startPeriodNo;
	}
	public void setInterestMonths(Integer interestMonths) {
		this.interestMonths = interestMonths;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	//equals method
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ForeverUserjoinDetail))
			return false;

		final ForeverUserjoinDetail foreverUserjoinDetail = (ForeverUserjoinDetail)other;
		if (!this.id.equals(foreverUserjoinDetail.getId()))
			return false;

		return true;
	}

	//hashCode method
	public int hashCode() {
		StringBuffer keys = new StringBuffer();
		keys.append(id).append(",");
		if (keys.length() > 0)
			keys.deleteCharAt(keys.length() - 1);
		return keys.toString().hashCode();
	}

	//toString method
	public String toString() {
		return new StringBuilder("ForeverUserjoinDetail[")
			.append("id=").append(id).append(", ")
			.append("userId=").append(userId).append(", ")
			.append("inverstAmount=").append(inverstAmount).append(", ")
			.append("inverstTime=").append(inverstTime).append(", ")
			.append("preInterestDate=").append(preInterestDate).append(", ")
			.append("realInterestDate=").append(realInterestDate).append(", ")
			.append("updateTime=").append(updateTime).append(", ")
			.append("productId=").append(productId).append(", ")
			.append("machedTime=").append(machedTime).append(", ")
			.append("loanId=").append(loanId).append(", ")
			.append("redId=").append(redId).append(", ")
			.append("redAmount=").append(redAmount).append(", ")
			.append("interestId=").append(interestId).append(", ")
			.append("interestRate=").append(interestRate).append(", ")
			.append("status=").append(status).append(", ")
			.append("type=").append(type).append(", ")
			.append("quitAuto=").append(quitAuto).append(", ")
			.append("applyQuitLast=").append(applyQuitLast).append(", ")
			.append("applyQuitTime=").append(applyQuitTime).append(", ")
			.append("benjinPaidTime=").append(benjinPaidTime).append(", ")
			.append("lixiPaidTime=").append(lixiPaidTime).append(", ")
			.append("curPeriodNo=").append(curPeriodNo).append(", ")
			.append("startPeriodNo=").append(startPeriodNo).append(", ")
			.append("interestMonths=").append(interestMonths).append(", ")
			.append("terminal=").append(terminal).append("]").toString();
	}

	//RowMapper
	public static class Mapper implements RowMapper<ForeverUserjoinDetail> {
		String s;
		public ForeverUserjoinDetail mapRow(ResultSet rs, int i) throws SQLException {
			ForeverUserjoinDetail foreverUserjoinDetail = new ForeverUserjoinDetail();
			foreverUserjoinDetail.setId(rs.getInt("id"));
			foreverUserjoinDetail.setUserId((s=rs.getString("user_id")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setInverstAmount((s=rs.getString("inverst_amount")) == null ? null : new Double(s));
			foreverUserjoinDetail.setInverstTime(rs.getTimestamp("inverst_time"));
			foreverUserjoinDetail.setPreInterestDate(rs.getDate("pre_interest_date"));
			foreverUserjoinDetail.setRealInterestDate(rs.getDate("real_interest_date"));
			foreverUserjoinDetail.setUpdateTime(rs.getTimestamp("update_time"));
			foreverUserjoinDetail.setProductId((s=rs.getString("product_id")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setMachedTime(rs.getTimestamp("mached_time"));
			foreverUserjoinDetail.setLoanId((s=rs.getString("loan_id")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setRedId((s=rs.getString("red_id")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setRedAmount((s=rs.getString("red_amount")) == null ? null : new Double(s));
			foreverUserjoinDetail.setInterestId((s=rs.getString("interest_id")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setInterestRate((s=rs.getString("interest_rate")) == null ? null : new Double(s));
			foreverUserjoinDetail.setStatus(rs.getString("status"));
			foreverUserjoinDetail.setType(rs.getString("type"));
			foreverUserjoinDetail.setQuitAuto((s=rs.getString("quit_auto")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setApplyQuitLast(rs.getTimestamp("apply_quit_last"));
			foreverUserjoinDetail.setApplyQuitTime(rs.getDate("apply_quit_time"));
			foreverUserjoinDetail.setBenjinPaidTime(rs.getTimestamp("benjin_paid_time"));
			foreverUserjoinDetail.setLixiPaidTime(rs.getTimestamp("lixi_paid_time"));
			foreverUserjoinDetail.setCurPeriodNo((s=rs.getString("cur_period_no")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setStartPeriodNo((s=rs.getString("start_period_no")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setInterestMonths((s=rs.getString("interest_months")) == null ? null : new Integer(s));
			foreverUserjoinDetail.setTerminal(rs.getString("terminal"));
			return foreverUserjoinDetail;
		}
	}

}