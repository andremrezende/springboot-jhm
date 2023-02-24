package br.com.rezende.jhm.spring.configuration.db;

import org.springframework.util.CollectionUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {

  public static final String DB_IMAGE = "mysql/mysql-server:5.7";
  private static MySQLContainer mysqlContainer;

  public static final String ROOT = "root";
  public static final String ACCESS = "dev";

  private Set<String> pathScripts;

  private CustomMySqlContainer(DockerImageName dockerImageName) {
    super(DB_IMAGE);
  }

  public static synchronized MySQLContainer getInstance(DockerImageName dockerImageName, String initScript, String dbName) {
    if (mysqlContainer == null) {
      mysqlContainer = new MySQLContainer<>(dockerImageName).withInitScript(initScript)
              .withDatabaseName(dbName)
              .withUsername(ROOT)
              .withPassword(ACCESS);
      mysqlContainer.start();
    }
    return mysqlContainer;
  }

  @Override
  public void start() {
    // By default database container is being stopped as soon as last connection is closed. There
    // are cases when you might need to start container and keep it running till you stop it
    // explicitly or JVM is shutdown. To do this, add TC_DAEMON parameter to the URL.
    System.setProperty("TC_DAEMON", "true");
    super.start();
  }

  public CustomMySqlContainer withInitScript(Set<String> pathScripts) {
    this.pathScripts = pathScripts;
    return this;
  }

  @Override
  protected void runInitScriptIfRequired() {
    if (pathScripts == null || pathScripts.isEmpty()) {
      super.runInitScriptIfRequired();
    }

    // Sort list to guarantee proper execution
    if(!CollectionUtils.isEmpty(pathScripts)) {
      List<String> sortedScripts = new ArrayList<>(pathScripts);
      Collections.sort(sortedScripts);
      for (String pathScript : sortedScripts) {
        ScriptUtils.runInitScript(this.getDatabaseDelegate(), pathScript);
      }
    }
  }

}
