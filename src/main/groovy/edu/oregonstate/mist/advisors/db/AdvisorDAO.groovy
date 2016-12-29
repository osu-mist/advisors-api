package edu.oregonstate.mist.advisors.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import java.sql.Blob
import edu.oregonstate.mist.contrib.AbstractAdvisorDAO

@RegisterMapper(AdvisorMapper)
interface AdvisorDAO extends Closeable {

    @SqlQuery("SELECT 1 FROM dual")
    Integer checkHealth()

    @Override
    void close()
}