#!groovy
import jenkins.model.*
import hudson.slaves.*

/**
 *  Global node properties configuration
 */

def inst = Jenkins.getInstance()
def globalNodeProperties = inst.getGlobalNodeProperties()
def envVarsNodePropertyList = globalNodeProperties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)

def newEnvVarsNodeProperty = null
def envVars = null

if (envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
    newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty()
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}

// Global properties
{% for item in jenkins_env_variables %}
    envVars.put("{{ item.name }}", "{{ item.value }}")
{% endfor %}


inst.save()