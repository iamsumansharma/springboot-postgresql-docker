package com.infy.fbl.flowbasedlending.model;

import com.infy.fbl.flowbasedlending.util.Status;

/**
 * This class includes the parameters corresponding to response at the time of raise dispute
 */
public class RaiseDisputeResponse {
    private String transactionId;
    private Status status;
    private Dispute dispute;

    public RaiseDisputeResponse() {
    }

    public RaiseDisputeResponse(String transactionId,Status status,Dispute dispute ) {
        this.transactionId = transactionId;
        this.status = status;
        this.dispute = dispute;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Dispute getDispute() {
        return dispute;
    }

    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
}
