#!/bin/bash
# Usage: java com.example.afs.jamming.Jamming options file
# Where: options is one or more of the following:
#        --backgroundCondition=GREATER_THAN|LESS_THAN (defaults to LESS_THAN)
#        --backgroundThreshold=hex-rgb-value (defaults to 404040)
#        --colorMap=color map (defaults to DrumRgbColorMap)
#        --imageBaseFilename=name (defaults to Jamming)
#        --imageBrightness=0-100 (defaults to 30)
#        --imageCaptureProgram=path (defaults to raspistill)
#        --imageHeight=0-1944 pixels (defaults to 768)
#        --imageRotation=0-359 degrees (defaults to 90)
#        --imageHeight=0-2592 pixels (defaults to 1024)
#        --isDisplayImage=false|true (defaults to true)
#        --isPlayAudio=false|true (defaults to true)
#        --isVerbose=false|true (defaults to false)
#        --midiChannel=channel (defaults to 9)
#        --midiProgram=midi program number (defaults to 1)
#        --midiTempoFactor=0.0-1.0 (defaults to 1.0)
#        --midiTickOrigin=LEFT|MIDPOINT (defaults to MIDPOINT)
#        --midiVelocity=1-127 (defaults to 127)
#        --objectFuzziness=min pixel delta between items in successive frames (defaults to 20)
#        --objectMinimizeSize=max-speckle-size (defaults to 50)
# Supported color maps:
#        DrumRgbColorMap
#        Note2OctaveHsbColorMap
#        Note1OctaveLowHsbColorMap
#        DrumHsbColorMap
#        Note4OctaveHsbColorMap

java -jar Jamming.jar \
--backgroundCondition=LESS_THAN \
--backgroundThreshold=404040 \
--colorMap=Note4OctaveHsbColorMap \
--imageBaseFilename=Jamming \
--imageCaptureProgram=raspistill \
--imageBrightness=30 \
--imageHeight=768 \
--imageRotation=90 \
--imageWidth=1024 \
--isDisplayImage=true \
--isPlayAudio=true \
--isVerbose=true \
--midiChannel=0 \
--midiProgram=1 \
--midiTempoFactor=0.5 \
--midiTickOrigin=LEFT \
--midiVelocity=127 \
--objectFuzziness=20 \
--objectMinimumSize=50
