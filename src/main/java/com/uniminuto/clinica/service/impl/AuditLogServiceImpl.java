package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.AuditLog;
import com.uniminuto.clinica.repository.AuditLogRepository;
import com.uniminuto.clinica.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // ESTE MÉTODO ES EL QUE GUARDA LOS LOGS
    // Se agrega Propagation.REQUIRES_NEW para que siempre use una nueva transacción
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AuditLog saveLog(AuditLog log) {
        return auditLogRepository.save(log);
    }

    @Override
    public Page<AuditLog> getAuditLogs(int page, int size, String username, String eventType, LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

        Specification<AuditLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null && !username.isEmpty()) predicates.add(cb.equal(root.get("username"), username));
            if (eventType != null && !eventType.isEmpty()) predicates.add(cb.equal(root.get("eventType"), eventType));
            if (startDate != null) predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), startDate.atStartOfDay()));
            if (endDate != null) predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), endDate.atTime(23, 59, 59)));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return auditLogRepository.findAll(spec, pageable);
    }
}
