#!/usr/bin/env bash
gradle -b build-war.gradle clean war
ls -l build/libs/*.war
