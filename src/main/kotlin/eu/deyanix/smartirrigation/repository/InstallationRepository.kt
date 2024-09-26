package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Installation
import org.springframework.data.jpa.repository.JpaRepository

interface InstallationRepository : JpaRepository<Installation, Int>
