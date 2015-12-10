package com.nhl.bootique.cayenne.runtime;

import javax.sql.DataSource;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;
import org.apache.cayenne.java8.CayenneJava8Module;

import com.nhl.bootique.cayenne.datasource.PoolingDataSourceFactory;

public class ServerRuntimeFactory {

	private String project;
	private PoolingDataSourceFactory datasource;

	/**
	 * Creates and returns a preconfigured {@link ServerRuntimeBuilder} with
	 * project, java8 integration and a DataSource. Override to add custom
	 * modules, extra projects, etc.
	 * 
	 * @param dataSource
	 *            A {@link DataSource}, which is usually provided by Spring.
	 * @return a {@link ServerRuntimeBuilder} that can be extended in
	 *         subclasses.
	 */
	protected ServerRuntimeBuilder cayenneBuilder(DataSource dataSource) {
		String project = this.project != null ? this.project : "cayenne-project.xml";
		return ServerRuntimeBuilder.builder().addModule(new CayenneJava8Module()).addConfig(project)
				.dataSource(dataSource);
	}

	public ServerRuntime createCayenneRuntime() {
		DataSource ds = datasource.toDataSource();
		return cayenneBuilder(ds).build();
	}

	public void setProject(String project) {
		this.project = project;
	}

	public void setDatasource(PoolingDataSourceFactory datasource) {
		this.datasource = datasource;
	}
}