#!/bin/bash
set -eux

lein with-profile "+$1" classpath | sed -e 's/:/\'$'\n/g' | head
