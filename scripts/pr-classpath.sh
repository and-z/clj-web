#!/bin/bash
lein classpath | sed -e 's/:/\'$'\n/g' | head
