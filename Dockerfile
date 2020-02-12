FROM jboss/wildfly
COPY target/tax-calc-0.0.1-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/tax-calc.war
