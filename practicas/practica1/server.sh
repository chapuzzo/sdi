#!/bin/bash

java servidor u 5000 &
echo $! > udp.pid
java servidor m 5000 &
echo $! > tcp.pid
trap "kill  $(< udp.pid) $(< tcp.pid); rm udp.pid tcp.pid; echo; exit " 2
sleep 0.01
trap -p
echo "pulsa ^D o ^C para acabar"
cat
kill  $(< udp.pid) $(< tcp.pid)
rm udp.pid tcp.pid
