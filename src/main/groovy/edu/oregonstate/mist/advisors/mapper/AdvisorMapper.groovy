package edu.oregonstate.mist.advisors.mapper

import edu.oregonstate.mist.advisors.core.Attributes
import edu.oregonstate.mist.contrib.AbstractAdvisorDAO
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

class AdvisorMapper implements ResultSetMapper<Attributes> {

    public Attributes map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Boolean primary = rs.getString(AbstractAdvisorDAO.mapperPrimaryColumn) == 'Y'
        new Attributes(
                lastName:           rs.getString(AbstractAdvisorDAO.mapperLastNameColumn),
                firstName:          rs.getString(AbstractAdvisorDAO.mapperFirstNameColumn),
                username:           rs.getString(AbstractAdvisorDAO.mapperLoginNameColumn),
                effectiveTermCode:  rs.getString(AbstractAdvisorDAO.mapperEffectiveTermCodeColumn),
                advisorTypeCode:    rs.getString(AbstractAdvisorDAO.mapperAdvisorTypeCodeColumn),
                primary:            primary
        )
    }
}
