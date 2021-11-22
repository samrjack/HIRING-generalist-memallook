#!/bin/sh
gradle installDist
ln -s ./memallook/build/install/memallook/bin/memallook ./mem
