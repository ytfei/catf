#!/bin/bash

### Function definitions START

function usage {
    echo "usage: catf -s <device-sid> -m <module>"
}

function run_test {
local cmd="${adb_cmd} shell am instrument -w -e notAnnotation com.seven.asimov.it.annotation.Ignore -e package ${module_id} com.seven.asimov.it/com.seven.asimov.it.IntegrationTestRunnerGa"

echo "Start to test"
echo ${cmd}
`${cmd}`

local date_id=`date +%Y%m%d_%H%M%S`
local pack=`echo ${module_id} | awk -F '.' '{ print $NF }'`
cmd="${adb_cmd} pull /sdcard/OCIntegrationTestsResults ./OCIntegrationTestsResults_${pack}_${date_id}"

echo "Start to archive test result"
echo ${cmd}
`${cmd}`
}

### Function definitions END

############################################################3##################

# global variables
device_sid=""
module_id=""

while getopts :s:m: opt
do
    case $opt in
    s)
    device_sid=$OPTARG
    ;;

    m)
    module_id=$OPTARG
    ;;

    *) ;;
    esac
done

if [ X${device_sid} != X ]
then
    adb_cmd="adb -s ${device_sid} "
    echo "Device serial id is: ${device_sid}"
else
    adb_cmd="adb "

    device_num=`adb devices | grep 'device' | wc -l`
    if [ ${device_num} -gt 2 ]
    then
        echo "There are more than one device connected, please specify the device sid!"
        usage
        exit 1
    fi
fi

if [ X${module_id} != X ]
then
    echo "CATF module: ${module_id}"
else
    echo "module id cannot be empty!"
    usage
    exit 1
fi

run_test
