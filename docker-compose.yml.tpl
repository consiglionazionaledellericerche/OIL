version: '2'

services:

  helpdesk:
    image: docker.si.cnr.it/##{CONTAINER_ID}##
    mem_limit: 1024m
    network_mode: bridge
    labels:
    - SERVICE_NAME=##{SERVICE_NAME}##
