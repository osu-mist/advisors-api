package edu.oregonstate.mist.advisors.mapper

import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

class AdvisorMapper implements ResultSetMapper<Integer> {

    public Integer map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        new Integer(rs.getInt("1"))
    }
}
