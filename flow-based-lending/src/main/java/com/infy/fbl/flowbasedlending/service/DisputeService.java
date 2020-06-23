package com.infy.fbl.flowbasedlending.service;

import com.infy.fbl.flowbasedlending.controller.DisputeController;
import com.infy.fbl.flowbasedlending.exception.ResourceNotFoundException;
import com.infy.fbl.flowbasedlending.model.*;
import com.infy.fbl.flowbasedlending.repository.ComplaintDetailsRepository;
import com.infy.fbl.flowbasedlending.repository.DisputeDetailsRepository;
import com.infy.fbl.flowbasedlending.util.ApplicationConstants;
import com.infy.fbl.flowbasedlending.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a service class which includes the logic related to DisputeManagement API.
 */
@Service
public class DisputeService {
    @Autowired
    private DisputeDetailsRepository disputeDetailsRepository;

    @Autowired
    private ComplaintDetailsRepository complaintDetailsRepository;

    private static final Logger log = LoggerFactory.getLogger(DisputeController.class);

    /**
     * @param complaintDetails
     * @return RaiseDisputeResponse
     * @throws ResourceNotFoundException
     */
    public RaiseDisputeResponse raiseDispute(ComplaintDetails complaintDetails) throws ResourceNotFoundException {
        log.debug("********DisputeService :: Method :: RaiseDispute********");
        try {
            if (complaintDetails.getTransactionId() == null) {
                throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
            } else if (complaintDetails.getComplaint_message() == null) {
                throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
            } else if (complaintDetails.getComplaint_Code() == null) {
                throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
            } else {
                log.debug("Saving the details to staging_complaint_details");
                ComplaintDetails raiseDispute = complaintDetailsRepository.save(complaintDetails);

                DisputeDetails disputeDetails = new DisputeDetails();
                disputeDetails.setTransactionId(complaintDetails.getTransactionId());
                disputeDetails.setLoan_application_no("Get from third party");
                disputeDetails.setDispute_id(complaintDetails.getDispute_id());
                disputeDetails.setComplaintCode(complaintDetails.getComplaint_Code());
                disputeDetails.setComplaint_message(complaintDetails.getComplaint_message());
                disputeDetails.setStatus(Status.NEW);
                disputeDetails.setResolution_message("Get from Resolution details"); //temporary value
                disputeDetails.setDisputeTrack_url(ApplicationConstants.DISPUTE_URL);

                log.debug("Saving the details to staging_masterdispute_details");
                disputeDetailsRepository.save(disputeDetails);

                Dispute dispute = new Dispute();
                RaiseDisputeResponse response = new RaiseDisputeResponse();
                log.debug("DisputeID :: " + raiseDispute.getDispute_id());

                dispute.setId(complaintDetails.getDispute_id());
                dispute.setStatus(Status.NEW);
                dispute.setUrl(ApplicationConstants.DISPUTE_URL);

                response.setTransactionId(complaintDetails.getTransactionId());
                response.setStatus(Status.NEW);
                response.setDispute(dispute);
                return response;
            }
        } catch (Exception e) {
            log.error("Error wile saving records to the database" + e);
            throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
        }
    }

    /**
     * @param disputeReq
     * @return disputeStatus
     * @throws ResourceNotFoundException
     */
    public DisputeStatus getDisputeStatus(DisputeReq disputeReq) throws ResourceNotFoundException {
        log.debug("********DisputeService :: Method :: getDisputeStatus********");
        String dispute_id = "";

        log.debug("TransactionID :: " + disputeReq.getTransactionId());

        try {
            if (disputeReq.getDispute().getId() == null) {
                throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
            } else if (disputeReq.getTransactionId() == null) {
                throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
            } else {
                dispute_id = disputeReq.getDispute().getId();
                log.debug("disputeID :: " + dispute_id);

                DisputeDetails disputeDetails = disputeDetailsRepository.findById(dispute_id)
                        .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.MESSAGE));

                DisputeStatus disputeStatus = new DisputeStatus();
                disputeStatus.setTransactionId(disputeDetails.getTransactionId());
                disputeStatus.setStatus(disputeDetails.getStatus());
                disputeStatus.setMessage(disputeDetails.getComplaint_message());
                log.debug("Response :: " + disputeStatus);

                return disputeStatus;
            }
        } catch (Exception e) {
            log.debug("Handled exception :: " + e);
            throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
        }
    }

}
