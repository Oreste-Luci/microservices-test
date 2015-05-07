#!/usr/bin/env bash
java -jar target/mstest_b-1.0-SNAPSHOT.jar --spring.profiles.active=local \
    --eureka.appinfo.replicate.interval=1 \
    --eureka.appinfo.initial.replicate.time=1
