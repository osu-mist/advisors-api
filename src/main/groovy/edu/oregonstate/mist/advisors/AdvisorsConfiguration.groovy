package edu.oregonstate.mist.advisors

import com.fasterxml.jackson.annotation.JsonProperty
import edu.oregonstate.mist.api.Configuration
import javax.validation.Valid
import io.dropwizard.db.DataSourceFactory
import javax.validation.constraints.NotNull

class AdvisorsConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    DataSourceFactory database = new DataSourceFactory()

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        database
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory
    }
}
