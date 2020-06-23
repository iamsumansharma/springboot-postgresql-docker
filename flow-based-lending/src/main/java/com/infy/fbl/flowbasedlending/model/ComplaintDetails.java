package com.infy.fbl.flowbasedlending.model;

import com.infy.fbl.flowbasedlending.util.complaintcode;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity class contains the details of staging_complaint_details table.
 */
@Entity
@Table(name = "staging_complaint_details")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class ComplaintDetails {
    //private static final long serialVersionUID = -2343243243242432341L;


    @Column(name = "transactionid", nullable = false)
    private String transactionId;

    @Column(name = "loan_application_no", nullable = false)
    private String loan_application_no;

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "dispute_id", nullable = false)
    private String dispute_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaintcode", nullable = false)
    @Type( type = "pgsql_enum" )
    private complaintcode complaint_Code;

    @Column(name = "complaint_message", nullable = false)
    private String complaint_message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_complaint", insertable=false)
    private Date dateOfComplaint;

    public ComplaintDetails() {
    }

    public ComplaintDetails(String transactionId, String loan_application_no, String dispute_id, String complaint_message, complaintcode complaint_Code,Date dateOfComplaint) {
        this.transactionId = transactionId;
        this.loan_application_no = loan_application_no;
        this.dispute_id = dispute_id;
        this.complaint_message = complaint_message;
        this.complaint_Code = complaint_Code;
        this.dateOfComplaint = dateOfComplaint;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLoan_application_no() {
        return loan_application_no;
    }

    public void setLoan_application_no(String loan_application_no) {
        this.loan_application_no = loan_application_no;
    }

    public String getDispute_id() {
        return dispute_id;
    }

    public void setDispute_id(String dispute_id) {
        this.dispute_id = dispute_id;
    }

    public complaintcode getComplaint_Code() {
        return complaint_Code;
    }

    public void setComplaint_Code(complaintcode complaint_Code) {
        this.complaint_Code = complaint_Code;
    }

    public String getComplaint_message() {
        return complaint_message;
    }

    public void setComplaint_message(String complaint_message) {
        this.complaint_message = complaint_message;
    }

    public Date getDateOfComplaint() {
        return dateOfComplaint;
    }

    public void setDateOfComplaint(Date dateOfComplaint) {
        this.dateOfComplaint = dateOfComplaint;
    }
}
