#!/bin/bash
tempo_corte=36
nome_video_entrada=samsung.mp4
hora=0
minuto=0
segundo=0
ffmpeg -i samsung.mp4 -ss 0:0:0 -t 36 1.mp4
