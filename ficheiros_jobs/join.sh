#!/bin/bash
ffmpeg -f concat -safe 0 -i list.txt -c copy samsung_720.mp4
