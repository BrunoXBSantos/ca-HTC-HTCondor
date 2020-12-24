#!/bin/bash
tempo_corte=36
nome_video_entrada=samsung.mp4
hora=0
minuto=0
segundo=36
ffmpeg -i samsung.mp4 -ss 0:0:36 -t 36 2.mp4
