package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    /**
     * Creates and inserts compensation for shared employee
     * @param comp  Compensation Object
     * @return  Inserted Compensation
     */
    @Override
    public Compensation createCompensation(Compensation comp) {
        LOG.debug("Creating compensation [{}]", comp);

        compensationRepository.insert(comp);
        return comp;
    }

    /**
     * Reads compensation for shared employeeId
     * @param id    EmployeeId
     * @return  Compensation Object for shared employeeId
     */
    @Override
    public Compensation readCompensation(String id) {
        LOG.debug("Reading employee compensation with id [{}]", id);
        Compensation comp = compensationRepository.findCompensationByEmployeeEmployeeId(id);

        if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return comp;
    }
}

