#!/usr/bin/env bash

set -e

# Environment
export JSR308=$(cd $(dirname "$0")/../ && pwd)
export CF=${JSR308}/checker-framework
export JAVAC=${CF}/checker/bin/javac
export NRCHECKER=$(cd $(dirname "$0")/ && pwd)

# Dependencies
export CLASSPATH=${NRCHECKER}/build/classes/java/main:${NRCHECKER}/build/resources/main:\
${NRCHECKER}/build/libs/network-request-checker.jar:${NRCHECKER}/debug/*

# Command
DEBUG=""
CHECKER="org.checkerframework.checker.networkrequest.NetworkRequestChecker"

declare -a ARGS
for i in "$@" ; do
    if [[ ${i} == "-d" ]] ; then
        echo "Typecheck using debug mode. Listening at port 5005. Waiting for connection...."
        DEBUG="-J-Xdebug -J-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
        continue
    fi
    ARGS[${#ARGS[@]}]="$i"
done

cmd=""

# Note:
# -Aignorejdkastub
# -Astubs=stubs/
# -AstubDebug

if [[ "$DEBUG" == "" ]]; then
    cmd="$JAVAC -cp "${CLASSPATH}" -processor "${CHECKER}" -Astubs=stubs/ "${ARGS[@]}""
else
    cmd="$JAVAC "${DEBUG}" -cp "${CLASSPATH}" -processor "${CHECKER}" -Astubs=stubs/ "${ARGS[@]}""
fi

eval "${cmd}"
