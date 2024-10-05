package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Measurement
import org.springframework.data.jpa.repository.JpaRepository

interface MeasurementRepository : JpaRepository<Measurement, Int>