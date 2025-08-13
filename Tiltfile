custom_build(
  ref = 'edge-service',
  command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
  deps = ['build.gradle.kts', 'src']
)

k8s_yaml(['k8s/deployment.yaml', 'k8s/service.yaml'])

k8s_resource('edge-service', port_forwards=['9000'])
