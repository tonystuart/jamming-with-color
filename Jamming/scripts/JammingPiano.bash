#!/bin/bash
set -x
# Usage: java com.example.afs.jamming.Jamming options file
# Where: options is one or more of the following:
#        --backgroundCondition=GREATER_THAN|LESS_THAN (defaults to LESS_THAN)
#        --backgroundThreshold=hex-rgb-value (defaults to 404040)
#        --colorMap=color map (defaults to Chord7ColorHsbColorMap)
#        --imageBaseFilename=name (defaults to Jamming)
#        --imageBrightness=0-100 (defaults to 40)
#        --imageCaptureProgram=path (defaults to raspistill)
#        --imageHeight=0-1944 pixels (defaults to 768)
#        --imageRotation=0-359 degrees (defaults to 90)
#        --imageWhiteBalance=auto|cloud|flash|fluorescent|horizon|incandescent|off|shade|sun|tungsten (defaults to auto)
#        --imageWhiteBalanceGain=red,blue (defaults to 1.0,1.2, only used with imageWhiteBalance=off)
#        --imageWidth=0-2592 pixels (defaults to 1024)
#        --isDisplayImage=false|true (defaults to true)
#        --isPlayAudio=false|true (defaults to true)
#        --isVerbose=false|true (clears/sets all trace flags, defaults to false)
#        --midiChannel=channel (defaults to 9)
#        --midiProgram=midi program number (defaults to 1)
#        --midiTempoFactor=0.0-1.0 (defaults to 1.0)
#        --midiTickOrigin=LEFT|MIDPOINT (defaults to MIDPOINT)
#        --midiVelocity=1-127 (defaults to 127)
#        --objectFuzziness=min pixel delta between items in successive frames (defaults to 10)
#        --objectMinimizeSize=max-speckle-size (defaults to 30)
#        --rowSpacing=pixels between rows (defaults to 0, zero to disable multiple rows)
#        --threads=image-processing-threads (defaults to 0, zero to default to number of processors)
#        --trace=CALIBRATE|COMPARISON|CONVERSION|DESPECKLING|INPUT|MAPPING|OUTPUT|PERFORMANCE (may be specified more than once, defaults to [CALIBRATE, MAPPING, PERFORMANCE])
# Supported color maps:
#        Chord7ColorHsbColorMap
#        DrumRgbColorMap
#        Note2OctaveHsbColorMap
#        Note1OctaveLowHsbColorMap
#        Chord7ColorRgbColorMap
#        DrumHsbColorMap
#        Note4OctaveHsbColorMap

java -jar Jamming.jar \
--backgroundCondition=LESS_THAN \
--backgroundThreshold=404040 \
--colorMap=Chord7ColorHsbColorMap \
--imageBaseFilename=Jamming \
--imageCaptureProgram=raspistill \
--imageBrightness=50 \
--imageHeight=768 \
--imageRotation=90 \
--imageWhiteBalance=auto \
--imageWhiteBalanceGain=1.0,1.0 \
--imageWidth=1024 \
--isDisplayImage=true \
--isPlayAudio=true \
--midiBaseVelocity=127 \
--midiChannel=0 \
--midiProgram=17 \
--midiTempoFactor=1.5 \
--midiTickOrigin=LEFT \
--objectFuzziness=10 \
--objectMinimumSize=10 \
--rowSpacing=20 \
--threads=3
