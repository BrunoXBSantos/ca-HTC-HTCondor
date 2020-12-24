#!/bin/bash
tempo_corte=36
nome_video_entrada=samsung.mp4
hora=0
minuto=1
segundo=12
ffmpeg -i samsung.mp4 -ss 0:1:12 -t 36 3.mp4
