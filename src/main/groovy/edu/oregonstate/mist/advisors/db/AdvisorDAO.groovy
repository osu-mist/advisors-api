package edu.oregonstate.mist.advisors.db

import edu.oregonstate.mist.advisors.core.Attributes
import edu.oregonstate.mist.advisors.mapper.AdvisorMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import edu.oregonstate.mist.contrib.AbstractAdvisorDAO

@RegisterMapper(AdvisorMapper)
interface AdvisorDAO extends Closeable {

    @SqlQuery("SELECT 1 FROM dual")
    Integer checkHealth()

    @SqlQuery(AbstractAdvisorDAO.getByOnidOrOsuid)
    List<Attributes> getAdvisors(@Bind("id") String id)

    @Override
    void close()
}