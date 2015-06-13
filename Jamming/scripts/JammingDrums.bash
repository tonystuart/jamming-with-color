# Usage: java com.example.afs.jamming.Jamming options file
# Where: options is one or more of the following:
#        --audio=false|true (defaults to true)
#        --backgroundCondition=GREATER_THAN|LESS_THAN (defaults to LESS_THAN)
#        --backgroundThreshold=hex-rgb-value (defaults to 404040)
#        --channel=channel (defaults to 9
#        --colorMap=color map (defaults to DrumRgbColorMap
#        --image=false|true (defaults to true)
#        --loopDelay=delay-milliseconds, zero to disable looping (defaults to 500)
#        --minimizeSize=max-speckle-size (defaults to 50)
#        --program=midi program number (defaults to 1)
#        --tempoFactor=0.0-1.0 (defaults to 1.0)
#        --tickOrigin=LEFT|MIDPOINT (defaults to MIDPOINT)
#        --velocity=1-127 (defaults to 127)
#        --verbose=false|true (defaults to false)
# Supported color maps:
#        DrumRgbColorMap
#        DrumHsbColorMap
#        NoteHsbColorMap

java -jar Jamming.jar \
	--audio=true \
	--backgroundCondition=LESS_THAN \
	--backgroundThreshold=404040 \
	--channel=9 \
	--colorMap=DrumHsbColorMap \
	--image=true \
	--loopDelay=500 \
	--minimumSize=20 \
	--program=1 \
	--tempoFactor=1.0 \
	--tickOrigin=MIDPOINT \
	--velocity=127 \
	--verbose=true \
	Jamming.jpg
