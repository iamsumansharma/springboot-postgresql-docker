package com.infy.fbl.flowbasedlending.controller;

import com.infy.fbl.flowbasedlending.exception.ResourceNotFoundException;
import com.infy.fbl.flowbasedlending.model.ComplaintDetails;
import com.infy.fbl.flowbasedlending.model.DisputeReq;
import com.infy.fbl.flowbasedlending.model.DisputeStatus;
import com.infy.fbl.flowbasedlending.model.RaiseDisputeResponse;
import com.infy.fbl.flowbasedlending.service.DisputeService;
import com.infy.fbl.flowbasedlending.util.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class includes APIs related to raise dispute and dispute status requirements
 */
@RestController
@RequestMapping("/lsp/v1")
public class DisputeController {

    private static final Logger log = LoggerFactory.getLogger(DisputeController.class);

    @Autowired
    private DisputeService disputeService;

    /**
     * This API is invoked by the LSP to create a dispute in the
     * lender system for an issue that is faced by the borrower.
     *
     * @param complaintDetails
     * @return RaiseDisputeResponse
     * @throws ResourceNotFoundException
     */
    @PostMapping(path = "/raiseDispute", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RaiseDisputeResponse> raiseDispute(@RequestBody ComplaintDetails complaintDetails) throws ResourceNotFoundException {
        log.debug("******DisputeController :: Method :: RaiseDispute*******");
        try {
            complaintDetails.setLoan_application_no("Get from third party");//temporary value
            RaiseDisputeResponse response = new RaiseDisputeResponse();
            response = disputeService.raiseDispute(complaintDetails);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            log.debug("Exception :: " + e);
            throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
        }
    }


    /**
     * This API is invoked to check the status of the dispute.
     *
     * @param disputeReq
     * @return DisputeStatus
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/disputeStatus", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DisputeStatus> disputeStatus(@RequestBody DisputeReq disputeReq) throws ResourceNotFoundException {
        log.debug("******DisputeController :: Method :: DisputeStatus*******");
        try {
            DisputeStatus disputeStatus = new DisputeStatus();
            disputeStatus = disputeService.getDisputeStatus(disputeReq);

            return new ResponseEntity<>(disputeStatus, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            log.debug("Exception :: " + e);
            throw new ResourceNotFoundException(ApplicationConstants.MESSAGE);
        }
    }

}
